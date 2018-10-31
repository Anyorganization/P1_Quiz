package lospros.com.androidquiz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

import lospros.com.androidquiz.utilidades.Utilidades;

public class SQLiteManager extends SQLiteOpenHelper {


    public SQLiteManager(Context context,String name,SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);

        //TODO

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(Utilidades.CREAR_TABLA_PERFIL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Utilidades.TABLA_PERFIL);
        onCreate(db);
    }
}
