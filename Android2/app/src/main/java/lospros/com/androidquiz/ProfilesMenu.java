package lospros.com.androidquiz;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;

import lospros.com.androidquiz.entidades.Perfil;
import lospros.com.androidquiz.utilidades.Utilidades;

public class ProfilesMenu extends AppCompatActivity {

    Button create_profile;
    ListView listView;
    ArrayList<String> infoList;
    ArrayList<Perfil> profileList;

    SQLiteManager conn;

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

        listView = (ListView) findViewById(R.id.profileList);

        conn = new SQLiteManager(getApplicationContext(), "bd_perfiles", null, 1);

        getProfileList();

        ArrayAdapter adapter = new ArrayAdapter(this, R.layout.activity_profiles_menu, infoList);
        listView.setAdapter(adapter);

    }

    private void openProfileCreator(){
        startActivity(new Intent(this,CreateProfileActivity.class));
    }

    private void getProfileList(){
        SQLiteDatabase db = conn.getReadableDatabase();
        Perfil p = null;
        profileList = new ArrayList<Perfil>();

        //SELECT * FROM perfiles
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFIL, null);

        while (cursor.moveToNext()) {
            p = new Perfil();
            p.setId(cursor.getInt(0));
            p.setNombre(cursor.getString(1));
            p.setFotoPath(cursor.getString(2));
            p.setFecha(Date.valueOf(String.valueOf(new Date(Calendar.getInstance().getTime().getTime()))));
            p.setMaxPunt(cursor.getInt(3));
            p.setnPartidas(cursor.getInt(4));
        }
        writeInfoList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(),"Ey", Toast.LENGTH_SHORT);
            }
        });
    }

    private void writeInfoList() {
        infoList = new ArrayList<String>();

        for(int i = 0; i < profileList.size(); i++){
            infoList.add(profileList.get(i).getNombre() + " - " + profileList.get(i).getFotoPath());
            //TODO añadir todos los campos con formato correcto
        }
    }
}
