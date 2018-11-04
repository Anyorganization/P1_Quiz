package lospros.com.androidquiz.utilidades;

public class Utilidades {

    public static final String TABLA_PERFIL = "perfiles";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_FOTOPATH = "fotoPath";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_MAXPUNT = "maxPunt";
    public static final String CAMPO_NPARTIDAS = "nPartidas";
    public static final String CAMPO_DIRIMAGE = "dirImage";//0 es Sin camara (drawable), 1 es con camara(

    public final static String CREAR_TABLA_PERFIL ="CREATE TABLE "+TABLA_PERFIL+" ("+CAMPO_NOMBRE+" TEXT," +
            ""+CAMPO_FOTOPATH+" TEXT," + ""+CAMPO_FECHA+" LONG, "+CAMPO_MAXPUNT+" INTEGER, "+ CAMPO_NPARTIDAS +" INTEGER, "+ CAMPO_DIRIMAGE +" INTEGER)";

}
