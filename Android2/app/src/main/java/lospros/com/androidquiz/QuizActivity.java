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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);




        ///Creating JSON.
        Gson gson = new Gson();
        ArrayList<Question> qs = new ArrayList<>();
        qs.add( new Question("Pregunta 1","pepa",0,false, new String[]{"false", "patata","potato", "oreja"}));
        qs.add( new Question("Pregunta 2","",1,false, new String[]{"false", "patata","potato", "oreja"}));
        qs.add( new Question("Pregunta 3","artefinal",1,false, new String[]{"false", "patata","potato", "oreja"}));




/*
        try {
            Log.i("msgJSON","Escribiendo...");
            gson.toJson(qs,new FileWriter(getFilesDir()+ File.separator+"enres.json"));
        } catch (IOException e) {
            Log.v("errorJSON","No se ha escrito na");
            e.printStackTrace();
        }

*/

        ///
        TextView text_question = (TextView) findViewById(R.id.text_question);
        text_question.setText(R.string.question_content);
        //final ImageView questionImg = (ImageView) findViewById(R.id.question_image);
        final ImageView questionImg = new ImageView(this);
        questionImg.setImageResource(R.drawable.pepa);

        /*ConstraintSet set = new ConstraintSet();
        set.connect(questionImg.getId(), ConstraintSet.TOP, 60);
        */
        String[] answers = getResources().getStringArray(R.array.answers);

        for (int i = 0; i < ids_answers.length; i++) {
            RadioButton rb = (RadioButton) findViewById(ids_answers[i]);
            rb.setText(answers[i]);
        }

        final RadioGroup group = (RadioGroup) findViewById(R.id.answer_group);
        final int cA = getResources().getInteger(R.integer.cA);

        Button btn_check = (Button) findViewById(R.id.btn_check);
        btn_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = group.getCheckedRadioButtonId();
                int answer = -1;
                for (int i = 0; i < ids_answers.length; i++) {
                    if (ids_answers[i] == id) {
                        answer = i;
                    }
                }
                if (answer ==  cA) {
                    Toast.makeText(QuizActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
                    //ImageView questionImg = (ImageView) findViewById(R.id.question_image);
                    questionImg.setVisibility(View.GONE);
                } else {//Incorrecta
                    Toast.makeText(QuizActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
                    //ImageView questionImg = (ImageView) findViewById(R.id.question_image);
                    questionImg.setVisibility(View.VISIBLE);
                    questionImg.setImageResource(R.drawable.artefinal);
                }
            }
        });



    }
}
