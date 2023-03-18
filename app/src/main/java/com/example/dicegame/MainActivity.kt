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
        customGameButton.setOnClickListener() {
            val alertDialogBuilder = AlertDialog.Builder(this)
            alertDialogBuilder.setTitle("Custom Game")
            alertDialogBuilder.setMessage("Enter the winning score:")
            val input = EditText(this)
            input.inputType = android.text.InputType.TYPE_CLASS_NUMBER
            alertDialogBuilder.setView(input)
            alertDialogBuilder.setPositiveButton("OK") { _, _ ->
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("winningScore", input.text.toString().toInt())
                startActivity(intent)
            }
            alertDialogBuilder.setNegativeButton("Cancel", null)
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()
        }
        aboutButton = findViewById(R.id.about_button)
        aboutButton.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            with(builder) {
                setTitle("About")
                setMessage("Author: Purindu Weerawardana (w1867462)\n\n" +
                        "I confirm that I understand what plagiarism is and have read and "+
                        "understood the section on Assessment Offences in the Essential " +
                        "Information for Students. The work that I have submitted is " +
                        "entirely my own. Any work from other authors is duly referenced " +
                        "and acknowledged.")
                setPositiveButton("OK", null)
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