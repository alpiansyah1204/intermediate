package id.whynot.submission3.preference

import android.content.Context
import id.whynot.submission3.model.modelUserPreferences

internal class userprefreference(context: Context)  {
    companion object {
        private const val PREFS_NAME = "user_pref"
        private const val NAME = "name"
        private const val TOKEN = "token"

    }
    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    fun setUser(value: modelUserPreferences) {
        val editor = preferences.edit()
        editor.putString(NAME, value.name)
        editor.putString(TOKEN, value.token)

        editor.apply()
    }
    fun getUser(): modelUserPreferences {
        val model = modelUserPreferences()
        model.name = preferences.getString(NAME, "")
        model.token = preferences.getString(TOKEN, "")

        return model
    }
}