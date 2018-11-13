package lospros.com.androidquiz;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileOutputStream;
import java.io.IOException;

import lospros.com.androidquiz.utilidades.Utilidades;
import lospros.com.androidquiz.utilidades.sharedUtilities;

//Esta clase se instancia al hacer click en "crear nuevo perfil".
public class CreateProfileActivity extends AppCompatActivity {

    EditText campoNombre;
    Button btn_submit;
    Button btn_camera;
    ImageView img_profile;

    ImageView img_1, img_2, img_3, img_4;

    String fotoPath = "";
    Boolean hasCam;//Guarda información para saber si el usuario tiene cámara

    @SuppressLint("ClickableViewAccessibility")
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

        hasCam = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);
        //Si tiene cámara se carga el layout preparado para sacar foto.
        if (hasCam){
            setContentView(R.layout.activity_create_profile);
            btn_camera = (Button) findViewById(R.id.btn_camera);
            img_profile = (ImageView) findViewById(R.id.img_profile);

            btn_camera.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, 0);
                }
            });

        //Si no tiene cámara:
        } else {
            setContentView(R.layout.activity_create_profile_withoutcam);
            img_1 = (ImageView) findViewById(R.id.img_1);
            img_2 = (ImageView) findViewById(R.id.img_2);
            img_3 = (ImageView) findViewById(R.id.img_3);
            img_4 = (ImageView) findViewById(R.id.img_4);

            //Una a una se cargan todas las funciones de selección de imagen, se guarda el id de la última seleccionada "defaultX".
            img_1.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        img_1.setColorFilter(Color.argb(70,0,0,0));


                        img_2.setColorFilter(Color.argb(0,0,0,0));
                        img_3.setColorFilter(Color.argb(0,0,0,0));
                        img_4.setColorFilter(Color.argb(0,0,0,0));
                        fotoPath = "default1";
                    }
                    return false;
                }
            });
            img_2.setOnTouchListener(new View.OnTouchListener() {

                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        img_2.setColorFilter(Color.argb(70,0,0,0));

                        img_1.setColorFilter(Color.argb(0,0,0,0));
                        img_3.setColorFilter(Color.argb(0,0,0,0));
                        img_4.setColorFilter(Color.argb(0,0,0,0));
                        fotoPath = "default2";
                    }
                    return false;
                }
            });
            img_3.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        img_3.setColorFilter(Color.argb(70,0,0,0));

                        img_1.setColorFilter(Color.argb(0,0,0,0));
                        img_2.setColorFilter(Color.argb(0,0,0,0));
                        img_4.setColorFilter(Color.argb(0,0,0,0));
                        fotoPath = "default3";
                    }
                    return false;
                }
            });
            img_4.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if(event.getAction() == MotionEvent.ACTION_DOWN){
                        img_4.setColorFilter(Color.argb(70,0,0,0));

                        img_1.setColorFilter(Color.argb(0,0,0,0));
                        img_2.setColorFilter(Color.argb(0,0,0,0));
                        img_3.setColorFilter(Color.argb(0,0,0,0));
                        fotoPath = "default4";
                    }
                    return false;
                }
            });
        }


        campoNombre = (EditText) findViewById(R.id.editText_name);
        btn_submit = (Button) findViewById(R.id.btn_edit);


        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //createProfile();
                createProfileSQL();
            }
        });

    }

    private void createProfileSQL() {
        //Se evita que no ponga nombre o que el nombre ya esté usado.
        if(campoNombre.getText().toString().equals("") || campoNombre.getText().toString().equals(sharedUtilities.PREF_ANON)){

            Toast.makeText(CreateProfileActivity.this, R.string.incorrect_name, Toast.LENGTH_SHORT).show();

        }else if(fotoPath.equals("")){//Se evita que no haga foto.
            Toast.makeText(CreateProfileActivity.this, R.string.no_photo, Toast.LENGTH_SHORT).show();
        }else{
            SQLiteManager conn = new SQLiteManager(this, "bd_perfiles", null, 1);

            SQLiteDatabase db = conn.getWritableDatabase();


            ///////
            Cursor cursor = db.rawQuery("select DISTINCT " + Utilidades.CAMPO_NOMBRE + " from " + Utilidades.TABLA_PERFIL + " where "
                    + Utilidades.CAMPO_NOMBRE + " = '" + campoNombre.getText().toString() + "'", null);

            //cursor = your-query-here


            if (cursor.getCount() < 1) {
                int myInt = hasCam ? 1 : 0;
                //INSERT INTO perfiles (nombre, fotopath, maxpunt, npartidas, dirimage) VALUES ('Pepe', 'IMG_0000.jpg', 25, 4, 0)
                String insert = "INSERT INTO " + Utilidades.TABLA_PERFIL
                        + " ("
                        + Utilidades.CAMPO_NOMBRE + ","
                        + Utilidades.CAMPO_FOTOPATH + ","
                        + Utilidades.CAMPO_FECHA + ","
                        + Utilidades.CAMPO_MAXPUNT + ","
                        + Utilidades.CAMPO_NPARTIDAS + ","
                        + Utilidades.CAMPO_DIRIMAGE
                        + ")"
                        + " VALUES ("
                        + "'" + campoNombre.getText().toString() + "',"
                        + "'" + fotoPath + "',"
                        + 0 + ","
                        + 0 + ","
                        + 0 + ","
                        + myInt
                        //La puntuación máxima y número de partidas jugadas se ponen a 0 al principio


                        + ")";

                Log.i("sentencia SQL", insert);

                db.execSQL(insert);
                db.close();

                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("NAME_PLAYER",campoNombre.getText().toString());
                editor.commit();

                Toast.makeText(CreateProfileActivity.this,getString(R.string.new_profile_selected) + " "+ campoNombre.getText().toString(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getApplicationContext(), ProfilesMenu.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(CreateProfileActivity.this, R.string.name_already_exists, Toast.LENGTH_LONG).show();
            }
        }



        //////


    }
    //La cámara llama a esta función tras aceptar.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        img_profile.setImageBitmap(bitmap);
        try {
            fotoPath = "IMG_" + System.currentTimeMillis() + ".jpg";//Se asegura tener un nombre distinto para cada perfil.
            FileOutputStream out = openFileOutput(fotoPath, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, out);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), ProfilesMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}