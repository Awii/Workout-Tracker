package no.hiof.edgarass.workouttracker

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.database.ExerciseDb

class AddExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.show()
        actionBar?.title = null
        setHasOptionsMenu(true)

        btn_add.setOnClickListener {
            addExercise()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Custom action bar
        inflater.inflate(R.menu.menu_finish_workout, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                MainActivity.hideKeyboard(context!!, view!!)
                view!!.clearFocus()
                activity!!.onBackPressed()
                return true
            }
            R.id.confirm_button -> {
                addExercise()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addExercise() {
        val db = AppDatabase.getInstance(context!!)!!.exerciseDao()

        // Hold field data
        val routine = addExerciseSpinner.selectedItem.toString()
        val name = addExerciseName.text.toString()
        val sets = addExerciseSets.text.toString()
        val reps = addExerciseReps.text.toString()
        val weight = addExerciseWeight.text.toString()
        val unit = addExerciseUnit.text.toString()
        val increment = addExerciseIncrement.text.toString()

        if (name.isNotBlank() && reps.isNotBlank() && sets.isNotBlank() && weight.isNotBlank() && unit.isNotBlank() && increment.isNotBlank()) {
            db.insertAll(ExerciseDb(routine, name, sets.toInt(), reps.toInt(), weight.toDouble(), unit, increment.toDouble()))
            MainActivity.hideKeyboard(context!!, view!!)
            view!!.clearFocus()
            activity!!.onBackPressed()
        } else {
            Toast.makeText(context, R.string.cannotBeEmpty, Toast.LENGTH_LONG).show()
        }
    }
}