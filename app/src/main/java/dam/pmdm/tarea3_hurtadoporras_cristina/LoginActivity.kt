package dam.pmdm.tarea3_hurtadoporras_cristina

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.ActivityLoginBinding
import dam.pmdm.tarea3_hurtadoporras_cristina.databinding.ActivityMainBinding

class LoginActivity : AppCompatActivity() {

    // Binding de la actividad principal
    private lateinit var binding: ActivityLoginBinding
    private var user: FirebaseUser? = null

    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract(),
    ) { res ->
        this.onSignInResult(res)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        user = FirebaseAuth.getInstance().currentUser
        if (user != null)
            showMain()
        else
            showLogin()

    }

    private fun showLogin() {
        // Choose authentication providers
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build()
        )

        // Create and launch sign-in intent
        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setTheme(R.style.Theme_Tarea3_HurtadoPorras_Cristina)
            .setAvailableProviders(providers)
            .build()
        signInLauncher.launch(signInIntent)
    }

    private fun showMain() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        if (result.resultCode == RESULT_OK) {
            // Successfully signed in
            user = FirebaseAuth.getInstance().currentUser
            showMain()
        } else {
            Toast.makeText(this, R.string.toast_text, Toast.LENGTH_SHORT).show()
        }
    }
}