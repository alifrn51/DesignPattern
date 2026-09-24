package users

import kotlinx.serialization.json.Json
import java.io.File

class UserRepository private constructor() {


    private val file = File("users.json")

    private val _users  = getAllUser()
    val user
        get() = _users.toList()

    private fun getAllUser(): MutableList<User> = Json.decodeFromString(file.readText().trim())



    companion object{

        private var instance : UserRepository? = null

        fun getInstance(password: String): UserRepository {

            if (password != File("user_password.txt").readText().trim())
                throw IllegalArgumentException("Wrong password")

            if(instance == null)
                instance = UserRepository()

            return instance!!
        }
    }


}