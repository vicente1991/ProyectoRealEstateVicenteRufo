package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter @SuperBuilder
@EntityListeners(AuditingEntityListener.class)
public class Interesa {

    @Builder.Default
    @EmbeddedId
    private InteresaPK id= new InteresaPK();

    @ManyToOne
    @MapsId("vivienda_id")
    @JoinColumn(name = "vivienda_id")
    private Vivienda vivienda;

    @ManyToOne
    @MapsId("interesado_id")
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
