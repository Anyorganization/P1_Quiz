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
    private Button profilesButton;

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

        profilesButton = (Button) findViewById(R.id.btn_Profiles);

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

        profilesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfilesActivity();
            }
        });


    }

    private void openRecordsActivity() {
        startActivity(new Intent(this,ScoresActivity.class));
    }
    public void openQuizActivity(){ startActivity(new Intent(this, QuizActivity.class)); }
    public void openSettingsActivity(){startActivity(new Intent(this, SettingsActivity.class)); }
    public void openProfilesActivity(){ startActivity(new Intent(this, ProfilesMenu.class));}

    @Override
    public void onBackPressed() {finish(); System.exit(0);}
}
