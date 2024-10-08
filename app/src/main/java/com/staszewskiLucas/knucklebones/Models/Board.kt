package com.staszewskiLucas.knucklebones.Models

import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

 data class Board (
    val pointView: TextView,
    val diceColumnOne: LinearLayout,
    val diceColumnTwo: LinearLayout,
    val diceColumnThree: LinearLayout,
    val allDiceAreas: List<List<ImageView>>,
) {
    var boardPointValues = mutableListOf(
        mutableListOf(0, 0, 0),
        mutableListOf(0, 0, 0),
        mutableListOf(0, 0, 0)
    )
 }