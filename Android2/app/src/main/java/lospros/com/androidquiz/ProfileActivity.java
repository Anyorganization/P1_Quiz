package lospros.com.androidquiz;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import lospros.com.androidquiz.utilidades.Utilidades;
import lospros.com.androidquiz.utilidades.sharedUtilities;

//TODO poner en los botones de los layouts @Strings...
public class ProfileActivity extends AppCompatActivity {
    EditText campoNombre;
    Button btn_edit, btn_select, btn_delete;
    Button btn_camera;
    ImageView img_profile;

    ImageView img_1, img_2, img_3, img_4;

    String fotoPath = "";
    Boolean hasCam;
    String nameProfile;



    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //hasCam = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA);

        loadProfileData();

        if (hasCam){
            setupCameraLayout();
            loadCommonStuff();
        } else {
            setupNonCameraLayout();
            loadCommonStuff();
        }
    }

    private void loadProfileData(){
        Bundle bundle = getIntent().getExtras();
        nameProfile = bundle.getString("nameProfile");

        SQLiteManager conn = new SQLiteManager(getApplicationContext(), "bd_perfiles", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFIL + " WHERE " + Utilidades.CAMPO_NOMBRE + " = "+ "'"+nameProfile+"'", null);
        cursor.moveToFirst();
        int dirImage = cursor.getInt(cursor.getColumnIndex(Utilidades.CAMPO_DIRIMAGE));//TODO Hacer así en todas las lecturas (RecordsManager...etc).
        fotoPath=cursor.getString(cursor.getColumnIndex(Utilidades.CAMPO_FOTOPATH));
        if(dirImage==1){
            hasCam=true;
        }else{
            hasCam=false;
        }



    }

    private void setupCameraLayout() {
        setContentView(R.layout.activity_profile);

        btn_camera = (Button) findViewById(R.id.btn_camera);
        img_profile = (ImageView) findViewById(R.id.img_profile);


        try {
            FileInputStream fis = openFileInput(fotoPath);
            InputStream is = new BufferedInputStream(fis);
            Bitmap bitmap = BitmapFactory.decodeStream(is);
            img_profile.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });


    }

    private void setupNonCameraLayout() {
        setContentView(R.layout.activity_profile_withoutcam);
        img_1 = (ImageView) findViewById(R.id.img_1);
        img_2 = (ImageView) findViewById(R.id.img_2);
        img_3 = (ImageView) findViewById(R.id.img_3);
        img_4 = (ImageView) findViewById(R.id.img_4);

        hasCam = false; //TODO quitar esta linea cuando se ponga el if sin el false forzado.


        img_1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    img_1.setColorFilter(Color.argb(70, 0, 0, 0));


                    img_2.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_3.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_4.setColorFilter(Color.argb(0, 0, 0, 0));
                    fotoPath = "default1";
                }
                return false;
            }
        });
        img_2.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    img_2.setColorFilter(Color.argb(70, 0, 0, 0));

                    img_1.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_3.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_4.setColorFilter(Color.argb(0, 0, 0, 0));
                    fotoPath = "default2";
                }
                return false;
            }
        });
        img_3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    img_3.setColorFilter(Color.argb(70, 0, 0, 0));

                    img_1.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_2.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_4.setColorFilter(Color.argb(0, 0, 0, 0));
                    fotoPath = "default3";
                }
                return false;
            }
        });
        img_4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    img_4.setColorFilter(Color.argb(70, 0, 0, 0));

                    img_1.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_2.setColorFilter(Color.argb(0, 0, 0, 0));
                    img_3.setColorFilter(Color.argb(0, 0, 0, 0));
                    fotoPath = "default4";
                }
                return false;
            }
        });


    }

    private void loadCommonStuff() {

        campoNombre = (EditText) findViewById(R.id.editText_name);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        btn_select = (Button) findViewById(R.id.btn_select);
        btn_delete = (Button) findViewById(R.id.btn_delete);


        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                editProfileSQL();
            }
        });

        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectProfile();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteProfileSQL();
            }
        });

        campoNombre.setText(nameProfile);
    }

    private void editProfileSQL() {

        SQLiteDatabase db = new SQLiteManager(this, "bd_perfiles", null, 1).getWritableDatabase();

        ContentValues newValues = new ContentValues();

        String newNameProfile = campoNombre.getText().toString();
        newValues.put(Utilidades.CAMPO_FOTOPATH,fotoPath);
        newValues.put(Utilidades.CAMPO_NOMBRE, newNameProfile);


        db.update(Utilidades.TABLA_PERFIL, newValues, Utilidades.CAMPO_NOMBRE + "=" + "'"+nameProfile+"'", null);
        Toast.makeText(getApplicationContext(), "profile updated", Toast.LENGTH_SHORT).show(); //TODO hacer String

        Intent intent = new Intent(getApplicationContext(), ProfilesMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();



    }

    private void selectProfile(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(sharedUtilities.NAME_PLAYER,nameProfile);
        editor.commit();
        Toast.makeText(getApplicationContext(),R.string.profile_selected+" "+ nameProfile, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), ProfilesMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void deleteProfileSQL(){

        SQLiteDatabase db = new SQLiteManager(this, "bd_perfiles", null, 1).getWritableDatabase();
        db.delete(Utilidades.TABLA_PERFIL,Utilidades.CAMPO_NOMBRE + "=" + "'"+nameProfile+"'",null);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String namePlayer = sharedPreferences.getString(sharedUtilities.NAME_PLAYER, sharedUtilities.PREF_ANON);
        if(namePlayer.equals(nameProfile)){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(sharedUtilities.NAME_PLAYER,sharedUtilities.PREF_ANON);
            editor.commit();
        }

        Toast.makeText(getApplicationContext(), "profile deleted", Toast.LENGTH_SHORT).show(); //TODO hacer String

        Intent intent = new Intent(getApplicationContext(), ProfilesMenu.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        img_profile.setImageBitmap(bitmap);
        try {
            fotoPath = "IMG_" + System.currentTimeMillis() + ".jpg";
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
