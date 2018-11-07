package lospros.com.androidquiz;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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
    SQLiteDatabase db;

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

        db = conn.getWritableDatabase();

        getProfileList();

        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, infoList);
        listView.setAdapter(adapter);

    }

    private void openProfileCreator(){
        startActivity(new Intent(this,CreateProfileActivity.class));
    }

    private void getProfileList(){
        Perfil p = null;
        profileList = new ArrayList<Perfil>();

        //SELECT * FROM perfiles
        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFIL, null);

        while (cursor.moveToNext()) {
            p = new Perfil();
            p.setNombre(cursor.getString(0));
            p.setFotoPath(cursor.getString(1));
            p.setFecha(new Date(cursor.getLong(2))); //DateFormat.getDateInstance().format(System.currentTimeMillis());
            p.setMaxPunt(cursor.getInt(3));
            p.setnPartidas(cursor.getInt(4));

            profileList.add(p);
        }
        writeInfoList();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("NAME_PLAYER",profileList.get(position).getNombre());
                editor.commit();
                Toast.makeText(ProfilesMenu.this,"Ey " + profileList.get(position).getNombre(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class); //Mostrar pantalla de final.
                intent.putExtra("nameProfile", profileList.get(position).getNombre());
                startActivity(intent);

            }
        });
    }

    private void writeInfoList() {
        infoList = new ArrayList<String>();

        for(int i = 0; i < profileList.size(); i++){
            infoList.add(profileList.get(i).getNombre() + " - " + profileList.get(i).getFotoPath()+ " - Fecha: "+ profileList.get(i).getFecha() +  " - nPartidas: "+ profileList.get(i).getnPartidas() + " - maxPunt: "+ profileList.get(i).getMaxPunt());
            //TODO añadir todos los campos con formato correcto
        }
    }
}
