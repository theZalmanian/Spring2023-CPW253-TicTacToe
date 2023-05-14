package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    /**
     * An array containing all the "game" buttons
     */
    private lateinit var gameButtons: Array<Button>

    /**
     * Keeps track of which player's turn it is
     *
     * It's always player X's turn at the start of the game
     */
    private var playerXTurn = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setup array of all "game" buttons
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

        // run through all the "game" buttons
        for(currButton in gameButtons) {
            // set up the current buttons onclick event
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
                    // display an X on the button
                    currButton.setText(R.string.x_symbol)

                    // swap the turn over to Player O
                    swapPlayerTurn()
                }

                // otherwise it's currently Player O's turn
                else {
                    // display an O on the button
                    currButton.setText(R.string.o_symbol)

                    // swap the turn over to Player X
                    swapPlayerTurn()
                }
            }
        }
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

                // enable them so the players can click them again
                currButton.isEnabled = true
            }

            // if it is currently Player O's turn
            if(!playerXTurn) {
                // make it Player X's turn
                swapPlayerTurn()
            }
        }
    }

    /**
     * When called one of three things will occur:
     * 1. The current player won the game, or the game ended in a tie, at which point the game will end
     * 2. The current player did not win the game, the turn swaps on to the next player,
     * and display's that players name in the TextView under the tic-tac-toe board
     */
    private fun swapPlayerTurn() {
        // if the current player won, end the game
        if(checkIfCurrentPlayerWon()) {
            endGame(false)
        }

        // or if the game ended in a tie, end the game
        else if(checkIfGameEndedInTie()) {
            endGame(true)
        }

        // otherwise, swap the turn to the next player
        else {
            // get the "game text" TextView
            val gameText: TextView = findViewById(R.id.txtGameText)

            // if it's currently Player X's turn
            if (playerXTurn) {
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
    }

    /**
     * Checks if the current player's latest move caused them to win the game,
     * and returns true or false accordingly
     * @return True if the current player won the game; otherwise False
     */
    private fun checkIfCurrentPlayerWon():Boolean {
        // get all the game buttons
        val btnTopLeft:Button = findViewById(R.id.btnTopLeft)
        val btnTopMiddle:Button = findViewById(R.id.btnTopMiddle)
        val btnTopRight:Button = findViewById(R.id.btnTopRight)
        val btnMiddleLeft:Button = findViewById(R.id.btnMiddleLeft)
        val btnMiddle:Button = findViewById(R.id.btnMiddle)
        val btnMiddleRight:Button = findViewById(R.id.btnMiddleRight)
        val btnBottomLeft:Button = findViewById(R.id.btnBottomLeft)
        val btnBottomMiddle:Button = findViewById(R.id.btnBottomMiddle)
        val btnBottomRight:Button = findViewById(R.id.btnBottomRight)

        /**
         * Keeps track of the current player's symbol:
         * either "X" or "O"
         */
        val playerSymbol: String
        if(playerXTurn) {
            playerSymbol = "X"
        }
        else {
            playerSymbol = "O"
        }

        /**
         * Flag to keep track of if the current player won the game
         */
        var currPlayerWon = false

        // check if the current player won the game horizontally
        if(btnTopLeft.text.equals(playerSymbol)
            && btnTopMiddle.text.equals(playerSymbol)
            && btnTopRight.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        else if(btnMiddleLeft.text.equals(playerSymbol)
                && btnMiddle.text.equals(playerSymbol)
                && btnMiddleRight.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        else if(btnBottomLeft.text.equals(playerSymbol)
                && btnBottomMiddle.text.equals(playerSymbol)
                && btnBottomRight.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        // check if the current player won the game vertically
        else if(btnTopLeft.text.equals(playerSymbol)
                && btnMiddleLeft.text.equals(playerSymbol)
                && btnBottomLeft.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        else if(btnTopMiddle.text.equals(playerSymbol)
                && btnMiddle.text.equals(playerSymbol)
                && btnBottomMiddle.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        else if(btnTopRight.text.equals(playerSymbol)
                && btnMiddleRight.text.equals(playerSymbol)
                && btnBottomRight.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        // check if the current player won the game diagonally
        else if(btnTopLeft.text.equals(playerSymbol)
                && btnMiddle.text.equals(playerSymbol)
                && btnBottomRight.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        else if(btnTopRight.text.equals(playerSymbol)
                && btnMiddle.text.equals(playerSymbol)
                && btnBottomLeft.text.equals(playerSymbol)) {
            currPlayerWon = true
        }

        return currPlayerWon
    }

    /**
     * Checks if the current game has ended in a tie,
     * and returns true or false accordingly
     * @return True if the game has ended in a tie; otherwise False
     */
    private fun checkIfGameEndedInTie():Boolean {
        /**
         * Counter of how many total buttons were clicked
         */
        var buttonClicked = 0

        // run through all "game" buttons
        for(currButton in gameButtons) {
            // if the current button has been clicked
            if(!currButton.text.equals("")) {
                // increment the counter
                buttonClicked++
            }
        }

        // if all 9 buttons were clicked, the game has ended in a tie
        if(buttonClicked == 9) {
            return true
        }

        // otherwise the game is still ongoing
        return false
    }

    /**
     * When called disables all the "game" buttons, and displays if the current player won,
     * or if the game ended in a tie
     * @param gameTied Determines the "end game" text displayed in the TextView:
     * should be True if the game ended in a tie; otherwise False
     */
    private fun endGame(gameTied:Boolean) {
        // run through all "game" buttons
        for(currButton in gameButtons) {
            // disable the current button so players can't click it
            currButton.isEnabled = false
        }

        // get the "game text" TextView
        val gameText:TextView = findViewById(R.id.txtGameText)

        // if the game ended in a tie, alert the players
        if(gameTied) {
            gameText.setText(R.string.game_tied)
        }

        // otherwise display the winner in the "game text" TextView
        else {
            if(playerXTurn) {
                gameText.setText(R.string.player_x_won)
            }
            else {
                gameText.setText(R.string.player_o_won)
            }
        }
    }
}