package jp.room417.common.liveData.preferences

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferencesLiveData<Key, Value>(
    internal val pref: SharedPreferences,
    val key: Key,
    val default: Value,
) : LiveData<Value>() {
    private val listener = SharedPreferences.OnSharedPreferenceChangeListener { pref, key ->
        if (key == this.key) {
            value = getValue(pref, key, default)
        }
    }

    abstract fun getValue(pref: SharedPreferences, key: Key, default: Value): Value

    override fun onActive() {
        super.onActive()
        value = getValue(pref, key, default)
        pref.registerOnSharedPreferenceChangeListener(listener)
    }

    override fun onInactive() {
        pref.unregisterOnSharedPreferenceChangeListener(listener)
        super.onInactive()
    }
}

// Concrete implementations for different data types
class SharedPreferencesIntLiveData<Key>(pref: SharedPreferences, key: Key, default: Int) :
    SharedPreferencesLiveData<Key, Int>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Int): Int = pref.getInt(key.toString(), default)
}

class SharedPreferencesStringLiveData<Key>(pref: SharedPreferences, key: Key, default: String) :
    SharedPreferencesLiveData<Key, String>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: String): String = pref.getString(key.toString(), default) ?: default
}

class SharedPreferencesBooleanLiveData<Key>(pref: SharedPreferences, key: Key, default: Boolean) :
    SharedPreferencesLiveData<Key, Boolean>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Boolean): Boolean = pref.getBoolean(key.toString(), default)
}

class SharedPreferencesFloatLiveData<Key>(pref: SharedPreferences, key: Key, default: Float) :
    SharedPreferencesLiveData<Key, Float>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Float): Float = pref.getFloat(key.toString(), default)
}

class SharedPreferencesLongLiveData<Key>(pref: SharedPreferences, key: Key, default: Long) :
    SharedPreferencesLiveData<Key, Long>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Long): Long = pref.getLong(key.toString(), default)
}

class SharedPreferencesStringSetLiveData<Key>(pref: SharedPreferences, key: Key, default: Set<String>) :
    SharedPreferencesLiveData<Key, Set<String>>(pref, key, default) {
    override fun getValue(pref: SharedPreferences, key: Key, default: Set<String>): Set<String> = pref.getStringSet(key.toString(), default) ?: default
}

// Extension functions for creating LiveData instances
fun <Key> SharedPreferences.intLiveData(key: Key, default: Int) = SharedPreferencesIntLiveData(this, key, default)

fun <Key> SharedPreferences.stringLiveData(key: Key, default: String) = SharedPreferencesStringLiveData(this, key, default)

fun <Key> SharedPreferences.booleanLiveData(key: Key, default: Boolean) = SharedPreferencesBooleanLiveData(this, key, default)

fun <Key> SharedPreferences.floatLiveData(key: Key, default: Float) = SharedPreferencesFloatLiveData(this, key, default)

fun <Key> SharedPreferences.longLiveData(key: Key, default: Long) = SharedPreferencesLongLiveData(this, key, default)

fun <Key> SharedPreferences.stringSetLiveData(key: Key, default: Set<String>) = SharedPreferencesStringSetLiveData(this, key, default)

// Overloads for String keys
fun SharedPreferences.intLiveData(key: String, default: Int) = SharedPreferencesIntLiveData(this, key, default)

fun SharedPreferences.stringLiveData(key: String, default: String) = SharedPreferencesStringLiveData(this, key, default)

fun SharedPreferences.booleanLiveData(key: String, default: Boolean) = SharedPreferencesBooleanLiveData(this, key, default)

fun SharedPreferences.floatLiveData(key: String, default: Float) = SharedPreferencesFloatLiveData(this, key, default)

fun SharedPreferences.longLiveData(key: String, default: Long) = SharedPreferencesLongLiveData(this, key, default)

fun SharedPreferences.stringSetLiveData(key: String, default: Set<String>) = SharedPreferencesStringSetLiveData(this, key, default)
