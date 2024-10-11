package com.staszewskiLucas.knucklebones.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.staszewskiLucas.knucklebones.R
import com.staszewskiLucas.knucklebones.databinding.EndGameBinding

class EndGameActivity: AppCompatActivity() {
    private lateinit var binding: EndGameBinding
    private lateinit var endGameHeader: TextView
    private lateinit var endGameText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = EndGameBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val computerEndPoints = intent.getIntExtra("COMPUTER_POINTS", 0)
        val playerEndPoints = intent.getIntExtra("PLAYER_POINTS", 0)
        val computerName = intent.getStringExtra("COMPUTER_NAME")!!
        val playerName = intent.getStringExtra("PLAYER_NAME")!!
        val isTwoPlayerGame = intent.getBooleanExtra("IS_TWO_PLAYER_GAME", false)
        val gameDifficulty = intent.getStringExtra("DIFFICULTY")

        endGameHeader = binding.endGameHeader
        endGameText = binding.endGameText

        setEndGameTexts(computerName, computerEndPoints, playerName, playerEndPoints)

        binding.newGameButton.setOnClickListener {
            val intent = Intent(this, GameBoardActivity::class.java)
            intent.putExtra("COMPUTER_NAME", computerName)
            intent.putExtra("PLAYER_NAME", playerName)
            intent.putExtra("IS_TWO_PLAYER_GAME", isTwoPlayerGame)
            intent.putExtra("DIFFICULTY", gameDifficulty)
            startActivity(intent)
        }

        binding.homeButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("COMPUTER_NAME", computerName)
            intent.putExtra("PLAYER_NAME", playerName)
            intent.putExtra("IS_TWO_PLAYER_GAME", isTwoPlayerGame)
            intent.putExtra("DIFFICULTY", gameDifficulty)
            startActivity(intent)
        }
    }

    fun setEndGameTexts(computerName: String, computerEndPoints: Int, playerName:String, playerEndPoints: Int) {
        val winner = if(computerEndPoints > playerEndPoints) computerName else playerName

        endGameHeader.text = String.format(getResources().getString(R.string.end_game_header), "$winner\nwon the game!")
        endGameText.text = String.format(getResources().getString(R.string.end_game_text), "$computerName: $computerEndPoints", "$playerName: $playerEndPoints")
    }
}