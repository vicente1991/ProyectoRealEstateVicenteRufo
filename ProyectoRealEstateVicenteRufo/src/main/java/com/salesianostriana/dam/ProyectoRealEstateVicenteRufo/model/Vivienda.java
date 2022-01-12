package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import lombok.*;


import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@NamedEntityGraph(
        name = "grafo-vivienda-inmobiliaria-propietario",
        attributeNodes = {
                @NamedAttributeNode("inmobiliaria"),
                @NamedAttributeNode("propietario")
        },
        subgraphs = {
                @NamedSubgraph(
                        name = "grafo-vivienda-inmobiliaria",
                        attributeNodes = {@NamedAttributeNode("inmobiliaria")}
                ),
                @NamedSubgraph(
                        name = "grafo-vivienda-propietario",
                        attributeNodes = {@NamedAttributeNode("propietario")}
                )
        }
)


@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @Builder
public class Vivienda implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    private String titulo,descripcion,direccion,avatar,latlng,poblacion,provincia;
    private double precio,mCuadrados;

    @Column(name = "codigoPostal")
    private String codigoPostal;

    @Column(name = "nHabitaciones")
    private double numHabitaciones;

    @Column(name = "nBanios")
    private double numBanios;

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
    @JsonIgnore
    private UserEntity propietario;

    @Builder.Default
    @OneToMany(mappedBy = "vivienda")
    private List<Interesa> interesaList= new ArrayList<>();



    ///HELPERS///

    public void addToInmobiliaria(Inmobiliaria i){
        inmobiliaria = i;
        if (i.getVivienda() == null){
            i.setVivienda(new ArrayList<>());
            i.getVivienda().add(this);
        }
    }

    public void removeFromInmobiliaria(Inmobiliaria i) {
        i.getVivienda().remove(this);
        inmobiliaria = null;
    }


    public void removePropietario(Propietario p) {
        p.getViviendaList().remove(this);
        this.propietario = null;
    }
    public void addInteresa(Interesa i){
        this.interesaList.add(i);
    }

    @PreRemove
    public void removeViviendasToIntereses(){
        interesaList.forEach(interesa -> interesa.setVivienda(null));
    }

}
