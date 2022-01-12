package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.security.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class JwtUsuarioResponse {

    private String email;
    private String nombre;
    private String avatar;
    private String role;
    private String token;
}
