package lib

import kotlin.reflect.KClass

/** Esta classe retorna uma lista com o valor de todas as strings dos vários elementos*/
class SearchString: Visitor {
    val resulfOfSearch = mutableListOf<String>()

    override fun visit(s: JsonSimple) {
        if(s.receivedValue is String) resulfOfSearch.add(s.receivedValue)

    }

}

/** Recebe a key pretendida e devolve o respetivo value/values associados numa lista*/

class SearchKey(val name:String): Visitor {
    val resulfOfSearch = mutableListOf<Any?>()

    override fun visit(o: JsonObject): Boolean {
        o.objectMap.forEach(){
            if(it.key == name) resulfOfSearch.add(it.value.element)
        }

        return true
    }

    override fun visit(o: JsonArray): Boolean {
        return false /** Dentro de um array, não há keys, não entra nos seus filhos*/

    }


}

/** Devolve uma lista com todas as keys existentes (não as repete)*/
class GetAllKeys: Visitor {
    val resulfOfSearch = mutableListOf<String>()

    override fun visit(o: JsonObject): Boolean {
        o.objectMap.forEach(){
            if(!resulfOfSearch.contains(it.key)) resulfOfSearch.add(it.key)
        }
        return true
    }
    override fun visit(o: JsonArray): Boolean {
        return false /** Dentro de um array, não há keys, não entra nos seus filhos*/

    }


}