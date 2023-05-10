package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var gameButtons: Array<Button> // array to hold all game buttons
    private var playerXTurn = true // it's player X's turn at start of game

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup array of all game buttons
        gameButtons = arrayOf(
            findViewById(R.id.btnTopLeft),
            findViewById(R.id.btnTopMiddle),
            findViewById(R.id.btnTopRight),
            findViewById(R.id.btnMiddleLeft),
            findViewById(R.id.btnMiddle),
            findViewById(R.id.btnMiddleRight),
            findViewById(R.id.btnBottomLeft),
            findViewById(R.id.btnBottomMiddle),
            findViewById(R.id.btnBottomRight)
        )

        // run through all game buttons
        for(currButton in gameButtons) {
            setupGameButtonOnClick(currButton)
        }

        // setup onclick event for the "new game" button
        setupNewGameButtonOnClick()
    }

    /**
     * Sets up an on-click event for the given "game" button, so that when clicked
     * it displays the current players symbol, and then passes the turn on to the next player
     * @param currButton The given button for which the on-click event is set up
     */
    private fun setupGameButtonOnClick(currButton:Button) {
        // set-up the given button's onclick event
        currButton.setOnClickListener {
            // as long as the button has not already been selected
            if(currButton.text.equals("")) {
                // if it's currently Player X's turn
                if(playerXTurn) {
                    // set the button's text to contain an X
                    currButton.setText(R.string.x_symbol)

                    // swap the turn over to Player O
                    swapPlayerTurn()
                }

                // otherwise it's currently Player O's turn
                else {
                    // set the button's text to contain an O
                    currButton.setText(R.string.o_symbol)

                    // swap the turn over to Player X
                    swapPlayerTurn()
                }
            }
        }
    }

    /**
     * When called, swaps the turn on to the next player,
     * and display's that players name in the under the tic-tac-toe board
     */
    private fun swapPlayerTurn() {
        // get the "game text" text view
        val gameText:TextView = findViewById(R.id.txtGameText)

        // if it's currently Player X's turn
        if(playerXTurn) {
            // update the display so it is Player O's turn
            gameText.setText(R.string.player_o_turn)
        }

        // otherwise it's currently Player O's turn
        else {
            // update the display so it is Player X's turn
            gameText.setText(R.string.player_x_turn)
        }

        // pass the turn on to the next player
        playerXTurn = !playerXTurn
    }


    /**
     * When called, sets up the on-click event for the "new game" button,
     * so that when clicked it resets the tic-tac-toe board to it's original state,
     * and restarts the game with Player X as the starting player
     */
    private fun setupNewGameButtonOnClick() {
        // get the "new game" button
        val btnNewGame:Button = findViewById(R.id.btnNewGame)

        // set up it's onclick event
        btnNewGame.setOnClickListener {
            // run through all nine game buttons
            for(currButton in gameButtons) {
                // reset their text
                currButton.text = ""
            }

            // if it is currently Player O's turn
            if(!playerXTurn) {
                // make it Player X's turn
                swapPlayerTurn()
            }
        }
    }
}