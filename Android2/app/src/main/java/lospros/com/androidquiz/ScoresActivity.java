package lospros.com.androidquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class ScoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        //Select Theme
        Boolean darkTheme = sharedPreferences.getBoolean("DARK_THEME", false);
        if(darkTheme){
            super.setTheme(R.style.DarkTheme);
        }else{
            super.setTheme(R.style.LightTheme);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        String [] [] r =  new RecordsManager().getScores(getApplicationContext());

        for(int i =0; i<r.length; i++){
            TextView tv = findViewById(getResources().getIdentifier("name_"+i, "id", getPackageName()));
            tv.setText(r[i][0]);
            tv = findViewById(getResources().getIdentifier("score_"+i, "id", getPackageName()));
            tv.setText(r[i][1]);

            tv = findViewById(getResources().getIdentifier("time_"+i, "id", getPackageName()));
            tv.setText(r[i][2]+"s");
        }

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), StartMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
