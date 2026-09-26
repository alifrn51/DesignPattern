package users

import kotlinx.serialization.json.Json
import java.io.File

class UserRepository private constructor() {

    init {
        println("Create UserRepository...!")
    }

    private val file = File("users.json")

    private val _users = getAllUser()
    val users
        get() = _users.toList()

    private fun getAllUser(): MutableList<User> = Json.decodeFromString(file.readText().trim())

    private val observers = mutableListOf<Display>()
    private fun notifyObservers(){
        for (observer in observers){
            observer.onChange(_users)
        }
    }

    fun registerObserver(observer: Display){
        observers.add(observer)
        observer.onChange(_users)
    }

    fun add(firstName: String, lastName: String, age: Int) {
        val id = _users.maxOf { it.id } + 1
        val user = User(id = id, firstName = firstName, lastName = firstName, age = age)
        _users.add(user)
        notifyObservers()
    }

    fun remove(id: Int) {
        _users.removeIf { it.id == id }
        notifyObservers()
    }

    fun saveChanges() {
        file.writeText(Json.encodeToString(_users))
    }



    companion object {

        private val lock = Any()
        private var instance: UserRepository? = null

        fun getInstance(password: String): UserRepository {

            if (password != File("user_password.txt").readText().trim())
                throw IllegalArgumentException("Wrong password")

            instance?.let { return it }

            synchronized(lock) {
                instance?.let { return it }

                return UserRepository().also {
                    instance = it
                }
            }
        }
    }


}