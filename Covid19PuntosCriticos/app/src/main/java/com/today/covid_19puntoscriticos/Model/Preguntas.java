package com.today.covid_19puntoscriticos.Model;

import java.util.ArrayList;

public class Preguntas {
    private String id;
    private String descripcion;
    private String tipo;
    private boolean estado;
    private String categoria;
    private ArrayList<Object> answer;

    private Preguntas(){

    }

    public Preguntas(String id, String descripcion, String tipo, boolean estado, String categoria, ArrayList<Object> answer) {
        this.id = id;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.estado = estado;
        this.categoria = categoria;
        this.answer = answer;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public ArrayList<Object> getAnswer() {
        return answer;
    }

    public void setAnswer(ArrayList<Object> answer) {
        this.answer = answer;
    }
}
