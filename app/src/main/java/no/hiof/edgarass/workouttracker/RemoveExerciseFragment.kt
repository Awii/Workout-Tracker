package no.hiof.edgarass.workouttracker


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.database.AppDatabase
import java.lang.Exception

class RemoveExerciseFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater


            builder.setView(inflater.inflate(R.layout.fragment_remove_exercise, mainFragment))
                .setPositiveButton("Delete") { dialog, which ->

                    val db = AppDatabase.getInstance(context!!)!!.exerciseDao()

                    val name = arguments?.getString("name")
                    if (name != null) {
                        Log.d("WWW", name)
                    }


                    // TODO: pass arguments from previous fragment so that an exercise can be deleted
                    //db.delete(db.findByName(name))
                    //Exercise.removeExercise(name)
                }
                .setNegativeButton(R.string.cancel) {dialog, which ->
                    dialog.dismiss()
                }
            builder.create()

        } ?: throw Exception ("Could not inflate dialog")
    }

}
