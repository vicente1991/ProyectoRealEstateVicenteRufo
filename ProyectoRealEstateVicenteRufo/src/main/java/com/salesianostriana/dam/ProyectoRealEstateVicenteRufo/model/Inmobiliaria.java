package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = Inmobiliaria.INMOBILIARIA_CON_VIVIENDA,
        attributeNodes = {
                @NamedAttributeNode("viviendas")
        }
)

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @SuperBuilder
public class Inmobiliaria implements Serializable {

    public static final String INMOBILIARIA_CON_VIVIENDA= "GRAFO-INMOBILIARIA-VIVIENDA";

    @Id
    @GeneratedValue
    private Long id;

    private String nombre,email, telefono;

    @Builder.Default
    @OneToMany(mappedBy = "inmobiliaria",fetch = FetchType.EAGER)
    private List<Vivienda> vivienda= new ArrayList<>();

}