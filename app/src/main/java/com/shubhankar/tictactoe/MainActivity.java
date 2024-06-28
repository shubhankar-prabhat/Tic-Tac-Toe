package com.shubhankar.tictactoe;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private Button[][] buttons = new Button[3][3];
    private boolean player1Turn = true;
    private int roundCount;
    private char player1Symbol = 'X';
    private char player2Symbol = 'O';
    private String[][] field = new String[3][3];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showChoosePlayerDialog();

        initializeButtons();

        Button resetButton = findViewById(R.id.resetButton);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void showChoosePlayerDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Player Symbol");
        String[] symbols = {"X", "O"};
        builder.setItems(symbols, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    player1Symbol = 'X';
                    player2Symbol = 'O';
                } else {
                    player1Symbol = 'O';
                    player2Symbol = 'X';
                }
                Toast.makeText(MainActivity.this, "Player 1: " + player1Symbol + ", Player 2: " + player2Symbol, Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();
    }

    private void initializeButtons() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
            }
        }
    }

    public void onGridButtonClick(View view) {
        Button button = (Button) view;
        if (button.getText().toString().isEmpty()) {
            if (player1Turn) {
                button.setText(String.valueOf(player1Symbol));
            } else {
                button.setText(String.valueOf(player2Symbol));
            }
            roundCount++;
            updateFieldArray();
            if (checkForWin()) {
                if (player1Turn) {
                    playerWins(player1Symbol);
                } else {
                    playerWins(player2Symbol);
                }
            } else if (roundCount == 9) {
                draw();
            } else {
                player1Turn = !player1Turn;
            }
        }
    }

    private void updateFieldArray() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }
    }

    private boolean checkForWin() {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1]) && field[i][1].equals(field[i][2]) && !field[i][0].isEmpty()) {
                return true;
            }
        }
        // Check columns
        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i]) && field[1][i].equals(field[2][i]) && !field[0][i].isEmpty()) {
                return true;
            }
        }
        // Check diagonals
        if (field[0][0].equals(field[1][1]) && field[1][1].equals(field[2][2]) && !field[0][0].isEmpty()) {
            return true;
        }
        if (field[0][2].equals(field[1][1]) && field[1][1].equals(field[2][0]) && !field[0][2].isEmpty()) {
            return true;
        }
        return false;
    }

    private void playerWins(char playerSymbol) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Player " + playerSymbol + " wins!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void draw() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("It's a draw!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetGame();
            }
        });
        builder.setCancelable(false);
        builder.show();
    }

    private void resetGame() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
        initializeFieldArray();
    }

    private void initializeFieldArray() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = "";
            }
        }
    }
}