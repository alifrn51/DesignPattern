package users

import kotlinx.serialization.json.Json
import java.io.File

class UserRepository private constructor() {

    private val file = File("users.json")

    private val _users  = getAllUser()
    val users
        get() = _users.toList()

    private fun getAllUser(): MutableList<User> = Json.decodeFromString(file.readText().trim())



    companion object{

        private val lock = Any()
        private var instance : UserRepository? = null

        fun getInstance(password: String): UserRepository {

            if (password != File("user_password.txt").readText().trim())
                throw IllegalArgumentException("Wrong password")

            instance?.let { return it }

            synchronized(lock){
                instance?.let { return it }

                return UserRepository().also {
                    instance = it
                }
            }
        }
    }


}