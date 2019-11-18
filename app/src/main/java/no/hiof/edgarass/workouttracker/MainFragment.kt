package no.hiof.edgarass.workouttracker

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.RelativeLayout
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
        setHasOptionsMenu(true)

        // Customize action bar title
        val exDaysA = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysA", null)
        val exDaysB = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysB", null)
        val exDaysC = PreferenceManager.getDefaultSharedPreferences(context).getStringSet("exercise_daysC", null)

        val today = Calendar.getInstance().getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
        var exDay = ""

        if (exDaysA!!.contains(today)) {
            exDay = ", A"
        } else if (exDaysB!!.contains(today)){
            exDay = ", B"
        } else if (exDaysC!!.contains(today)){
            exDay = ", C"
        }

        // Title of action bar = Weekday, A
        actionBar?.title = today + exDay
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))


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
        // Respond to action bar clicks
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController(mainFragment).navigate(R.id.action_mainFragment_to_settingsFragment)
                return true
            }
            R.id.action_synchronize -> {
                findNavController(mainFragment).navigate(R.id.action_mainFragment_to_synchronizeFragment)
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpRecycleView() {
        exerciseRecycleView.adapter = ExerciseAdapter(exerciseList,
            View.OnClickListener { view ->
                val position = exerciseRecycleView.getChildAdapterPosition(view)
                val clickExercise = exerciseRecycleView[position]

                // Put name of the exercise to RemoveExerciseFragment so it can identify which exercise to remove
                val bundle = Bundle()
                bundle.putString("name", clickExercise.exerciseName.text.toString())

                // Set arguments for RemoveExerciseFragment
                val receiver = RemoveExerciseFragment()
                receiver.arguments = bundle

                findNavController().navigate(R.id.action_mainFragment_to_removeExerciseFragment, bundle)

                // Work-around for updating the view after deleting an exercise
                val handler = Handler()
                handler.postDelayed({
                    onDelete()
                }, 3000)
            })
        exerciseRecycleView.layoutManager = LinearLayoutManager(context)
    }

    private fun updateExerciseList() {
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

        if (exerciseList.isEmpty() && exDay == "") {
            // TODO: center

            val tv = TextView(activity)
            tv.text = resources.getString(R.string.rest_day)
            tv.textSize = 30f
            tv.setPadding(20, 150, 20, 0)
            tv.gravity = Gravity.CENTER

            val fl = RelativeLayout(activity!!)
            fl.addView(tv)
            mainFragment.addView(fl)
        }
    }

    private fun onDelete() {
        updateExerciseList()
        setUpRecycleView()
    }
}
