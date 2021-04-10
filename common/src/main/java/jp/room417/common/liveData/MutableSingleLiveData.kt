package jp.room417.common.liveData

class MutableSingleLiveData<T> : SingleLiveData<T>() {
    override fun getValue(): T? = source.value
    public override fun setValue(value: T?) = value.let { source.value = it }

    @get:JvmName("_getValue")
    @set:JvmName("_setValue")
    var value: T?
        get() = getValue()
        set(value) = setValue(value)
}
