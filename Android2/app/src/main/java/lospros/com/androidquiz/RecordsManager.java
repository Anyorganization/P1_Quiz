package lospros.com.androidquiz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import lospros.com.androidquiz.utilidades.Utilidades;

public class RecordsManager {
    private ArrayList<Integer> records;
    private ArrayList<String> times; //Tiempos en String porque van en formato mm:ss
    private ArrayList<String> names;

    /*public void createRecords(){
        for(int i =0; i<5;i++) {

            names.add("A"+i);
            records.add((int) Math.round(Math.random()*10));

        }
    }


*/
    public RecordsManager() {
        times = new ArrayList<>();
        records = new ArrayList<>();
        names = new ArrayList<>();
    }

    public void updateRecords(String name, int score, String time, Context ctx){
        readFile(ctx);
        records.add(score);
        names.add(name);
        times.add(time);

        ordenar();

        if(records.size()>5){
            records.remove(records.size()-1);
            names.remove(names.size()-1);
            records.remove((records.size()-1));
        }

        try {
            FileOutputStream fos =  ctx.openFileOutput("records.json",Context.MODE_PRIVATE);
            Gson gson = new Gson();
            String prueba = gson.toJson(this);
            Log.i("Nuevos Records: " ,prueba);
            fos.write(prueba.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
        }



        ///
        SQLiteManager conn = new SQLiteManager(ctx, "bd_perfiles", null, 1);
        SQLiteDatabase db = conn.getWritableDatabase();


        Cursor cursor = db.rawQuery("SELECT * FROM " + Utilidades.TABLA_PERFIL + " WHERE " + Utilidades.CAMPO_NOMBRE + " = "+ "'"+name+"'", null);
        cursor.moveToFirst();
        int oldScore = cursor.getInt(3);
        int nPartidas = cursor.getInt(4);
        ContentValues newValues = new ContentValues();
        newValues.put(Utilidades.CAMPO_FECHA, System.currentTimeMillis());
        newValues.put(Utilidades.CAMPO_MAXPUNT, (int) Math.max(score, oldScore));
        newValues.put(Utilidades.CAMPO_NPARTIDAS, (nPartidas + 1));
        db.update(Utilidades.TABLA_PERFIL, newValues, Utilidades.CAMPO_NOMBRE + "=" + "'"+name+"'", null);



    }

    public void ordenar(){
        for (int i = 0; i<records.size()-1;i++){
            for(int j = 0; j<records.size()-i -1 ; j++){
                if(records.get(j)<records.get(j+1)){

                    int temp = records.get(j+1);
                    records.set(j+1,records.get(j));
                    records.set(j,temp);

                    String aux = names.get(j+1);
                    names.set(j+1,names.get(j));
                    names.set(j, aux);

                    String temp2 = times.get(j+1);
                    times.set(j+1,times.get(j));
                    times.set(j,temp2);

                }
            }

        }
    }

    public String[][] getScores(Context ctx){//Devuelve pares nombre-puntuación
        readFile(ctx);
        String p [][] = new String[records.size()] [3];
        for(int i =0; i<records.size();i++){

            p[i][0] = names.get(i);
            p[i][1] = Integer.toString(records.get(i));
            p[i][2] = times.get(i);
        }
        return p;
    }

    private void readFile(Context ctx){
        Gson gson = new Gson();
        FileInputStream fin = null;
        try {
            fin = ctx.openFileInput("records.json");
            BufferedReader d = new BufferedReader(new InputStreamReader(fin));
            String data = d.readLine();
            RecordsManager RM = gson.fromJson(data,RecordsManager.class);

            this.records= new ArrayList<>(RM.getRecords());
            this.names = new ArrayList<>(RM.getNames());
            this.times = new ArrayList<>(RM.getTimes());



            Log.i("Records Leidos: ", data);

        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }


    public void setRecords(ArrayList<Integer> records) {
        this.records = records;
    }
    public void setNames(ArrayList<String> names) {
        this.names = names;
    }
    public void setTimes(ArrayList<String> times) { this.times = times; }

    public ArrayList<String> getTimes() {
        return times;
    }



    public ArrayList<String> getNames() {
        return names;
    }

    public ArrayList<Integer> getRecords() {
        return records;
    }
}
