package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.TipoVivienda;
import lombok.*;

@NoArgsConstructor @AllArgsConstructor
@Data
@Getter @Setter
@Builder
public class GetViviendaPropietarioDTO {

    private Long id;
    private String titulo, descripcion, avatar;
    private String direccion, codigoPostal, provincia, poblacion, latlng;
    private double numHabitaciones, numBanios;
    private double metrosCuadrados, precio;
    private TipoVivienda tipo;

    private boolean tienePiscina, tieneAscensor, tieneGaraje;

    private String nombre;
    private String apellidos;
    private String email;
    private String telefono;
    private String avatar2;
}
