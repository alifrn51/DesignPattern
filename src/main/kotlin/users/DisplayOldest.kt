package users

import java.awt.Dimension
import java.awt.Font
import java.awt.Insets
import javax.swing.JFrame
import javax.swing.JScrollPane
import javax.swing.JTextArea

class DisplayOldest {

    fun show() {
        val textArea = JTextArea().apply {
            isEditable = false
            text = "Hello wordl"
            font = Font(Font.SANS_SERIF, Font.PLAIN, 18)
            margin = Insets(32, 32, 32, 32)
        }
        val scrollPane = JScrollPane(textArea)
        JFrame().apply {
            isVisible = true
            size = Dimension(600, 800)
            isResizable = false
            add(scrollPane)
        }

        UserRepository.getInstance("sfeoi").oldestUsers.registerObserver {
            textArea.text = it.toString()
        }

    }


}