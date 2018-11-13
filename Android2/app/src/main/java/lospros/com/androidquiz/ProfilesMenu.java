package lospros.com.androidquiz;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;

import lospros.com.androidquiz.entidades.Perfil;
import lospros.com.androidquiz.utilidades.Utilidades;

public class ProfilesMenu extends AppCompatActivity {

    Button create_profile;
    ListView listView;
    ArrayList<Perfil> profileList;

    SQLiteManager conn;
    SQLiteDatabase db;

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

        ArrayAdapter adapter = new ItemViewAdapater(getApplicationContext(),profileList);
        listView.setAdapter(adapter);

    }

    private void openProfileCreator(){
        startActivity(new Intent(this,CreateProfileActivity.class));
    }

    private void getProfileList(){
        Perfil p = null;
        profileList = new ArrayList<Perfil>();

        //SELECT * FROM perfiles
        try{
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFIL, null);

            while (cursor.moveToNext()) {
                p = new Perfil();
                p.setNombre(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_NOMBRE)));
                p.setFotoPath(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_FOTOPATH)));
                p.setFecha(new Date(cursor.getLong(cursor.getColumnIndex(Utilidades.CAMPO_FECHA)))); //DateFormat.getDateInstance().format(System.currentTimeMillis());
                p.setMaxPunt(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_MAXPUNT)));
                p.setnPartidas(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_NPARTIDAS)));
                p.setDirImage(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_DIRIMAGE)));
                profileList.add(p);
            }
        }catch (Exception e){
            db.execSQL(Utilidades.CREAR_TABLA_PERFIL);
            Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFIL, null);

            while (cursor.moveToNext()) {
                p = new Perfil();
                p.setNombre(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_NOMBRE)));
                p.setFotoPath(cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_FOTOPATH)));
                p.setFecha(new Date(cursor.getLong(cursor.getColumnIndex(Utilidades.CAMPO_FECHA)))); //DateFormat.getDateInstance().format(System.currentTimeMillis());
                p.setMaxPunt(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_MAXPUNT)));
                p.setnPartidas(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_NPARTIDAS)));
                p.setDirImage(cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_DIRIMAGE)));
                profileList.add(p);


            }

        }


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ProfileActivity.class); //Mostrar pantalla de final.
                intent.putExtra("nameProfile", profileList.get(position).getNombre());
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), StartMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    public class ItemViewAdapater extends ArrayAdapter<Perfil> {

        ArrayList<Perfil>  perfiles;


        public ItemViewAdapater(Context ctx, ArrayList<Perfil> perfiles) {
            super(ctx, R.layout.row_layout,perfiles);
            this.perfiles=perfiles;
            /*private view holder class*/
        }

        public View getView(int position, View convertView, ViewGroup parent){
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row_layout,parent, false);

            ImageView imagen_icon = (ImageView) row.findViewById(R.id.icono_imagen);
            TextView name_plist = (TextView) row.findViewById(R.id.name_profile_list);
            TextView score_plist = (TextView) row.findViewById(R.id.record_list);
            Perfil cp = perfiles.get(position);
            if(perfiles.get(position).getDirImage()==0){
                imagen_icon.setImageResource(getResources().getIdentifier(cp.getFotoPath(), "drawable", getPackageName()));
            }else{

                try {
                    FileInputStream fis = openFileInput(cp.getFotoPath());
                    InputStream is = new BufferedInputStream(fis);
                    Bitmap bitmap = BitmapFactory.decodeStream(is);
                    imagen_icon.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
            name_plist.setText(cp.getNombre());
            score_plist.setText(Integer.toString(cp.getMaxPunt()));

            return  row;

        }

    }

}
