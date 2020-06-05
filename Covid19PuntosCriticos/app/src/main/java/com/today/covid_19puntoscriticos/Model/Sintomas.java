package com.today.covid_19puntoscriticos.Model;

public class Sintomas {
    private String id;
    private int numero_sintomas;
    private String id_usuario;
    private String fecha;

    public Sintomas(){

    }

    public Sintomas(String id, int numero_sintomas, String id_usuario, String fecha) {
        this.id = id;
        this.numero_sintomas = numero_sintomas;
        this.id_usuario = id_usuario;
        this.fecha = fecha;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumero_sintomas() {
        return numero_sintomas;
    }

    public void setNumero_sintomas(int numero_sintomas) {
        this.numero_sintomas = numero_sintomas;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
