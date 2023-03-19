package com.example.dicegame.models

import com.example.dicegame.R

class UserDice: Dice(1,6) {

    fun resetDice() {
        super.setDiceValue(0)
    }

    fun getDiceImage(): Int {
        return when (super.getDiceValue()) {
            1 -> R.drawable.dice_face_1_blue
            2 -> R.drawable.dice_face_2_blue
            3 -> R.drawable.dice_face_3_blue
            4 -> R.drawable.dice_face_4_blue
            5 -> R.drawable.dice_face_5_blue
            6 -> R.drawable.dice_face_6_blue
            else -> R.drawable.dice_face_roll_blue
        }
    }
}