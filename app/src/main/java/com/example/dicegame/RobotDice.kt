package com.example.dicegame

import kotlin.random.Random

class RobotDice: Dice() {
    private val maxReRollCount: Int = 2
    private var reRollCount: Int = 0
    private var doReRoll: Boolean = false

    private fun shouldReRoll(): Boolean {
        return Random.nextBoolean()
    }

    override fun rollDice() {
        if (reRollCount < 2) {
            super.rollDice()
            doReRoll = shouldReRoll()
        }
    }

    fun reRoll() {
        if (doReRoll) {
            super.rollDice()
            reRollCount++
        }
    }

}