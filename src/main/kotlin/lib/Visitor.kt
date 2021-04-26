package project

interface Visitor {
    /** Implementação por omissão para usarmos as funções apenas quando precisamos, com o {}*/
    fun visit(o: JsonObject) :Boolean = true
    fun endOfObjectVisit(o: JsonObject) { }

    fun visit(a: JsonArray) :Boolean = true
    fun endOfArrayVisit(a: JsonArray) { }

    fun visit(s: JsonSimple) { }
}


