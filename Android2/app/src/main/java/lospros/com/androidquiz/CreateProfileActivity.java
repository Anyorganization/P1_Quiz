package lospros.com.androidquiz;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import lospros.com.androidquiz.utilidades.Utilidades;

public class CreateProfileActivity extends AppCompatActivity {

    EditText campoNombre;
    Button btn_submit;
    Button btn_camera;
    ImageView img_profile;

    String fotoPath= "";
    Boolean hasCam;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);


        campoNombre = (EditText) findViewById(R.id.editText_name);
        btn_submit = (Button) findViewById(R.id.btn_submit);
        btn_camera = (Button) findViewById(R.id.btn_camera);
        img_profile = (ImageView) findViewById(R.id.img_profile);

        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PackageManager pm = getApplicationContext().getPackageManager();

                hasCam = pm.hasSystemFeature(PackageManager.FEATURE_CAMERA);
                if (hasCam) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent,0);
                }else{
                    //TODO hacer la actividad para seleccionar imagen default.
                }

            }
        });

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //createProfile();
            createProfileSQL();
            }
        });

    }

    private void createProfileSQL() {
        SQLiteManager conn = new SQLiteManager(this,"bd_perfiles",null,1);

        SQLiteDatabase db = conn.getWritableDatabase();



        ///////
        Cursor cursor = db.rawQuery("select DISTINCT " + Utilidades.CAMPO_NOMBRE + " from " + Utilidades.TABLA_PERFIL + " where "
                + Utilidades.CAMPO_NOMBRE + " = '" + campoNombre.getText().toString() + "'", null);

        //cursor = your-query-here

        if(cursor.getCount() < 1){
            //INSERT INTO perfiles (nombre, fotopath, maxpunt, npartidas, dirimage) VALUES ('Pepe', 'IMG_0000.jpg', 25, 4, 0)
            String insert="INSERT INTO "+Utilidades.TABLA_PERFIL
                    +" ("
                    +Utilidades.CAMPO_NOMBRE+ ","
                    +Utilidades.CAMPO_FOTOPATH+ ","
                    +Utilidades.CAMPO_FECHA+ ","
                    +Utilidades.CAMPO_MAXPUNT+ ","
                    +Utilidades.CAMPO_NPARTIDAS + ","
                    +Utilidades.CAMPO_DIRIMAGE
                    +")"
                    +" VALUES ("
                    +"'"+campoNombre.getText().toString()+"',"
                    +"fotoPath,"
                    + System.currentTimeMillis() +","
                    +0+","
                    +0+","
                    +hasCam
                    //La puntuación máxima y número de partidas jugadas se ponen a 0 al principio




                    +")";

            Log.i("sentencia SQL", insert);

            db.execSQL(insert);
            db.close();
        }else{
            Toast.makeText(CreateProfileActivity.this,"Ya existe un perfil con ese nombre", Toast.LENGTH_SHORT).show(); //TODO traducir string
        }
        //////




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        img_profile.setImageBitmap(bitmap);
        try {
            FileOutputStream out = openFileOutput("IMG_" + System.currentTimeMillis()+".jpg", Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 75, out);
            out.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }



    //TODO Esta función es antigua
    /*private void createProfile() {
        SQLiteManager conn = new SQLiteManager(this,"bd_perfiles",null,1);

        SQLiteDatabase db = conn.getWritableDatabase();

        ContentValues  values = new ContentValues();
        values.put(Utilidades.CAMPO_ID, campoId.toString());
        values.put(Utilidades.CAMPO_NOMBRE, campoNombre.toString());
        values.put(Utilidades.CAMPO_FOTOPATH, campoFotoPath.toString());

        Long idResultante = db.insert(Utilidades.TABLA_PERFIL, Utilidades.CAMPO_ID,values);
        Log.i("PerfilCreado","id Registro:"+ idResultante );
        Toast.makeText(getApplicationContext(),"id Registro:"+ idResultante, Toast.LENGTH_LONG).show();

        db.close();
    }*/

}