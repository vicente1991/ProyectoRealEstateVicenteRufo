package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @SuperBuilder
public class Interesa {

    @Builder.Default
    @EmbeddedId
    private InteresaPK id= new InteresaPK();

    @ManyToOne
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;

    @ManyToOne
    @JoinColumn(name = "interesado_id")
    private Interesado interesado;

    @CreatedDate
    private LocalDateTime creDateTime= LocalDateTime.now();
    private String mensaje;

    public Interesa(LocalDateTime dateTime, String mensaje) {
    }


    public void addInteresado(Interesado i) {
        this.interesado = i;
        i.getInteresaList().add(this);
    }

    public void removeInteresado(Interesado i) {
        i.getInteresaList().remove(this);
        this.interesado = null;
    }

    public void addVivienda(Vivienda v) {
        this.vivienda = v;
        v.getInteresaList().add(this);
    }

    public void removeVivienda(Vivienda v) {
        v.getInteresaList().remove(this);
        this.vivienda = null;

    }
}
