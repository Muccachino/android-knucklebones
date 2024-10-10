package com.staszewskiLucas.knucklebones.Activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.staszewskiLucas.knucklebones.R
import com.staszewskiLucas.knucklebones.databinding.EndGameBinding

class EndGameActivity: AppCompatActivity() {
    private lateinit var binding: EndGameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EndGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val computerEndPoints = intent.getIntExtra("COMPUTER_POINTS", 0)
        val playerEndPoints = intent.getIntExtra("PLAYER_POINTS", 0)
        val computerName = intent.getStringExtra("COMPUTER_NAME")!!
        val playerName = intent.getStringExtra("PLAYER_NAME")!!

        setEndGameTexts(computerName, computerEndPoints, playerName, playerEndPoints)

        binding.newGameButton.setOnClickListener {
            val intent = Intent(this, GameBoardActivity::class.java)
            intent.putExtra("COMPUTER_NAME", computerName)
            intent.putExtra("PLAYER_NAME", playerName)
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    fun setEndGameTexts(computerName: String, computerEndPoints: Int, playerName:String, playerEndPoints: Int) {
        val winner = if(computerEndPoints > playerEndPoints) computerName else playerName

        getString(R.string.end_game_header, "$winner\nwon the game!")
        getString(R.string.end_game_text, "$computerName: $computerEndPoints", "$playerName: $playerEndPoints")
    }
}