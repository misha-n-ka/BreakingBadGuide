package com.breakingBadGuide.ui

import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.breakingBadGuide.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }
}