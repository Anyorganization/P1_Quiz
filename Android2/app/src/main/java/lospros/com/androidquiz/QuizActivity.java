package lospros.com.androidquiz;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Scanner;

public class QuizActivity extends AppCompatActivity {

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);



        ArrayList<Question> qs = new ArrayList<>();
        ///Reading JSON.
        InputStream is = getResources().openRawResource(R.raw.questions);
        String dataFile = "";
        try {
            int size = is.available();
            byte [] buffer = new byte[size];
            is.read(buffer);
            is.close();
            dataFile =  new String(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ArrayList<Question> questionList = new Gson().fromJson(dataFile, new TypeToken<ArrayList<Question>>(){}.getType());






        ///

        Button[] buttons = { (Button) findViewById(R.id.answer1), (Button) findViewById(R.id.answer2), (Button) findViewById(R.id.answer3), (Button) findViewById(R.id.answer4)};
        TextView text_question = (TextView) findViewById(R.id.text_question);
        text_question.setText(questionList.get(0).getQuestion());
        final ImageView questionImg = (ImageView) findViewById(R.id.question_image);
        questionImg.setImageResource(getResources().getIdentifier(questionList.get(0).getImage(), "drawable", getPackageName()));

        String[] answers = questionList.get(0).getAns();

        final int cA = questionList.get(0).getcA();

        for (int i = 0; i < ids_answers.length; i++) {
            Button btn = buttons[i];
            btn.setText(answers[i]);
            final int aux = i;
            btn.setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (aux == cA) {
                        Toast.makeText(QuizActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                        //ImageView questionImg = (ImageView) findViewById(R.id.question_image);
                        questionImg.setVisibility(View.GONE);
                        questionImg.setVisibility(View.VISIBLE);
                        questionImg.setImageResource(R.drawable.pepa);
                    } else {//Incorrecta
                        Toast.makeText(QuizActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                        //ImageView questionImg = (ImageView) findViewById(R.id.question_image);
                        questionImg.setVisibility(View.VISIBLE);
                        questionImg.setImageResource(R.drawable.artefinal);
                    }
                }
            }));
        }
    }
}
