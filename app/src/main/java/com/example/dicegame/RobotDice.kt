package com.example.dicegame

import kotlin.random.Random

class RobotDice: Dice() {
    private val maxReRollCount: Int = 2
    private var reRollCount: Int = 0
    private var doReRoll: Boolean = false

    private fun shouldReRoll(): Boolean {
        return Random.nextBoolean()
    }

    fun reRoll() {
        doReRoll = shouldReRoll()
        if (doReRoll && reRollCount < maxReRollCount) {
            rollDice()
        }
        reRollCount++
    }

    fun getFinalDiceValue(): Int {
        if (reRollCount < maxReRollCount) {
            reRoll()
            getFinalDiceValue()
        } else {
            reRollCount = 0
        }
        return getDiceValue()
    }

}