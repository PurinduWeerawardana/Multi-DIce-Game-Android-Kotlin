package com.example.dicegame

import kotlin.random.Random

class RobotDice: Dice(1,6) {
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

    fun getDiceImage(): Int {
        return when (super.getDiceValue()) {
            1 -> R.drawable.dice_face_1_red
            2 -> R.drawable.dice_face_2_red
            3 -> R.drawable.dice_face_3_red
            4 -> R.drawable.dice_face_4_red
            5 -> R.drawable.dice_face_5_red
            6 -> R.drawable.dice_face_6_red
            else -> R.drawable.dice_face_roll_red
        }
    }

    fun resetDice() {
        super.setDiceValue(0)
        reRollCount = 0
    }

}