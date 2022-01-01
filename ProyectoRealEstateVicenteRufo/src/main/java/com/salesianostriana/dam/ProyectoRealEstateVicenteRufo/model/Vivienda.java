package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Vivienda implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo,descripcion,avatar,latlng;
}
