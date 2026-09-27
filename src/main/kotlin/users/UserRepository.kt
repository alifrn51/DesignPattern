package users

import kotlinx.serialization.json.Json
import observer.MutableObservable
import observer.Observable
import observer.Observer
import java.io.File

class UserRepository private constructor(){

    init {
        println("Create UserRepository...!")
    }

    private val file = File("users.json")

    private val _users = getAllUser()

    val users = MutableObservable(_users.toList())
    val oldestUsers = MutableObservable(_users.maxBy { it.age })

    private fun getAllUser(): MutableList<User> = Json.decodeFromString(file.readText().trim())


    fun add(firstName: String, lastName: String, age: Int) {
        val id = _users.maxOf { it.id } + 1
        val user = User(id = id, firstName = firstName, lastName = lastName, age = age)
        _users.add(user)
        users.currentValue = _users.toList()
        if(age > oldestUsers.currentValue.age){
            oldestUsers.currentValue = user
        }
    }

    fun remove(id: Int) {
        _users.removeIf { it.id == id }
        users.currentValue = _users.toList()
        val newOldest = _users.maxBy { it.age }
        if(newOldest.age != oldestUsers.currentValue.age){
            oldestUsers.currentValue = newOldest
        }
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