package lib

@Target(AnnotationTarget.PROPERTY)
annotation class RemoveProperty

@Target(AnnotationTarget.PROPERTY)
annotation class ChangeKey(val newKey: String)