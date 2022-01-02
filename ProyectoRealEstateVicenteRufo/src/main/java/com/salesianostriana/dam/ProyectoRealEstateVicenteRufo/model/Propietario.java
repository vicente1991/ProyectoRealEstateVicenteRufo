package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @SuperBuilder
@NoArgsConstructor @AllArgsConstructor
public class Propietario extends Persona {

    @Builder.Default
    @OneToMany(mappedBy = "propietario",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Vivienda> viviendaList= new ArrayList<>();


    public Propietario(String nombre, String apellidos, String telefono, String avatar, String email, List<String> viviendaList) {
    }
}
