package me.brunofelix.news.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import me.brunofelix.news.R
import me.brunofelix.news.databinding.ActivityNewsBinding

class NewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        uiSetup()
    }

    private fun uiSetup() {
        binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.inflateMenu(R.menu.main_menu)
        binding.navView.setupWithNavController(findNavController(R.id.nav_host))
    }

}