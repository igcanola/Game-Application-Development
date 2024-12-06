package com.example.assignment2_1

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class GameMainPage : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_main)

        // Initialize buttons
        val btnEasy: Button = findViewById(R.id.btnEasy)
        val btnMedium: Button = findViewById(R.id.btnMedium)
        val btnIntermediate: Button = findViewById(R.id.btnIntermediate)
        val btnAdvanced: Button = findViewById(R.id.btnAdvanced)

        // Set up click listeners for each button
        btnEasy.setOnClickListener {
            startActivity(Intent(this, EasyLevelActivity::class.java))
        }

        btnMedium.setOnClickListener {
            startActivity(Intent(this, MediumLevelActivity::class.java))
        }

        btnIntermediate.setOnClickListener {
            startActivity(Intent(this, IntermediateLevelActivity::class.java))
        }

        btnAdvanced.setOnClickListener {
            startActivity(Intent(this, AdvancedLevelActivity::class.java))
        }
    }
}
