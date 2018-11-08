package lospros.com.androidquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import lospros.com.androidquiz.utilidades.sharedUtilities;

public class EndOfQuiz extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkTheme = sharedPreferences.getBoolean("DARK_THEME", false);
        String namePlayer = sharedPreferences.getString(sharedUtilities.NAME_PLAYER, sharedUtilities.PREF_ANON);

        if(darkTheme){
            super.setTheme(R.style.DarkTheme);
        }else{
            super.setTheme(R.style.LightTheme);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_end_of_quiz);

        Bundle bundle = getIntent().getExtras();
        int value = bundle.getInt("score");

        TextView score = (TextView) findViewById(R.id.scoreText);
        score.setText(Integer.toString(value));
        if(!namePlayer.equals(sharedUtilities.PREF_ANON)){

            new RecordsManager().updateRecords(namePlayer,value,getApplicationContext());
        }

        Button playAgain = (Button) findViewById(R.id.restartButton);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgainButton();
            }
        });



    }

    public void playAgainButton(){
        Intent intent = new Intent(this, StartMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {}
}
