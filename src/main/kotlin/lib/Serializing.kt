package project

/** Classe do tipo Visitor para fazer o serializing de texto */
class Serializing : Visitor {

    var serialized = ""

    /** No override tenho sempre que devolver o mesmo tipo? */
    override fun visit(o: JsonObject): Boolean {
        //println("i visited a JsonObject ${o.objectMap} ")
        if (o.parent is JsonObject) {
            serialized += "\"" + "${o.key}" + "\": "
        }
        serialized += "{\n"
        return true
    }

    override fun endOfObjectVisit(o: JsonObject) {
        if (serialized.takeLast(2) == ",\n") serialized = serialized.dropLast(2)
        serialized += "\n}"

        if (o.parent is JsonObject) serialized += ",\n"
    }

    override fun visit(a: JsonArray): Boolean {
        //println("i visited a JsonArray ${a.auxArray} ")
        /** Se o pai for um objeto, adiciona a key */
        if (a.parent is JsonObject) {
            serialized += "\"" + "${a.key}" + "\": "
        }
        serialized += "["
        return true

    }

    override fun endOfArrayVisit(a: JsonArray) {
        //println(serialized)
        /** Para o último elemento de um array vai verificar se tem a vírgula e retira, adiciona depois o ] */
        if (serialized.takeLast(2) == ", ") serialized = serialized.dropLast(2)

        serialized += "]"

        if (a.parent is JsonObject) serialized += ",\n"

    }

    override fun visit(s: JsonSimple) {
        //println("i visited a JsonSimple $s")
        //s.serializedSimple += "${s.receivedValue}"
        var vvalue = ""

        if (s.receivedValue is String || s.receivedValue is Enum<*>) vvalue += "\"" + "${s.receivedValue}" + "\""
        else if (s.receivedValue is Int || s.receivedValue is Double) vvalue += "${s.receivedValue}"
        else if (s.receivedValue is Boolean || s.receivedValue == null) vvalue += "${s.receivedValue}"


        if (s.parent is JsonArray) {
            serialized += vvalue + ", "
        }
        /** Se o parent for um objeto, adiciona também a key*/
        else if (s.parent is JsonObject) {
            serialized += "\"" + "${s.key}" + "\": " + vvalue + ",\n"
        }


    }
}



