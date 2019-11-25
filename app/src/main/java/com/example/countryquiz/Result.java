package com.example.countryquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Result extends AppCompatActivity {
    private TextView totalResult;
    private Button playAgain;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        totalResult=(TextView)findViewById(R.id.TotalResult);
        Intent intent = getIntent();
        int points = intent.getIntExtra(CountryQuiz.Points_Holder, 0);

        totalResult.setText("You earned" + points + "points!");
        playAgainButton();


    }

    public void playAgainButton(){
        playAgain=(Button)findViewById(R.id.PlayAgainButton);
        playAgain.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent myIntent = new Intent(Result.this,
                                CountryQuiz.class);
                        startActivity(myIntent);

                    }
                }

        );

    }
}
