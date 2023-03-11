package com.example.dicegame

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.KeyEvent
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

@SuppressLint("UseSwitchCompatOrMaterialCode")
class GameActivity : AppCompatActivity() {
    private val userDiceList = mutableListOf<Dice>()
    private val userDiceSwitchesList = mutableListOf<Switch>()
    private val robotDiceList = mutableListOf<Dice>()
    private var userTotal: Int = 0
    private var robotTotal: Int = 0
    private var throwCount: Int = 0
    private val maxThrowCount: Int = 3
    private lateinit var userTotalText: TextView
    private lateinit var robotTotalText: TextView
    private lateinit var userScoreText: TextView
    private lateinit var robotScoreText: TextView
    private lateinit var userDice1: ImageView
    private lateinit var userDice1Switch: Switch
    private lateinit var userDice2: ImageView
    private lateinit var userDice2Switch: Switch
    private lateinit var userDice3: ImageView
    private lateinit var userDice3Switch: Switch
    private lateinit var userDice4: ImageView
    private lateinit var userDice4Switch: Switch
    private lateinit var userDice5: ImageView
    private lateinit var userDice5Switch: Switch
    private lateinit var robotDice1: ImageView
    private lateinit var robotDice2: ImageView
    private lateinit var robotDice3: ImageView
    private lateinit var robotDice4: ImageView
    private lateinit var robotDice5: ImageView
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
            robotDiceList.add(Dice())
        }

        // Set the initial dice images
        showDiceImages()

        // throwButton click listener
        throwButton.setOnClickListener {
            throwDices()
            showDiceImages()
            userTotal = 0
            for (dice in userDiceList) {
                userTotal += dice.getDiceValue()
            }
            robotTotal = 0
            for (dice in robotDiceList) {
                robotTotal += dice.getDiceValue()
            }
            userTotalText.text = userTotal.toString()
            robotTotalText.text = robotTotal.toString()
            if (throwCount == maxThrowCount) {
                throwButton.isEnabled = false
                Handler(Looper.getMainLooper()).postDelayed(Runnable {
                    scoreButton.performClick()
                }, 4000)
                Toast.makeText(this, "Optional rolls are over. Score will update automatically.", Toast.LENGTH_SHORT).show()
            } else{
                throwButton.text = "Throw (${throwCount + 1})"
            }
        }

        // scoreButton click listener
        scoreButton.setOnClickListener {
            updateScore(userTotal, robotTotal)
            throwButton.isEnabled = false
            userTotal = 0
            robotTotal = 0
            userTotalText.text = userTotal.toString()
            robotTotalText.text = robotTotal.toString()
            throwCount = 0
            throwButton.text = "Throw"
            throwButton.isEnabled = true
        }
    }

    private fun initializeViews() {
        userScoreText = findViewById(R.id.user_score_text)
        robotScoreText = findViewById(R.id.robot_score_text)
        robotTotalText = findViewById(R.id.robot_total_text)
        robotDice1 = findViewById(R.id.robot_dice_image_1)
        robotDice2 = findViewById(R.id.robot_dice_image_2)
        robotDice4 = findViewById(R.id.robot_dice_image_4)
        robotDice3 = findViewById(R.id.robot_dice_image_3)
        robotDice5 = findViewById(R.id.robot_dice_image_5)
        userTotalText = findViewById(R.id.user_total_text)
        userDice1 = findViewById(R.id.user_dice_image_1)
        userDice1Switch = findViewById(R.id.user_dice_switch_1)
        userDice2 = findViewById(R.id.user_dice_image_2)
        userDice2Switch = findViewById(R.id.user_dice_switch_2)
        userDice3 = findViewById(R.id.user_dice_image_3)
        userDice3Switch = findViewById(R.id.user_dice_switch_3)
        userDice4 = findViewById(R.id.user_dice_image_4)
        userDice4Switch = findViewById(R.id.user_dice_switch_4)
        userDice5 = findViewById(R.id.user_dice_image_5)
        userDice5Switch = findViewById(R.id.user_dice_switch_5)
        throwButton = findViewById(R.id.throw_button)
        scoreButton = findViewById(R.id.score_button)
    }

    private fun showDiceImages() {
        userDice1.setImageResource(userDiceList[0].getDiceImage())
        userDice2.setImageResource(userDiceList[1].getDiceImage())
        userDice3.setImageResource(userDiceList[2].getDiceImage())
        userDice4.setImageResource(userDiceList[3].getDiceImage())
        userDice5.setImageResource(userDiceList[4].getDiceImage())
        robotDice1.setImageResource(robotDiceList[0].getDiceImage())
        robotDice2.setImageResource(robotDiceList[1].getDiceImage())
        robotDice3.setImageResource(robotDiceList[2].getDiceImage())
        robotDice4.setImageResource(robotDiceList[3].getDiceImage())
        robotDice5.setImageResource(robotDiceList[4].getDiceImage())
    }

    private fun throwDices() {
        if (throwCount == 0){
            for (i in 0..4) {
                userDiceList[i].rollDice()
                robotDiceList[i].rollDice()
            }
        } else {
            if (userDice1Switch.isChecked) {
                userDice1Switch.isChecked = false
            } else {
                userDiceList[0].rollDice()
            }
            if (userDice2Switch.isChecked) {
                userDice2Switch.isChecked = false
            } else {
                userDiceList[1].rollDice()
            }
            if (userDice3Switch.isChecked) {
                userDice3Switch.isChecked = false
            } else {
                userDiceList[2].rollDice()
            }
            if (userDice4Switch.isChecked) {
                userDice4Switch.isChecked = false
            } else {
                userDiceList[3].rollDice()
            }
            if (userDice5Switch.isChecked) {
                userDice5Switch.isChecked = false
            } else {
                userDiceList[4].rollDice()
            }
        }
        throwCount++
    }

    private fun updateScore(playerTotal: Int, computerTotal: Int) {
        userScoreText.text = userScoreText.text.toString().toInt().plus(playerTotal).toString()
        if (userScoreText.text.toString().toInt() >= 101) {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Game Over!")
            alertDialogBuilder.setMessage("You have won the game!")
            alertDialogBuilder.setPositiveButton("OK", null)
            alertDialogBuilder.setOnKeyListener(
                DialogInterface.OnKeyListener { dialog, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    dialog.dismiss()
                    finish()
                    return@OnKeyListener true
                }
                false
                }
            )
            // TODO: Change the color of the dialog box
            // TODO: finish the game
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
            return@updateScore
        }
        robotScoreText.text = robotScoreText.text.toString().toInt().plus(computerTotal).toString()
        if (robotScoreText.text.toString().toInt() >= 101) {
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("Game Over!")
                setMessage("You have lost the game!")
                setPositiveButton("OK", null)
                setNegativeButton("CANCEL", null)
                setNeutralButton("NEUTRAL", null)
            }
            val alertDialog = builder.create()
            alertDialog.show()

            val button = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
            with(button) {
                setBackgroundColor(Color.RED)
                setPadding(0, 0, 20, 0)
                setTextColor(Color.WHITE)
            }
            //finish()
        }
        resetDices()
    }

    private fun resetDices() {
        for (i in 0..4) {
            userDiceList[i].resetDice()
            robotDiceList[i].resetDice()
        }
        showDiceImages()
        userDice1Switch.isChecked = false
        userDice2Switch.isChecked = false
        userDice3Switch.isChecked = false
        userDice4Switch.isChecked = false
        userDice5Switch.isChecked = false
    }

}