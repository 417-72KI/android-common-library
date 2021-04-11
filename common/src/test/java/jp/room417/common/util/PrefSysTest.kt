package jp.room417.common.util

import android.content.Context
import android.content.SharedPreferences
import com.nhaarman.mockito_kotlin.doAnswer
import com.nhaarman.mockito_kotlin.eq
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito

internal class PrefSysTest {
    private lateinit var prefMock: SharedPreferences
    private lateinit var prefEditMock: SharedPreferences.Editor
    private lateinit var sysMock: SharedPreferences
    private lateinit var sysEditMock: SharedPreferences.Editor
    private lateinit var prefSys: PrefSys

    @BeforeEach
    fun setUp() {
        prefMock = Mockito.mock(SharedPreferences::class.java)
        prefEditMock = Mockito.mock(SharedPreferences.Editor::class.java)
        sysMock = Mockito.mock(SharedPreferences::class.java)
        sysEditMock = Mockito.mock(SharedPreferences.Editor::class.java)

        val context = Mockito.mock(Context::class.java)
        Mockito.`when`(context.getSharedPreferences(eq("PrefSysTest.pref"), anyInt()))
            .thenReturn(prefMock)
        Mockito.`when`(context.getSharedPreferences(eq("PrefSysTest.pref"), anyInt()).edit())
            .thenReturn(prefEditMock)
        Mockito.`when`(context.getSharedPreferences(eq("PrefSysTest.sys"), anyInt()))
            .thenReturn(sysMock)
        Mockito.`when`(context.getSharedPreferences(eq("PrefSysTest.sys"), anyInt()).edit())
            .thenReturn(sysEditMock)

        setupEdit(prefMock, prefEditMock)
        setupEdit(sysMock, sysEditMock)

        prefSys = PrefSys(context, "PrefSysTest")
        assertNotNull(prefSys.pref)
        assertNotNull(prefSys.sys)
    }

    // pref
    @Test
    fun prefString() {
        val key = "key"
        assertNull(prefMock.getString(key, null))
        assertNull(prefSys.getPrefString(key))

        prefSys.setPrefString(key, "foo")

        assertEquals(prefMock.getString(key, null), "foo")
        assertEquals(prefSys.getPrefString(key), "foo")
    }

    @Test
    fun prefInt() {
        val key = "key"
        assertEquals(prefMock.getInt(key, 0), 0)
        assertEquals(prefSys.getPrefInt(key), 0)

        prefSys.setPrefInt(key, 114514)

        assertEquals(prefMock.getInt(key, 0), 114514)
        assertEquals(prefSys.getPrefInt(key), 114514)
    }

    @Test
    fun prefBoolean() {
        val key = "key"
        assertFalse(prefMock.getBoolean(key, false))
        assertFalse(prefSys.getPrefBoolean(key))

        prefSys.setPrefBoolean(key, true)

        assertTrue(prefMock.getBoolean(key, false))
        assertTrue(prefSys.getPrefBoolean(key))
    }

    @Test
    fun removePref() {
        val key = "key"
        prefEditMock.putString(key, "bar")

        assertEquals(prefMock.getString(key, null), "bar")
        assertEquals(prefSys.getPrefString(key), "bar")

        prefSys.removePref(key)

        assertNull(prefMock.getString(key, null))
        assertNull(prefSys.getPrefString(key))
    }

    // sys
    @Test
    fun sysString() {
        val key = "key"
        assertNull(sysMock.getString(key, null))
        assertNull(prefSys.getSysString(key))

        prefSys.setSysString(key, "foo")

        assertEquals(sysMock.getString(key, null), "foo")
        assertEquals(prefSys.getSysString(key), "foo")
    }

    @Test
    fun sysInt() {
        val key = "key"
        assertEquals(sysMock.getInt(key, 0), 0)
        assertEquals(prefSys.getSysInt(key), 0)

        prefSys.setSysInt(key, 114514)

        assertEquals(sysMock.getInt(key, 0), 114514)
        assertEquals(prefSys.getSysInt(key), 114514)
    }

