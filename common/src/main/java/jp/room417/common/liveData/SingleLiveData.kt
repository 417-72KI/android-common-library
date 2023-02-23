package jp.room417.common.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveData<T>(initialValue: T? = null) : LiveData<T>() {
    private val source = MutableLiveData<T>().apply {
        setValue(initialValue)
    }
    private val firstIgnore = AtomicBoolean(true)

    @Suppress("MemberVisibilityCanBePrivate")
    fun observe(owner: LifecycleOwner, observer: ((T) -> Unit)) =
        source.observe(
            owner,
            Observer {
                if (firstIgnore.getAndSet(false)) {
                    return@Observer
                }
                it?.let { observer(it) }
            },
        )

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) =
        observe(owner) { observer.onChanged(it) }

    @Suppress("MemberVisibilityCanBePrivate")
    fun observeForever(observer: ((T) -> Unit)) =
        source.observeForever(
            Observer {
                if (firstIgnore.getAndSet(false)) {
                    return@Observer
                }
                it?.let { observer(it) }
            },
        )

    override fun observeForever(observer: Observer<in T>) =
        observeForever { observer.onChanged(it) }

    override fun getValue(): T? = source.value

    override fun setValue(value: T?) {
        source.value = value
    }

    override fun postValue(value: T) = source.postValue(value)

    override fun hasObservers(): Boolean = source.hasObservers()
    override fun hasActiveObservers(): Boolean = source.hasActiveObservers()
    override fun hashCode(): Int = source.hashCode()
    override fun equals(other: Any?): Boolean = source == other
}
