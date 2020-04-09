package com.ajijul.givemeforcast.ui.main

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.ajijul.givemeforcast.R
import com.ajijul.givemeforcast.utils.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpNavigation()
    }

    fun setUpNavigation() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment?
        NavigationUI.setupWithNavController(
            bttm_nav,
            navHostFragment!!.navController
        )
        bttm_nav.setOnNavigationItemReselectedListener {
            // Do nothing to ignore the reselection
        }
    }
}