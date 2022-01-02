package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class InteresaPK implements Serializable {

    private Long vivienda_id;

    private Long interesado_id;
}
