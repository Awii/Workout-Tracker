package no.hiof.edgarass.workouttracker

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        setSupportActionBar(settingsMenu)
        //setupActionBarWithNavController(this, R.id.navHostFragment)

        /*supportActionBar?.title = Calendar.getInstance().getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG,
            Locale.getDefault())
        supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))*/
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }*/

}
