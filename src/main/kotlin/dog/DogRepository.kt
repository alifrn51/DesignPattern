package dog

import kotlinx.serialization.json.Json
import users.User
import java.io.File

class DogRepository private constructor() {

    private val file = File("dogs.json")

    private val _dogs = getAllDogs()
    val dogs
        get() = _dogs.toList()

    private fun getAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())

    private val observers = mutableListOf<Display>()

    fun registerObserver(observer: Display){
        observers.add(observer)
        observer.onChange(_dogs)
    }

    private fun notifyObservers() {
        for (observer in observers) {
            observer.onChange(_dogs)
        }
    }

    fun saveChanges() {
        file.writeText(Json.encodeToString(_dogs))
    }

    fun remove(id: Int) {
        _dogs.removeIf { it.id == id }
        notifyObservers()
    }

    fun add(breedName: String, dogName: String, weight: Int) {
        val id = _dogs.maxOf { it.id } + 1
        val dog = Dog(id, breedName, dogName, weight)
        _dogs.add(dog)
        notifyObservers()
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