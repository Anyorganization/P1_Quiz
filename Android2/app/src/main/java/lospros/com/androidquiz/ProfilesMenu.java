package lospros.com.androidquiz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ProfilesMenu extends AppCompatActivity {

    Button create_profile;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles_menu);

        create_profile = (Button) findViewById(R.id.btn_createprofile);

        create_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openProfileCreator();

            }
        });



    }

    private void openProfileCreator(){startActivity(new Intent(this,CreateProfileActivity.class));}
}
