package com.staszewskiLucas.knucklebones.Activities

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import com.staszewskiLucas.knucklebones.GameController
import com.staszewskiLucas.knucklebones.Models.Board
import com.staszewskiLucas.knucklebones.Models.Player
import com.staszewskiLucas.knucklebones.R
import com.staszewskiLucas.knucklebones.databinding.GameBoardBinding

class GameBoardActivity: AppCompatActivity() {
    private lateinit var binding: GameBoardBinding
    lateinit var computer: Player
    lateinit var player: Player
    lateinit var computerBoard: Board
    lateinit var playerBoard: Board
    lateinit var controller: GameController
    lateinit var diceButtonBottom: ImageView
    lateinit var diceButtonTop: ImageView
    lateinit var computerStats: TextView
    lateinit var playerStats: TextView
    var isTwoPlayerGame: Boolean = false
    var gameDifficulty: String = "easy"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GameBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val computerName = intent.getStringExtra("COMPUTER_NAME")!!
        val playerName = intent.getStringExtra("PLAYER_NAME")!!
        isTwoPlayerGame = intent.getBooleanExtra("IS_TWO_PLAYER_GAME", false)
        gameDifficulty = intent.getStringExtra("DIFFICULTY")!!

        computerBoard = createComputerBoard()
        playerBoard = createPlayerBoard()
        computer = Player(computerName, computerBoard, !isTwoPlayerGame, gameDifficulty)
        player = Player(playerName, playerBoard, false)

        controller = GameController(this, listOf(player, computer))

        computerStats = binding.computerStats
        playerStats = binding.playerStats

        binding.backHomeButton.setOnClickListener {
            showExitConfirmationDialog()
        }

        updatePointsLabel()

        diceButtonTop = binding.diceButtonTop
        diceButtonTop.setOnClickListener {
            if(controller.currentPlayer == computer) {
                controller.startRound()
            }
        }

        diceButtonBottom = binding.diceButtonBottom
        diceButtonBottom.setOnClickListener {
            if(controller.currentPlayer == player) {
                controller.startRound()
            }
        }

        playerBoard.diceColumnOne.setOnClickListener {
            if(controller.currentPlayer == player && !controller.checkIfColumnFull(playerBoard.boardPointValues[0])) {
                controller.setDiceInColumn(0)
            }
        }
        playerBoard.diceColumnTwo.setOnClickListener {
            if(controller.currentPlayer == player && !controller.checkIfColumnFull(playerBoard.boardPointValues[1])) {
                controller.setDiceInColumn(1)
            }
        }
        playerBoard.diceColumnThree.setOnClickListener {
            if(controller.currentPlayer == player && !controller.checkIfColumnFull(playerBoard.boardPointValues[2])) {
                controller.setDiceInColumn(2)
            }
        }

