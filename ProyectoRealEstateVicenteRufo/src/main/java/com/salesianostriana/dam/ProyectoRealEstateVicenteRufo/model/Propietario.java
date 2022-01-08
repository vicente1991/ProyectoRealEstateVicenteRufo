package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
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

    @PreRemove
    public void nullPropietarioDeViviendas(){
        viviendaList.forEach(vivienda -> vivienda.setPropietario(null));
    }
}
