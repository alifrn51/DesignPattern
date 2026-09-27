package dog

import kotlinx.serialization.json.Json
import observer.Observable
import observer.Observer
import java.io.File

class DogRepository private constructor(): Observable<List<Dog>> {

    init {
        println("Create DogRepository...!")
    }
    private val file = File("dogs.json")

    private val _dogs = getAllDogs()

    override val currentValue: List<Dog>
        get() = _dogs.toList()

    private val _observers = mutableListOf<Observer<List<Dog>>>()
    override val observers
        get() = _observers.toList()

    private fun getAllDogs(): MutableList<Dog> = Json.decodeFromString(file.readText().trim())

    override fun registerObserver(observer: Observer<List<Dog>>) {
        _observers.add(observer)
        observer.onChange(currentValue)
    }

    override fun unregisterObserver(observer: Observer<List<Dog>>) {
        _observers.remove(observer)
    }

    fun addOnDogsChangedListener(observer: Observer<List<Dog>>){
        _observers.add(observer)
        observer.onChange(_dogs)
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