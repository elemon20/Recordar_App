package com.example.recordarapp;

import java.io.Serializable;

public class Usuario implements Serializable {
    private String rut;
    private String nombre;
    private String clave;
    private String evento;


    //region Constructores
    public Usuario(String rut, String nombre, String clave, String evento) {
        this.rut = rut;
        this.nombre = nombre;
        this.clave = clave;
        this.evento = evento;
    }

    public Usuario() {
    }

    //end region

    //region Get y Set


    public String getId() {
        return rut;
    }

    public void setId(String rut) {
        this.rut = rut;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getEvento() {
        return evento;
    }

    public void setEvento(String evento) {
        this.evento = evento;
    }

    //End region
}
