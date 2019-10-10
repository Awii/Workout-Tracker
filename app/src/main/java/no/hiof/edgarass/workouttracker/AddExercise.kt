package no.hiof.edgarass.workouttracker


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.room.Room
import kotlinx.android.synthetic.main.fragment_add_exercise.*
import no.hiof.edgarass.workouttracker.model.AppDatabase

class AddExercise : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_exercise, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val db = Room.databaseBuilder(activity!!.applicationContext, AppDatabase::class.java, "app-database").build()

        addExerciseButton.setOnClickListener {
            //db.exerciseDao().insertOne(Exercise())
        }

        //addExercises()

    }


}
