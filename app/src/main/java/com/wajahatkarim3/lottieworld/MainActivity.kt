package com.wajahatkarim3.lottieworld

import android.os.Bundle
import android.os.Handler
import androidx.core.os.postDelayed
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupActionBarWithNavController
import com.wajahatkarim3.lottieworld.base.BaseActivity
import com.wajahatkarim3.lottieworld.databinding.ActivityMainBinding
import com.wajahatkarim3.lottieworld.utils.gone
import com.wajahatkarim3.lottieworld.utils.show
import com.wajahatkarim3.lottieworld.utils.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private lateinit var bi: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bi = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bi.root)

        setupViews()
    }

    private fun setupViews() {
        setupBottomNav()
    }

    private fun setupBottomNav() {
        var navHostFragment = supportFragmentManager.findFragmentById(R.id.fragNavHost) as NavHostFragment
        navController = navHostFragment.navController
        NavigationUI.setupWithNavController(bi.bottomNavView, navHostFragment.navController)

        var appBarConfiguration = AppBarConfiguration(
            getBottomNavigationIds().toSet()
        )
        setupActionBarWithNavController(navHostFragment.navController, appBarConfiguration)

        // Setting BottomNavigationView visibility for different screens
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Screens which show Bottom Navigation
            if (getScreenIdsWithBottomNavigation().contains(destination.id)) {
                showBottomNavigation()
            }
            // Screens which hide Bottom Navigation
            else {
                hideBottomNavigation()
            }
        }
    }

    //bottom navigation items ids
    private fun getBottomNavigationIds() = listOf(
        R.id.exploreFragment,
        R.id.newsFragment,
        R.id.scanFragment,
        R.id.favoritesFragment,
        R.id.profileFragment
    )

    /**
     * Add all the IDs of the destination fragments in
     * which you want to show the Bottom Navigation View
     * component.
     */
    private fun getScreenIdsWithBottomNavigation() = listOf(
        R.id.exploreFragment,
        R.id.newsFragment,
        R.id.scanFragment,
        R.id.favoritesFragment,
        R.id.profileFragment
    )

    private var backPressedOnce = false

    override fun onBackPressed() {
        if (navController.graph.startDestination == navController.currentDestination?.id)
        {
            if (backPressedOnce)
            {
                super.onBackPressed()
                return
            }

            backPressedOnce = true
            showToast(getString(R.string.back_to_exit_message))

            Handler().postDelayed(2000) {
                backPressedOnce = false
            }
        }
        else {
            super.onBackPressed()
        }
    }

    private fun showBottomNavigation() {
        bi.bottomNavView.show()
    }

    private fun hideBottomNavigation() {
        bi.bottomNavView.gone()
    }
}