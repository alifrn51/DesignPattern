package observer

class MutableObservable<T>(initialValue: T) : Observable<T> {

    private val _observers = mutableListOf<Observer<T>>()
    override val observers: List<Observer<T>>
        get() = _observers.toList()

    override var currentValue: T = initialValue
        set(value) {
            field = value
            notifyObservers()
        }

    override fun registerObserver(observer: Observer<T>) {
        _observers.add(observer)
        observer.onChange(currentValue)
    }

    override fun unregisterObserver(observer: Observer<T>) {
        _observers.remove(observer)
    }

}