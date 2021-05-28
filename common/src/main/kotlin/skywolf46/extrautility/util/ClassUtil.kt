package skywolf46.extrautility.util

import io.github.classgraph.ClassGraph
import skywolf46.extrautility.ExtraUtilityCore
import java.lang.reflect.Modifier
import kotlin.reflect.KVisibility

object ClassUtil {

    @JvmStatic
    inline fun Class<*>.iterateParentClasses(iterator: Class<*>.() -> Unit) {
        var clsOrig = this
        do {
            iterator(clsOrig)
            clsOrig = clsOrig.superclass
        } while (clsOrig != Any::class.java)
        iterator(Any::class.java)
    }

    @JvmOverloads
    @JvmStatic
    fun scanClass(ignoredPrefix: List<String> = emptyList()): List<Class<*>> {
        val scanned = ClassGraph().enableClassInfo().enableAnnotationInfo().scan()
        val target = mutableListOf<Class<*>>()
        for (x in scanned.allClasses.filter {
            for (prefix in ignoredPrefix)
                if (it.packageName.startsWith(prefix))
                    return@filter false
            return@filter true
        }) {
            try {
                ExtraUtilityCore.logger.fine("..Loading class ${x.name}")
                target += x.loadClass(false)
                ExtraUtilityCore.logger.finer("....Class ${x.name} loaded")
            } catch (e: Exception) {
                ExtraUtilityCore.logger.severe("....Failed to load class ${x.name} : ${e.javaClass.simpleName} (${e.message})")
            }
        }
        return target
    }


    class ClassFilter {
        val vis = KVisibility.INTERNAL
    }

    enum class ReflectionClassFilter(
        private val negative: () -> ReflectionClassFilter,
        private val methodFilter: Class<*>.(Any?) -> Boolean,
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

                (kotlin.visibility?.equals(KVisibility.INTERNAL) == true)
            }),

        /*
            Accept non-kotlin internal method.
         */
        ACCESSOR_NON_INTERNAL({ ACCESSOR_INTERNAL },
            {
                (kotlin.visibility?.equals(KVisibility.INTERNAL) == false)
            }),

        /*
            Accept instance required method.
         */
        INSTANCE_REQUIRED(
            { INSTANCE_NOT_REQUIRED }, {
                it == null && !STATIC.filter(this, it)
            }
        ),

        /*
            Accept instance not required method like static, or kotlin object, kotlin companion.
         */
        INSTANCE_NOT_REQUIRED(
            { INSTANCE_REQUIRED }, {
                STATIC.filter(this, it) || it != null
            }
        );

        fun negative() = this.negative.invoke()

        fun filter(mtd: Class<*>, instance: Any?): Boolean = methodFilter.invoke(mtd, instance)
    }

}