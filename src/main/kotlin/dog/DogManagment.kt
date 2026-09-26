package dog

import users.UserRepository

fun main() {

    DogRepository.getInstance("asdfsadf").dogs.forEach(::println)

}