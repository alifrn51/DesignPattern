package users

class Administrator() {

    val repository = UserRepository.getInstance("sfeoi")
    fun work(){

        while (true){
            print("Enter an operation: ")
            val operations = Operation.entries
            for ((index, operation) in operations.withIndex()){
                print("$index -> ${operation.title}")
                val separator =if(index== operations.size-1) ": " else ", "
                print(separator)
            }
            val operationIndex = readln().toInt()
            val operation = operations[operationIndex]

            when(operation){
                Operation.EXIT ->{
                    AdministratorCommands.SaveChanges(repository)
                    break
                }
                Operation.ADD_USER -> registerUser()
                Operation.DELETE_USER -> deleteUser()
            }
        }


    }

    private fun deleteUser() {

        print("Enter id: ")
        val id = readln().toInt()
        UserInvoker.addCommand(AdministratorCommands.DeleteUser(repository, id))

    }

    private fun registerUser() {
        print("Enter firstName: ")
        val firstName = readln()
        print("Enter lastName: ")
        val lastName = readln()
        print("Enter age:")
        val age = readln().toInt()

        UserInvoker.addCommand(AdministratorCommands.AddUser(repository, firstName, lastName, age))
    }

}