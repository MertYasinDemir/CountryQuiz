package com.example.countryquiz;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class CountryQuiz extends Activity {

    public static String Points_Holder;
    static final int Restart_Quiz = 1;
    private int points;
    private int correctAnswer;
    private ImageView countryOutline;
    private TextView bonusQuestion;
    private TextView bonusQuestionAnswer;
    private TextView bonusQuestionResult;
    private TextView mainQuestionResult;
    private TextView pointsView;
    private TextView guess;
    private EditText bonusQueGirdi;
    private ArrayList<Answers> answers;

    private List<Integer> outlines = new ArrayList(Arrays.asList(R.drawable.turkey, R.drawable.america, R.drawable.argentina, R.drawable.azerbaijan, R.drawable.brazil, R.drawable.belgium, R.drawable.china, R.drawable.czechrepublic, R.drawable.germany,
            R.drawable.france, R.drawable.southkorea, R.drawable.india, R.drawable.holland, R.drawable.iraq, R.drawable.england, R.drawable.spain, R.drawable.switzerland, R.drawable.sweden, R.drawable.japan, R.drawable.kazakhstan,
            R.drawable.colombia, R.drawable.hungary, R.drawable.norway, R.drawable.portugal, R.drawable.russia, R.drawable.syria, R.drawable.greece, R.drawable.ukraine, R.drawable.tunisia, R.drawable.italy));

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;
    private Button next;

    private static final NumberFormat currencyInstance = NumberFormat.getCurrencyInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.country_quiz);
        bonusQueGirdi = (EditText) findViewById(R.id.BonusQueGirdi);
        bonusQueGirdi.setEnabled(true);
        countryOutline = (ImageView) findViewById(R.id.imageTurkey);
        bonusQuestion = (TextView) findViewById(R.id.BonusQuestion);
        bonusQuestionAnswer = (TextView) findViewById(R.id.BQAnswer);
        bonusQuestionResult = (TextView) findViewById(R.id.BQResult);
        mainQuestionResult = (TextView) findViewById(R.id.MQResult);
        pointsView = (TextView) findViewById(R.id.PointsView);
        guess = (TextView) findViewById(R.id.Guess);
        button1 = (Button) findViewById(R.id.Button1);
        button2 = (Button) findViewById(R.id.Button2);
        button3 = (Button) findViewById(R.id.Button3);
        button4 = (Button) findViewById(R.id.Button4);
        Button restartQuiz = (Button) findViewById(R.id.RestartButton);
        next = (Button) findViewById(R.id.NextButton);
        button1.setOnClickListener(clickAnswer);
        button2.setOnClickListener(clickAnswer);
        button3.setOnClickListener(clickAnswer);
        button4.setOnClickListener(clickAnswer);
        restartQuiz.setOnClickListener(clickRestartQuiz);
        next.setOnClickListener(clickNext);
        readAnswersFile();
        startQuiz();

    }

    private void readAnswersFile() {
        InputStream stream = getResources().openRawResource(R.raw.answers);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Charset.forName("UTF-16")));
        Answers newAnswers;
        String answer = null;
        answers = new ArrayList<Answers>();
        try {
            while ((answer = reader.readLine()) != null) {
                String[] differentAnswers = answer.split(";");
                if ((differentAnswers[0].equalsIgnoreCase("C")) &&
                        (differentAnswers[5].equalsIgnoreCase("A") || differentAnswers[5].equalsIgnoreCase("B") || differentAnswers[5].equalsIgnoreCase("C")
                                || differentAnswers[5].equalsIgnoreCase("D"))) {
                    newAnswers = new Answers(differentAnswers[0], differentAnswers[1], differentAnswers[2], differentAnswers[3], differentAnswers[4], differentAnswers[5], differentAnswers[6]);
                    answers.add(newAnswers);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Answers findCurrentAnswers() {
        String answerType = "C";
        for (Answers answers : answers) {
            if (answers.getAnswerType().equalsIgnoreCase(answerType)) {
                return answers;
            }
        }
        return null;
    }

    private void showCurrentAnswers() {
        Answers currentAnswers;
        currentAnswers = findCurrentAnswers();
        if (currentAnswers != null) {
            for (int i = 0; i < outlines.size(); i++) {
                countryOutline.setImageResource(outlines.get(i));
            }
            button1.setText(currentAnswers.getAnswer1());
            button2.setText(currentAnswers.getAnswer2());
            button3.setText(currentAnswers.getAnswer3());
            button4.setText(currentAnswers.getAnswer4());

            if (currentAnswers.getCorrectAnswer().equalsIgnoreCase("A")) {
                correctAnswer = 1;
            } else {
                if (currentAnswers.getCorrectAnswer().equalsIgnoreCase("B")) {
                    correctAnswer = 2;
                } else {
                    if (currentAnswers.getCorrectAnswer().equalsIgnoreCase("C")) {
                        correctAnswer = 3;
                    } else {
                        correctAnswer = 4;
                    }
                }
            }

        }
    }

    private void startQuiz() {
        for (int i = 0; i < outlines.size(); i++) {
            countryOutline.setImageResource(outlines.get(i));
        }
        points = 0;
        Collections.shuffle(answers);
        showCurrentAnswers();
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        button4.setEnabled(true);

        Intent intent = new Intent(this, Result.class);
        intent.putExtra(Points_Holder, points);
        startActivity(intent);
        finish();

    }


    public OnClickListener clickAnswer = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (((String) v.getTag()).equalsIgnoreCase(Integer.toString(correctAnswer))) {
                points = points + 20;
                pointsView.setText(currencyInstance.format(points));
                guess.setText(R.string.FirstGuess + (String) v.getTag());
                mainQuestionResult.setText(R.string.Correct);
                bonusQuestion.setVisibility(View.VISIBLE);
                bonusQuestionAnswer.setVisibility(View.VISIBLE);
                bonusQueGirdi.setVisibility(View.VISIBLE);
                button1.setEnabled(false);
                button2.setEnabled(false);
                button3.setEnabled(false);
                button4.setEnabled(false);

                for (Answers answers : answers) {
                    if ((answers.getCorrectAnswerCapital().equalsIgnoreCase(String.valueOf(bonusQueGirdi))) && bonusQueGirdi.length() > 0) {
                        points = points + 10;
                        pointsView.setText(currencyInstance.format(points));
                        bonusQuestionResult.setText(R.string.Correct);
                        next.setVisibility(View.VISIBLE);
                        bonusQueGirdi.setEnabled(false);
                    } else {
                        if (!(answers.getCorrectAnswerCapital().equalsIgnoreCase(String.valueOf(bonusQueGirdi))) && bonusQueGirdi.length() > 0) {
                            bonusQuestionResult.setText(R.string.Wrong);
                            next.setVisibility(View.VISIBLE);
                            bonusQueGirdi.setEnabled(false);
                        }
                    }
                }
            } else {
                if (((String) v.getTag()).equalsIgnoreCase(String.valueOf(button1))) {
                    button1.setEnabled(false);
                } else {
                    if (((String) v.getTag()).equalsIgnoreCase(String.valueOf(button2))) {
                        button2.setEnabled(false);
                    } else {
                        if (((String) v.getTag()).equalsIgnoreCase(String.valueOf(button3))) {
                            button3.setEnabled(false);
                        } else {
                            if (((String) v.getTag()).equalsIgnoreCase(String.valueOf(button4))) {
                                button4.setEnabled(false);
                            }
                        }
                    }
                }
                guess.setText(R.string.FirstGuess + (String) v.getTag());
                mainQuestionResult.setText(R.string.Wrong);
                if (((String) v.getTag()).equalsIgnoreCase(Integer.toString(correctAnswer))) {
                    points=points+10;
                    pointsView.setText(currencyInstance.format(points));
                    guess.setText(R.string.NextGuess + (String) v.getTag());
                    mainQuestionResult.setText(R.string.Correct);
                    next.setVisibility(View.VISIBLE);
                    button1.setEnabled(false);
                    button2.setEnabled(false);
                    button3.setEnabled(false);
                    button4.setEnabled(false);
                }

            }

        }



    };

    public OnClickListener clickRestartQuiz = new OnClickListener() {
        @Override
        public void onClick(View v) {
            startQuiz();
        }
    };

    public OnClickListener clickNext = new OnClickListener() {
        @Override
        public void onClick(View v) {
            findCurrentAnswers();
        }
    };

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Restart_Quiz) {
            if (resultCode == RESULT_OK) {
                startQuiz();
            }
        }
    }
}
