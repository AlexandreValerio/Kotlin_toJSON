package `interface`


import lib.JsonArray
import lib.JsonObject
import lib.JsonSimple
import lib.Serializing
import org.eclipse.swt.events.SelectionAdapter
import org.eclipse.swt.events.SelectionEvent
import org.eclipse.swt.widgets.Display
import org.eclipse.swt.widgets.Shell
import java.io.File
import org.eclipse.swt.*
import org.eclipse.swt.layout.GridLayout
import org.eclipse.swt.widgets.Label

/** Edits the text of the selected node*/
class Edit : Action {
    override val name: String
        get() = "Edit"

    override var tBoxText: String = ""

    override val hasTextBox: Boolean
        get() = true

    override fun execute(window: Window) {
        if(window.clickedItem != null) {
            window.clickedItem!!.text = tBoxText
        }
    }

}

/** Exports JSON to a file, in this case to ExportedJSON.json*/
class Export : Action {
    override val name: String
        get() = "Export JSON"
    override val hasTextBox: Boolean
        get() = false
    override var tBoxText: String = ""

    override fun execute(window: Window) {
        val exportedJSON = File("ExportedJSON.json")
        val vis = Serializing()
        val data = window.tree.selection.first().data
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
        exportedJSON.writeText(ser)
    }

}

/** This action creates the pop button and opens a new window with the JSON visualization*/
class PopWindow : Action {
    override val name: String
        get() = "Pop"
    override val hasTextBox: Boolean
        get() = false
    override var tBoxText = ""

    override fun execute(window: Window) {
        if(window.clickedItem != null){
            var display = Display.getCurrent()
            var newWindow = Shell(display)
            newWindow.setSize(200,200)
            newWindow.layout = GridLayout(2, false)

            val vis = Serializing()
            val label = Label(newWindow, SWT.BORDER)
            window.clickedItem = window.tree.selection.first()
            val data = window.tree.selection.first().data
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
            label.text = ser

            newWindow.pack()
            newWindow.open()
            while (!newWindow.isDisposed) {
                if (!display.readAndDispatch()) display.sleep()
            }

        }

    }

}


