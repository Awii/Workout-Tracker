package no.hiof.edgarass.workouttracker

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_remove_exercise.*
import no.hiof.edgarass.workouttracker.database.AppDatabase

class RemoveExerciseFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_remove_exercise, container, false)
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
        //inflater.inflate(R.menu.finish_workout_menu, menu)
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
        val name = arguments?.getString("name")

        val exercise = db.findByName(name!!)

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
            db.delete(db.findByName(name!!))
            view!!.clearFocus()
            activity!!.onBackPressed()
        }
        btn_edit.setOnClickListener {
            db.updateExercise(
                name,
                editExerciseSpinner.selectedItem.toString(),
                editExerciseName.text.toString(),
                editExerciseSets.text.toString().toInt(),
                editExerciseReps.text.toString().toInt(),
                editExerciseWeight.text.toString().toDouble(),
                editExerciseUnit.text.toString(),
                editExerciseIncrement.text.toString().toDouble()
            )
            view!!.clearFocus()
            activity!!.onBackPressed()
        }
    }
}
