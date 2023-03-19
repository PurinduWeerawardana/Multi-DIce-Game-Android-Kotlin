package com.example.dicegame

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.*
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.dicegame.models.RobotDice
import com.example.dicegame.models.UserDice
import java.util.*

@SuppressLint("UseSwitchCompatOrMaterialCode")
class GameActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "GameActivity"
        private var userWins: Int = 0
        private var robotWins: Int = 0
    }
    private var smartRobot: Boolean = false
    private var hardMode: Boolean = false
    private var minScoreGap: Int = 0
    private var userDiceList = mutableListOf<UserDice>()
    private var userDiceSwitchesList = mutableListOf<Switch>()
    private var robotDiceList = mutableListOf<RobotDice>()
    private var userTotal: Int = 0
    private var userScore: Int = 0
    private var robotTotal: Int = 0
    private var robotScore: Int = 0
    private var throwCount: Int = 0
    private var maxThrowCount: Int = 3
    private var winningScore: Int = 101
    private lateinit var userWinScore: TextView
    private lateinit var robotWinScore: TextView
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
    private lateinit var winCounter: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val intent: Intent = intent
        if (intent.hasExtra("winningScore")) {
            winningScore = intent.getIntExtra("winningScore", 101)
        }
        if (intent.hasExtra("mode")) {
            if (intent.getStringExtra("mode") == "medium"){
                smartRobot = true
                minScoreGap = 10
            } else if (intent.getStringExtra("mode") == "hard") {
                smartRobot = true
                hardMode = true
            }
        }

        // Initialize the views
        initializeViews()

        // Initialize the dice lists ans switches
        for (i in 1..5) {
            userDiceList.add(UserDice())
            robotDiceList.add(RobotDice())
        }
        userDiceSwitchesList.add(userDice1Switch)
        userDiceSwitchesList.add(userDice2Switch)
        userDiceSwitchesList.add(userDice3Switch)
        userDiceSwitchesList.add(userDice4Switch)
        userDiceSwitchesList.add(userDice5Switch)

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
                Handler(Looper.getMainLooper()).postDelayed( {
                    scoreButton.performClick()
                }, 4000)
                Toast.makeText(this, getString(R.string.no_optional_roll_text), Toast.LENGTH_SHORT).show()
            } else{
                throwButton.text = getString(R.string.re_roll_btn, throwCount)
            }
        }

        // scoreButton click listener
        scoreButton.setOnClickListener {
            throwButton.isEnabled = false
            updateScore()
            userTotal = 0
            robotTotal = 0
            userTotalText.text = userTotal.toString()
            robotTotalText.text = robotTotal.toString()
            throwCount = 0
            throwButton.text = getString(R.string.throw_btn)
            throwButton.isEnabled = true
        }
    }

    private fun initializeViews() {
        userWinScore = findViewById(R.id.user_win_score)
        robotWinScore = findViewById(R.id.robot_win_score)
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
        winCounter = findViewById(R.id.win_counter)
        winCounter.text = getString(R.string.win_counter_text, userWins, robotWins)
        userWinScore.text = getString(R.string.winning_score_text, winningScore)
        robotWinScore.text = getString(R.string.winning_score_text, winningScore)
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
            for (i in 0..4) {
                if (userDiceSwitchesList[i].isChecked) {
                    userDiceSwitchesList[i].isChecked = false
                } else {
                    userDiceList[i].rollDice()
                }
            }
            for (robotDice in robotDiceList) {
                if (smartRobot && hardMode){
                    Log.d("Robot", "Robot is smart and hard")
                    robotDice.smartReRoll()
                } else if (smartRobot && minScoreGap <= userScore-robotScore){
                    Log.d("Robot", "Robot is smart and minScoreGap is met")
                    robotDice.smartReRoll()
                } else {
                    Log.d("Robot", "Robot is not smart")
                    robotDice.reRoll()
                }
            }
        }
        throwCount++
    }

    private fun updateScore() {
        userScore = userScoreText.text.toString().toInt().plus(this.userTotal)
        robotScore = robotScoreText.text.toString().toInt().plus(this.robotTotal)
        if (userScore < winningScore && robotScore < winningScore){
            if (throwCount == 0){
                Toast.makeText(this, R.string.throw_dices_first_text, Toast.LENGTH_SHORT).show()
            } else if (throwCount < maxThrowCount){
                var finalRobotTotal = 0
                for (robotDice in robotDiceList) {
                    finalRobotTotal += if (smartRobot && hardMode){
                        robotDice.getSmartFinalDiceValue()
                    } else if (smartRobot && minScoreGap <= robotScore-userScore){
                        robotDice.getSmartFinalDiceValue()
                    } else {
                        robotDice.getFinalDiceValue()
                    }
                }
                if (finalRobotTotal != this.robotTotal){
                    robotScore += finalRobotTotal-robotTotal
                    Toast.makeText(this, getString(R.string.final_robot_total_text, (maxThrowCount-throwCount), finalRobotTotal), Toast.LENGTH_LONG).show()
                }
            }
            updateScoreUI(userScore,robotScore)
        } else{
            updateScoreUI(userScore,robotScore)
            maxThrowCount = 1
            if (userScore == robotScore){
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle(R.string.scores_tied_popup_title_text)
                alertDialogBuilder.setMessage(R.string.scores_tied_popup_msg)
                alertDialogBuilder.setPositiveButton(R.string.positive_btn_text, null)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            } else if (userScore > robotScore) {
                userWins++
                winCounter.text = getString(R.string.win_counter_text, userWins, robotWins)
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle(R.string.game_over_popup_title_text)
                alertDialogBuilder.setMessage(R.string.game_won_popup_msg)
                alertDialogBuilder.setPositiveButton(R.string.positive_btn_text) { _, _ -> this.finish() }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.holo_green_light)
            } else {
                robotWins++
                winCounter.text = getString(R.string.win_counter_text, userWins, robotWins)
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle(R.string.game_over_popup_title_text)
                alertDialogBuilder.setMessage(getString(R.string.game_lost_popup_msg))
                alertDialogBuilder.setPositiveButton(R.string.positive_btn_text) { _, _ -> this.finish() }
                val alertDialog = alertDialogBuilder.create()
                alertDialog.setCancelable(false)
                alertDialog.show()
                alertDialog.window?.setBackgroundDrawableResource(android.R.color.holo_red_light)
            }
        }
        resetDices()
    }

    private fun updateScoreUI(userScore: Int, robotScore: Int) {
        userScoreText.text = userScore.toString()
        robotScoreText.text = robotScore.toString()
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
        outState.putInt("userWins", userWins)
        outState.putInt("robotWins", robotWins)
        outState.putInt("userTotal", userTotal)
        outState.putInt("userScore", userScore)
        outState.putInt("robotTotal", robotTotal)
        outState.putInt("robotScore", robotScore)
        outState.putInt("throwCount", throwCount)
        outState.putInt("maxThrowCount", maxThrowCount)
        outState.putInt("winningScore", winningScore)
        outState.putSerializable("userDiceList", userDiceList as ArrayList<UserDice>)
        outState.putSerializable("robotDiceList", robotDiceList as ArrayList<RobotDice>)
        outState.putSerializable("userDiceSwitchesList", userDiceSwitchesList as ArrayList<Switch>)
    }

    @Suppress("DEPRECATION")
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        Log.i(TAG, "onRestoreInstanceState")
        userWins = savedInstanceState.getInt("userWins")
        robotWins = savedInstanceState.getInt("robotWins")
        winCounter.text = resources.getString(R.string.win_counter_text, userWins, robotWins)
        userTotal = savedInstanceState.getInt("userTotal")
        userTotalText.text = userTotal.toString()
        userScore = savedInstanceState.getInt("userScore")
        userScoreText.text = userScore.toString()
        robotTotal = savedInstanceState.getInt("robotTotal")
        robotTotalText.text = robotTotal.toString()
        robotScore = savedInstanceState.getInt("robotScore")
        robotScoreText.text = robotScore.toString()
        throwCount = savedInstanceState.getInt("throwCount")
        maxThrowCount = savedInstanceState.getInt("maxThrowCount")
        if (throwCount > 0){
            throwButton.text = getString(R.string.re_roll_btn, throwCount)
        }
        winningScore = savedInstanceState.getInt("winningScore")
        userWinScore.text = getString(R.string.winning_score_text, winningScore)
        robotWinScore.text = getString(R.string.winning_score_text, winningScore)
        userDiceList = savedInstanceState.getSerializable("userDiceList") as MutableList<UserDice>
        robotDiceList = savedInstanceState.getSerializable("robotDiceList") as MutableList<RobotDice>
        userDiceSwitchesList = savedInstanceState.getSerializable("userDiceSwitchesList") as MutableList<Switch>
        showDiceImages()
    }
}