package no.hiof.edgarass.workouttracker


import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.settings, SettingsFragment()).commit()

        (activity as AppCompatActivity).supportActionBar?.hide()


    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            // TODO: nuke database button
            //val myPref = findPreference("clearDatabase")
            //myPref.onPreferenceClickListener.onPreferenceClick

        }

    }
}
