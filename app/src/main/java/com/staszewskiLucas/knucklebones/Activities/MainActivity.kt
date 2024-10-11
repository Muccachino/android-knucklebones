package com.staszewskiLucas.knucklebones.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial
import com.staszewskiLucas.knucklebones.R
import com.staszewskiLucas.knucklebones.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var nameInput: EditText
    private lateinit var nameInputTwo: EditText
    private lateinit var switchButton: SwitchMaterial
    private lateinit var easyButton: TextView
    private lateinit var mediumButton: TextView
    private lateinit var hardButton: TextView
    private var playerName: String = "Spieler"
    private var playerNameTwo: String = ""
    private var isTwoPlayerGame = false
    private var gameDifficulty = "easy"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val usedPlayerName = intent.getStringExtra("PLAYER_NAME")
        val usedComputerName = intent.getStringExtra("COMPUTER_NAME")
        val usedDifficulty = intent.getStringExtra("DIFFICULTY")
        val wasTwoPlayerGame = intent.getBooleanExtra("IS_TWO_PLAYER_GAME", false)


        nameInput = binding.nameInput
        nameInputTwo = binding.nameInputTwo

        switchButton = binding.materialSwitch
        switchButton.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked) {
                binding.difficultyPanel.visibility = View.GONE
                binding.playerTwoPanel.visibility = View.VISIBLE
                isTwoPlayerGame = true
            }
            else {
                binding.difficultyPanel.visibility = View.VISIBLE
                binding.playerTwoPanel.visibility = View.GONE
                isTwoPlayerGame = false
            }
        }


        easyButton = binding.difficultyEasy
        mediumButton = binding.difficultyMedium
        hardButton = binding.difficultyHard
        easyButton.setOnClickListener {
            setGameDifficulty("easy")
        }
        mediumButton.setOnClickListener {
            setGameDifficulty("medium")
        }
        hardButton.setOnClickListener {
            setGameDifficulty("hard")
        }
        binding.startButton.setOnClickListener {
            startGame()
        }

        binding.infoButton.setOnClickListener {
            openGameInformation()
        }

        if (usedDifficulty.isNullOrEmpty()) {
            setGameDifficulty("easy")
        } else {
            setGameDifficulty(usedDifficulty)
        }
        if(!usedPlayerName.isNullOrEmpty() && !usedComputerName.isNullOrEmpty()) {
            setUsedElements(usedPlayerName, usedComputerName, wasTwoPlayerGame)
        }
    }

    private fun setUsedElements(usedPlayerName: String, usedComputerName: String, wasTwoPlayerGame: Boolean) {
        nameInput.setText(usedPlayerName)
        if(usedComputerName == "Computer (${gameDifficulty})") {
            nameInputTwo.setText(playerNameTwo)
        } else {
            nameInputTwo.setText(usedComputerName)
        }
        switchButton.isChecked = wasTwoPlayerGame
    }

    private fun setGameDifficulty(difficulty: String){
        gameDifficulty = difficulty
        when (difficulty) {
            "easy" -> {
                easyButton.setBackgroundResource(R.drawable.difficulty_button_border_active)
                mediumButton.setBackgroundResource(R.drawable.difficulty_button_border_inactive)
                hardButton.setBackgroundResource(R.drawable.difficulty_button_border_inactive)
            }
            "medium" -> {
                mediumButton.setBackgroundResource(R.drawable.difficulty_button_border_active)
                easyButton.setBackgroundResource(R.drawable.difficulty_button_border_inactive)
                hardButton.setBackgroundResource(R.drawable.difficulty_button_border_inactive)
            }
            "hard" -> {
                hardButton.setBackgroundResource(R.drawable.difficulty_button_border_active)
                mediumButton.setBackgroundResource(R.drawable.difficulty_button_border_inactive)
                easyButton.setBackgroundResource(R.drawable.difficulty_button_border_inactive)
            }
        }
    }

    private fun startGame() {
        playerName = nameInput.text.toString().trim()
        if (playerName.isEmpty()) {
            playerName = "Spieler"
        }
        if(isTwoPlayerGame) {
            playerNameTwo = nameInputTwo.text.toString().trim()
            if(playerNameTwo.isEmpty()) {
                playerNameTwo = "Spieler 2"
            }
        }

        val intent = Intent(this, GameBoardActivity::class.java)
        intent.putExtra("PLAYER_NAME", playerName)
        intent.putExtra("COMPUTER_NAME", if(isTwoPlayerGame) playerNameTwo else "Computer (${gameDifficulty})")
        intent.putExtra("IS_TWO_PLAYER_GAME", isTwoPlayerGame)
        intent.putExtra("DIFFICULTY", gameDifficulty)
        startActivity(intent)
    }

    private fun openGameInformation() {
        playerName = nameInput.text.toString().trim()
        if (playerName.isEmpty()) {
            playerName = "Spieler"
        }
        if(isTwoPlayerGame) {
            playerNameTwo = nameInputTwo.text.toString().trim()
            if(playerNameTwo.isEmpty()) {
                playerNameTwo = "Spieler 2"
            }
        }

        val intent = Intent(this, GameInformationActivity::class.java)
        intent.putExtra("PLAYER_NAME", playerName)
        intent.putExtra("COMPUTER_NAME", if(isTwoPlayerGame) playerNameTwo else "Computer (${gameDifficulty})")
        intent.putExtra("IS_TWO_PLAYER_GAME", isTwoPlayerGame)
        intent.putExtra("DIFFICULTY", gameDifficulty)
        startActivity(intent)
    }
}