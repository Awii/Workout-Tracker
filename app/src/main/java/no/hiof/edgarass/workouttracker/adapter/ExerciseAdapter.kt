package no.hiof.edgarass.workouttracker.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import no.hiof.edgarass.workouttracker.R
import no.hiof.edgarass.workouttracker.model.Exercise

class ExerciseAdapter(private val items: ArrayList<Exercise>, var clickListener: View.OnLongClickListener) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent, false)
        return ExerciseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val currentExercise = items[position]
        holder.bind(currentExercise, clickListener)
    }

    class ExerciseViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        private val exerciseName : TextView = view.exerciseName
        private val exerciseReps : TextView = view.exerciseReps
        private val exerciseSets : TextView = view.exerciseSets
        private val exerciseUnit : TextView = view.exerciseUnit
        private val exerciseWeight : TextView = view.exerciseWeight

        fun bind(item: Exercise, clickListener: View.OnLongClickListener) {
            exerciseName.text = item.name
            exerciseReps.text = item.reps.toString()
            exerciseSets.text = item.sets.toString()
            exerciseUnit.text = item.unit
            exerciseWeight.text = item.weight.toString()

            this.itemView.setOnLongClickListener(clickListener)
        }
    }

}