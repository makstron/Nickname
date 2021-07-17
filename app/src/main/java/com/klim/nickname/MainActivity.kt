package com.klim.nickname

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.klim.nickname.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var vm: MainViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        vm = ViewModelProviders.of(this).get(MainViewModel::class.java)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = vm
//        setContentView(R.layout.activity_main)



        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        val appBarConfiguration = AppBarConfiguration(setOf(R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
//        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}