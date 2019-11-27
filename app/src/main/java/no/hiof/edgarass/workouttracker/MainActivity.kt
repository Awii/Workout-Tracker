package no.hiof.edgarass.workouttracker

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import android.app.Activity
import android.view.View
import android.view.inputmethod.InputMethodManager


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLocale(this)
        setContentView(R.layout.activity_main)
        setSupportActionBar(settingsMenu)
        initSharedPreferences()
    }

    fun initSharedPreferences() {
        val sharedPrefs = PreferenceManager.getDefaultSharedPreferences(this)

        // Initiates default values on first launch
        if (sharedPrefs.getString("different_workouts", null) == null) {
            val editor = sharedPrefs.edit()
            editor.putString("different_workouts", "1")

            val weekdays = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

            // All days by default
            editor.putStringSet("exercise_daysA", weekdays.toSet())
            // nullptr exception
            editor.putStringSet("exercise_daysB", Collections.emptySet())
            editor.putStringSet("exercise_daysC", Collections.emptySet())
            editor.apply()
        }
    }

    companion object {

        fun setLocale(context: Context) {
            val lang = PreferenceManager.getDefaultSharedPreferences(context).getString("language", "en").toString()
            val res = context.resources
            val conf = res.configuration
            Locale.setDefault(Locale(lang))
            conf.setLocale(Locale(lang))
            res.updateConfiguration(Configuration(), res.displayMetrics)
            context.createConfigurationContext(conf)
        }

        fun hideKeyboard(context: Context, view: View) {
            val imm = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

}
