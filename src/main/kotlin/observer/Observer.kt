package observer

fun interface Observer<T> {
    fun onChange(newValue: T)
}