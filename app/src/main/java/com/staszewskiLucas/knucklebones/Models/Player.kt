package com.staszewskiLucas.knucklebones.Models

class Player(val name: String, val board: Board, val computer: Boolean, val difficulty: String = "easy", var points: Int = 0)