package `interface`


import lib.JsonArray
import lib.JsonObject
import lib.JsonSimple
import org.eclipse.swt.graphics.Image
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.TreeItem

class DefaultSetup : FrameSetup {
    override val title: String
        get() = "Kotlin_toJSON"
    override val layoutManager: GridLayout
        get() = GridLayout(2,false)
    override val width: Int
        get() = 700
    override val height: Int
        get() = 450

    override fun applyIcons(node: TreeItem, display: Display) {
        val objNode = Image(display, "object_icon.png")
        val arrayNode = Image(display, "array_icon.png")
        val simpleNode = Image(display, "simple_icon.png")


        if(node.data is JsonObject){
            node.image = objNode

        }
        else if(node.data is JsonArray){
            node.image = arrayNode
        }
        else{
            node.image = simpleNode
        }

    }

    override fun applyText(node: TreeItem, display: Display) {

        if (node.data is JsonObject) {
            val obj = node.data as JsonObject
            obj.objectMap.forEach() {
                if (it.key == "name" || it.key == "Name") {
                    if (it.value.element != null) node.text = it.value.element.toString()

                }
            }

        }
    }

    /** In this case, it will exclude the presentation of JsonArray childs*/
    override fun applyExclusion(node: TreeItem, display: Display) {
        if(node.data is JsonArray){
            node.items.forEach {
                if(it.data is JsonSimple)
                {
                    it.dispose()
                }
            }
        }
    }


}

/** Just phase 3, without any special additions to the puglin*/
class DefaultSetup2 : FrameSetup {
    override val title: String
        get() = "Kotlin_toJSON"
    override val layoutManager: GridLayout
        get() = GridLayout(2,false)
    override val width: Int
        get() = 700
    override val height: Int
        get() = 400

}

