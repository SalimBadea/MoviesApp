package com.creditfins.moviesApp.helper

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import com.creditfins.moviesApp.common.domain.model.Movie
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.creditfins.moviesApp.common.domain.model.User
import java.lang.reflect.Type
import java.util.*


object SharedPreferencesManager {
    private lateinit var prefs: SharedPreferences
    private const val PREFS_NAME = "params"
    private const val TOKEN = "TOKEN"
    private const val FIREBASE_TOKEN = "FIREBASE_TOKEN"
    private const val LANGUAGE = "language"
    private const val CLASS = "class"
    private const val USER = "user"
    private const val PAGE_COUNT = "page_count"

    fun init(context: Context) {
        prefs = context.getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
    }

    fun getToken(): String? = prefs.getString(TOKEN, "")

    fun saveToken(value: String?) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(TOKEN, value)
            commit()
        }
    }

    fun getFireBaseToken(): String = prefs.getString(FIREBASE_TOKEN, "")!!

    fun saveFireBaseToken(value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(FIREBASE_TOKEN, value)
            commit()
        }
    }

    fun saveLanguage(value: String) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(LANGUAGE, value)
            commit()
        }
    }

    fun getLanguage(): String = prefs.getString(LANGUAGE, "")!!

    fun isLang(value: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putBoolean("is_lang", value)
            commit()
        }
    }

    fun saveUser(user: User) {
        val gson = Gson()
        val json = gson.toJson(user)
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(USER, json)
            commit()
        }
    }

    fun getUser(): User {
        val gson = Gson()
        val json = prefs.getString(USER, "")
        val type: Type = object : TypeToken<User>() {}.type
        return gson.fromJson(json, type) ?: User()
    }

    fun saveSkip(skip: Boolean) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putBoolean("is_skip", skip)
            commit()
        }
    }

    fun isSkip(): Boolean = prefs.getBoolean("is_skip", false)

    fun isLang(): Boolean = prefs.getBoolean("is_lang", true)


    fun savePageCount(count: Int) {
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putInt(PAGE_COUNT, count)
            commit()
        }
    }

    fun getPageCount(): Int = prefs.getInt(PAGE_COUNT, 20)


    private fun <T> saveObject(key: String, obj: T) {
        val gson = Gson()
        val json = gson.toJson(obj)
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, json)
            commit()
        }
    }

    private fun <T> getObject(key: String): T? {
        val gson = Gson()
        val json = prefs.getString(key, "")
        val type: Type = object : TypeToken<T>() {}.type
        return gson.fromJson(json, type)
    }

     fun <T> saveList(key: String, list: MutableList<Movie>) {
        val gson = Gson()
        val json = gson.toJson(list)
        val prefsEditor: SharedPreferences.Editor = prefs.edit()
        with(prefsEditor) {
            putString(key, json)
            commit()
        }
    }

     fun <T> getList(key: String): MutableList<T> {
        val gson = Gson()
        val json = prefs.getString(key, "")
        val type: Type = object : TypeToken<MutableList<T>>() {}.type
        return gson.fromJson(json, type)
    }

    inline fun <reified T> fromJson(json: String): MutableList<T> {
        return Gson().fromJson(json, object : TypeToken<MutableList<T>>() {}.type)
    }

    fun setLocal(context: Context) {
        val myLocal = Locale(getLanguage())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Locale.setDefault(Locale.forLanguageTag(getLanguage()))
        }
        val res = context.resources
        val dm = res.displayMetrics
        val conf = res.configuration
        conf.locale = myLocal

        res.updateConfiguration(conf, dm)
        conf.setLayoutDirection(myLocal)
    }
}