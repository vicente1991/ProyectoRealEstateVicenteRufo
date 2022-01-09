package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetInteresadoDTO {

    private Long id;
    private LocalDateTime creaDate;
    private String nombre,apellidos,direccion,email,telefono,avatar;
}
