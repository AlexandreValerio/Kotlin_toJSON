package lib
import `interface`.GUI
import org.eclipse.swt.SWT
import org.eclipse.swt.widgets.*


/** Viewer Class for the user interface*/
class Viewer: Visitor {

    var currentElement: TreeItem? = null
    /** just an aux variable to complete the node.text*/
    var aux = ""
    var objectkey = ""
    var arraykey = ""
    lateinit var tree: Tree

    override fun openViewer(el: JsonElement){
        val gui = GUI()
        tree = gui.tree

        el.accept(this)
        gui.open()
    }

    override fun visit(o: JsonObject): Boolean {

        var node: TreeItem
        if(o.parent == null){
            node = TreeItem(tree, SWT.NONE)
            node.text = "(object)" //TODO
            node.data = o
        }
        else{
            node = TreeItem(currentElement, SWT.NONE)
            node.text = "$objectkey (object)" //TODO
            node.data = o
            objectkey = ""
        }
        currentElement = node
        return true
    }

    override fun visitObjectKey(key: String, el: JsonElement) {
        aux = key
        if(el is JsonSimple){
            if (el.receivedValue is String) aux += ": \"" + el.receivedValue as String + "\""
            else if (el.receivedValue is Enum<*>) aux += ": \"" + el.receivedValue.toString() + "\""
            else if (el.receivedValue is Int || el.receivedValue is Boolean ||
                el.receivedValue is Double || el.receivedValue == null) aux += ": " + el.receivedValue.toString()

            val node = TreeItem(currentElement, SWT.NONE)
            node.text = aux
            node.data = el
        }
        else if (el is JsonObject) objectkey = key
        else if (el is JsonArray) arraykey = key

    }

    override fun endOfObjectVisit(o: JsonObject) {
        currentElement = currentElement!!.parentItem
    }

    override fun visit(a: JsonArray): Boolean {

        var node: TreeItem = TreeItem(currentElement, SWT.NONE)
        node.text = "$arraykey (array)"
        node.data = a
        arraykey = ""

        currentElement = node
        return true
    }


    override fun visit(s: JsonSimple) {
        if(s.parent is JsonArray){
            aux = ""
            if (s.receivedValue is String) aux += "\"" + s.receivedValue as String + "\""
            else if (s.receivedValue is Enum<*>) aux += "\"" + s.receivedValue.toString() + "\""
            else if (s.receivedValue is Int || s.receivedValue is Boolean ||
                s.receivedValue is Double || s.receivedValue == null) aux += s.receivedValue.toString()

            val node = TreeItem(currentElement, SWT.NONE)
            node.text = aux
            node.data = s
        }

    }


    override fun endOfArrayVisit(a: JsonArray) {
        currentElement = currentElement!!.parentItem
    }


}