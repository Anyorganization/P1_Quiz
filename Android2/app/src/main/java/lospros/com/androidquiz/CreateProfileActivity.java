package lospros.com.androidquiz;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import lospros.com.androidquiz.utilidades.Utilidades;

public class CreateProfileActivity extends AppCompatActivity {

    EditText campoId;
    EditText campoNombre;
    EditText campoFotoPath;
    Button btn_submit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_profile);


        campoId = (EditText) findViewById(R.id.editText_id);
        campoNombre = (EditText) findViewById(R.id.editText_name);
        campoFotoPath = (EditText) findViewById(R.id.editText_fotoPath);
        btn_submit = (Button) findViewById(R.id.btn_submit);

    
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

        //INSERT INTO perfiles (id, nombre, fotopath, maxpunt, npartidas) VALUES (123, 'Pepe', 'IMG_0000.jpg', 25, 4)
        String insert="INSERT INTO "+Utilidades.TABLA_PERFIL
                +" ("
                +Utilidades.CAMPO_ID + ","
                +Utilidades.CAMPO_NOMBRE+ ","
                +Utilidades.CAMPO_FOTOPATH+ ","
                +Utilidades.CAMPO_MAXPUNT+ ","
                +Utilidades.CAMPO_NPARTIDAS
                +")"
                +" VALUES ("
                +campoId.getText().toString()+","
                +"'"+campoNombre.getText().toString()+"',"
                +"'"+campoFotoPath.getText().toString()+"',"
                +0+","
                +0
                //La puntuación máxima y número de partidas jugadas se ponen a 0 al principio




                +")";

        Log.i("sentencia SQL", insert);

        db.execSQL(insert);
        db.close();

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