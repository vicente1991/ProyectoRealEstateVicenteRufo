package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Getter @Setter
@Builder
public class GetPropietarioViviendaDTO {

    private Long id;
    private String nombre,apellidos,email,direccion,telefono;

}
