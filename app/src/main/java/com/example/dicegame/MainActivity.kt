package com.example.dicegame

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    private lateinit var newGameButton: Button
    private lateinit var customGameButton: Button
    private lateinit var aboutButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        newGameButton = findViewById(R.id.new_game_button)
        newGameButton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        customGameButton = findViewById(R.id.custom_game_button)
        customGameButton.setOnClickListener {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle(R.string.custom_game_btn)
            alertDialogBuilder.setMessage(R.string.custom_win_score_prompt_text)
            val layout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            val winScoreInput = EditText(this)
            winScoreInput.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            layout.addView(winScoreInput)
            val modeInput = RadioGroup(this)
            val easyOption = RadioButton(this)
            easyOption.text = getString(R.string.easy_mode)
            val mediumOption = RadioButton(this)
            mediumOption.text = getString(R.string.medium_mode)
            val hardOption = RadioButton(this)
            hardOption.text = getString(R.string.hard_mode)
            modeInput.addView(easyOption)
            modeInput.addView(mediumOption)
            modeInput.addView(hardOption)
            layout.addView(modeInput)
            alertDialogBuilder.setView(layout)
            alertDialogBuilder.setPositiveButton(R.string.positive_btn_text, null)
            alertDialogBuilder.setNegativeButton(R.string.negative_btn_text, null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.setOnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                    if (winScoreInput.text.toString().isEmpty() || winScoreInput.text.toString().toInt() < 10) {
                        winScoreInput.setText(getString(R.string.default_win_score_text))
                        Toast.makeText(this, R.string.custom_win_score_toast_text, Toast.LENGTH_SHORT).show()
                    } else if (!(easyOption.isChecked || hardOption.isChecked || mediumOption.isChecked)) {
                        Toast.makeText(this, R.string.custom_mode_toast_text, Toast.LENGTH_SHORT).show()
                    } else {
                        val intent = Intent(this, GameActivity::class.java)
                        intent.putExtra("winningScore", winScoreInput.text.toString().toInt())
                        if (easyOption.isChecked) {
                            intent.putExtra("mode", "easy")
                        } else if (mediumOption.isChecked) {
                            intent.putExtra("mode", "medium")
                        } else {
                            intent.putExtra("mode", "hard")
                        }
                        startActivity(intent).also { alertDialog.dismiss() }
                    }
                }
            }
            alertDialog.show()
        }
        aboutButton = findViewById(R.id.about_button)
        aboutButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle(R.string.about_label_text)
                setMessage(R.string.about_text)
                setPositiveButton(R.string.positive_btn_text, null)
            }
            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}