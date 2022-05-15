package skywolf46.extrautility.util

import io.github.classgraph.ClassGraph
import io.github.classgraph.ScanResult
import skywolf46.extrautility.ExtraUtilityCore
import java.lang.reflect.Modifier
import kotlin.reflect.KVisibility

object ClassUtil {

    private var cache: ClassFilter? = null

    private val updator: MutableList<() -> List<Class<*>>> = mutableListOf(
        { scanClass(ExtraUtilityCore.getIgnoredList()) }
    )

    fun getCache(): ClassFilter = cache ?: updateAndGetCache()


    fun updateAndGetCache() = run {
        val list = mutableListOf<Class<*>>()
        updator.forEach {
            list.addAll(it())
        }
        cache = ClassFilter(list)
        return@run cache!!
    }

    fun registerUpdater(unit: () -> List<Class<*>>) {
        updator += unit
    }

    @JvmStatic
    fun Class<*>.iterateParentClasses(iterator: Class<*>.() -> Unit) {
        var clsOrig: Class<*>? = this
        do {
            if (clsOrig == null)
                return
            iterator(clsOrig)
            for (x in clsOrig.interfaces) {
                x.iterateParentClasses(iterator)
            }
            clsOrig = clsOrig.superclass
        } while (clsOrig != Any::class.java)
        iterator(Any::class.java)
    }

    @JvmStatic
    fun Class<*>.iterateParentClassesUntil(iterator: Class<*>.() -> Any?): Any? {
        var clsOrig: Class<*>? = this
        do {
            if (clsOrig == null)
                return null
            iterator(clsOrig)?.apply {
                return this
            }
            for (x in clsOrig.interfaces) {
                x.iterateParentClassesUntil(iterator)?.apply {
                    // Break recursive iteration
                    return this
                }
            }
            clsOrig = clsOrig.superclass
        } while (clsOrig != Any::class.java)
        return iterator(Any::class.java)
    }


    @Deprecated(
        "Deprecated from 1.63.0", ReplaceWith(
            "scanClassExactly(ignoredPrefix, false, *additionalLoader)",
            "skywolf46.extrautility.util.ClassUtil.scanClassExactly"
        ),
        level = DeprecationLevel.WARNING
    )
    @JvmOverloads
    @JvmStatic
    fun scanClass(ignoredPrefix: List<String> = emptyList(), vararg additionalLoader: ClassLoader): List<Class<*>> {
        return scanClassExactly(ignoredPrefix, false, *additionalLoader)
    }

    @JvmOverloads
    @JvmStatic
    fun scanClassExactly(
        ignoredPrefix: List<String> = emptyList(),
        replaceClassLoader: Boolean = true,
        vararg additionalLoader: ClassLoader
    ): List<Class<*>> {
        return scanClass0(ignoredPrefix, ClassGraph().apply {
            if (replaceClassLoader) {
                overrideClassLoaders(*additionalLoader)
            } else {
                for (x in additionalLoader)
                    addClassLoader(x)
            }
        }.enableClassInfo().enableAnnotationInfo().scan())
    }

    private fun scanClass0(ignoredPrefix: List<String> = emptyList(), scanned: ScanResult): List<Class<*>> {
        val target = mutableListOf<Class<*>>()
        for (x in scanned.allClasses.filter {
            if (!it.name.contains('.')) {
                return@filter false
            }
            for (prefix in ignoredPrefix)
                if (it.name.startsWith(prefix)) {
                    return@filter false
                }
            return@filter true
        }) {
            try {
                val cls = x.loadClass(false)
                target += cls
            } catch (e: Exception) {
//                ExtraUtilityCore.logger.finer("....Failed to load class ${x.name} : ${e.javaClass.simpleName} (${e.message})")
            }
        }
        return target
    }


    class ClassFilter(val list: List<Class<*>>) {
        fun filter(vararg filter: ReflectionClassFilter): ClassFilter {
            return ClassFilter(list.filter { x -> filter.all { it.filter(x) } })
        }

        fun filter(annotation: Class<out Annotation>): ClassFilter {
            return ClassFilter(list.filter { x -> x.getAnnotation(annotation) != null })
        }

        @Suppress("LABEL_NAME_CLASH")
        fun filter(requireAllAnnotation: Boolean, vararg annotation: Class<out Annotation>): ClassFilter {
            return if (requireAllAnnotation)
                ClassFilter(list.filter { x ->
                    for (data in annotation)
                        if (x.getAnnotation(data) == null)
                            return@filter false
                    return@filter true
                })
            else ClassFilter(list.filter { x ->
                for (data in annotation)
                    if (x.getAnnotation(data) != null)
                        return@filter true
                return@filter false
            })
        }

