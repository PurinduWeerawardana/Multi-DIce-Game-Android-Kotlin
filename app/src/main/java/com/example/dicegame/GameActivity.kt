package com.example.dicegame

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class GameActivity : AppCompatActivity() {
    private val userDiceList = mutableListOf<Dice>()
    private val computerDiceList = mutableListOf<Dice>()
    private var userTotal: Int = 0
    private var computerTotal: Int = 0
    private var throwCount: Int = 0
    private lateinit var playerTotalText: TextView
    private lateinit var computerTotalText: TextView
    private lateinit var playerScoreText: TextView
    private lateinit var computerScoreText: TextView
    private lateinit var userDice1: ImageView
    private lateinit var userDice2: ImageView
    private lateinit var userDice3: ImageView
    private lateinit var userDice4: ImageView
    private lateinit var userDice5: ImageView
    private lateinit var computerDice1: ImageView
    private lateinit var computerDice2: ImageView
    private lateinit var computerDice3: ImageView
    private lateinit var computerDice4: ImageView
    private lateinit var computerDice5: ImageView
    private lateinit var throwButton: Button
    private lateinit var scoreButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        // Initialize the views
        initializeViews()

        // Initialize the dice lists
        for (i in 1..5) {
            userDiceList.add(Dice())
            computerDiceList.add(Dice())
        }

        // Set the initial dice images
        showDiceImages()

        // throwButton click listener
        throwButton.setOnClickListener {
            if (throwCount == 3) {
                scoreButton.performClick()
                Toast.makeText(this, "You have used all optional rolls. Score automatically updated.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            throwDices()
            showDiceImages()
            userTotal = 0
            for (dice in userDiceList) {
                userTotal += dice.getDiceValue()
            }
            computerTotal = 0
            for (dice in computerDiceList) {
                computerTotal += dice.getDiceValue()
            }
            playerTotalText.text = userTotal.toString()
            computerTotalText.text = computerTotal.toString()
        }

        // scoreButton click listener
        scoreButton.setOnClickListener {
            updateScore(userTotal, computerTotal)
            userTotal = 0
            computerTotal = 0
            playerTotalText.text = userTotal.toString()
            computerTotalText.text = computerTotal.toString()
            throwCount = 0
            throwButton.isEnabled = true
        }
    }

    private fun initializeViews() {
        playerTotalText = findViewById(R.id.player_total_text)
        computerTotalText = findViewById(R.id.robot_total_text)
        playerScoreText = findViewById(R.id.player_score_text)
        computerScoreText = findViewById(R.id.robot_score_text)
        userDice1 = findViewById(R.id.user_dice_image_one)
        userDice2 = findViewById(R.id.user_dice_image_two)
        userDice3 = findViewById(R.id.user_dice_image_three)
        userDice4 = findViewById(R.id.user_dice_image_four)
        userDice5 = findViewById(R.id.user_dice_image_five)
        computerDice1 = findViewById(R.id.robot_dice_image_one)
        computerDice2 = findViewById(R.id.robot_dice_image_two)
        computerDice3 = findViewById(R.id.robot_dice_image_three)
        computerDice4 = findViewById(R.id.robot_dice_image_four)
        computerDice5 = findViewById(R.id.robot_dice_image_five)
        throwButton = findViewById(R.id.throw_button)
        scoreButton = findViewById(R.id.score_button)
    }

    private fun showDiceImages() {
        userDice1.setImageResource(userDiceList[0].getDiceImage())
        userDice2.setImageResource(userDiceList[1].getDiceImage())
        userDice3.setImageResource(userDiceList[2].getDiceImage())
        userDice4.setImageResource(userDiceList[3].getDiceImage())
        userDice5.setImageResource(userDiceList[4].getDiceImage())
        computerDice1.setImageResource(computerDiceList[0].getDiceImage())
        computerDice2.setImageResource(computerDiceList[1].getDiceImage())
        computerDice3.setImageResource(computerDiceList[2].getDiceImage())
        computerDice4.setImageResource(computerDiceList[3].getDiceImage())
        computerDice5.setImageResource(computerDiceList[4].getDiceImage())
    }

    private fun throwDices() {
        throwCount++
        for (i in 0..4) {
            userDiceList[i].rollDice()
            computerDiceList[i].rollDice()
        }
    }

    private fun updateScore(playerTotal: Int, computerTotal: Int) {
        playerScoreText.text = playerScoreText.text.toString().toInt().plus(playerTotal).toString()
        if (playerScoreText.text.toString().toInt() >= 101) {
            Toast.makeText(this, "You have won the game!", Toast.LENGTH_LONG).show()
            finish()
        }
        computerScoreText.text = computerScoreText.text.toString().toInt().plus(computerTotal).toString()
        if (computerScoreText.text.toString().toInt() >= 101) {
            Toast.makeText(this, "You have lost the game!", Toast.LENGTH_LONG).show()
            finish()
        }
        resetDices()
    }

    private fun resetDices() {
        for (i in 0..4) {
            userDiceList[i].resetDice()
            computerDiceList[i].resetDice()
        }
        showDiceImages()
    }

}