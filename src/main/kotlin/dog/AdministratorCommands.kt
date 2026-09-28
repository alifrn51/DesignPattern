package dog

import command.Command

sealed interface AdministratorCommands: Command{
    data class AddDog(
        val repository: DogRepository,
        val breedName: String,
        val dogName: String,
        val weight: Int
    ): AdministratorCommands{
        override fun execute() {
            repository.add(breedName = breedName, dogName = dogName, weight = weight)
        }
    }

    data class DeleteDog(
        val repository: DogRepository,
        val id: Int
    ): AdministratorCommands{
        override fun execute() {
            repository.remove(id)
        }
    }

    data class SaveChange(
        val repository: DogRepository
    ): AdministratorCommands{
        override fun execute() {
            repository.saveChanges()
        }
    }
}