package users

fun main() {

    val users = UserRepository.getInstance("sfeofi").user.forEach(::println)

}