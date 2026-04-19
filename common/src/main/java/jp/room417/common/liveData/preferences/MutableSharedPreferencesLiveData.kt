package jp.room417.common.liveData.preferences

import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.lifecycle.MutableLiveData

abstract class MutableSharedPreferencesLiveData<Key, Value>(
    val pref: SharedPreferences,
    val key: Key,
    val default: Value,
) : MutableLiveData<Value>() {
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { pref, key ->
        if (key == this.key) {
            value = getValue(pref, key, default)
        }
    }

    abstract fun getValue(pref: SharedPreferences, key: Key, default: Value): Value
    abstract fun updateValue(pref: SharedPreferences, key: Key, value: Value?)

    override fun onActive() {
        super.onActive()
        value = getValue(pref, key, default)
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onInactive() {
        pref.unregisterOnSharedPreferenceChangeListener(listener)
        super.onInactive()
    }

    override fun setValue(value: Value?) {
        super.setValue(value)
        updateValue(pref, key, value)
    }
}

// Concrete implementations for different data types
class MutableSharedPreferencesIntLiveData<Key>(pref: SharedPreferences, key: Key, default: Int) :
    MutableSharedPreferencesLiveData<Key, Int>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Int): Int = pref.getInt(key.toString(), default)
    override fun updateValue(pref: SharedPreferences, key: Key, value: Int?) = pref.edit { value?.let { putInt(key.toString(), value) } ?: remove(key.toString()) }
}

class MutableSharedPreferencesStringLiveData<Key>(pref: SharedPreferences, key: Key, default: String) :
    MutableSharedPreferencesLiveData<Key, String>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: String): String = pref.getString(key.toString(), default) ?: default
    override fun updateValue(pref: SharedPreferences, key: Key, value: String?) = pref.edit { value?.let { putString(key.toString(), it) } ?: remove(key.toString()) }
}

class MutableSharedPreferencesBooleanLiveData<Key>(pref: SharedPreferences, key: Key, default: Boolean) :
    MutableSharedPreferencesLiveData<Key, Boolean>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Boolean): Boolean = pref.getBoolean(key.toString(), default)
    override fun updateValue(pref: SharedPreferences, key: Key, value: Boolean?) = pref.edit { value?.let { putBoolean(key.toString(), it) } ?: remove(key.toString()) }
}

class MutableSharedPreferencesFloatLiveData<Key>(pref: SharedPreferences, key: Key, default: Float) :
    MutableSharedPreferencesLiveData<Key, Float>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Float): Float = pref.getFloat(key.toString(), default)
    override fun updateValue(pref: SharedPreferences, key: Key, value: Float?) = pref.edit { value?.let { putFloat(key.toString(), it) } ?: remove(key.toString()) }
}

class MutableSharedPreferencesLongLiveData<Key>(pref: SharedPreferences, key: Key, default: Long) :
    MutableSharedPreferencesLiveData<Key, Long>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Long): Long = pref.getLong(key.toString(), default)
    override fun updateValue(pref: SharedPreferences, key: Key, value: Long?) = pref.edit { value?.let { putLong(key.toString(), it) } ?: remove(key.toString()) }
}

class MutableSharedPreferencesStringSetLiveData<Key>(pref: SharedPreferences, key: Key, default: Set<String>) :
    MutableSharedPreferencesLiveData<Key, Set<String>>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Set<String>): Set<String> = pref.getStringSet(key.toString(), default) ?: default
    override fun updateValue(pref: SharedPreferences, key: Key, value: Set<String>?) = pref.edit { value?.let { putStringSet(key.toString(), it) } ?: remove(key.toString()) }
}

// Extension functions for creating LiveData instances
fun <Key> SharedPreferences.mutableIntLiveData(key: Key, default: Int) = MutableSharedPreferencesIntLiveData(this, key, default)

fun <Key> SharedPreferences.mutableStringLiveData(key: Key, default: String) = MutableSharedPreferencesStringLiveData(this, key, default)

fun <Key> SharedPreferences.mutableBooleanLiveData(key: Key, default: Boolean) = MutableSharedPreferencesBooleanLiveData(this, key, default)

fun <Key> SharedPreferences.mutableFloatLiveData(key: Key, default: Float) = MutableSharedPreferencesFloatLiveData(this, key, default)

fun <Key> SharedPreferences.mutableLongLiveData(key: Key, default: Long) = MutableSharedPreferencesLongLiveData(this, key, default)

fun <Key> SharedPreferences.mutableStringSetLiveData(key: Key, default: Set<String>) = MutableSharedPreferencesStringSetLiveData(this, key, default)

// Overloads for String keys
fun SharedPreferences.mutableIntLiveData(key: String, default: Int) = MutableSharedPreferencesIntLiveData(this, key, default)

fun SharedPreferences.mutableStringLiveData(key: String, default: String) = MutableSharedPreferencesStringLiveData(this, key, default)

fun SharedPreferences.mutableBooleanLiveData(key: String, default: Boolean) = MutableSharedPreferencesBooleanLiveData(this, key, default)

fun SharedPreferences.mutableFloatLiveData(key: String, default: Float) = MutableSharedPreferencesFloatLiveData(this, key, default)

fun SharedPreferences.mutableLongLiveData(key: String, default: Long) = MutableSharedPreferencesLongLiveData(this, key, default)

fun SharedPreferences.mutableStringSetLiveData(key: String, default: Set<String>) = MutableSharedPreferencesStringSetLiveData(this, key, default)
