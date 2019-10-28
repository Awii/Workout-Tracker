package no.hiof.edgarass.workouttracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedpref = PreferenceManager.getDefaultSharedPreferences(this)
        if (sharedpref.getBoolean("dark_theme", false)) { // if true -> enabled
            setTheme(R.style.DarkTheme)
        } else {
            setTheme(R.style.LightTheme)
        }


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

            val weekdays = arrayOf(
                resources.getString(R.string.monday),
                resources.getString(R.string.tuesday),
                resources.getString(R.string.wednesday),
                resources.getString(R.string.thursday),
                resources.getString(R.string.friday),
                resources.getString(R.string.saturday),
                resources.getString(R.string.sunday)
            )

            editor.putStringSet("exercise_daysA", weekdays.toSet())
            editor.apply()
        }
    }
}
