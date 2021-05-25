package lib

class JsonSimple(val receivedValue: Any?, parent: JsonElement? = null) : JsonElement(receivedValue, parent){


    override fun accept(v: Visitor) {
        v.visit(this)

    }

}