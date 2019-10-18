package no.hiof.edgarass.workouttracker


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import kotlinx.android.synthetic.main.fragment_edit_exercise.*
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.database.ExerciseDb
import no.hiof.edgarass.workouttracker.model.Exercise
import java.lang.Exception

class EditExerciseFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater


            builder.setView(inflater.inflate(R.layout.fragment_edit_exercise, mainFragment))
                .setPositiveButton("edit") { dialog, which ->
                }
                .setNegativeButton(R.string.cancel) {dialog, which ->
                    dialog.dismiss()
                }
            editExerciseName.setText("asd")
            builder.create()

        } ?: throw Exception ("Could not inflate dialog")
    }

    fun addExercise(exerciseDb : ExerciseDb) {
        // Add the exerciseDb to the exerciseDb database and object class
        val db = AppDatabase.getInstance(context!!)!!.exerciseDao()
        db.insertAll(exerciseDb)
        Exercise.addExercises(Exercise(
            exerciseDb.name!!, exerciseDb.sets!!, exerciseDb.reps!!, exerciseDb.weight!!, exerciseDb.unit!!))
        Log.d("WWW", db.getAll().toString() + "\n")
    }

}
