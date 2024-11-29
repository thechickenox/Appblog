package com.example.appblog

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.appblog.core.hide
import com.example.appblog.core.show
import com.example.appblog.databinding.ActivityMainBinding
import com.google.firebase.crashlytics.FirebaseCrashlytics

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Configurar Crashlytics manualmente (si es necesario)
        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true)

        // Inflar el diseño y configurar NavController
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        // Observar cambios en el destino de navegación
        observeDestinationChange()
    }

    private fun observeDestinationChange() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.loginFragment,
                R.id.registerFragment,
                R.id.setupProfileFragment -> {
                    binding.bottomNavigationView.hide() // Ocultar BottomNavigationView
                }
                else -> {
                    binding.bottomNavigationView.show() // Mostrar BottomNavigationView
                }
            }
        }
    }
}
