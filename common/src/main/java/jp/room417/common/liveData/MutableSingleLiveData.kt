package jp.room417.common.liveData

class MutableSingleLiveData<T>(initialValue: T? = null) : SingleLiveData<T>(initialValue) {
    public override fun setValue(value: T?) = super.setValue(value)

    @get:JvmName("_getValue")
    @set:JvmName("_setValue")
    var value: T?
        get() = getValue()
        set(value) = setValue(value)
}
