package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;

import lombok.*;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Interesa {

    @Builder.Default
    @EmbeddedId
    private InteresaPK id= new InteresaPK();

    @ManyToOne
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;

}
