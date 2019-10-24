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
    }
}
