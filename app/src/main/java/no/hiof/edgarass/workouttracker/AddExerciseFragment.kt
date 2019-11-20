package no.hiof.edgarass.workouttracker


import android.app.AlertDialog
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.preference.PreferenceManager
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.database.ExerciseDb
import no.hiof.edgarass.workouttracker.model.Exercise
import java.lang.Exception
import java.util.*

class AddExerciseFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.fragment_add_exercise, mainFragment))
                .setPositiveButton(R.string.add) {dialog, which ->

                    // (dialog as Dialog) Resolves nptr exceptions from fields

                    // Hold field data
                    val routine = (dialog as Dialog).addExerciseSpinner.selectedItem.toString()
                    val name = (dialog as Dialog).addExerciseName.text.toString()
                    val sets = (dialog as Dialog).addExerciseSets.text.toString()
                    val reps = (dialog as Dialog).addExerciseReps.text.toString()
                    val weight = (dialog as Dialog).addExerciseWeight.text.toString()
                    val unit = (dialog as Dialog).addExerciseUnit.text.toString()

                    // Check fields before adding a new exercise to the database
                    if (name.isNotBlank() && reps.isNotBlank() && sets.isNotBlank() && weight.isNotBlank() && unit.isNotBlank()) {
                        addExercise(ExerciseDb(routine, name, sets.toInt(), reps.toInt(), weight.toDouble(), unit, 2.5))
                    } else {
                        // TODO: Keep dialog open
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

        // Immediately refresh MainFragment with the newly added Exercise (?)
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
        if (exerciseDb.routine == exDay)
        Exercise.addExercise(Exercise(
           exerciseDb.routine!!, exerciseDb.name!!, exerciseDb.sets!!, exerciseDb.reps!!, exerciseDb.weight!!, exerciseDb.unit!!, exerciseDb.increment!!))
    }

}
