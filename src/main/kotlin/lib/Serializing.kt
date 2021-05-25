package lib

/** Classe do tipo Visitor para fazer o serializing de texto */
class Serializing() : Visitor {
    var depth = 0
    var serialized = ""

    /** No override tenho sempre que devolver o mesmo tipo? */
    override fun visit(o: JsonObject): Boolean {

        serialized += "{\n"
        depth += 1
        return true
    }

    override fun visitObjectKey(key: String, el: JsonElement) {

        identation()
        serialized += "\"$key\": "


    }

    override fun endOfObjectVisit(o: JsonObject) {
        depth -= 1
        if (serialized.takeLast(2) == ",\n") serialized = serialized.dropLast(2)
        serialized += "\n"
        identation()
        serialized += "}"

        if (o.parent is JsonObject) serialized += ",\n"
    }

    override fun visit(a: JsonArray): Boolean {
        /** Se o pai for um objeto, adiciona a key */

        serialized += "["
        return true

    }

    override fun endOfArrayVisit(a: JsonArray) {

        /** Para o último elemento de um array vai verificar se tem a vírgula e retira, adiciona depois o ] */
        if (serialized.takeLast(2) == ", ") serialized = serialized.dropLast(2)

        serialized += "]"

        if (a.parent is JsonObject) serialized += ",\n"

    }

    override fun visit(s: JsonSimple) {

        var vvalue = ""

        if (s.receivedValue is String || s.receivedValue is Enum<*>) vvalue += "\"" + "${s.receivedValue}" + "\""
        else if (s.receivedValue is Int || s.receivedValue is Double) vvalue += "${s.receivedValue}"
        else if (s.receivedValue is Boolean || s.receivedValue == null) vvalue += "${s.receivedValue}"


        if (s.parent is JsonArray) {
            serialized += vvalue + ", "
        }
        else if(s.parent is JsonObject){
            serialized += "$vvalue,\n"
        }

    }

    /** Identation function, it checks the depth of the object*/
    private fun identation(){
        var aux = 0
        while(aux < depth){
            serialized += "\t"
            aux += 1
        }
    }



}



