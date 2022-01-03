package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria;


import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetInmobiliariaDTO {

    private Long id;

    private String nombre,telefono,email;
}
