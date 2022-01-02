package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Vivienda implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo,descripcion,avatar,latlng;
    private double precio,mCuadrados;

    @Column(name = "codigoPostal")
    private String codigoPostal;

    @Column(name = "nHabitaciones")
    private int nHabitaciones;

    @Column(name = "nBanios")
    private int nBanios;

    @Column(name = "tienePiscina")
    private boolean tienePiscina;

    @Column(name = "tieneGaraje")
    private boolean tieneGaraje;

    @Column(name = "tieneAscensor")
    private boolean tieneAscensor;

    @ManyToOne
    @JoinColumn(name = "inmobiliaria_id")
    private Inmobiliaria inmobiliaria;

    @Column(name = "TipoVivienda")
    @Enumerated(EnumType.STRING)
    private TipoVivienda tipoVivienda;

    @ManyToOne
    @JoinColumn(name = "propietario_id")
    private Propietario propietario;

    @Builder.Default
    @OneToMany(mappedBy = "vivienda")
    private List<Interesa> interesaList= new ArrayList<>();

}
