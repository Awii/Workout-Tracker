package no.hiof.edgarass.workouttracker

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.os.Handler
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

                    db.delete(db.findByName(name!!))

                }
                .setNegativeButton(R.string.cancel) {dialog, which ->
                    dialog.dismiss()
                }

            // Dismiss the dialog window after 3 seconds
            val handler = Handler()
            handler.postDelayed({
                dialog?.dismiss()
            }, 3000)

            builder.create()
        } ?: throw Exception ("Could not inflate dialog")

    }
}
