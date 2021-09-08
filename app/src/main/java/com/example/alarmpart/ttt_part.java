package com.example.alarmpart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class ttt_part extends AppCompatActivity implements View.OnClickListener {

    private Button[][] buttons = new Button[3][3];

    private boolean player1Turn = true;

    private int cc=0;

    private ArrayList<String> list = new ArrayList<String>(Arrays.asList("00","01","02","10","11","12","20","21","22"));



    private int roundCount;

    private int player1Points;
    private int player2Points;

    private TextView textViewPlayer1;
    private TextView textViewPlayer2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ttt_part);



        textViewPlayer1 = findViewById(R.id.text_view_p1);
        textViewPlayer2 = findViewById(R.id.text_view_p2);

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button buttonReset = findViewById(R.id.button_reset);
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    @Override
    public void onClick(View v) {
        Button mute = (Button) findViewById(R.id.mute);
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        if (!((Button) v).getText().toString().equals("")) {
            return;
        }

        if (player1Turn) {
            ((Button) v).setText("X");
            String s = v.getResources().getResourceName(v.getId());
            list.remove(s);
            cc++;

        } else {
            //((Button) v).setText("O");
            cc++;
            fillinempty();
            randomize();
        }

        roundCount++;

        if (checkForWin()) {
            if (player1Turn) {
                player1Wins();
                if(player1Points>=3){
                    mute.setEnabled(true);
                    mute.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            audioManager.setRingerMode(1);
                        }
                    });
                }else{
                    mute.setEnabled(false);
                }
            } else {
                player2Wins();
                if(player2Points>=3){
                    mute.setEnabled(true);
                    mute.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            audioManager.setRingerMode(1);
                        }
                    });
                }else{
                    mute.setEnabled(false);
                }
            }
        } else if (roundCount == 9) {
            draw();
        } else {
            player1Turn = !player1Turn;
        }




    }

    private boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }
        return false;
    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
        list.add("00");
        list.add("01");
        list.add("02");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("20");
        list.add("21");
        list.add("22");
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        updatePointsText();
        resetBoard();
        list.add("00");
        list.add("01");
        list.add("02");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("20");
        list.add("21");
        list.add("22");
    }

    private void draw() {
        Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        resetBoard();
        list.add("00");
        list.add("01");
        list.add("02");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("20");
        list.add("21");
        list.add("22");
    }

    private void updatePointsText() {
        textViewPlayer1.setText("Player 1: " + player1Points);
        textViewPlayer2.setText("Player 2: " + player2Points);
    }

    private void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount = 0;
        player1Turn = true;
    }

    private void resetGame(){
        player1Points =0;
        player2Points =0;
        updatePointsText();
        list.clear();
        resetBoard();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Points",player1Points);
        outState.putInt("player2Points",player2Points);
        outState.putBoolean("player1Turn",player1Turn);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        roundCount = savedInstanceState.getInt("roundCount");
        player1Points = savedInstanceState.getInt("player1Points");
        player2Points = savedInstanceState.getInt("player2Points");
    }

    public void randomize(){
        String exit = "";


        while(!exit.equals("exit")){
            Random r = new Random();
            int num1 = r.nextInt(2);
            int num2 = r.nextInt(2);
            String id = "button_"+num1+num2;
            int resID = getResources().getIdentifier(id, "id", getPackageName());
            Button b = (Button) findViewById(resID);

            if(b.getText().toString().equals("")){
                b.setText("O");
                exit = "exit";
            }


        }


    }


    public void randomize2(){
        String exit = "";



        while(!exit.equals("exit")){
            Random r = new Random();
            int num1 = r.nextInt(list.size());
            //int num2 = r.nextInt(9);
            String s = list.get(num1);
            String id = "button_"+s;
            int resID = getResources().getIdentifier(id, "id", getPackageName());
            Button b = (Button) findViewById(resID);

            //if(b.getText().toString().equals("")){
                b.setText("O");
                list.remove(id);
                exit = "exit";
            //}


        }


    }

    public void fillinempty(){



    }
}
