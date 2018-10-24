package lospros.com.androidquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartMenu extends AppCompatActivity {

    private Button startButton;
    private Button settingsButton;
    private Button scoresButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean darkTheme = sharedPreferences.getBoolean("DARK_THEME", false);
        if(darkTheme){
            getApplication().setTheme(R.style.DarkTheme);
        }else{
            getApplication().setTheme(R.style.LightTheme);
        }


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_start_menu);

        startButton = (Button) findViewById(R.id.btn_start);

        settingsButton = (Button)findViewById(R.id.btn_settings);

        scoresButton = (Button) findViewById(R.id.btn_scores);

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQuizActivity();
            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettingsActivity();
            }
        });

        scoresButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRecordsActivity();
            }
        });

    }

    private void openRecordsActivity() {
        startActivity(new Intent(this,ScoresActivity.class));
    }



    public void openQuizActivity(){
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void openSettingsActivity(){
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {}
}
