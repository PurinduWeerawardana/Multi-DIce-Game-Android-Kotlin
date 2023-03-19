package com.example.dicegame.models

import com.example.dicegame.R
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

    /** Implementing an efficient strategy for the computer player
     * These smart methods will use strategically according to the game score within the GameActivity.
     * Smart re-rolling based on the following rules:
     * 1. If the dice value is less than or equal to half of the maximum dice value, re-roll the dice.
     *      - This smart method will manually assign "true" to the doReRoll variable if the dice value is less than or equal to half of the maximum dice value.
     *      - It won't manually assign a higher value in order  to keep the game fair.
     *      - It will try to get a higher dice value by re-rolling for a random value.
     *  2. If the dice value is greater than half of the maximum dice value, keep the dice value.
     *      - This smart method will manually assign "false" to the doReRoll variable if the dice value is greater than half of the maximum dice value.
     *      - It will keep the current dice value without rolling.
    */

    fun smartReRoll() {
        if (reRollCount < maxReRollCount) {
            doReRoll = getDiceValue() <= super.getMaximumDiceValue()/2
            if (doReRoll) {
                rollDice()
            }
            reRollCount++
        }
    }

    fun getSmartFinalDiceValue(): Int {
        if (reRollCount < maxReRollCount) {
            smartReRoll()
            getSmartFinalDiceValue()
        } else {
            reRollCount = 0
        }
        return getDiceValue()
    }

}