    @Test
    fun sysLong() {
        val key = "key"
        assertEquals(sysMock.getLong(key, 0), 0)
        assertEquals(prefSys.getSysLong(key), 0)

        prefSys.setSysLong(key, 114514)

        assertEquals(sysMock.getLong(key, 0), 114514)
        assertEquals(prefSys.getSysLong(key), 114514)
    }

    @Test
    fun sysFloat() {
        val key = "key"
        assertEquals(sysMock.getFloat(key, 0f), 0f)
        assertEquals(prefSys.getSysFloat(key), 0f)

        prefSys.setSysFloat(key, 3.1415926535f)

        assertEquals(sysMock.getFloat(key, 0f), 3.1415926535f)
        assertEquals(prefSys.getSysFloat(key), 3.1415926535f)
    }

    @Test
    fun sysBoolean() {
        val key = "key"
        assertFalse(sysMock.getBoolean(key, false))
        assertFalse(prefSys.getSysBoolean(key))

        prefSys.setSysBoolean(key, true)

        assertTrue(sysMock.getBoolean(key, false))
        assertTrue(prefSys.getSysBoolean(key))
    }

    @Test
    fun removeSys() {
        val key = "key"
        sysEditMock.putString(key, "bar")

        assertEquals(sysMock.getString(key, null), "bar")
        assertEquals(prefSys.getSysString(key), "bar")

        prefSys.removeSys(key)

        assertNull(sysMock.getString(key, null))
        assertNull(prefSys.getSysString(key))
    }

    @Suppress("UNCHECKED_CAST")
    private fun setupEdit(mock: SharedPreferences, mockEdit: SharedPreferences.Editor) {
        Mockito.`when`(mockEdit.putString(anyString(), anyString())).doAnswer {
            val key = it.arguments[0] as String
            val value = it.arguments[1] as? String
            Mockito.`when`(mock.getString(key, null))
                .thenReturn(value)
            mockEdit
        }
        Mockito.`when`(mockEdit.putStringSet(anyString(), anySet())).doAnswer {
            val key = it.arguments[0] as String
            val value = it.arguments[1] as Set<String>
            Mockito.`when`(mock.getStringSet(key, null))
                .thenReturn(value)
            mockEdit
        }
        Mockito.`when`(mockEdit.putInt(anyString(), anyInt())).doAnswer {
            val key = it.arguments[0] as String
            val value = it.arguments[1] as Int
            Mockito.`when`(mock.getInt(key, 0))
                .thenReturn(value)
            mockEdit
        }
        Mockito.`when`(mockEdit.putLong(anyString(), anyLong())).doAnswer {
            val key = it.arguments[0] as String
            val value = it.arguments[1] as Long
            Mockito.`when`(mock.getLong(key, 0L))
                .thenReturn(value)
            mockEdit
        }
        Mockito.`when`(mockEdit.putFloat(anyString(), anyFloat())).doAnswer {
            val key = it.arguments[0] as String
            val value = it.arguments[1] as Float
            Mockito.`when`(mock.getFloat(key, 0f))
                .thenReturn(value)
            mockEdit
        }
        Mockito.`when`(mockEdit.putBoolean(anyString(), anyBoolean())).doAnswer {
            val key = it.arguments[0] as String
            val value = it.arguments[1] as Boolean
            Mockito.`when`(mock.getBoolean(key, false))
                .thenReturn(value)
            mockEdit
        }
        Mockito.`when`(mockEdit.remove(anyString())).doAnswer {
            val key = it.arguments[0] as String
            Mockito.`when`(mock.getString(key, null))
                .thenReturn(null)
            Mockito.`when`(mock.getStringSet(key, null))
                .thenReturn(null)
            Mockito.`when`(mock.getInt(key, 0))
                .thenReturn(0)
            Mockito.`when`(mock.getLong(key, 0))
                .thenReturn(0)
            Mockito.`when`(mock.getFloat(key, 0F))
                .thenReturn(0F)
            Mockito.`when`(mock.getBoolean(key, false))
                .thenReturn(false)
            mockEdit
        }
    }
}
