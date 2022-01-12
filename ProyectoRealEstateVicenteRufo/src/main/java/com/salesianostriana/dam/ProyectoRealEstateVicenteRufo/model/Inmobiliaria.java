package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = Inmobiliaria.INMOBILIARIA_CON_VIVIENDA,
        attributeNodes = {
                @NamedAttributeNode("vivienda")
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

    @OneToMany(mappedBy = "inmobiliaria",fetch = FetchType.EAGER)
    private List<Vivienda> vivienda= new ArrayList<>();

    @OneToMany(mappedBy = "inmobiliaria",fetch = FetchType.EAGER)
    private List<UserEntity> gestores= new ArrayList<>();

    public Inmobiliaria(String nombre, String email, String telefono) {
    }


    public void setGestoresNull(UserEntity user) {
        gestores.forEach(gestor -> {
            if (gestor.getId() == user.getId()) {
                gestor.setInmobiliaria(null);
            }
        });
    }
}
