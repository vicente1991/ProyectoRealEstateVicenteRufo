package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria;


import lombok.*;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetInmobiliariaDTO {

    private Long id;

    private String nombre,telefono,email;
}
