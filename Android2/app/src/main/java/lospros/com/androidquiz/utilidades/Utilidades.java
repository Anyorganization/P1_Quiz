package lospros.com.androidquiz.utilidades;

public class Utilidades {

    public static final String TABLA_PERFIL = "perfiles";
    public static final String CAMPO_NOMBRE = "nombre";
    public static final String CAMPO_FOTOPATH = "fotoPath";
    public static final String CAMPO_FECHA = "fecha";
    public static final String CAMPO_MAXPUNT = "maxPunt";
    public static final String CAMPO_NPARTIDAS = "nPartidas";
    public static final String CAMPO_DIRIMAGE = "dirImage";//0 es Sin camara (drawable), 1 es con camara

    public final static String CREAR_TABLA_PERFIL ="CREATE TABLE "+TABLA_PERFIL+" ("
            +CAMPO_NOMBRE+" TEXT," //0
            +CAMPO_FOTOPATH+" TEXT," //1
            +CAMPO_FECHA+" LONG, " //2
            +CAMPO_MAXPUNT+" INTEGER, "//3
            +CAMPO_NPARTIDAS +" INTEGER, "//4
            +CAMPO_DIRIMAGE +" INTEGER)";//5

}
