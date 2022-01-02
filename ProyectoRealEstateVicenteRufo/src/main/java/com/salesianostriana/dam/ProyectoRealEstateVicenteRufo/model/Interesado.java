package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Interesado extends Persona{

    @Builder.Default
    @OneToMany(mappedBy = "interesado")
    private List<Interesa> interesaList = new ArrayList<>();


}
