package com.example.dicegame

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val newGamebutton: Button = findViewById(R.id.new_game_button)
        newGamebutton.setOnClickListener {
            val intent = Intent(this, GameActivity::class.java)
            startActivity(intent)
        }
        val aboutButton: Button = findViewById(R.id.about_button)
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
}