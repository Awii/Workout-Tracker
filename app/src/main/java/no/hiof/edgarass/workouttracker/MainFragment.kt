package no.hiof.edgarass.workouttracker


import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.fragment_main.*
import no.hiof.edgarass.workouttracker.model.Exerciseee
import java.util.*

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar?.show()


        //setSupportActionBar(settingsMenu)
        //setupActionBarWithNavController(this, R.id.navHostFragment)


        //activity?.setActionBar(settingsMenu)

        setHasOptionsMenu(true)

        //activity?.setActionBar(settingsMenu)

        val actionBar = activity?.actionBar

        actionBar?.title = Calendar.getInstance().getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG,
            Locale.getDefault())

        actionBar?.title = Calendar.getInstance().getDisplayName(
            Calendar.DAY_OF_WEEK,
            Calendar.LONG,
            Locale.getDefault())
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.LTGRAY))


        floating_action_button.setOnClickListener {
            it.findNavController().navigate(R.id.action_mainFragment_to_addExerciseFragment)
        }

        addExercises()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // action bar clicks

        return when (item.itemId) {

            R.id.action_settings -> {
                findNavController(floating_action_button).navigate(R.id.action_mainFragment_to_settingsFragment)
                return true
            }
            R.id.action_synchronize -> {
                //val intent = Intent(this, FirebaseActivity::class.java)
                //startActivity(intent)
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
        main_layout.addView(ex1)

        val dl = Exerciseee("Dl", 3, 5, 40, "kg")
        val ex3 = TextView(activity!!.applicationContext)
        ex3.text = dl.toString()
        ex3.textSize = 30f
        ex3.gravity = Gravity.CENTER
        ex3.layoutParams = params

        main_layout.addView(ex3)
    }


}
