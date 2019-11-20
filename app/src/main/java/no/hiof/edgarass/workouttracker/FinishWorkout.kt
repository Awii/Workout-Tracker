package no.hiof.edgarass.workouttracker


import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_finish_workout.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.model.Exercise
import java.util.*
import kotlin.collections.ArrayList
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.finish_workout_list_item.*
import no.hiof.edgarass.workouttracker.adapter.FinishWorkoutAdapter


class FinishWorkout : Fragment() {
    private var exerciseList : ArrayList<Exercise> = Exercise.getExercises()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_finish_workout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val supportBar = (activity as AppCompatActivity).supportActionBar
        supportBar?.show()
        supportBar?.title = "Finish workout" // TODO R.string
        supportBar?.setDisplayHomeAsUpEnabled(true)


        floating_action_button_finish_workout_back.setOnClickListener {
            //activity!!.onBackPressed()
        }


        setUpRecycleView()

        /*val fabConfirm = floating_action_button_finish_workout_confirm
        fabConfirm.isEnabled = false
        fabConfirm.alpha = 0f
        fabConfirm.setOnClickListener {

        }*/

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.finish_workout_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Hide keyboard if it's open
                view!!.clearFocus()
                activity!!.onBackPressed()
                return true
            }
            R.id.confirm_button -> {
                updateExercises()
                // Hide keyboard if it's open
                view!!.clearFocus()
                activity!!.onBackPressed()
                Toast.makeText(activity, "Updated successfully", Toast.LENGTH_SHORT).show()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun updateExercises() {
        val db = AppDatabase.getInstance(activity!!)!!.exerciseDao()
        for (ex in exerciseList) {
            db.updateWeight(
                ex.name,
                ex.weight + ex.increment,
                ex.increment
            )
        }
    }

    private fun setUpRecycleView() {
        finish_workout_recycler_view.adapter = FinishWorkoutAdapter(exerciseList)
        finish_workout_recycler_view.layoutManager = LinearLayoutManager(context)

        // Find current weekday
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

        // Fill exerciseList with exercises from db
        exerciseList.clear()
        for (ex in db.getAll()) {
            if (ex.routine == exDay) {
                exerciseList.add(Exercise(ex.routine!!, ex.name!!, ex.sets!!, ex.reps!!, ex.weight!!, ex.unit!!, ex.increment!!))
            }
        }
    }
}
