package project

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
        if(o.key == name) resulfOfSearch.add(o.receivedObj)
        return true
    }

    override fun visit(o: JsonArray): Boolean {
        if(o.key == name) resulfOfSearch.add(o.receivedList)
        return false /** Dentro de um array, não há keys, não entra nos seus filhos*/

    }

    override fun visit(o: JsonSimple) {
        if(o.key == name) resulfOfSearch.add(o.receivedValue)

    }

}

/** Devolve uma lista com todas as keys existentes (não as repete)*/
class GetAllKeys: Visitor {
    val resulfOfSearch = mutableListOf<String>()

    override fun visit(o: JsonObject): Boolean {
        if(o.key != null && !resulfOfSearch.contains(o.key)) resulfOfSearch.add(o.key)
        return true
    }

    override fun visit(o: JsonArray): Boolean {
        if(o.key != null && !resulfOfSearch.contains(o.key)) resulfOfSearch.add(o.key)
        return false /** Dentro de um array, não há keys, não entra nos seus filhos*/

    }

    override fun visit(o: JsonSimple) {
        if(o.key != null && !resulfOfSearch.contains(o.key)) resulfOfSearch.add(o.key)

    }
}