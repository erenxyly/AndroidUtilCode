package com.xuyang.utilcode.util

import android.content.Context
import android.content.SharedPreferences
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

/**
 * Created by XuYang
 * 2019-11-04
 * Email:544066591@qq.com
 */
object SPUtils {

    private val defaultName = "spUtils"

    @JvmStatic
    fun put(context: Context, key: String, `object`: Any, name: String) {
        var mName = name;
        if (isSpace(mName)) mName = defaultName

        val sp = context.getSharedPreferences(
            mName,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()

        if (`object` is String) {
            editor.putString(key, `object`)
        } else if (`object` is Int) {
            editor.putInt(key, `object`)
        } else if (`object` is Boolean) {
            editor.putBoolean(key, `object`)
        } else if (`object` is Float) {
            editor.putFloat(key, `object`)
        } else if (`object` is Long) {
            editor.putLong(key, `object`)
        } else {
            editor.putString(key, `object`.toString())
        }

        SharedPreferencesCompat.apply(editor)
    }

    @JvmStatic
    operator fun get(context: Context, key: String, defaultObject: Any, name: String): Any? {
        var mName = name;
        if (isSpace(mName)) mName = defaultName
        val sp = context.getSharedPreferences(
            mName,
            Context.MODE_PRIVATE
        )

        if (defaultObject is String) {
            return sp.getString(key, defaultObject)
        } else if (defaultObject is Int) {
            return sp.getInt(key, defaultObject)
        } else if (defaultObject is Boolean) {
            return sp.getBoolean(key, defaultObject)
        } else if (defaultObject is Float) {
            return sp.getFloat(key, defaultObject)
        } else if (defaultObject is Long) {
            return sp.getLong(key, defaultObject)
        }
        return null
    }

    @JvmStatic
    fun getString(context: Context, key: String, defaultObject: Any, name: String): String? {
        var mName = name;
        if (isSpace(mName)) mName = defaultName
        val sp = context.getSharedPreferences(
            mName,
            Context.MODE_PRIVATE
        )
        return if (defaultObject is String) {
            sp.getString(key, defaultObject)
        } else {
            sp.getString(key, null)
        }
    }

    @JvmStatic
    fun remove(context: Context, key: String, name: String) {
        var mName = name;
        if (isSpace(mName)) mName = defaultName
        val sp = context.getSharedPreferences(
            mName,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.remove(key)
        SharedPreferencesCompat.apply(editor)
    }

    @JvmStatic
    fun clear(context: Context, name: String) {
        var mName = name;
        if (isSpace(mName)) mName = defaultName
        val sp = context.getSharedPreferences(
            mName,
            Context.MODE_PRIVATE
        )
        val editor = sp.edit()
        editor.clear()
        SharedPreferencesCompat.apply(editor)
    }

    @JvmStatic
    fun contains(context: Context, key: String, name: String): Boolean {
        var mName = name;
        if (isSpace(mName)) mName = defaultName
        val sp = context.getSharedPreferences(
            mName,
            Context.MODE_PRIVATE
        )
        return sp.contains(key)
    }

    @JvmStatic
    fun getAll(context: Context, name: String): Map<String, *> {
        var mName = name;
        if (isSpace(mName)) mName = defaultName
        val sp = context.getSharedPreferences(
            mName,
            Context.MODE_PRIVATE
        )
        return sp.all
    }

    // 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
    private object SharedPreferencesCompat {

        private val sApplyMethod =
            findApplyMethod()

        //反射查找apply的方法
        private fun findApplyMethod(): Method? {
            try {
                val clz = SharedPreferences.Editor::class.java
                return clz.getMethod("apply")
            } catch (e: NoSuchMethodException) {
                e.printStackTrace()
            }

            return null
        }

        //如果找到则使用apply执行，否则使用commit
        internal fun apply(editor: SharedPreferences.Editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor)
                    return
                }
            } catch (e: IllegalArgumentException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            } catch (e: InvocationTargetException) {
                e.printStackTrace()
            }

            editor.commit()
        }
    }

    private fun isSpace(s: String?): Boolean {
        if (s == null) return true
        var i = 0
        val len = s.length
        while (i < len) {
            if (!Character.isWhitespace(s[i])) {
                return false
            }
            ++i
        }
        return true
    }
}