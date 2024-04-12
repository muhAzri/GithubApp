package com.muhazri.githubapp.data.prefferences

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate

object SharedPreferencesManager {
    private const val PREF_NAME = "MyPrefs"
    private const val IS_DARK_MODE = "isDarkMode"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    fun setIsDarkMode(context: Context, isDarkMode: Boolean) {
        val editor = getSharedPreferences(context).edit()
        editor.putBoolean(IS_DARK_MODE, isDarkMode)
        editor.apply()
    }

    fun getIsDarkMode(context: Context): Boolean? {
        val sharedPreferences = getSharedPreferences(context)
        return if (sharedPreferences.contains(IS_DARK_MODE)) {
            sharedPreferences.getBoolean(IS_DARK_MODE, false)
        } else {
            null
        }
    }

    fun applyDarkMode(context: Context) {
        val isDarkMode = getIsDarkMode(context)
        println(isDarkMode)
        when (isDarkMode) {
            true -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            false -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        if (context is Activity) {
            context.recreate()
        }
    }
}