        fun <X : Any> filterImplementation(inherit: Class<X>): ClassFilter {
            return ClassFilter(list.filter { x -> inherit.isAssignableFrom(x) })
        }

        fun <X : Any> filterImplementation(vararg inherit: Class<X>): ClassFilter {
            return ClassFilter(list.filter { x -> inherit.all { cls -> cls.isAssignableFrom(x) } })
        }

        fun <X : Any> anyImplementation(vararg inherit: Class<X>): ClassFilter {
            return ClassFilter(list.filter { x -> inherit.any { cls -> cls.isAssignableFrom(x) } })
        }


        fun <X : Annotation> filter(annotation: Class<X>, unit: (X) -> Boolean): ClassFilter {
            return ClassFilter(list.filter { x -> x.getAnnotation(annotation)?.run(unit) == true })
        }

        fun toMethodFilter() = MethodUtil.filter(list)

        fun toFieldFilter() = FieldUtil.filter(list)

    }

    enum class ReflectionClassFilter(
        private val negative: () -> ReflectionClassFilter,
        private val methodFilter: Class<*>.() -> Boolean,
    ) {
        /*
            Accept static class.
         */
        STATIC({ NON_STATIC }, {
            modifiers.and(Modifier.STATIC) != 0
        }),

        /*
            Accept non-static class.
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
            Accept non-synthetic class.
         */
        NON_SYNTHETIC({ SYNTHETIC }, {
            !isSynthetic
        }),

        /*
            Accept private class.
         */
        ACCESSOR_PRIVATE({ ACCESSOR_NON_PRIVATE },
            {
                modifiers.and(Modifier.PRIVATE) != 0
            }),

        /*
            Accept non-private class.
         */
        ACCESSOR_NON_PRIVATE({ ACCESSOR_PRIVATE },
            {
                modifiers.and(Modifier.PRIVATE) == 0
            }),

        /*
            Accept protected class.
         */
        ACCESSOR_PROTECTED({ ACCESSOR_NON_PROTECTED },
            {
                modifiers.and(Modifier.PROTECTED) != 0
            }),

        /*
            Accept non-protected class.
         */
        ACCESSOR_NON_PROTECTED({ ACCESSOR_PROTECTED },
            {
                modifiers.and(Modifier.PROTECTED) == 0
            }),

        /*
            Accept public class.
         */
        ACCESSOR_PUBLIC({ ACCESSOR_NON_PUBLIC },
            {
                modifiers.and(Modifier.PUBLIC) != 0
            }),

        /*
            Accept non-public class.
         */
        ACCESSOR_NON_PUBLIC({ ACCESSOR_PUBLIC },
            {
                modifiers.and(Modifier.PUBLIC) == 0
            }),

        /*
            Accept kotlin internal class.
         */
        ACCESSOR_INTERNAL({ ACCESSOR_NON_INTERNAL },
            {
                (kotlin.visibility?.equals(KVisibility.INTERNAL) == true)
            }),

        /*
            Accept non-kotlin internal class.
         */
        ACCESSOR_NON_INTERNAL({ ACCESSOR_INTERNAL },
            {
                (kotlin.visibility?.equals(KVisibility.INTERNAL) == false)
            }),

        /*
            Accept object class (kotlin).
         */
        INSTANCE_REQUIRED(
            { INSTANCE_NOT_REQUIRED }, {
                kotlin.objectInstance == null
            }
        ),

        /*
            Accept non-object class (kotlin).
         */
        INSTANCE_NOT_REQUIRED(
            { INSTANCE_REQUIRED }, {
                !INSTANCE_REQUIRED.filter(this)
            }
        ),

        /*
          Accept if field is abstract or interface.
       */
        ABSTRACTED(
            { NOT_ABSTRACTED }, {
                Modifier.isInterface(modifiers) || Modifier.isAbstract(modifiers)
            }
        ),

        /*
            Accept if field is not abstract and interface.
         */
        NOT_ABSTRACTED(
            { ABSTRACTED }, {
                !ABSTRACTED.filter(this)
            }
        )

        ;

        fun negative() = this.negative.invoke()

        fun filter(mtd: Class<*>): Boolean = methodFilter.invoke(mtd)
    }

}

//fun <X : Annotation> Class<*>.getAnnotationRecursively(annotation: Class<out X>): {
//    iterateParentClasses {
//        getAnnotation(annotation)?.apply {
//            return this
//        }
//    }
//}