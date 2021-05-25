package lib

abstract class JsonElement(val element: Any?, val parent: JsonElement?) {
    init{
        if(parent is JsonObject) parent.children.add(this)
        else if(parent is JsonArray) parent.children.add(this)

    }
    abstract fun accept(v: Visitor)

}