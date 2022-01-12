package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class LoginDto {

    private String email;
    private String password;
}
