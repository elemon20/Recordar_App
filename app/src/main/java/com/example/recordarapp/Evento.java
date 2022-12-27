package com.example.recordarapp;

import java.io.Serializable;
import java.util.Date;

public class Evento implements Serializable {
    private String titulo;
    private String fecha;
    private String importancia;
    private String observacion;
    private String lugar;

    //region Constructores


    public Evento() {
    }

    public Evento(String titulo, String fecha, String observacion, String importancia, String lugar) {
        this.titulo = titulo;
        this.fecha = fecha;
        this.observacion = observacion;
        this.importancia = importancia;
        this.lugar = lugar;
    }

    //end region

    //region Get y Set

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }


    //end region
}



