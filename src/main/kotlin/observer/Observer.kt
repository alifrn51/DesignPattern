package observer

interface Observer<T> {
    fun onChange(newValue: T)
}