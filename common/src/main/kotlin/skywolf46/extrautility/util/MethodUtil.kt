package skywolf46.extrautility.util

import skywolf46.extrautility.ExtraUtilityCore
import java.lang.reflect.Method
import java.lang.reflect.Modifier
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.jvm.kotlinFunction

object MethodUtil {
    fun filter(vararg cls: Class<*>): MethodFilter {
        val methods = mutableListOf<MethodWrapper>()
        for (x in cls)
            findMethods(null, x, methods)
        return MethodFilter(methods)
    }

    private fun findMethods(instance: Any?, cls: Class<*>, methodList: MutableList<MethodWrapper>) {
        try {
            try {
                if (cls.kotlin.companionObjectInstance != null) {
                    forceFindMethods(
                        cls.kotlin.companionObjectInstance,
                        cls.kotlin.companionObjectInstance!!.javaClass,
                        methodList
                    )
                }

                if (cls.kotlin.objectInstance != null) {
                    forceFindMethods(
                        cls.kotlin.objectInstance,
                        cls.kotlin.objectInstance!!.javaClass,
                        methodList
                    )
                    return
                }
            } catch (e: UnsupportedOperationException) {
                // Ignored.
            } catch (e: Exception) {
                ExtraUtilityCore.logger.severe("Cannot parse ${cls.javaClass.name} with kotlin reflection : ${e.javaClass.name} (${e.message})")
            }
            for (x in cls.declaredMethods) {
                methodList += MethodWrapper(x, instance)
            }
        } catch (e: Throwable) {
            ExtraUtilityCore.logger.severe("Cannot parse ${cls.javaClass.name} : ${e.javaClass.name} (${e.message})")
        }
    }

    private fun forceFindMethods(instance: Any?, cls: Class<*>, methodList: MutableList<MethodWrapper>) {
        for (x in cls.declaredMethods) {
            methodList += MethodWrapper(x, instance)
        }
    }

    @Suppress("MemberVisibilityCanBePrivate")
    class MethodFilter(methods: List<MethodWrapper>) {
        val methods = methods.distinct()
            get() = ArrayList(field)

        fun filter(onDropped: MethodWrapper.() -> Unit, vararg condition: ReflectionMethodFilter): MethodFilter {
            val targets = mutableListOf<MethodWrapper>()
            main@ for (x in methods) {
                for (cond in condition) {
                    if (!cond.filter(x.method, x.instance)) {
                        onDropped(x)
                        continue@main
                    }
                }
                targets += x
            }
            return MethodFilter(targets)
        }

        fun filter(vararg condition: ReflectionMethodFilter): MethodFilter = filter({}, *condition)

        fun filter(annotation: Class<out Annotation>): MethodFilter {
            return filter(true, annotation)
        }

        fun filter(requireAllAnnotation: Boolean, vararg annotation: Class<out Annotation>): MethodFilter {
            val targets = mutableListOf<MethodWrapper>()
            main@ for (x in methods) {
                var accepted = false
                for (an in annotation) {
                    if (x.method.getDeclaredAnnotation(an) != null) {
                        accepted = true
                        if (!requireAllAnnotation) {
                            break
                        }
                    } else if (requireAllAnnotation) {
                        continue@main
                    }
                }
                if (accepted)
                    targets += x
            }
            return MethodFilter(targets)
        }

        fun <X : Annotation> filter(
            filterAction: X.() -> Boolean,
            target: Class<X>,
        ): MethodFilter {
            val targets = mutableListOf<MethodWrapper>()
            main@ for (x in methods) {
                val annotation = x.method.getDeclaredAnnotation(target)
                if (annotation != null && filterAction(annotation)) {
                    targets += x
                }
            }
            return MethodFilter(targets)
        }

        fun unlockAll(): MethodFilter {
            for (x in this.methods)
                try {
                    x.method.isAccessible = true
                } catch (e: Exception) {
                    ExtraUtilityCore.logger.finer("Failed to unlock method ${x.method.name} : ${e.javaClass.name} (${e.message})")
                }
            return this
        }
    }


    enum class ReflectionMethodFilter(
        private val negative: () -> ReflectionMethodFilter,
        private val methodFilter: Method.(Any?) -> Boolean,
    ) {
        /*
            Accept static method.
         */
        STATIC({ NON_STATIC }, {
            modifiers.and(Modifier.STATIC) != 0
        }),

        /*
            Accept non-static method.
         */
        NON_STATIC({ STATIC }, {
            modifiers.and(Modifier.STATIC) == 0
        }),

        /*
            Accept synthetic method.
         */
        SYNTHETIC({ NON_SYNTHETIC }, {
            isSynthetic
        }),

        /*
            Accept non-synthetic method.
         */
        NON_SYNTHETIC({ SYNTHETIC }, {
            !isSynthetic
        }),

        /*
            Accept private method.
         */
        ACCESSOR_PRIVATE({ ACCESSOR_NON_PRIVATE },
            {
                modifiers.and(Modifier.PRIVATE) != 0
            }),

        /*
            Accept non-private method.
         */
        ACCESSOR_NON_PRIVATE({ ACCESSOR_PRIVATE },
            {
                modifiers.and(Modifier.PRIVATE) == 0
            }),

        /*
            Accept protected method.
         */
        ACCESSOR_PROTECTED({ ACCESSOR_NON_PROTECTED },
            {
                modifiers.and(Modifier.PROTECTED) != 0
            }),

        /*
            Accept non-protected method.
         */
        ACCESSOR_NON_PROTECTED({ ACCESSOR_PROTECTED },
            {
                modifiers.and(Modifier.PROTECTED) == 0
            }),

        /*
            Accept public method.
         */
        ACCESSOR_PUBLIC({ ACCESSOR_NON_PUBLIC },
            {
                modifiers.and(Modifier.PUBLIC) != 0
            }),

        /*
            Accept public method.
         */
        ACCESSOR_NON_PUBLIC({ ACCESSOR_PUBLIC },
            {
                modifiers.and(Modifier.PUBLIC) == 0
            }),

        /*
            Accept kotlin internal method.
         */
        ACCESSOR_INTERNAL({ ACCESSOR_NON_INTERNAL },
            {
                (kotlinFunction?.visibility?.equals(kotlin.reflect.KVisibility.INTERNAL) == true)
            }),

        /*
            Accept non-kotlin internal method.
         */
        ACCESSOR_NON_INTERNAL({ ACCESSOR_INTERNAL },
            {
                (kotlinFunction?.visibility?.equals(kotlin.reflect.KVisibility.INTERNAL) == false)
            }),

        /*
            Accept instance required method.
         */
        INSTANCE_REQUIRED(
            { INSTANCE_NOT_REQUIRED }, {
                !Modifier.isStatic(modifiers) && it == null
            }
        ),

        /*
            Accept instance not required method like static, or kotlin object, kotlin companion.
         */
        INSTANCE_NOT_REQUIRED(
            { INSTANCE_REQUIRED }, {
                Modifier.isStatic(modifiers) || it != null
            }
        );

        fun negative() = this.negative.invoke()

        fun filter(mtd: Method, instance: Any?): Boolean = methodFilter.invoke(mtd, instance)
    }
}