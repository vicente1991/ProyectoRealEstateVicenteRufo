package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;


@NoArgsConstructor @AllArgsConstructor
@MappedSuperclass @SuperBuilder
@Getter @Setter
public abstract class Persona implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre,apellidos,direccion,email,telefono,avatar;
}
