package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @SuperBuilder
public class Interesado extends Persona{

    @Builder.Default
    @OneToMany(mappedBy = "interesado")
    private List<Interesa> interesaList = new ArrayList<>();


    @PreRemove
    public void preRemove(){interesaList.forEach(v -> v.setInteresado(null));}


}
