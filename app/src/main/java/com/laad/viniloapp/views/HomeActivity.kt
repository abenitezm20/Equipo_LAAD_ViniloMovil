package com.laad.viniloapp.views

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.laad.viniloapp.R
import com.laad.viniloapp.databinding.ActivityHomeBinding
import com.laad.viniloapp.utilities.AppRole

class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        binding.appBarHome.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_albums, R.id.nav_collector, R.id.nav_artist
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onStart() {
        Log.d("HomeActivity", "onStart")
        super.onStart()

        val role: String = intent.getStringExtra("role") ?: AppRole.VISITOR.value
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.getHeaderView(0)
        val textViewAppRole: TextView = headerView.findViewById(R.id.textViewAppRole)
        textViewAppRole.text = getString(getStringRoleValue(role))
        menuSetting(role, navigationView);
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d("HomeActivity", "onSupportNavigateUp")
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun getStringRoleValue(role: String): Int {
        return when (role) {
            AppRole.COLLECTOR.value -> R.string.nav_view_role_collector
            AppRole.VISITOR.value -> R.string.nav_view_role_visitor
            else -> {
                R.string.nav_view_role_visitor
            }
        }
    }

    private fun menuSetting(role:String, navView:NavigationView){
        val menu = navView.menu
        val albums = menu.findItem(R.id.nav_albums)
        val collector = menu.findItem(R.id.nav_collector)
        albums.isVisible = true
        collector.isVisible=true
        if(role == AppRole.VISITOR.value){
            collector.isVisible = false
        }else if (role == AppRole.COLLECTOR.value){
            albums.isVisible = true
        }
    }
}