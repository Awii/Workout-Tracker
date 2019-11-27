package no.hiof.edgarass.workouttracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_list_item.view.exerciseName
import kotlinx.android.synthetic.main.exercise_list_item.view.exerciseUnit
import kotlinx.android.synthetic.main.exercise_list_item.view.exerciseWeight
import kotlinx.android.synthetic.main.finish_workout_list_item.view.*
import no.hiof.edgarass.workouttracker.R
import no.hiof.edgarass.workouttracker.model.Exercise
import java.lang.NumberFormatException

class FinishWorkoutAdapter(private val items: ArrayList<Exercise>) : RecyclerView.Adapter<FinishWorkoutAdapter.ExerciseViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.finish_workout_list_item, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = items[position]
        holder.bind(currentExercise)
        // Update increment with the new value in EditText
        holder.exerciseIncrement.doAfterTextChanged {
            try {
                currentExercise.increment = it.toString().toDouble()
            } catch (e: NumberFormatException) {
                // Double cannot be null
            }
        }
    }

    class ExerciseViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val exerciseName : TextView = view.exerciseName
        private val exerciseWeight : TextView = view.exerciseWeight
        val exerciseIncrement : EditText = view.exerciseIncrement
        private val exerciseUnit : TextView = view.exerciseUnit

        fun bind(item: Exercise) {
            exerciseName.text = (item.name + ": ")
            exerciseWeight.text = (item.weight.toString() + " + ")
            exerciseIncrement.setText(item.increment.toString())
            exerciseUnit.text = item.unit
        }
    }

}