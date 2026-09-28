package dog

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

object DogInvoker : Invoker<AdministratorCommands> {

    private val commands = LinkedBlockingDeque<Command>()

    init {
        thread {
            while (true) {
                val command = commands.take()
                command.execute()
            }

        }
    }

    override fun addCommand(command: AdministratorCommands) {
        commands.add (command)
    }
}