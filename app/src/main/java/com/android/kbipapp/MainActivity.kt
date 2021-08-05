package com.android.kbipapp

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var prefs : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val navController = findNavController(R.id.nav_fragment)
        bottomNavigationView.setupWithNavController(navController)

        prefs = this.getSharedPreferences("User", Context.MODE_PRIVATE)
        if (prefs.getBoolean("firstrun", true)) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setMessage(R.string.help_text)
            alertDialogBuilder.setTitle(R.string.welcome)
            alertDialogBuilder.setPositiveButton("OK") { dialog, id -> dialog.cancel() }
            alertDialogBuilder.show()
            prefs.edit().putBoolean("firstrun", false).apply()

        }

    }
}