package com.example.dicegame

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText

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
            val input = EditText(this)
            input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            alertDialogBuilder.setView(input)
            alertDialogBuilder.setPositiveButton(R.string.positive_btn_text) { _, _ ->
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("winningScore", input.text.toString().toInt())
                startActivity(intent)
            }
            alertDialogBuilder.setNegativeButton(R.string.negative_btn_text, null)
            val alertDialog = alertDialogBuilder.create()
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
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState")
    }
}