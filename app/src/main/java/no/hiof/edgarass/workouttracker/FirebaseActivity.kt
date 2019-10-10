package no.hiof.edgarass.workouttracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class FirebaseActivity : AppCompatActivity() {

    //TODO: implement firebase for holding users and transfering data

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase)
        supportFragmentManager
            .beginTransaction()
            .commit()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

}