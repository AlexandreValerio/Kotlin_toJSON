
package `interface`

import lib.*
import org.eclipse.swt.SWT
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*


interface FrameSetup {
    val title: String
    val layoutManager: GridLayout
    val width: Int
    val height: Int
    fun applyIcons(node: TreeItem, display: Display) { }
    fun applyText(node: TreeItem, display: Display) { }
    fun applyExclusion(node: TreeItem, display: Display) { }
}


interface Action {
    val name: String
    val hasTextBox: Boolean
    var tBoxText: String
    fun execute(window: Window)

}


data class Dummy(val number: Int)

class Window() {
    private var shell = Shell(Display.getDefault())
    lateinit var tree: Tree
    var clickedItem: TreeItem? = null

    @Inject
    private lateinit var setup: FrameSetup

    @InjectAdd
    private lateinit var actions: MutableList<Action>

    fun initWindow() {
        shell.setSize(setup.width, setup.height)
        shell.text = setup.title
        shell.layout = setup.layoutManager


        tree = Tree(shell, SWT.SINGLE or SWT.BORDER)
        val labelSerialized = Label(shell, SWT.BORDER)
        val searchBox = Text(shell, SWT.BORDER)
        searchBox.text = "Search here"

        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                clickedItem = tree.selection.first()
                val vis = Serializing()
                val data = tree.selection.first().data
                var ser = ""
                if (data is JsonSimple) {
                    if (data.receivedValue is String) ser = "\"" + data.receivedValue as String + "\""
                    else if (data.receivedValue is Enum<*>) ser = "\"" + data.receivedValue.toString() + "\""
                    else if (data.receivedValue is Int || data.receivedValue is Boolean ||
                        data.receivedValue is Double || data.receivedValue == null
                    ) ser = data.receivedValue.toString()
                } else if (data is JsonArray) {
                    data.accept(vis)
                    ser = vis.serialized
                    /** as data might have a parent, it will add the ",\n". Therefore we must take it out*/
                    if (ser.takeLast(2) == ",\n") ser = ser.dropLast(2)
                } else if (data is JsonObject) {
                    data.accept(vis)
                    ser = vis.serialized
                    /** as data might have a parent, it will add the ",\n". Therefore we must take it out*/
                    if (ser.takeLast(2) == ",\n") ser = ser.dropLast(2)
                }
                labelSerialized.text = ser
                labelSerialized.requestLayout()
                //shell.pack()
            }
        })

        searchBox.addModifyListener {
            clearBackgrounds(tree.selection.first())
            val searched = searchBox.text
            searchValues(tree.selection.first(), searched)
        }
    }


    fun open() {


        tree.expandAll()
        initActions()
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()

    }

    private fun clearBackgrounds(node: TreeItem){
        node.items.forEach {

            it.background = null
            if(it.data is JsonObject || it.data is JsonArray) clearBackgrounds(it)
        }

    }

    private fun searchValues(node: TreeItem, searched: String){
        node.items.forEach {
            if(it.text.contains(searched)){

                val red = Color(Display.getCurrent(), 0, 250, 0)
                it.background = red
            }
            if(it.data is JsonObject || it.data is JsonArray) searchValues(it, searched)
        }

    }

    private fun initActions() {

        actions.forEach { action ->

            var text: Text? = null
            if(action.hasTextBox) text = Text(shell, SWT.BORDER)

            if(action.name != ""){

                val button = Button(shell, SWT.PUSH)
                button.text = action.name

                button.addSelectionListener(object : SelectionAdapter() {
                    override fun widgetSelected(e: SelectionEvent?) {
                        super.widgetSelected(e)
                        if (text != null) {
                            action.tBoxText = text.text
                        }
                        action.execute(this@Window)
                    }
                })
            }
        }

    }
    // auxiliares para varrer a árvore

    fun Tree.expandAll() = traverse { it.expanded = true }



    fun Tree.traverse(visitor: (TreeItem) -> Unit) {
        fun TreeItem.traverse() {
            visitor(this)
            setup.applyIcons(this, display)
            setup.applyText(this, display)
            setup.applyExclusion(this, display)
            items.forEach {
                it.traverse()
            }
        }
        items.forEach { it.traverse() }
    }



}




