package jp.room417.common.util

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

/**
 * Wrapper of `SharedPreferences`
 *
 * This class can be initialized only after context created ( = after `onCreate()` called).
 *
 * @param context Created context
 */
class PrefSys(context: Context, identifier: String? = null) {
    internal val pref: SharedPreferences
    internal val sys: SharedPreferences

    init {
        val appName = identifier ?: context.applicationInfo.packageName
        Log.d(javaClass.name, "Initialize PrefSys with identifier: \"${appName}\".")
        pref = context.getSharedPreferences("${appName}.pref", Context.MODE_PRIVATE)
        sys = context.getSharedPreferences("${appName}.sys", Context.MODE_PRIVATE)
    }

    fun setPrefInt(label: String, put: Int) = pref.edit().run {
        putInt(label, put)
        apply()
    }

    fun setPrefString(label: String, put: String) = pref.edit().run {
        putString(label, put)
        apply()
    }

    fun setPrefBoolean(label: String, put: Boolean) = pref.edit().run {
        putBoolean(label, put)
        apply()
    }

    fun setSysInt(label: String, put: Int) = sys.edit().run {
        putInt(label, put)
        apply()
    }

    fun setSysFloat(label: String, put: Float) = sys.edit().run {
        putFloat(label, put)
        apply()
    }

    fun setSysString(label: String, put: String) = sys.edit().run {
        putString(label, put)
        apply()
    }

    fun setSysLong(label: String, put: Long) = sys.edit().run {
        putLong(label, put)
        apply()
    }

    fun setSysBoolean(label: String, put: Boolean) = sys.edit().run {
        putBoolean(label, put)
        apply()
    }

    fun removePref(label: String) = pref.edit().run {
        remove(label)
        apply()
    }

    fun removeSys(label: String) = sys.edit().run {
        remove(label)
        apply()
    }

    fun getPrefInt(label: String, def: Int = 0): Int = pref.getInt(label, def)

    fun getPrefString(label: String, def: String? = null): String? = pref.getString(label, def)

    fun getPrefBoolean(label: String, def: Boolean = false): Boolean = pref.getBoolean(label, def)

    fun getSysInt(label: String, def: Int = 0): Int = sys.getInt(label, def)

    fun getSysFloat(label: String, def: Float = 0f): Float = sys.getFloat(label, def)

    fun getSysLong(label: String, def: Long = 0): Long = sys.getLong(label, def)

    fun getSysBoolean(label: String, def: Boolean = false): Boolean = sys.getBoolean(label, def)

    fun getSysString(label: String, def: String? = null): String? = sys.getString(label, def)
}
