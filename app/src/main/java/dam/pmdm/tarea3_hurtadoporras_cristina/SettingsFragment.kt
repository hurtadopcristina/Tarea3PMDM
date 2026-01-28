package dam.pmdm.tarea3_hurtadoporras_cristina

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.os.IResultReceiver
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.google.firebase.auth.FirebaseAuth
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.FragmentSettingsBinding
import java.util.Locale

class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireContext().getSharedPreferences(
            Prefs.FILE,
            Context.MODE_PRIVATE
        )

        binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
            preferences.edit().putBoolean(Prefs.DARK_MODE, isChecked).apply()

            AppCompatDelegate.setDefaultNightMode(
                if (isChecked)
                    AppCompatDelegate.MODE_NIGHT_YES
                else
                    AppCompatDelegate.MODE_NIGHT_NO
            )
        }

        val isEnglish = preferences.getBoolean(Prefs.ENGLISH, false)
        binding.radioEn.isChecked = isEnglish
        binding.radioEs.isChecked = !isEnglish

        binding.radioLanguage.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.radio_en -> {
                    preferences.edit().putBoolean(Prefs.ENGLISH, true).apply()
                    setLocale("en")
                }
                R.id.radio_es -> {
                    preferences.edit().putBoolean(Prefs.ENGLISH, false).apply()
                    setLocale("es")
                }
            }
        }

        binding.btnLogout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
    }

    private fun setLocale(language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)

        val config = resources.configuration
        config.setLocale(locale)

        resources.updateConfiguration(config, resources.displayMetrics)
        requireActivity().recreate()
    }
}