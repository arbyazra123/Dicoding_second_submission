package com.kmm.dicodingsecondsubmission.activity

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentManager
import com.google.android.material.navigation.NavigationView
import com.kmm.dicodingsecondsubmission.R
import com.kmm.dicodingsecondsubmission.databinding.ActivityMainBinding
import com.kmm.dicodingsecondsubmission.ui.home.HomePage
import com.kmm.dicodingsecondsubmission.utlity.DrawerInterface

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    DrawerInterface {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var preferences: SharedPreferences
    private var isNightTheme: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        preferences = getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        isNightTheme = preferences.getBoolean(IS_NIGHT_THEME, true)
        if (isNightTheme)
            setTheme(R.style.Theme_Primary)
        else
            setTheme(R.style.Theme_Secondary)
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        mToggle = ActionBarDrawerToggle(this, binding.mainDrawer, R.string.open, R.string.close)
        binding.mainDrawer.addDrawerListener(mToggle)
        binding.navView.setNavigationItemSelectedListener(this)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_settings_24)
        mToggle.syncState()

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.changeTheme -> {
                println("onNavigationItemSelected : changeTheme")
                preferences.edit().putBoolean(IS_NIGHT_THEME, !isNightTheme).apply()
                recreate()
            }
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mToggle.onOptionsItemSelected(item)
    }


    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.container)
        fragment?.parentFragmentManager?.popBackStackImmediate(
            HomePage.TAG,
            FragmentManager.POP_BACK_STACK_INCLUSIVE
        )
        super.onBackPressed()
    }

    companion object {
        const val PREF_NAME = "github.user"
        const val IS_NIGHT_THEME = "is_night_theme"
    }

    override fun lockDrawer() {
        binding.mainDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)
    }

    override fun unlockDrawer() {
        binding.mainDrawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED)

    }
}