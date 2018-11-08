package lospros.com.androidquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;

import lospros.com.androidquiz.utilidades.sharedUtilities;


// Change layout: setContentView(R.layout.video_layout);

public class QuizActivity extends AppCompatActivity implements PlayingAsAnonDialog.NoticeDialogListener{


    //Variables:
    Button[] buttons;
    TextView text_question;
    TextView label_question;
    TextView label_hits;
    TextView label_time;
    ImageView questionImg;
    ArrayList<Question> questionList;
    int currentQuestion =0;
    int cA;
    private int ids_answers[] = {
            R.id.answer1, R.id.answer2, R.id.answer3, R.id.answer4
    };
    int score = 0;
    int hits = 0;
    int fails = 0;
    int nQuestions;
    MediaPlayer mediaPlayer;

    long startTime = 0L, timeInMil = 0L;
    Handler customHandler = new Handler();







    Runnable updateStopwatch = new Runnable() {
        @Override
        public void run() {
            timeInMil = SystemClock.uptimeMillis()-startTime;
            int secs = (int) (timeInMil/1000);
            int min = secs/60;
            secs%=60;
            if(secs<10){
                label_time.setText(min+":0"+secs);
            }else{

                label_time.setText(min+":"+secs);
            }
            Log.i("cronómetro: ", Integer.toString(secs));

            customHandler.postDelayed(this,1000);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //// Settings:

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String namePlayer = sharedPreferences.getString(sharedUtilities.NAME_PLAYER,sharedUtilities.PREF_ANON);

        //Select Theme
        Boolean darkTheme = sharedPreferences.getBoolean("DARK_THEME", false);
        if(darkTheme){
            super.setTheme(R.style.DarkTheme);
        }else{
            super.setTheme(R.style.LightTheme);
        }


        //AUDIO TEST



        int nQ = Integer.parseInt(sharedPreferences.getString("N_QUESTIONS", "5"));
        nQuestions = nQ;
        Log.i("darkTheme", darkTheme.toString());
        Log.i("nQuestions", Integer.toString(nQ));


        ////

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        if(namePlayer.equals(sharedUtilities.PREF_ANON)){
            DialogFragment fragmentDialog = new PlayingAsAnonDialog();
            fragmentDialog.show(getSupportFragmentManager(),"playingasanondialog");

        }

        questionList = new Gson().fromJson(questionsFile(sharedPreferences), new TypeToken<ArrayList<Question>>(){}.getType());
        Collections.shuffle(questionList);

        initQuestion(questionList.get(currentQuestion));


        //Se inicia el cronómetro:
        startTime = SystemClock.uptimeMillis();

    }

    private String questionsFile(SharedPreferences sharedPreferences ){
        String topoc = sharedPreferences.getString("TOPIC","topic0");
        InputStream is;
        ///Reading JSON.
        int id =getResources().getIdentifier(topoc+"_questions", "raw", getPackageName());

        if(id!=0){
            is = getResources().openRawResource(id);
        }else{
            Log.i("Error reading "+topoc+"_questions.json: ","Trying to read "+topoc);
            is = getResources().openRawResource(R.raw.topic0_questions);
        }

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


        return dataFile;

    }
    private void loadCommonStuff(Question q){


        label_time = findViewById(R.id.label_time);

        customHandler.removeCallbacks(updateStopwatch);
        customHandler.postDelayed(updateStopwatch, 0);
        //BUTTONS:
        buttons = new Button[]{(Button) findViewById(R.id.answer1), (Button) findViewById(R.id.answer2), (Button) findViewById(R.id.answer3), (Button) findViewById(R.id.answer4)};
        text_question = (TextView) findViewById(R.id.text_question);
        text_question.setText(getResources().getIdentifier(q.getQuestion(), "string", getPackageName()));

        label_question = (TextView) findViewById(R.id.label_question);
        label_question.setText(getString(R.string.question) + " " + (currentQuestion+1) + "/" + nQuestions);

        label_hits = (TextView) findViewById(R.id.label_hits);
        label_hits.setText(hits + "/" + fails);





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

        //Correct Answer id
        cA = q.getcA();

        //image or text topic0_questions.
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





    private void initQuestion(Question q){
        String type = q.getType();

        switch (type){
            case "text":
                setContentView(R.layout.activity_quiz);
                questionImg = (ImageView) findViewById(R.id.question_image);
                questionImg.setVisibility(View.GONE);
                break;
            case "image":
                setContentView(R.layout.activity_quiz);

                questionImg = (ImageView) findViewById(R.id.question_image);
                questionImg.setVisibility(View.VISIBLE);
                questionImg.setImageResource(getResources().getIdentifier(q.getPath(), "drawable", getPackageName()));
                break;
            case "video":
                loadVideo(q.getPath());
                break;

            case "audio":

                Log.i("switch initQuestion: ","Pregunta de Audio");
                loadAudio(q.getPath());
                break;


            default: Log.i("switch initQuestion: ","Esto no debería pasar.");
                currentQuestion++;
                initQuestion(questionList.get(currentQuestion));
                break;


        }


        loadCommonStuff(q);




    }



    private void loadAudio(String name){
        setContentView(R.layout.audio_layout);
        mediaPlayer = MediaPlayer.create(this,getResources().getIdentifier(name, "raw", getPackageName()));
        MediaController mc = new MediaController(this);
        //mediaPlayer.start();

        Button playPause = findViewById(R.id.buttonPlayPause);
        playPause.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(mediaPlayer.isPlaying()){
                    Log.i("audio","Pause");
                    mediaPlayer.pause();

                }else{
                    mediaPlayer.start();
                    Log.i("audio","Play");
                }
            }
        }));

        Button restart = findViewById(R.id.buttonRestart);
        restart.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.seekTo(0);
                mediaPlayer.start();
            }
        }));


        /*mc.setAnchorView(audioView);
        mc.setEnabled(true);
        mc.show(10);
        mediaPlayer.start();*/


    }
    private void loadVideo(String name ){

        setContentView(R.layout.video_layout);

        VideoView videoView = findViewById(R.id.videoView);

        String videoFile = "android.resource://"+getPackageName()+"/"+ getResources().getIdentifier(name,"raw",getPackageName());//R.raw.calico; //


        videoView.setVideoURI(Uri.parse(videoFile));


        MediaController mc = new MediaController(this);
        mc.setAnchorView(videoView);
        videoView.setMediaController(mc);

        //videoView.requestFocus();

        videoView.setZOrderOnTop(true);

        videoView.start();
    }

    private void checkQuestion(int aux){




        if(mediaPlayer != null && mediaPlayer.isPlaying()){
            mediaPlayer.pause();
        }

        if (aux == cA) {//Correct Answer!
            Toast.makeText(QuizActivity.this, R.string.correct, Toast.LENGTH_SHORT).show();
            score += 3;
            hits++;


            if (currentQuestion < (nQuestions - 1)) {//Si no es la última
                currentQuestion++;
                initQuestion(questionList.get(currentQuestion));
            }else{//Si es la última:
               finishQuiz();
            }

        } else {//Incorrecta
            Toast.makeText(QuizActivity.this, R.string.incorrect, Toast.LENGTH_SHORT).show();
            score -= 2;
            fails++;

            if (currentQuestion < (nQuestions - 1)){
                currentQuestion++;
                initQuestion(questionList.get(currentQuestion));
            }else{
                finishQuiz();
            }
        }
    }

    private void finishQuiz(){

        int secs = (int) (timeInMil/1000);

        int finalScore = 0;
        if(score>0){
            finalScore = (int) (score*300000/timeInMil);
        }else{

            finalScore = (int) (score*timeInMil/300000);
        }



        Intent intent = new Intent(this, EndOfQuiz.class); //Mostrar pantalla de final.
        intent.putExtra("score", finalScore);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();


    }

    @Override
    protected void onDestroy () {
        super.onDestroy();

        customHandler.removeCallbacks(updateStopwatch);


    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {

    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), StartMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}