package no.hiof.edgarass.workouttracker.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import no.hiof.edgarass.workouttracker.R
import no.hiof.edgarass.workouttracker.model.Exercise

class ExerciseAdapter(private val items: ArrayList<Exercise>, var clickListener: View.OnClickListener) : RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {
    override fun getItemCount(): Int {
        return items.size
    }

    // Called when there's a need for a new ViewHolder (a new item in the list/grid)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        // Log so we can see when the onCreateViewHolder method is called
        Log.d("ExerciseAdapter", "Creating View")

        // Inflates the exercise_list_item.xml to a view for us
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.exercise_list_item, parent, false)

        // Create the viewholder with the corresponding view (list item)
        return ExerciseViewHolder(itemView)
    }

    // Called when data is bound to a specific ViewHolder (and item in the list/grid)
    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        // Log so we can see when the bind method is called
        Log.d("ExerciseAdapter", "Binding View $position")

        // Gets the movie data we are going to use at the given position
        val currentExercise = items[position]

        // Gives the movie data and clickListener to the ViewHolder
        // Makes it fill up the given position with the new data and add the clicklistener to the view
        holder.bind(currentExercise, clickListener)
    }

    class ExerciseViewHolder (view: View) : RecyclerView.ViewHolder(view) {
        // Gets a reference to all the specific views we are going to use or fill with data
        private val exerciseName : TextView = view.exerciseName
        private val exerciseReps : TextView = view.exerciseReps
        private val exerciseSets : TextView = view.exerciseSets
        private val exerciseUnit : TextView = view.exerciseUnit
        private val exerciseWeight : TextView = view.exerciseWeight

        fun bind(item: Exercise, clickListener: View.OnClickListener) {
            // Fills the views with the given data
            exerciseName.text = item.name
            exerciseReps.text = item.reps.toString()
            exerciseSets.text = item.sets.toString()
            exerciseUnit.text = item.unit
            exerciseWeight.text = item.weight.toString()

            // Sets the onClickListener
            this.itemView.setOnClickListener(clickListener)
        }
    }
}