package no.hiof.edgarass.workouttracker

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.adapter.ExerciseAdapter
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.model.Exercise
import java.util.*
import kotlin.collections.ArrayList
import android.view.Gravity


class MainFragment : Fragment() {
    private var exerciseList : ArrayList<Exercise> = Exercise.getExercises()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateExerciseList()

        // Show action bar
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        actionBar?.setDisplayHomeAsUpEnabled(false)
        setHasOptionsMenu(true)

        // Customize action bar title
        val exDaysA = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysA", null)
        val exDaysB = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysB", null)
        val exDaysC = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysC", null)

        val todayLocal = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault()).capitalize()
        val today = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale("en"))
        var exDay = ""

        // Possible to combine multiple workout days into one day
        if (exDaysA!!.contains(today))
            exDay += "A"
        if (exDaysB!!.contains(today))
            exDay += "B"
        if (exDaysC!!.contains(today))
            exDay += "C"

        // Title of action bar = Weekday, A
        var isDay = ""
        if (exDay != "") isDay = ", $exDay"

        actionBar?.title = todayLocal + isDay

        // Floating action button navigation
        floating_action_button_add_exercise.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addExerciseFragment)
        }
        floating_action_button_finish_workout.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_finishWorkout)
        }

        setUpRecycleView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController(mainFragment).navigate(R.id.action_mainFragment_to_settingsFragment)
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpRecycleView() {
        exercise_recycle_view.adapter = ExerciseAdapter(exerciseList,
            View.OnLongClickListener { view ->
                val position = exercise_recycle_view.getChildAdapterPosition(view)
                val clickExercise = exercise_recycle_view[position]

                // Put name of the exercise to EditExerciseFragment so it can identify which exercise to remove
                val bundle = Bundle()
                bundle.putString("name", clickExercise.exerciseName.text.toString())

                // Set arguments for EditExerciseFragment
                val receiver = EditExerciseFragment()
                receiver.arguments = bundle

                findNavController().navigate(R.id.action_mainFragment_to_editExerciseFragment, bundle)
                true
            })
        exercise_recycle_view.layoutManager = LinearLayoutManager(context)
    }

    private fun updateExerciseList() {
        // Add existing exercises from database
        val db = AppDatabase.getInstance(activity!!)!!.exerciseDao()

        val exDaysA = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysA", null)
        val exDaysB = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysB", null)
        val exDaysC = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysC", null)

        val today = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale("en"))
        var exDay = ""

        // Possible to combine multiple workout days into one day
        if (exDaysA!!.contains(today))
            exDay += "A"
        if (exDaysB!!.contains(today))
            exDay += "B"
        if (exDaysC!!.contains(today))
            exDay += "C"

        exerciseList.clear()
        for (ex in db.getAll()) {
            if (exDay.contains(ex.routine!!)) {
                exerciseList.add(Exercise(ex.routine, ex.name!!, ex.sets!!, ex.reps!!, ex.weight!!, ex.unit!!, ex.increment!!))
            }
        }

        // If current weekday is not an exercise day
        if (exerciseList.isEmpty() && exDay == "") {
            val tv = TextView(activity)
            tv.text = resources.getString(R.string.rest_day)
            tv.textSize = 22f
            tv.setTypeface(null, Typeface.BOLD)
            tv.width = mainFragment.maxWidth
            tv.height = mainFragment.maxHeight
            tv.setTextColor(Color.parseColor("#616161"))
            tv.gravity = Gravity.CENTER
            mainFragment.addView(tv)

            // Hide and disable FAB
            floating_action_button_add_exercise.alpha = 0f
            floating_action_button_add_exercise.isEnabled = false
            floating_action_button_finish_workout.alpha = 0f
            floating_action_button_finish_workout.isEnabled = false
        } else {
            // Show and enable FAB
            floating_action_button_add_exercise.alpha = 1f
            floating_action_button_add_exercise.isEnabled = true
            floating_action_button_finish_workout.alpha = 1f
            floating_action_button_finish_workout.isEnabled = true
        }
    }
}
