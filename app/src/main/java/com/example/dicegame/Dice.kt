package com.example.dicegame

abstract class Dice(private val minDiceValue: Int = 1, private val maxDiceValue: Int = 6) {
    private var diceValue: Int = 0

    fun rollDice() {
        diceValue = (minDiceValue..maxDiceValue).random()
    }
    fun getDiceValue(): Int {
        return diceValue
    }

    fun setDiceValue(value: Int) {
        diceValue = value
    }
}