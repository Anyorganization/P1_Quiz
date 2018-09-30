package lospros.com.androidquiz;

import android.support.v4.app.DialogFragment;
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
import java.util.Collections;
import java.util.Scanner;


public class QuizActivity extends AppCompatActivity implements IncorrectAnswerDialog.NoticeDialogListener {


    //Variables:
    Button[] buttons;
    TextView text_question;
    ImageView questionImg;
    ArrayList<Question> questionList;
    int currentQuestion =0;
    int cA;

    int score = 0;
    int nQuestions;



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

        questionList = new Gson().fromJson(dataFile, new TypeToken<ArrayList<Question>>(){}.getType());
        Collections.shuffle(questionList);





        ///
        buttons = new Button[]{(Button) findViewById(R.id.answer1), (Button) findViewById(R.id.answer2), (Button) findViewById(R.id.answer3), (Button) findViewById(R.id.answer4)};
        text_question = (TextView) findViewById(R.id.text_question);
        text_question.setText(questionList.get(0).getQuestion());
        questionImg = (ImageView) findViewById(R.id.question_image);
        //questionImg.setImageResource(getResources().getIdentifier(questionList.get(0).getImage(), "drawable", getPackageName()));



        for (Button b : buttons){
            b.setBackgroundResource(android.R.drawable.btn_default);
        }




        for (int i = 0; i < ids_answers.length; i++) {
            final int aux = i;
            buttons[i].setOnClickListener((new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    checkQuestion(aux);
                }
            }));
        }
        initQuestion(questionList.get(currentQuestion));
    }

    private void initQuestion(Question q){
        //Question
        text_question.setText(getResources().getIdentifier(q.getQuestion(), "string", getPackageName()));

        //Image (?)
        if(q.getImage() != null) {
            questionImg.setVisibility(View.VISIBLE);
            questionImg.setImageResource(getResources().getIdentifier(q.getImage(), "drawable", getPackageName()));
        }else{
            questionImg.setVisibility(View.GONE);
        }

        //Correct Answer id
        cA = q.getcA();

        //image or text questions.
        if(q.isiA()) {
            for (int i = 0; i < buttons.length; i++) {
                String nameImg = getString(getResources().getIdentifier(q.getAns()[i], "string", getPackageName()));
                buttons[i].setBackgroundResource(getResources().getIdentifier(nameImg, "drawable", getPackageName()));
                buttons[i].setText("");
            }
        }else{
            for (int i = 0; i<buttons.length; i++){
                buttons[i].setBackgroundResource(android.R.drawable.btn_default);
                buttons[i].setText(getResources().getIdentifier(q.getAns()[i],"string", getPackageName()));
            }

        }
    }


    private void checkQuestion(int aux){
        if (aux == cA) {
            //Toast.makeText(QuizActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
            score += 3;
            currentQuestion++;
            initQuestion(questionList.get(currentQuestion));

        } else {//Incorrecta
            //ImageView questionImg = (ImageView) findViewById(R.id.question_image);
            DialogFragment fragmentDialog = new IncorrectAnswerDialog();
            fragmentDialog.show(getSupportFragmentManager(), "incorrentanswerdialog");
        }
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        if (currentQuestion < nQuestions - 1)
            currentQuestion++;

        score -= 2;
        initQuestion(questionList.get(currentQuestion));
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        currentQuestion = 0;
        score = 0;
        initQuestion(questionList.get(currentQuestion));
    }
}
