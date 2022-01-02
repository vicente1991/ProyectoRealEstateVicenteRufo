package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import lombok.*;

import java.util.List;

@Data
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetPropietarioDTO {

    private Long id;
    private String nombre,apellidos,email,telefono,avatar;
    private List<String> viviendaList;
}
