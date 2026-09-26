package users

fun main() {

    UserRepository.getInstance("sfeofi").user.forEach(::println)

}