        computerBoard.diceColumnOne.setOnClickListener {
            if(controller.currentPlayer == computer && !controller.checkIfColumnFull(computerBoard.boardPointValues[0])) {
                controller.setDiceInColumn(0)
            }
        }
        computerBoard.diceColumnTwo.setOnClickListener {
            if(controller.currentPlayer == computer && !controller.checkIfColumnFull(computerBoard.boardPointValues[1])) {
                controller.setDiceInColumn(1)
            }
        }
        computerBoard.diceColumnThree.setOnClickListener {
            if(controller.currentPlayer == computer && !controller.checkIfColumnFull(computerBoard.boardPointValues[2])) {
                controller.setDiceInColumn(2)
            }
        }
    }

    private fun createComputerBoard(): Board {
        return Board(binding.computerStats, binding.computerColumnOne, binding.computerColumnTwo, binding.computerColumnThree,
            listOf(
                listOf(binding.computerDiceOneOne, binding.computerDiceOneTwo, binding.computerDiceOneThree),
                listOf(binding.computerDiceTwoOne, binding.computerDiceTwoTwo, binding.computerDiceTwoThree),
                listOf(binding.computerDiceThreeOne, binding.computerDiceThreeTwo, binding.computerDiceThreeThree)
            )
        )
    }

    private fun createPlayerBoard(): Board {
        return Board(binding.playerStats, binding.playerColumnOne, binding.playerColumnTwo, binding.playerColumnThree,
            listOf(
                listOf(binding.playerDiceOneOne, binding.playerDiceOneTwo, binding.playerDiceOneThree),
                listOf(binding.playerDiceTwoOne, binding.playerDiceTwoTwo, binding.playerDiceTwoThree),
                listOf(binding.playerDiceThreeOne, binding.playerDiceThreeTwo, binding.playerDiceThreeThree)
            )
        )
    }

    fun setDiceButtonIconBottom(number: Int) {
        when (number) {
            1 -> diceButtonBottom.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_one_gold))
            2 -> diceButtonBottom.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_two_gold))
            3 -> diceButtonBottom.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_three_gold))
            4 -> diceButtonBottom.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_four_gold))
            5 -> diceButtonBottom.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_five_gold))
            6 -> diceButtonBottom.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_six_gold))
        }
    }

    fun setDiceButtonIconTop(number: Int) {
        when (number) {
            1 -> diceButtonTop.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_one_silver))
            2 -> diceButtonTop.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_two_silver))
            3 -> diceButtonTop.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_three_silver))
            4 -> diceButtonTop.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_four_silver))
            5 -> diceButtonTop.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_five_silver))
            6 -> diceButtonTop.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_six_silver))
        }
    }

    fun setDiceIconInColumn(column: Int, index: Int) {
        if(controller.currentPlayer == player) {
            when (controller.currentDiceNumber) {
                1 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_one_gold))
                2 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_two_gold))
                3 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_three_gold))
                4 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_four_gold))
                5 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_five_gold))
                6 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_six_gold))
            }
        } else {
            when (controller.currentDiceNumber) {
                1 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_one_silver))
                2 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_two_silver))
                3 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_three_silver))
                4 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_four_silver))
                5 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_five_silver))
                6 -> controller.currentPlayer.board.allDiceAreas[column][index].setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_six_silver))
            }
        }
    }

    fun updateEnemyColumn(enemy: Player, column: Int) {
        if(enemy == player) {
            enemy.board.allDiceAreas[column].forEachIndexed { index, area ->
                when (enemy.board.boardPointValues[column][index]) {
                    1 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_one_gold))
                    2 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_two_gold))
                    3 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_three_gold))
                    4 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_four_gold))
                    5 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_five_gold))
                    6 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_six_gold))
                    else -> area.setImageDrawable(null)
                }
            }
        } else {
            enemy.board.allDiceAreas[column].forEachIndexed { index, area ->
                when (enemy.board.boardPointValues[column][index]) {
                    1 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_one_silver))
                    2 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_two_silver))
                    3 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_three_silver))
                    4 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_four_silver))
                    5 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_five_silver))
                    6 -> area.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_six_silver))
                    else -> area.setImageDrawable(null)
                }
            }
        }
    }

    fun updatePointsLabel() {
        playerStats.text = String.format(getResources().getString(R.string.player_points), player.name, player.points)
        computerStats.text = String.format(getResources().getString(R.string.computer_points), computer.name, computer.points)
    }

    fun switchToEndGameActivity() {
        val intent = Intent(this, EndGameActivity::class.java)
        intent.putExtra("COMPUTER_POINTS", computer.points)
        intent.putExtra("PLAYER_POINTS", player.points)
        intent.putExtra("COMPUTER_NAME", computer.name)
        intent.putExtra("PLAYER_NAME", player.name)
        intent.putExtra("IS_TWO_PLAYER_GAME", isTwoPlayerGame)
        intent.putExtra("DIFFICULTY", gameDifficulty)
        startActivity(intent)
    }

    fun showExitConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Spiel beenden")
        builder.setMessage("Möchten sie das laufende Spiel beenden und zum Hauptmenü zurückkehren?")
        builder.setPositiveButton("Ja") { _, _ ->
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("COMPUTER_NAME", computer.name)
            intent.putExtra("PLAYER_NAME", player.name)
            intent.putExtra("IS_TWO_PLAYER_GAME", isTwoPlayerGame)
            intent.putExtra("DIFFICULTY", gameDifficulty)
            startActivity(intent)
        }
        builder.setNegativeButton("Abbrechen", null)
        builder.show()
    }
}

