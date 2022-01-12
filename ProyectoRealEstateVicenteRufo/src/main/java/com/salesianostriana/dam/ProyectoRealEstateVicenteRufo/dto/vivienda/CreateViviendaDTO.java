package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.GetInmobiliariaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.GetPropietarioDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.TipoVivienda;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
public class CreateViviendaDTO {

    private Long id;
    private String titulo,descripcion,avatar,latlng,direccion,codigoPostal,poblacion,provincia;
    private TipoVivienda tipo;
    private double precio;
    private int numHabitaciones,mCuadrados,numBanios;
    private boolean tienePiscina,tieneAscensor,tieneGaraje;
    private GetPropietarioDTO propietario;
    private GetInmobiliariaDTO inmobiliaria;
}
