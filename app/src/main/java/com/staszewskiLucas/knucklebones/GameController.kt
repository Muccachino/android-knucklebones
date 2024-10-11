package com.staszewskiLucas.knucklebones

import com.staszewskiLucas.knucklebones.Activities.GameBoardActivity
import com.staszewskiLucas.knucklebones.Models.Board
import com.staszewskiLucas.knucklebones.Models.Player
import kotlin.random.Random


class GameController(
    val gameActivity: GameBoardActivity,
    val allPlayers: List<Player>,
    var currentPlayer: Player = allPlayers[0],
    var currentPhase: String = "rollDice",
    var currentDiceNumber: Int = 0
    ) {

    fun computerTurn() {
        startRound()
        computerChooseColumn()
    }

    fun computerChooseColumn() {
        if(currentPlayer.difficulty == "easy") {
            easyComputerTurn()
        }
        if (currentPlayer.difficulty == "medium") {
            mediumComputerTurn()
        }
        if(currentPlayer.difficulty == "hard") {
            hardComputerTurn()
        }
    }

    fun easyComputerTurn() {
        var column = Random.nextInt(0,3)
        while (checkIfColumnFull(currentPlayer.board.boardPointValues[column])) {
            column = Random.nextInt(0,3)
        }
        setDiceInColumn(column)
    }

    fun mediumComputerTurn() {
        if (!checkIfColumnFull(currentPlayer.board.boardPointValues[0]) && checkColumnForCurrentNumber(0)) {
            setDiceInColumn(0)
        }
        else if (!checkIfColumnFull(currentPlayer.board.boardPointValues[1]) && checkColumnForCurrentNumber(1)) {
            setDiceInColumn(1)
        }
        else if (!checkIfColumnFull(currentPlayer.board.boardPointValues[2]) && checkColumnForCurrentNumber(2)) {
            setDiceInColumn(2)
        }
        else {
            easyComputerTurn()
        }
    }

    fun hardComputerTurn() {
        val enemy = allPlayers[0]
        val columnToAttack = checkForErasingEnemyDice()
        if(columnToAttack != -1) {
            setDiceInColumn(columnToAttack)
        } else {
            mediumComputerTurn()
        }
    }

    fun startRound() {
        if(currentPhase != "rollDice") return

        currentDiceNumber = rollDice()
        if(currentPlayer.computer || currentPlayer == allPlayers[1]) {
            gameActivity.setDiceButtonIconTop(currentDiceNumber)
        }else {
            gameActivity.setDiceButtonIconBottom(currentDiceNumber)
        }

        currentPhase = "setDice"
    }

    fun rollDice(): Int {
        return Random.nextInt(1,6) + 1
    }

    fun setDiceInColumn(columnNumber: Int) {
        if (currentPhase != "setDice") return

        val column = when(columnNumber) {
            0 -> currentPlayer.board.boardPointValues[0]
            1 -> currentPlayer.board.boardPointValues[1]
            else -> currentPlayer.board.boardPointValues[2]
        }

        val index = column.indexOfFirst { num -> num == 0 }

        if(index != -1) {
            column[index] = currentDiceNumber
            gameActivity.setDiceIconInColumn(columnNumber, index)

            checkEnemyColumn(columnNumber)

            resolvePoints(allPlayers[0], allPlayers[0].board.boardPointValues)
            resolvePoints(allPlayers[1], allPlayers[1].board.boardPointValues)

            gameActivity.updatePointsLabel()

            if(checkGameEnd()) {
                endGame()
            }
            else {
                nextTurn()
            }
        }

    }

    fun checkEnemyColumn(columnNumber: Int) {
        val enemy = if (currentPlayer == allPlayers[0]) allPlayers[1] else allPlayers[0]

        enemy.board.boardPointValues[columnNumber].forEachIndexed { index, _ ->
            if(enemy.board.boardPointValues[columnNumber][index] == currentDiceNumber) {
            enemy.board.boardPointValues[columnNumber][index] = 0
        }}

        gameActivity.updateEnemyColumn(enemy, columnNumber)
    }

    fun resolvePoints(player: Player, boardValues: MutableList<MutableList<Int>>) {
        var points = 0

        for (column in boardValues) {
            if(column[0] == column[1] && column[1] == column[2]) {
                points += 3 * (column[0] + column[1] + column[2])
            }
            else if (column[0] == column[1]) {
                points += 2 * column[0] + 2 * column[1] + column[2]
            }
            else if (column[0] == column[2]) {
                points += 2 * column[0] + column[1] + 2 * column[2]
            }
            else if (column[1] == column[2]) {
                points += column[0] + 2 * column[1] + 2 * column[2]
            }
            else {
                points += column[0] + column[1] + column[2]
            }
        }
        player.points = points
    }

    fun checkIfColumnFull(column: MutableList<Int>):Boolean {
        for (value in column) {
            if(value == 0){
                return false
            }
        }
        return true
    }

    fun checkColumnForCurrentNumber(columnNumber: Int): Boolean {
        return currentPlayer.board.boardPointValues[columnNumber].contains(currentDiceNumber)
    }

    fun checkForErasingEnemyDice(): Int {
        allPlayers[0].board.boardPointValues.forEachIndexed { index, column ->
            if (column.all { it == currentDiceNumber }) {
                return index
            }
            if(column.count{ it == currentDiceNumber } == 2) {
                return index
            }
            if(column.count { it == currentDiceNumber } == 1) {
                return index
            }
        }
        return -1
    }

    fun nextTurn() {
        currentPhase = "rollDice"
        currentPlayer = if(currentPlayer == allPlayers[0]) allPlayers[1] else allPlayers[0]

        if(currentPlayer.computer) {
            computerTurn()
        }
    }

    fun checkGameEnd(): Boolean {
        for (column in currentPlayer.board.boardPointValues) {
            for (value in column) {
                if (value == 0) {
                    return false
                }
            }
        }
        return true
    }

    fun endGame() {
        gameActivity.switchToEndGameActivity()
    }
}