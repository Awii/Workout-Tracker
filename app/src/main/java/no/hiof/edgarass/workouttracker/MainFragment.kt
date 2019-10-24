package no.hiof.edgarass.workouttracker


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.exercise_list_item.view.*
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.adapter.ExerciseAdapter
import no.hiof.edgarass.workouttracker.database.AppDatabase
import no.hiof.edgarass.workouttracker.model.Exercise
import java.util.*
import kotlin.collections.ArrayList

class MainFragment : Fragment() {
    private var exerciseList : ArrayList<Exercise> = Exercise.getExercises()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Add existing exercises from database
        val db = AppDatabase.getInstance(activity!!)!!.exerciseDao()
        exerciseList.clear()
        for (ex in db.getAll()) {
            //db.delete(ex)
            val temp = Exercise(ex.name!!, ex.sets!!, ex.reps!!, ex.weight!!, ex.unit!!)
            if (!exerciseList.contains(temp))
                exerciseList.add(temp)
        }


        // Show action bar only in main fragment
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        setHasOptionsMenu(true)

        // Title of action bar = weekday
        actionBar?.title = Calendar.getInstance().getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG,
            Locale.getDefault())
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))


        // Add new exercise fragment
        floating_action_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addExerciseFragment)
        }

        setUpRecycleView()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Respond to action bar clicks
        return when (item.itemId) {
            R.id.action_settings -> {
                findNavController(mainFragment).navigate(R.id.action_mainFragment_to_settingsFragment)
                return true
            }
            R.id.action_synchronize -> {
                findNavController(mainFragment).navigate(R.id.action_mainFragment_to_synchronizeFragment)
                return true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.menu_settings, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun setUpRecycleView() {
        exerciseRecycleView.adapter = ExerciseAdapter(exerciseList,
            View.OnClickListener { view ->
                val position = exerciseRecycleView.getChildAdapterPosition(view)
                val clickedMovie = exerciseRecycleView[position]

                val bundle = Bundle()
                bundle.putString("name", clickedMovie.exerciseName.text.toString())

                // Set arguments for RemoveExerciseFragment
                val receiver = RemoveExerciseFragment()
                receiver.setArguments(bundle)
                Log.d("WWW", receiver.arguments?.getString("name"))


                findNavController().navigate(R.id.action_mainFragment_to_removeExerciseFragment)



            })
        exerciseRecycleView.layoutManager = LinearLayoutManager(context)
    }

}
