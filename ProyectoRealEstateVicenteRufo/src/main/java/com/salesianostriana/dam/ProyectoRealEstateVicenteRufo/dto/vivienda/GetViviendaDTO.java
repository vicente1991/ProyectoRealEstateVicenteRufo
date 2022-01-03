package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.GetInmobiliariaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.GetInteresaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.GetPropietarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class GetViviendaDTO {

    private Long id;
    private String titulo;
    private String descripcion;
    private String avatar;
    private String latlng;
    private String direccion;
    private String codigoPostal;
    private String poblacion;
    private String provincia;
    private String tipo;
    private double precio;
    private int numHabitaciones;
    private double metrosCuadrados;
    private int numBanos;
    private boolean tienePiscina;
    private boolean tieneAscensor;
    private boolean tieneGaraje;
    private GetPropietarioDTO propietario;
    private GetInmobiliariaDTO inmobiliaria;
    private int meInteresas;
    private List<GetInteresaDTO> interesas = new ArrayList<>();
}
