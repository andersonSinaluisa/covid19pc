package com.today.covid_19puntoscriticos.Model;

public class HistorialPosition {
    private String id ;
    private double latitud;
    private double longitud;
    private String id_usuario;


    private HistorialPosition(){

    }

    public HistorialPosition(String id, double latitud, double longitud, String id_usuario) {
        this.id = id;
        this.latitud = latitud;
        this.longitud = longitud;
        this.id_usuario = id_usuario;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getLatitud() {
        return latitud;
    }

    public void setLatitud(double latitud) {
        this.latitud = latitud;
    }

    public double getLongitud() {
        return longitud;
    }

    public void setLongitud(double longitud) {
        this.longitud = longitud;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }
}
