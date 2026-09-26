package dog

import kotlinx.serialization.json.Json
import users.User
import java.io.File

class DogRepository private constructor() {

    private val file = File("dogs.json")

    private val _dogs  = getAllDogs()
    val dogs
        get() = _dogs.toList()

    private fun getAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())



    companion object{

        private val lock = Any()
        private var instance : DogRepository? = null

        fun getInstance(password: String): DogRepository {

            if (password != File("dog_password.txt").readText().trim())
                throw IllegalArgumentException("Wrong password")

            instance?.let { return it }

            synchronized(lock){
                instance?.let { return it }

                return DogRepository().also {
                    instance = it
                }
            }
        }
    }


}