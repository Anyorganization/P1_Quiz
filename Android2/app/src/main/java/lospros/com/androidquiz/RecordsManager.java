package lospros.com.androidquiz;

import java.util.ArrayList;

public class RecordsManager {
    private ArrayList<Integer> records;
    private ArrayList<String> names;

    public void updateRecords(String name, int score){
        readFile();


    }

    public String[] getRecords(){//Devuelve pares nombre-puntuación
        getRecords();
        String p [] = {"dd","fff"};

        return p;
    }

    private void readFile(){
        //TODO leer con GSON
    }





}
