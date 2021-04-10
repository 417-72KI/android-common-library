package jp.room417.common.liveData

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import java.util.concurrent.atomic.AtomicBoolean

open class SingleLiveData<T> : LiveData<T>() {
    protected val source = MutableLiveData<T>()
    private val firstIgnore = AtomicBoolean(true)

    fun observe(owner: LifecycleOwner, observer: ((T) -> Unit)) {
        source.observe(owner, Observer {
            if (firstIgnore.getAndSet(false)) {
                return@Observer
            }
            it?.let { observer(it) }
        })
    }

    override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
        observe(owner) { observer.onChanged(it) }
    }
}
