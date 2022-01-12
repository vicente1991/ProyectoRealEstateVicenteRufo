package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.GetUserDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@SuperBuilder
public class GetInmobiliariaDTO {

    private Long id;

    private String nombre,telefono,email;

    private int numViviendas;
    private List<GetUserDto> gestor;
}
