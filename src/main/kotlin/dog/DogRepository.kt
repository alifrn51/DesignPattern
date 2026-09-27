package dog

import kotlinx.serialization.json.Json
import observer.MutableObservable
import observer.Observable
import observer.Observer
import java.io.File

class DogRepository private constructor() {

    init {
        println("Create DogRepository...!")
    }
    private val file = File("dogs.json")

    private val _dogs = getAllDogs()

    val dogs = MutableObservable(_dogs.toList())

    private fun getAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())


    fun saveChanges() {
        file.writeText(Json.encodeToString(_dogs))
    }

    fun remove(id: Int) {
        _dogs.removeIf { it.id == id }
        dogs.currentValue = _dogs.toList()
    }

    fun add(breedName: String, dogName: String, weight: Int) {
        val id = _dogs.maxOf { it.id } + 1
        val dog = Dog(id, breedName, dogName, weight)
        _dogs.add(dog)
        dogs.currentValue = _dogs.toList()
    }


    companion object {

        private val lock = Any()
        private var instance: DogRepository? = null

        fun getInstance(password: String): DogRepository {

            if (password != File("dog_password.txt").readText().trim())
                throw IllegalArgumentException("Wrong password")

            instance?.let { return it }

            synchronized(lock) {
                instance?.let { return it }

                return DogRepository().also {
                    instance = it
                }
            }
        }
    }


}