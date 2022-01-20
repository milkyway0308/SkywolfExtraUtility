package skywolf46.extrautility.data

import skywolf46.extrautility.util.unlock
import java.lang.IllegalStateException
import java.lang.reflect.Field

class ReferenceInjectorStorage : ArgumentStorage() {

    fun addReference(annotation: Class<Annotation>, forcedReference: String) {
        this[annotation.name] = forcedReference
    }

    fun injectReference(field: Field) {

    }

    interface IReferenceProvider {
        fun provide(instance: Any, field: Field)

        fun change(name: String, any: Any)
    }

    private class ForcedInjectReferenceProvider(val data: Any) : IReferenceProvider {
        val cls = data.javaClass
        override fun provide(instance: Any, field: Field) {
            if (!field.type.isAssignableFrom(cls)) {
                System.err.println("SkywolfExtraUtility error; Cannot inject reference to field ${field.declaringClass.name}#${field.name} : Field type ${field.type.name} is not compatible with reference class ${cls.name}")
                return
            }
            field.unlock {
                it.set(instance, data)
            }
        }

        override fun change(name: String, any: Any) {
            throw IllegalStateException("ForceInjectReferenceProvider not supports injection target change")
        }

    }

    private class FieldNameBasedInjectReferenceProvider : IReferenceProvider {
        private val types = mutableMapOf<String, Any>()
        override fun provide(instance: Any, field: Field) {

        }

        override fun change(name: String, any: Any) {
            types[name] = any
        }
    }

    private class FieldTypeBasedInjectReferenceProvider {

    }


}