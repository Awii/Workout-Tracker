package no.hiof.edgarass.workouttracker


import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.database.ExerciseDb
import no.hiof.edgarass.workouttracker.model.Exercise

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
                // Hide keyboard if it's open
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


    /*
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

    */