package com.staszewskiLucas.knucklebones.Activities

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
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
    lateinit var diceButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = GameBoardBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val playerName = intent.getStringExtra("PLAYER_NAME")!!

        computerBoard = createComputerBoard()
        playerBoard = createPlayerBoard()

        computer = Player("Computer", computerBoard)
        player = Player(playerName, playerBoard)

        controller = GameController(this, computer, player)

        diceButton = binding.diceButton

        diceButton.setOnClickListener {
            controller.startRound()
        }


        playerBoard.diceColumnOne.setOnClickListener {
            controller.setDiceInColumn(1)
        }
        playerBoard.diceColumnTwo.setOnClickListener {
            controller.setDiceInColumn(2)
        }
        playerBoard.diceColumnThree.setOnClickListener {
            controller.setDiceInColumn(3)
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

    fun setDiceButtonIcon(number: Int) {
        when (number) {
            1 -> diceButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_one_gold))
            2 -> diceButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_two_gold))
            3 -> diceButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_three_gold))
            4 -> diceButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_four_gold))
            5 -> diceButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_five_gold))
            6 -> diceButton.setImageDrawable(AppCompatResources.getDrawable(this, R.drawable.icon_dice_six_gold))
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
}

