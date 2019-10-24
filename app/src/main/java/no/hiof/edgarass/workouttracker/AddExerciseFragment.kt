package no.hiof.edgarass.workouttracker


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.database.ExerciseDb
import no.hiof.edgarass.workouttracker.model.Exercise
import java.lang.Exception

class AddExerciseFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.fragment_add_exercise, mainFragment))
                .setPositiveButton(R.string.add) {dialog, which ->
                    // Hold field data
                    val name = (dialog as Dialog).addExerciseName.text.toString()
                    val sets = (dialog as Dialog).addExerciseSets.text.toString()
                    val reps = (dialog as Dialog).addExerciseReps.text.toString()
                    val weight = (dialog as Dialog).addExerciseWeight.text.toString()
                    val unit = (dialog as Dialog).addExerciseUnit.text.toString()

                    if (name.isNotBlank() && reps.isNotBlank() && sets.isNotBlank() && weight.isNotBlank() && unit.isNotBlank()) {
                        addExercise(ExerciseDb(name, sets.toInt(), reps.toInt(), weight.toInt(), unit))
                    } else {
                        Toast.makeText(activity!!, R.string.cannotBeEmpty, Toast.LENGTH_LONG).show()
                    }
                }
                .setNegativeButton(R.string.cancel) {dialog, which ->
                    dialog.dismiss()
                }
            builder.create()
        } ?: throw Exception ("Could not inflate dialog")
    }

    fun addExercise(exerciseDb : ExerciseDb) {
        // Add the exerciseDb to the exerciseDb database and object class
        val db = AppDatabase.getInstance(context!!)!!.exerciseDao()
        db.insertAll(exerciseDb)
        // Immediately refresh MainFragment with the newly added Exercise
        Exercise.addExercise(Exercise(
           exerciseDb.name!!, exerciseDb.sets!!, exerciseDb.reps!!, exerciseDb.weight!!, exerciseDb.unit!!))
        Log.d("WWW", db.getAll().toString() + "\n")
    }

}
