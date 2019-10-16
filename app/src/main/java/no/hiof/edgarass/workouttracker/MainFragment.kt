package no.hiof.edgarass.workouttracker


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.adapter.ExerciseAdapter
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

        // show action bar only in main fragment
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.show()
        setHasOptionsMenu(true)

        actionBar?.title = Calendar.getInstance().getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG,
            Locale.getDefault())
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))


        floating_action_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addExerciseFragment)
        }

        //addExercises()

        setUpRecycleView()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // action bar clicks

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


    fun addExercises() {


        val params = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 30, 0, 0)


        val ex1 = TextView(activity!!.applicationContext)
        ex1.text = "Sq 3x5 15kg"
        ex1.textSize = 30f
        ex1.gravity = Gravity.CENTER
        ex1.layoutParams = params
        //main_layout.addView(ex1)

        //val dl = Exercise("Dl", 3, 5, 40, "kg")
        val ex3 = TextView(activity!!.applicationContext)
        ex3.text = "Dl 3x5"
        ex3.textSize = 30f
        ex3.gravity = Gravity.CENTER
        ex3.layoutParams = params

        //main_layout.addView(ex3)
    }

    private fun setUpRecycleView() {
        // Set our own adapter to be used in the RecycleView, and sends it the data and creates the OnClickListener
        // With the listener gets called when an item in the list is clicked
        exerciseRecycleView.adapter = ExerciseAdapter(exerciseList,
            View.OnClickListener { view ->
                // Gets the position of the item that's clicked
                //val position = exerciseRecycleView.getChildAdapterPosition(view)

                // Gets the movie based on which item got clicked
                //val clickedMovie = exerciseRecycleView[position]

                // Creates the navigation action, including the uid argument
                //val action = MovieListFragmentDirections.actionMovieListToMovieDetailFragment(clickedMovie.uid)

                // Calls the navigat action, taking us to the MovieDetailFragment
                //findNavController().navigate(action)

                // Creates a toast with the movie that got clicked
                //Toast.makeText(view.context, clickedMovie.title + " clicked", Toast.LENGTH_LONG).show();
            })

        // Sets the layoutmanager we want to use
        //movieRecyclerView.layoutManager = GridLayoutManager(context, 3)
        exerciseRecycleView.layoutManager = LinearLayoutManager(context)
    }


}
