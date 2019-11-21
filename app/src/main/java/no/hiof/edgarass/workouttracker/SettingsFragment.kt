package no.hiof.edgarass.workouttracker

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.AttributeSet
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.*
import android.util.Log
import androidx.fragment.app.DialogFragment
import no.hiof.edgarass.workouttracker.database.AppDatabase
import java.util.*


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


        setHasOptionsMenu(true)
        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = resources.getString(R.string.action_settings)
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.show()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Hide keyboard if it's open
                MainActivity.hideKeyboard(context!!, view!!)
                view!!.clearFocus()
                activity!!.onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    class SettingsFragment : PreferenceFragmentCompat() {

        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)
            val sharedPrefs = preferenceManager.sharedPreferences

            /*
                Workout
             */

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
                val fragment = no.hiof.edgarass.workouttracker.SettingsFragment()

                // Reloads the fragment
                (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.settingsLayout, SettingsFragment()).commit()
                true
            }
            preferenceScreen.addPreference(differentWorkouts)


            // Multi-selection for days of workout
            // Multiple lists depending on differentWorkouts
            val amtMultiSelectLists = sharedPrefs.getString("different_workouts", null)!!.toInt()

            val weekdaysLocal = arrayOf(resources.getString(R.string.monday), resources.getString(R.string.tuesday), resources.getString(R.string.wednesday), resources.getString(R.string.thursday), resources.getString(R.string.friday), resources.getString(R.string.saturday), resources.getString(R.string.sunday))
            val weekdays = arrayOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")

            val workoutsToInt = arrayOf("A", "B", "C")
            // 1 to 3 MultiSelectLists
            for (i in 0 until amtMultiSelectLists) {
                val multiSelectPref = MultiSelectListPreference(activity)

                // multiple preferences like exercise_daysA etc
                multiSelectPref.key = "exercise_days" + workoutsToInt[i]
                multiSelectPref.title = resources.getString(R.string.exercise_days) + " " + workoutsToInt[i]
                multiSelectPref.entries = weekdaysLocal
                multiSelectPref.entryValues = weekdays

                val charSeq = sharedPrefs.getStringSet("exercise_days" + workoutsToInt[i], null).toString()
                val summary = charSequenceToStringsDaily(charSeq)
                multiSelectPref.summary = summary
                multiSelectPref.setOnPreferenceChangeListener { preference, newValue ->
                    preference.summary = charSequenceToStringsDaily(newValue.toString())
                    true
                }

                preferenceScreen.addPreference(multiSelectPref)
            }

            /*
                Other
             */

            // Second preference category
            val otherCategory = PreferenceCategory(activity)
            otherCategory.title = resources.getString(R.string.other_category)
            preferenceScreen.addPreference(otherCategory)


            // Change language
            val language = ListPreference(activity)
            language.key = "language"
            language.title = resources.getString(R.string.language)
            language.setDefaultValue("en")
            language.summary = resources.getString(R.string.language_summary)
            language.entries = arrayOf("English", "Norsk")
            language.entryValues = arrayOf("en", "no")
            language.setOnPreferenceChangeListener { preference, newValue ->
                sharedPrefs.edit().putString(preference.key, newValue.toString()).apply()

                val res = context!!.resources
                val conf = res.configuration
                Locale.setDefault(Locale(newValue.toString()))
                conf.setLocale(Locale(newValue.toString()))
                res.updateConfiguration(conf, res.displayMetrics) // deprecated but works
                context!!.createConfigurationContext(conf)
                activity!!.recreate()

                true
            }

            preferenceScreen.addPreference(language)


            // Find gyms near me
            val openMaps = Preference(activity)
            openMaps.key = "openMaps"
            openMaps.title = "Find gyms near me " // Todo R.string
            openMaps.setOnPreferenceClickListener {
                Log.d("asd", "clicked")
                val intent = Intent(Intent.ACTION_VIEW,
                    Uri.parse("geo:0,0?q=Gym"))
                startActivity(intent)
                true
            }

            preferenceScreen.addPreference(openMaps)


            // Delete all exercises
            val clearDb = Preference(activity)
            clearDb.setOnPreferenceClickListener {

                val dialog = AlertDialog.Builder(activity)
                    .setTitle("Are you sure? It is irreversible.")
                    .setPositiveButton("Yes", DialogInterface.OnClickListener { dialog, which ->
                        val db = AppDatabase.getInstance(activity!!)!!.exerciseDao()
                        db.deleteAll()
                        dialog.dismiss()
                    })
                    .setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
                        dialog.dismiss()
                    })
                    .show()
                true
            }
            clearDb.key = "clearDb"
            clearDb.title = "Remove all exercises"

            preferenceScreen.addPreference(clearDb)

            // Remind service, turn off/change time

            // Themes
        }

        private fun charSequenceToStringsDaily(set : CharSequence) : String {
            var temp = ""
            if (set.contains("Monday", true))
                temp += resources.getString(R.string.monday) + " "
            if (set.contains("Tuesday", true))
                temp += resources.getString(R.string.tuesday) + " "
            if (set.contains("Wednesday", true))
                temp += resources.getString(R.string.wednesday) + " "
            if (set.contains("Thursday", true))
                temp += resources.getString(R.string.thursday) + " "
            if (set.contains("Friday", true))
                temp += resources.getString(R.string.friday) + " "
            if (set.contains("Saturday", true))
                temp += resources.getString(R.string.saturday) + " "
            if (set.contains("Sunday", true))
                temp += resources.getString(R.string.sunday)
            return temp
        }
    }
}
