package no.hiof.edgarass.workouttracker

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.preference.*
import kotlinx.android.synthetic.main.fragment_settings.*


class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.settingsLayout, SettingsFragment()).commit()


        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar!!.title = resources.getString(R.string.action_settings)
        actionBar.show()
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        //override fun

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            val sharedPrefs = preferenceManager.sharedPreferences

            // Top preference category
            val workoutCategory = PreferenceCategory(activity)
            workoutCategory.title = resources.getString(R.string.workout_category)
            preferenceScreen.addPreference(workoutCategory)

            // Choices of 1 to 3 different workouts during the week (usual is 1 to 2)
            val differentWorkouts = ListPreference(activity)
            differentWorkouts.key = "different_workouts"
            differentWorkouts.title = resources.getString(R.string.different_workouts)
            differentWorkouts.setDefaultValue("1")
            differentWorkouts.summary = sharedPrefs.getString("different_workouts", null)
            differentWorkouts.entries = arrayOf("1", "2", "3")
            differentWorkouts.entryValues = arrayOf("1", "2", "3")

            // Update summary on change
            differentWorkouts.setOnPreferenceChangeListener { preference, newValue ->
                preference.summary = newValue.toString()
                true
            }
            preferenceScreen.addPreference(differentWorkouts)


            // Multi-selection for days of workout
            // Multiple lists depending on differentWorkouts
            val amtMultiSelectLists = sharedPrefs.getString("different_workouts", null)!!.toInt()

            val weekdays = arrayOf(
                resources.getString(R.string.monday),
                resources.getString(R.string.tuesday),
                resources.getString(R.string.wednesday),
                resources.getString(R.string.thursday),
                resources.getString(R.string.friday),
                resources.getString(R.string.saturday),
                resources.getString(R.string.sunday)
            )

            val workoutsToInt = arrayOf("A", "B", "C")
            // 1 to 3 MultiSelectLists
            for (i in 0 until amtMultiSelectLists) {
                val multiSelectPref = MultiSelectListPreference(activity)

                multiSelectPref.setOnPreferenceChangeListener { preference, newValue ->
                    preference.summary = charSequenceToStringsDaily(newValue.toString())
                    true
                }

                // multiple preferences like exercise_daysA etc
                multiSelectPref.key = "exercise_days" + workoutsToInt[i]
                multiSelectPref.title = resources.getString(R.string.exercise_days) + " " + workoutsToInt[i]
                multiSelectPref.entries = weekdays
                multiSelectPref.entryValues = weekdays

                val charSeq = sharedPrefs.getStringSet("exercise_days" + workoutsToInt[i], null).toString()
                val summary = charSequenceToStringsDaily(charSeq)
                multiSelectPref.summary = summary

                preferenceScreen.addPreference(multiSelectPref)
            }

            // Second preference category
            val otherCategory = PreferenceCategory(activity)
            otherCategory.title = resources.getString(R.string.other_category)
            preferenceScreen.addPreference(otherCategory)


            // Change language
            val language = ListPreference(activity)
            language.key = "language"
            language.title = resources.getString(R.string.language)
            language.setDefaultValue("1")
            language.summary = resources.getString(R.string.language_summary)
            language.entries = arrayOf("English", "Norsk")
            language.entryValues = arrayOf("1", "2")

            preferenceScreen.addPreference(language)


            // Remind service, turn off/change time


            // Implement nuke db?
        }

        private fun charSequenceToStringsDaily(set : CharSequence) : String {
            var temp = ""
            if (set.contains(resources.getString(R.string.monday), true))
                temp += resources.getString(R.string.monday) + " "
            if (set.contains(resources.getString(R.string.tuesday), true))
                temp += resources.getString(R.string.tuesday) + " "
            if (set.contains(resources.getString(R.string.wednesday), true))
                temp += resources.getString(R.string.wednesday) + " "
            if (set.contains(resources.getString(R.string.thursday), true))
                temp += resources.getString(R.string.thursday) + " "
            if (set.contains(resources.getString(R.string.friday), true))
                temp += resources.getString(R.string.friday) + " "
            if (set.contains(resources.getString(R.string.saturday), true))
                temp += resources.getString(R.string.saturday) + " "
            if (set.contains(resources.getString(R.string.sunday), true))
                temp += resources.getString(R.string.sunday) + " "
            return temp
        }
    }

}
