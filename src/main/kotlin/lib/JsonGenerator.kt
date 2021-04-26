package project

class JsonGenerator(val obj: Any) {
    private var json: Any? = null

    /** Função que vai percorrer todos os elementos, guardá-los em memória. Posteriormente inicia o visitor da
     *  serialização, retornando uma string com o JSON*/
    fun toJson(): String? {
        val v = Serializing()
        /** Se for uma lista cria um JSON Array */
        if (obj is List<*>) {
            val a = JsonArray(null, obj as List<Any?>) //vai sem parent, porque é a "raiz"
            a.addJsonArray()
            a.accept(v)
            json = a
            return(v.serialized)

        }
        /** Se for um elemento primitivo não retorna nada */
        else if(obj is String || obj is Int || obj is Boolean || obj == null || obj is Enum<*> || obj is Double ){
            return null
        }

        /** Se for uma classe ou um map cria um JSON object*/
        else {
            val o = JsonObject(null, obj)
            o.addJsonObj()
            o.accept(v)
            json = o
            return(v.serialized)
        }

    }

    /** Inicia o visitor que faz a pesquisa associada*/
    fun searchString(): List<String>? {
        val v = SearchString()
        auxaccept(v)
        if(v.resulfOfSearch.size == 0) return null
        return v.resulfOfSearch

    }

    /** Inicia o visitor que faz a pesquisa associada*/
    fun searchKey(key: String): List<Any?>? {
        val v = SearchKey(key)
        auxaccept(v)
        if(v.resulfOfSearch.size == 0) return null
        return v.resulfOfSearch

    }

    fun getAllKeys(): List<String>?{
        val v = GetAllKeys()
        auxaccept(v)
        if(v.resulfOfSearch.size == 0) return null
        return v.resulfOfSearch
    }

    /** Função auxiliar das anteriores, que chama o visitor consoante o respetivo elemento array ou object*/
    private fun auxaccept(v: Visitor){
        if(json is JsonArray){
            (json as JsonArray).accept(v)
        }
        else if(json is JsonObject){
            (json as JsonObject).accept(v)
        }
    }
}