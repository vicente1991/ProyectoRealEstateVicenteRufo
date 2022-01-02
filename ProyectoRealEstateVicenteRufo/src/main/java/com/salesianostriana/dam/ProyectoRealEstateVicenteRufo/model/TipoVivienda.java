package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;

public enum TipoVivienda {

    ALQUILER("Alquiler"),VENTA("Venta"),OBRA_NUEVA("Obra_Nueva");
    private final String texto;

    TipoVivienda(String texto) {
        this.texto = texto;
    }


    public String getTipo(){ return texto;}
}
