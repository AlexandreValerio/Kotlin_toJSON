package lib

import kotlin.reflect.KClass
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.hasAnnotation

class JsonObject(val receivedObj: Any, parent: JsonElement? = null) : JsonElement(receivedObj, parent) {

    /** Lista para guardar os elementos filhos deste elemento composto*/
    val children = mutableListOf<JsonElement>()
    val childrenSize get() = children.size

    /** Mapa para guardar os elementos em memória, ficam do tipo key, value*/
    val objectMap = mutableMapOf<String, JsonElement>()

    fun addJsonObj(){

        /** Se for um Map*/
        if(receivedObj is Map<*,*>) {
            receivedObj.forEach() {
                if (it.value is String || it.value is Int || it.value is Boolean || it.value == null ||
                    it.value is Enum<*> || it.value is Double)
                 {
                    val simple = JsonSimple(it.value, this)
                    objectMap["${it.key}"] = simple
                } else if (it.value is List<*>) {
                    /** caso seja uma lista cria um JsonArray*/
                    val aux = JsonArray(it.value as List<Any?>, this)
                    aux.addJsonArray()
                    objectMap["${it.key}"] = aux
                }
                else{
                    /** caso seja um objeto*/
                    val aux = JsonObject(it.value!!, this)
                    /** já está confirmado que não é null*/
                    aux.addJsonObj()
                    objectMap["${it.key}"] = aux
                }

            }
        }

        /** Se for um objeto*/
        else{
            val clazz : KClass<Any> = receivedObj::class as KClass<Any>

            clazz.declaredMemberProperties.forEach() {
                var hasRemoveProperty = it.hasAnnotation<RemoveProperty>()
                var name = it.name

                var hasChangeKey = it.hasAnnotation<ChangeKey>()
                if (hasChangeKey) name = it.findAnnotation<ChangeKey>()!!.newKey


                /** Cria uma variável isProperty para verificar se a propriedade a excluir é a mesma que está a ser instanciada neste ciclo*/

                /** Se não for a propriedade que queremos excluir avançamos, se não não faz nada*/
                if (!hasRemoveProperty) {

                    if (it.returnType.classifier == String::class || it.returnType.classifier == Int::class ||
                        it.returnType.classifier == Boolean::class || it.call(receivedObj) is Enum<*> ||
                        it.call(receivedObj) == null
                    ) {
                        val simple = JsonSimple(it.call(receivedObj), this)
                        objectMap[name] = simple
                    }
                    else if (it.returnType.classifier == List::class) {
                        /** caso seja uma lista cria um JsonArray*/
                        val aux = JsonArray(it.call(receivedObj) as List<Any?>, this)
                        aux.addJsonArray()
                        objectMap[name] = aux
                    } else {
                        /** caso seja um objeto*/
                        val aux = JsonObject(it.call(receivedObj)!!, this)
                        /** já está confirmado que não é null*/
                        aux.addJsonObj()
                        objectMap[name] = aux
                    }
                }
            }

        }

    }


    override fun accept(v: Visitor) {
        if(v.visit(this))
        objectMap.forEach(){
            v.visitObjectKey(it.key, it.value)
            it.value.accept(v)
        }

        v.endOfObjectVisit(this)

    }


}
