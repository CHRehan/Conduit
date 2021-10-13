package io.realworld.android.conduit

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import io.realworld.android.conduit.models.entities.User
import io.realworld.android.conduit.data.UserPreferences
import io.realworld.android.conduit.data.UserPreferences.Companion.ACCESS_TOKEN
import io.realworld.android.conduit.databinding.ActivityMainBinding
import io.realworld.android.conduit.databinding.NavHeaderMainBinding
import io.realworld.android.conduit.network.ConduitClient
import io.realworld.android.conduit.network.Resource
import kotlinx.coroutines.runBlocking
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navBinding: NavHeaderMainBinding
    private val authViewModel: AuthViewModel by viewModels()

    @Inject
    lateinit var userPreferences: UserPreferences

    @Inject
    lateinit var conduitClient: ConduitClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        navBinding = NavHeaderMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)


        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_feed,
                R.id.nav_my_feed,
                R.id.nav_auth
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        runBlocking {
            userPreferences.getAccessToken()[ACCESS_TOKEN]
                ?.let {
                    authViewModel.getCurrentUser()
                }
        }

        authViewModel.user.observe({ lifecycle }) {
            when (it) {
                is Resource.Success -> {
                    runBlocking {
                        userPreferences.saveAccessTokens(it.value.user.token)
                    }
                    updateMenu(user = it.value.user)
                    navController.navigateUp()
                }
                is Resource.Logout -> {
                    updateMenu(user = null)
                    navController.navigateUp()
                }
            }

        }


    }


    private fun updateMenu(user: User?) {
        binding.navView.apply {
            when (user) {
                is User -> {
                    navBinding.email.text = user.email

                    menu.clear()
                    inflateMenu(R.menu.menu_user)

                }
                else -> {
                    menu.clear()
                    inflateMenu(R.menu.menu_guest)
                }
            }

        }
    }


    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}