package com.example.dicegame

class Dice {
    private var diceValue: Int = 0
    fun rollDice() {
        diceValue = (1..6).random()
    }
    fun getDiceValue(): Int {
        return diceValue
    }
    fun getDiceImage(): Int {
        return when (diceValue) {
            1 -> R.drawable.dice_face_1
            2 -> R.drawable.dice_face_2
            3 -> R.drawable.dice_face_3
            4 -> R.drawable.dice_face_4
            5 -> R.drawable.dice_face_5
            6 -> R.drawable.dice_face_6
            else -> R.drawable.dice_face_roll
        }
    }

    fun resetDice() {
        diceValue = 0
    }

}