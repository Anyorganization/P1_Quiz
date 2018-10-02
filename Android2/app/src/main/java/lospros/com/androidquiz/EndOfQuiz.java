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

        public class EndOfQuiz extends AppCompatActivity {

            @Override
            protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_end_of_quiz);

                Bundle bundle = getIntent().getExtras();
                int value = bundle.getInt("score");

                TextView score = (TextView) findViewById(R.id.scoreText);
                score.setText(Integer.toString(value));

        Button playAgain = (Button) findViewById(R.id.restartButton);

        playAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAgainButton();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkTheme = sharedPreferences.getBoolean("DARK_THEME", false);
        if(darkTheme){
            super.setTheme(R.style.DarkTheme);
        }else{
            super.setTheme(R.style.LightTheme);
        }

    }

    public void playAgainButton(){
        Intent intent = new Intent(this, StartMenu.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {}
}
