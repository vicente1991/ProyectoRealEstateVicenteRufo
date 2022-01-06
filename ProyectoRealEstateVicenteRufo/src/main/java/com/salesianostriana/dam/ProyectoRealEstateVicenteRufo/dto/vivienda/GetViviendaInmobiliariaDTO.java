package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.GetInmobiliariaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.GetInteresaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.GetPropietarioDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
public class GetViviendaInmobiliariaDTO extends GetViviendaDTO{

    private String nombreInmo;

    private Long idInmobiliaria;

    public GetViviendaInmobiliariaDTO(Long id, String titulo, String descripcion, String avatar, String latlng, String direccion, String codigoPostal, String poblacion, String provincia, String tipo, double precio, int numHabitaciones, double metrosCuadrados, int numBanos, boolean tienePiscina, boolean tieneAscensor, boolean tieneGaraje, GetPropietarioDTO propietario, GetInmobiliariaDTO inmobiliaria, int meInteresas, List<GetInteresaDTO> interesas, String nombreInmo, Long idInmobiliaria) {
        super(id, titulo, descripcion, avatar, latlng, direccion, codigoPostal, poblacion, provincia, tipo, precio, numHabitaciones, metrosCuadrados, numBanos, tienePiscina, tieneAscensor, tieneGaraje, propietario, inmobiliaria, meInteresas, interesas);
        this.nombreInmo = nombreInmo;
        this.idInmobiliaria = idInmobiliaria;
    }
}
