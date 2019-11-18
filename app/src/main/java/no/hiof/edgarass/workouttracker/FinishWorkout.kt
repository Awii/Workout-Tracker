package no.hiof.edgarass.workouttracker


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NavUtils
import androidx.core.view.marginTop
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_finish_workout.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.model.Exercise
import java.util.*
import kotlin.collections.ArrayList
import android.widget.LinearLayout



class FinishWorkout : Fragment() {
    private var exerciseList : ArrayList<Exercise> = Exercise.getExercises()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_finish_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supportBar = (activity as AppCompatActivity).supportActionBar
        supportBar?.hide()


        floating_action_button_finish_workout_back.setOnClickListener {
            activity!!.onBackPressed()
        }


        addStuff()

        val fabConfirm = floating_action_button_finish_workout_confirm
        fabConfirm.isEnabled = false
        fabConfirm.alpha = 0f
        fabConfirm.setOnClickListener {

        }

    }

    private fun addStuff() {
        val layout = finish_workout_layout


        // Get exercises
        // Add existing exercises from database
        val db = AppDatabase.getInstance(activity!!)!!.exerciseDao()

        val exDaysA = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysA", null)
        val exDaysB = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysB", null)
        val exDaysC = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysC", null)

        val today = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        var exDay = ""

        when {
            exDaysA!!.contains(today) -> exDay = "A"
            exDaysB!!.contains(today) -> exDay = "B"
            exDaysC!!.contains(today) -> exDay = "C"
        }

        exerciseList.clear()
        for (ex in db.getAll()) {
            if (ex.routine == exDay) {
                exerciseList.add(Exercise(ex.routine!!, ex.name!!, ex.sets!!, ex.reps!!, ex.weight!!, ex.unit!!))
            }
        }

        //val params = FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT, FrameLayout.LayoutParams.WRAP_CONTENT)
        //params.setMargins(0, 20, 0, 0)


        for (ex in exerciseList) {
            var tv = TextView(activity)
            tv.textSize = 50f
            tv.text = ex.name
            layout.addView(tv)

        }

        //layout.addView()

    }

}
