package dam.pmdm.tarea3_hurtadoporras_cristina

import android.content.Context
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.ActivityMainBinding
import java.util.Locale

/**
 * Actividad principal de la aplicación que contiene la toolbar superior, el drawer lateral y el NavHostFragment para los fragmentos
 */

class MainActivity : AppCompatActivity() {

    // Binding de la actividad principal
    private lateinit var binding: ActivityMainBinding
    private lateinit var drawerToggle: ActionBarDrawerToggle
    private val episodeViewModel: EpisodeViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        applyLocale(this)
        super.onCreate(savedInstanceState)
        // Inicialización del binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar
        setSupportActionBar(binding.toolbar)

        // Configuración del NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        // Configuración para que el icono de la toolbar abra y cierre
        drawerToggle = ActionBarDrawerToggle(
            this,
            binding.drawerLayout,
            binding.toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.drawerLayout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()

        // Configuración para que al pulsar cada una de las opciones del drawer, nos dirija al fragmento correspondiente
        binding.navigationView.setNavigationItemSelectedListener { menuItem ->
            when(menuItem.itemId) {
                R.id.nav_episodes -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.episodesFragment)
                }
                R.id.nav_stats -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.statsFragment)
                }
                R.id.nav_settings -> {
                    findNavController(R.id.nav_host_fragment).navigate(R.id.settingsFragment)
                }
                R.id.aboutDialog -> {
                    showAboutDialog()
                }
            }

            // Cerrar el drawer después de hacer clic
            binding.drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        episodeViewModel.episodes.observe(this) {

        }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle(R.string.about)
            .setMessage(getString(R.string.about_text))
            .show()
    }

    fun applyLocale(context: Context) {
        val prefs = context.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val isEnglish = prefs.getBoolean(Prefs.ENGLISH, false)

        val locale = if (isEnglish) Locale("en") else Locale("es")
        Locale.setDefault(locale)

        val config = context.resources.configuration
        config.setLocale(locale)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)
    }
}