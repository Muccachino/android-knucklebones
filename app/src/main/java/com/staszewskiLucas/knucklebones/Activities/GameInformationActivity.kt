package com.staszewskiLucas.knucklebones.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.staszewskiLucas.knucklebones.databinding.GameInformationBinding

class GameInformationActivity: AppCompatActivity() {
    private lateinit var binding: GameInformationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GameInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}