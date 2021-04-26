package project

class JsonSimple(val key: String? = null, val receivedValue: Any?, parent: JsonElement? = null) : JsonElement(receivedValue, parent){


    override fun accept(v: Visitor) {
        v.visit(this)

    }

}