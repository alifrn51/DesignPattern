package dog

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class Display {

    private val textArea = JTextArea().apply {
        isEditable = false
        text = "..."
        margin = Insets(32,32,32,32)
        font = Font(Font.SANS_SERIF, Font.PLAIN,16)
    }
    fun show(){
        val scrollPane = JScrollPane(textArea)
        JFrame().apply {
            isVisible = true
            size = Dimension(600,800)
            isResizable = false
            add(scrollPane)
        }

        DogRepository.getInstance("asdfsadf").registerObserver(this)
    }

    fun onChange(dogs: List<Dog>){
        dogs.joinToString("\n")
            .let { textArea.text = it }
    }
}