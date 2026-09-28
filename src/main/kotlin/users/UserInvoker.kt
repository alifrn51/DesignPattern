package users

import command.Command
import command.Invoker
import java.util.concurrent.LinkedBlockingDeque
import kotlin.concurrent.thread

object UserInvoker : Invoker<AdministratorCommands> {

    private val commands = LinkedBlockingDeque<Command>()

    init {
        thread {
            while (true) {
                //   println("wating....")
                val command = commands.take()
                //   println("Executing$command....")
                command.execute()
                //  println("Executed$command.")
            }
        }
    }

    override fun addCommand(command: AdministratorCommands) {
        commands.add(command)
    }
}