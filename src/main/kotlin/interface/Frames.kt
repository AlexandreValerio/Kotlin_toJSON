package `interface`


import org.eclipse.swt.layout.GridLayout

class DefaultSetup : FrameSetup {
    override val title: String
        get() = "Kotlin_toJSON"
    override val layoutManager: GridLayout
        get() = GridLayout(2,false)
}