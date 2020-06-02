package com.today.covid_19puntoscriticos.Model;

public class Dispositivo {
    private String id;
    private int estado;
    private String id_usuario;
    private String token;


    public Dispositivo(){

    }


    public Dispositivo(String id, int estado, String id_usuario, String token) {
        this.id = id;
        this.estado = estado;
        this.id_usuario = id_usuario;
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public String getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(String id_usuario) {
        this.id_usuario = id_usuario;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
