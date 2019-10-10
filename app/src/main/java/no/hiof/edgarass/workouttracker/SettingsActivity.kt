package no.hiof.edgarass.workouttracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    //TODO: implement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        supportFragmentManager
            .beginTransaction()
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}