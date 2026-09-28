package builder

fun main() {

    val drink = Drink.Builder()
        .setType("Ice")
        .setTemperature("Ali")
        .build()


    println(drink)

}