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

        firstLaunch()
        // placeholder exercsises
        addExercises()
        //firstLaunch()
    }

    /*override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //menuInflater.inflate(R.menu.menu_settings, menu)
        return true
    }*/



    fun firstLaunch() {
        // TODO
        val PREF_NAME = "first-launch"
        val sharedpref : SharedPreferences = getSharedPreferences(PREF_NAME, 0)

    }

    fun addExercises() {
        // temp exercises

        /*
        // margin to top
        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 30, 0, 0)


        val ex1 = TextView(this)
        ex1.text = "Sq 3x5 15kg"
        ex1.textSize = 30f
        ex1.gravity = Gravity.CENTER
        ex1.layoutParams = params
        //main_layout.addView(ex1)

        val dl = Exerciseee("Dl", 3, 5, 40, "kg")
        val ex3 = TextView(this)
        ex3.text = dl.toString()
        ex3.textSize = 30f
        ex3.gravity = Gravity.CENTER
        ex3.layoutParams = params
        //main_layout.addView(ex3)
         */
    }
}
