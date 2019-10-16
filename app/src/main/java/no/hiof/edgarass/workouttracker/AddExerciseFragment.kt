package no.hiof.edgarass.workouttracker


import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.Exception

class AddExerciseFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater

            builder.setView(inflater.inflate(R.layout.fragment_add_exercise, mainFragment))
                .setNegativeButton(R.string.cancel) {dialog, which ->
                    dialog.cancel()
                }
                .setPositiveButton(R.string.add) {dialog, which ->
                    addExercise()
                }
            builder.create()
        } ?: throw Exception ("Couldn't inflate dialog")
    }

    fun addExercise() {

    }
}
