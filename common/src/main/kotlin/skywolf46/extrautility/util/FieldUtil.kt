package skywolf46.extrautility.util

import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.jvm.kotlinProperty

object FieldUtil {
    private var cache: FieldFilter? = null

    fun getCache() = cache ?: updateAndGetCache()

    fun updateAndGetCache() = kotlin.run {
        cache = ClassUtil.getCache().toFieldFilter()
        return@run cache!!
    }

    fun filter(vararg cls: Class<*>): FieldFilter {
        val fields = mutableListOf<FieldWrapper>()
        for (x in cls) {
            findfields(null, x, fields)
        }
        return FieldFilter(fields)
    }

    fun filter(cls: List<Class<*>>): FieldFilter {
        val fields = mutableListOf<FieldWrapper>()
        for (x in cls) {
            findfields(null, x, fields)
        }
        return FieldFilter(fields)
    }

    private fun findfields(instance: Any?, cls: Class<*>, fieldList: MutableList<FieldWrapper>) {
        try {
            try {
                if (cls.kotlin.companionObjectInstance != null) {
                    forceFindFields(
                        cls.kotlin.companionObjectInstance,
                        cls.kotlin.companionObjectInstance!!.javaClass,
                        fieldList
                    )
                }
                if (cls.kotlin.objectInstance != null) {
                    forceFindFields(
                        cls.kotlin.objectInstance,
                        cls.kotlin.objectInstance!!.javaClass,
                        fieldList
                    )
                    return
                }
            } catch (e: UnsupportedOperationException) {
                // Ignored.
            } catch (e: Exception) {
//                System.err.println("Cannot parse ${cls.javaClass.name} with kotlin reflection : ${e.javaClass.name} (${e.message})")
            }
            for (x in cls.declaredFields) {
                fieldList += FieldWrapper(x, instance)
            }
        } catch (e: Throwable) {
//            ExtraUtilityCore.logger.severe("Cannot parse ${cls.javaClass.name} : ${e.javaClass.name} (${e.message})")
        }
    }

    private fun forceFindFields(instance: Any?, cls: Class<*>, fieldList: MutableList<FieldWrapper>) {
        for (x in cls.declaredFields) {
            fieldList += FieldWrapper(x, instance)
        }
    }


    class FieldFilter(val fields: MutableList<FieldWrapper>) {
        fun unlockAll() {
            fields.forEach {
                it.field.isAccessible = true
            }
        }


        fun filter(onDropped: FieldWrapper.() -> Unit, vararg condition: ReflectionFieldFilter): FieldFilter {
            val targets = mutableListOf<FieldWrapper>()
            main@ for (x in fields) {
                for (cond in condition) {
                    if (!cond.filter(x.field, x.instance)) {
                        onDropped(x)
                        continue@main
                    }
                }
                targets += x
            }
            return FieldFilter(targets)
        }

        fun filter(vararg condition: ReflectionFieldFilter): FieldFilter =
            filter({}, *condition)

        fun filter(annotation: Class<out Annotation>): FieldFilter {
            return filter(true, annotation)
        }

        fun filter(requireAllAnnotation: Boolean, vararg annotation: Class<out Annotation>): FieldFilter {
            val targets = mutableListOf<FieldWrapper>()
            main@ for (x in fields) {
                var accepted = false
                for (an in annotation) {
                    if (x.field.getDeclaredAnnotation(an) != null) {
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
            return FieldFilter(targets)
        }
    }

    enum class ReflectionFieldFilter(
        private val negative: () -> ReflectionFieldFilter,
        private val methodFilter: Field.(Any?) -> Boolean,
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
                (kotlinProperty?.visibility?.equals(kotlin.reflect.KVisibility.INTERNAL) == true)
            }),

        /*
            Accept non-kotlin internal method.
         */
        ACCESSOR_NON_INTERNAL({ ACCESSOR_INTERNAL },
            {
                (kotlinProperty?.visibility?.equals(kotlin.reflect.KVisibility.INTERNAL) == false)
            }),

        /*
            Accept instance required method.
         */
        INSTANCE_REQUIRED(
            { INSTANCE_NOT_REQUIRED }, {
                val kotlin = declaringClass.kotlin
                (!Modifier.isStatic(modifiers) && it == null) || (!kotlin.isCompanion && kotlin.objectInstance == null)
            }
        ),

        /*
            Accept instance not required method like static, or kotlin object, kotlin companion.
         */
        INSTANCE_NOT_REQUIRED(
            { INSTANCE_REQUIRED }, {
                !INSTANCE_REQUIRED.filter(this, it)
            }
        );

        fun negative() = this.negative.invoke()

        fun filter(mtd: Field, instance: Any?): Boolean = methodFilter.invoke(mtd, instance)
    }
}