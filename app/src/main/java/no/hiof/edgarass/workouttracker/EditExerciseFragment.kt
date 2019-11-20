package no.hiof.edgarass.workouttracker

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import kotlinx.android.synthetic.main.fragment_edit_exercise.*
import no.hiof.edgarass.workouttracker.database.AppDatabase

class EditExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_edit_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.show()
        actionBar?.title = null
        setHasOptionsMenu(true)

        dialogFunctionality()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Custom action bar
        //inflater.inflate(R.menu.menu_finish_workout, menu)
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
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialogFunctionality() {
        val db = AppDatabase.getInstance(context!!)!!.exerciseDao()
        val argName = arguments?.getString("name")

        val exercise = db.findByName(argName!!)

        editExerciseName.setText(exercise.name)
        editExerciseSets.setText(exercise.sets.toString())
        editExerciseReps.setText(exercise.reps.toString())
        editExerciseWeight.setText(exercise.weight.toString())
        editExerciseUnit.setText(exercise.unit)
        editExerciseIncrement.setText(exercise.increment.toString())

        btn_clear.setOnClickListener {
            editExerciseSpinner.setSelection(0)
            editExerciseName.text.clear()
            editExerciseSets.text.clear()
            editExerciseReps.text.clear()
            editExerciseWeight.text.clear()
            editExerciseUnit.text.clear()
            editExerciseIncrement.text.clear()
        }
        btn_delete.setOnClickListener {
            db.delete(db.findByName(argName!!))
            view!!.clearFocus()
            activity!!.onBackPressed()
        }
        btn_edit.setOnClickListener {

            // Hold field data
            val routine = editExerciseSpinner.selectedItem.toString()
            val name = editExerciseName.text.toString()
            val sets = editExerciseSets.text.toString()
            val reps = editExerciseReps.text.toString()
            val weight = editExerciseWeight.text.toString()
            val unit = editExerciseUnit.text.toString()
            val increment = editExerciseIncrement.text.toString()

            if (name.isNotBlank() && reps.isNotBlank() && sets.isNotBlank() && weight.isNotBlank() && unit.isNotBlank() && increment.isNotBlank()) {
                db.updateExercise(argName, routine, name, sets.toInt(), reps.toInt(), weight.toDouble(), unit, increment.toDouble())
                view!!.clearFocus()
                activity!!.onBackPressed()
            } else {
                Toast.makeText(context, R.string.cannotBeEmpty, Toast.LENGTH_LONG).show()
            }
        }
    }
}