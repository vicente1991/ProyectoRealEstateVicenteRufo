package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CreatePropietarioDTO {

    private String nombre,apellidos,email,telefono,avatar;
    private List<Vivienda> viviendaList;
}
