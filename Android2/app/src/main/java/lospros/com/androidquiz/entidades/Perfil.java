package lospros.com.androidquiz.entidades;

import java.sql.Date;

public class Perfil {

    private String nombre;
    private String fotoPath;
    private Integer maxPunt;
    private Integer nPartidas;

    //https://stackoverflow.com/questions/7363112/best-way-to-work-with-dates-in-android-sqlite
    private Date fecha;

    public Perfil(){

    }

    public Perfil(String nombre, String fotoPath) {
        this.nombre = nombre;
        this.fotoPath = fotoPath;
        maxPunt =0;
        nPartidas=0;

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFotoPath() {
        return fotoPath;
    }

    public void setFotoPath(String fotoPath) {
        this.fotoPath = fotoPath;
    }

    public Integer getMaxPunt() {
        return maxPunt;
    }

    public void setMaxPunt(Integer maxPunt) {
        this.maxPunt = maxPunt;
    }

    public Integer getnPartidas() {
        return nPartidas;
    }

    public void setnPartidas(Integer nPartidas) {
        this.nPartidas = nPartidas;
    }

    public Date getFecha() {
        if(fecha == null){
            return null;
        }
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
