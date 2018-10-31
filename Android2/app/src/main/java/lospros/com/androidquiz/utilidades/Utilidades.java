package lospros.com.androidquiz.utilidades;

public class Utilidades {

    public static final String TABLA_PERFIL = "perfiles";
    public static final String CAMPO_ID = "id";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_FOTOPATH = "fotoPath";
    public static final String CAMPO_MAXPUNT = "maxPunt";
    public static final String CAMPO_NPARTIDAS = "nPartidas";


    public final static String CREAR_TABLA_PERFIL ="CREATE TABLE "+TABLA_PERFIL+" ("+CAMPO_ID+" INTEGER, "+CAMPO_NOMBRE+" TEXT," +
            ""+CAMPO_FOTOPATH+" TEXT, "+CAMPO_MAXPUNT+" INTEGER, "+ CAMPO_NPARTIDAS +" INTEGER)";

}
