package com.code.pixabay.ui.activities

import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupWithNavController
import com.code.pixabay.R
import com.code.pixabay.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
        lateinit var binding : com.code.pixabay.databinding.ActivityMainBinding
        lateinit var host:NavController


        override fun onCreate(savedInstanceState: Bundle?) {
                super.onCreate(savedInstanceState)
                binding = ActivityMainBinding.inflate(layoutInflater)

                val frag = supportFragmentManager.findFragmentById(R.id.navHostFrag)as NavHostFragment

                 host = frag.findNavController()


                setupBottomNavController(host)


                setContentView(binding.root)


        }
        fun setupBottomNavController(navController: NavController) {binding.botNav.setupWithNavController(navController)
                binding.botNav.labelVisibilityMode = NavigationBarView.LABEL_VISIBILITY_UNLABELED
        }


        override fun onOptionsItemSelected(item: MenuItem): Boolean {
                return item.onNavDestinationSelected(host)||super.onOptionsItemSelected(item)
        }
}