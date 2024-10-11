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

        val playerName = intent.getStringExtra("PLAYER_NAME")
        val computerName = intent.getStringExtra("COMPUTER_NAME")
        val isTwoPlayerGame = intent.getBooleanExtra("IS_TWO_PLAYER_GAME", false)
        val gameDifficulty = intent.getStringExtra("DIFFICULTY")

        binding.closeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("PLAYER_NAME", playerName)
            intent.putExtra("COMPUTER_NAME", computerName)
            intent.putExtra("IS_TWO_PLAYER_GAME", isTwoPlayerGame)
            intent.putExtra("DIFFICULTY", gameDifficulty)
            startActivity(intent)
        }
    }
}