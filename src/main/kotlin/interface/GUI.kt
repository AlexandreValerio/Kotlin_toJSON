package `interface`

import lib.*
import org.eclipse.swt.SWT
import org.eclipse.swt.events.ModifyEvent
import org.eclipse.swt.events.ModifyListener
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.graphics.Color
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.*
import org.eclipse.swt.graphics.Device


interface FrameSetup {
    val title: String
    val layoutManager: GridLayout
}


interface Action {
    val name: String
    fun execute(window: GUI)
}


data class Dummy(val number: Int)

class GUI() {
    val shell: Shell
    val tree: Tree
    private val setup: FrameSetup = DefaultSetup() //TODO Eliminar dependencia do default setup, utilizar o inject
    //private val actions = mutableListOf<Action>(Search())

    init {
        shell = Shell(Display.getDefault())
        shell.setSize(1000, 200)
        shell.text = setup.title
        shell.layout = setup.layoutManager


        tree = Tree(shell, SWT.SINGLE or SWT.BORDER)
        val labelSerialized = Label(shell, SWT.BORDER)
        val searchBox = Text(shell, SWT.BORDER)
        searchBox.text = "Search here"

        tree.addSelectionListener(object : SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                val vis = Serializing()
                val data = tree.selection.first().data
                var ser = ""
                if (data is JsonSimple){
                    if (data.receivedValue is String) ser = "\"" + data.receivedValue as String + "\""
                    else if (data.receivedValue is Enum<*>) ser = "\"" + data.receivedValue.toString() + "\""
                    else if (data.receivedValue is Int || data.receivedValue is Boolean ||
                        data.receivedValue is Double || data.receivedValue == null ) ser = data.receivedValue.toString()
                }
                else if(data is JsonArray){
                    data.accept(vis)
                    ser = vis.serialized
                    /** as data might have a parent, it will add the ",\n". Therefore we must take it out*/
                    if (ser.takeLast(2) == ",\n") ser = ser.dropLast(2)
                }
                else if(data is JsonObject){
                    data.accept(vis)
                    ser = vis.serialized
                    /** as data might have a parent, it will add the ",\n". Therefore we must take it out*/
                    if (ser.takeLast(2) == ",\n") ser = ser.dropLast(2)
                }
                labelSerialized.text = ser
                labelSerialized.requestLayout()
            }
        })



        searchBox.addModifyListener {
            clearBackgrounds(tree.selection.first())
            val searched = searchBox.text
            searchValues(tree.selection.first(), searched)
        }

/*        val button = Button(shell, SWT.PUSH)
        button.text = "Search"
        button.addSelectionListener(object: SelectionAdapter() {
            override fun widgetSelected(e: SelectionEvent) {
                clearBackgrounds(tree.selection.first())
                val searched = searchBox.text
                searchValues(tree.selection.first(), searched)

            }
        })*/
    }


    fun open() {

        tree.expandAll()
        shell.pack()
        shell.open()
        val display = Display.getDefault()
        while (!shell.isDisposed) {
            if (!display.readAndDispatch()) display.sleep()
        }
        display.dispose()

    }

    fun clearBackgrounds(node: TreeItem){
        node.items.forEach {

            it.background = null
            if(it.data is JsonObject || it.data is JsonArray) clearBackgrounds(it)
        }

    }

    fun searchValues(node: TreeItem, searched: String){
        node.items.forEach {
            if(it.text.contains(searched)){

                val red = Color(Display.getCurrent(), 0, 250, 0)
                it.background = red
            }
            if(it.data is JsonObject || it.data is JsonArray) searchValues(it, searched)
        }

    }


}


// auxiliares para varrer a árvore

fun Tree.expandAll() = traverse { it.expanded = true }



fun Tree.traverse(visitor: (TreeItem) -> Unit) {
    fun TreeItem.traverse() {
        visitor(this)
        items.forEach {
            it.traverse()
        }
    }
    items.forEach { it.traverse() }
}


