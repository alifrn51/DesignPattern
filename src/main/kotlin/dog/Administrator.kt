package dog


class Administrator {

    private val repository = DogRepository.getInstance("asdfsadf")

    fun work() {

        while (true) {
            print("Enter an operation: ")
            val operations = Operation.entries
            for ((index, operation) in operations.withIndex()) {
                print("$index -> ${operation.title}")
                val separator = if (index == operations.size - 1) ": " else ", "
                print(separator)
            }
            val operationIndex = readln().toInt()
            val operation = operations[operationIndex]

            when (operation) {
                Operation.EXIT -> {
                    DogInvoker.addCommand(AdministratorCommands.SaveChange(repository))
                    break
                }

                Operation.ADD_DOG -> registerDog()
                Operation.DELETE_DOG -> deleteDog()
            }
        }


    }

    private fun deleteDog() {
        print("Enter id: ")
        val id = readln().toInt()

        DogInvoker.addCommand(AdministratorCommands.DeleteDog(repository, id))
    }

    private fun registerDog() {
        print("Enter breed name: ")
        val breedName = readln()
        print("Enter dog name: ")
        val dogName = readln()
        print("Enter weight:")
        val weight = readln().toInt()

        DogInvoker.addCommand(AdministratorCommands.AddDog(repository, breedName, dogName, weight))
    }


}