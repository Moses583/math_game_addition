package com.example.bettysmathsgame;

import android.content.DialogInterface;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView txtOne,txtTwo,txtTimeRemaining,txtFinal;
    private EditText edtTxtAnswer;
    private Button btnSubmit,btnStart;
    private CountDownTimer countDownTimer;
    private String answer = "";
    private int count = 0;
    private int correct = 0;
    private MediaPlayer player1,player2,player3,player4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        player1 = MediaPlayer.create(this,R.raw.correct);
        player2 = MediaPlayer.create(this,R.raw.wrong);
        player3 = MediaPlayer.create(this,R.raw.pass);
        player4 = MediaPlayer.create(this,R.raw.fail);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtTxtAnswer.setText("");
                txtOne.setText(String.valueOf(randomOne()));
                txtTwo.setText(String.valueOf(randomTwo()));
                txtFinal.setVisibility(View.INVISIBLE);
                countDownTimer.start();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 10){
                    countDownTimer.cancel();
                    count = 0;
                    if (correct>=5){
                        txtFinal.setVisibility(View.VISIBLE);
                        txtFinal.setTextColor(Color.GREEN);
                        txtFinal.setText("You have gotten "+correct+" answers correct!");
                        player3.start();
                        correct = 0;
                    }else{
                        txtFinal.setVisibility(View.VISIBLE);
                        txtFinal.setTextColor(Color.RED);
                        txtFinal.setText("You have gotten "+correct+" answers correct!");
                        player4.start();
                        correct = 0;
                    }
                }else{
                    answer = edtTxtAnswer.getText().toString();
                    if (compare(answer)){
                        count++;
                        player1.start();
                        txtOne.setText(String.valueOf(randomOne()));
                        txtTwo.setText(String.valueOf(randomTwo()));
                        beginCountdown();
                        correct++;
                    }else{
                        count++;
                        player2.start();
                        txtOne.setText(String.valueOf(randomOne()));
                        txtTwo.setText(String.valueOf(randomTwo()));
                        beginCountdown();
                    }
                    edtTxtAnswer.setText("");
                }

            }
        });

        countDownTimer = new CountDownTimer(12000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimeRemaining.setText(String.valueOf(millisUntilFinished/1000)+"s remaining");
            }
            @Override
            public void onFinish() {
                if (count == 10){
                    countDownTimer.cancel();
                    count = 0;
                    if (correct>=5){
                        txtFinal.setVisibility(View.VISIBLE);
                        txtFinal.setTextColor(Color.GREEN);
                        txtFinal.setText("You have gotten "+correct+" answers correct!");
                        player3.start();
                    }else{
                        txtFinal.setVisibility(View.VISIBLE);
                        txtFinal.setTextColor(Color.RED);
                        txtFinal.setText("You have gotten "+correct+" answers correct!");
                        player4.start();
                    }
                }else{
                    if (answer.isEmpty()){
                        count++;
                        player2.start();
                        txtOne.setText(String.valueOf(randomOne()));
                        txtTwo.setText(String.valueOf(randomTwo()));
                        beginCountdown();
                        Toast.makeText(MainActivity.this, "Time is up!", Toast.LENGTH_SHORT).show();
                    }
                    else{
                        answer = edtTxtAnswer.getText().toString();
                        if (compare(answer)){
                            count++;
                            player1.start();
                            txtOne.setText(String.valueOf(randomOne()));
                            txtTwo.setText(String.valueOf(randomTwo()));
                            beginCountdown();
                            correct++;
                        }else{
                            count++;
                            player2.start();
                            txtOne.setText(String.valueOf(randomOne()));
                            txtTwo.setText(String.valueOf(randomTwo()));
                            beginCountdown();
                        }
                    }
                    edtTxtAnswer.setText("");
                }

            }
        };

    }

    private void initViews() {
        txtOne = findViewById(R.id.txtFirstNumber);
        txtTwo = findViewById(R.id.txtSecondNumber);
        txtTimeRemaining = findViewById(R.id.txtTimeRemaining);
        txtFinal = findViewById(R.id.txtFinal);
        edtTxtAnswer = findViewById(R.id.edtTxtAnswer);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnStart = findViewById(R.id.btnStart);
    }
    private int randomOne(){
        Random random = new Random();
        return random.nextInt(50);
    }
    private int randomTwo(){
        Random random = new Random();
        return random.nextInt(50);
    }
    private int compute(){
        int one = Integer.parseInt(txtOne.getText().toString());
        int two = Integer.parseInt(txtTwo.getText().toString());
        return one+two;
    }
    private boolean compare (String two){
        int one = compute();
        return String.valueOf(one).equals(two);
    }
    private void beginCountdown(){
       countDownTimer.cancel();
       countDownTimer.start();
    }
    @Override
    protected void onStop() {
        super.onStop();
        player1.release();
        player2.release();
        player3.release();
        player4.release();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player1.release();
        player2.release();
        player3.release();
        player4.release();
    }
}