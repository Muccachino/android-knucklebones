package com.staszewskiLucas.knucklebones

import com.staszewskiLucas.knucklebones.Activities.GameBoardActivity
import com.staszewskiLucas.knucklebones.Models.Board
import com.staszewskiLucas.knucklebones.Models.Player
import kotlin.random.Random


class GameController(
    val gameActivity: GameBoardActivity,
    val computer: Player,
    val player: Player,
    var currentPlayer: Player = player,
    var currenPhase: String = "rollDice",
    var currentDiceNumber: Int = 0
    ) {

    fun startRound() {
        if(currenPhase == "rollDice") {
            val currentDiceNumber = rollDice()
            gameActivity.setDiceButtonIcon(currentDiceNumber)
        }

    }

    fun rollDice(): Int {
        return Random.nextInt(1,6) + 1
    }

    fun setDiceInColumn(columnNumber: Int) {
        var column = when(columnNumber) {
            1 -> currentPlayer.board.boardPointValues[0]
            2 -> currentPlayer.board.boardPointValues[1]
            else -> currentPlayer.board.boardPointValues[2]
        }

        val index = column.indexOfFirst { num -> num == 0 }

        if(index != -1) {
            column[index] = currentDiceNumber
            //TODO: View
        }

    }
}