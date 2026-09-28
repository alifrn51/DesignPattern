package builder

data class Drink(
    val type: String,
    val additives: List<String>,
    val diningOption: String,
    val temperature: String
) {

    class Builder(
        private var type: String = "Cafe",
        private var additives: List<String> = emptyList(),
        private var diningOption: String = "Go to",
        private var temperature: String = "Hot"
    ){

        fun setType(type: String): Builder{
            this.type = type
            return this
        }

        fun setAdditives (additives: List<String>): Builder{
            this.additives = additives
            return this
        }

        fun setDiningOption(diningOption: String): Builder{
            this.diningOption = diningOption
            return this
        }

        fun setTemperature(temperature: String): Builder{
            this.temperature = temperature
            return this
        }

        fun build(): Drink{
            return Drink(type, additives, diningOption, temperature)
        }
    }
}