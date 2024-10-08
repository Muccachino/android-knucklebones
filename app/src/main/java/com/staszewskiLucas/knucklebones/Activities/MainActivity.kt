package com.staszewskiLucas.knucklebones.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.staszewskiLucas.knucklebones.GameController
import com.staszewskiLucas.knucklebones.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.startButton.setOnClickListener {
            var playerName = binding.nameInput.text.toString().trim()
            if (playerName.isEmpty()) {
                playerName = "Player"
            }
            startGame(playerName)
        }

        binding.infoButton.setOnClickListener {
            openGameInformation()
        }
    }

    private fun startGame(playerName: String) {
        val intent = Intent(this, GameBoardActivity::class.java)
        intent.putExtra("PLAYER_NAME", playerName)
        startActivity(intent)
    }

    private fun openGameInformation() {
        val intent = Intent(this, GameInformationActivity::class.java)
        startActivity(intent)
    }
}