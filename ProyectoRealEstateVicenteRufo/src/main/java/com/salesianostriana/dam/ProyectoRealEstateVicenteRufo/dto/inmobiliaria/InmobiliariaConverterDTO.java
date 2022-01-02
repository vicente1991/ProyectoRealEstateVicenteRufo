package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Inmobiliaria;
import org.springframework.stereotype.Component;

@Component
public class InmobiliariaConverterDTO {

    public Inmobiliaria createInmobiliariaDTOinInmobiliaria(CreateInmobiliariaDTO c){
        return new Inmobiliaria(
          c.getNombre(),
          c.getEmail(),
          c.getTelefono()
        );
    }

    public GetInmobiliariaDTO inmobiliariaDTOToInmobiliaria(Inmobiliaria i){
        return GetInmobiliariaDTO.builder()
                .id(i.getId())
                .nombre(i.getNombre())
                .email(i.getEmail())
                .telefono(i.getTelefono())
                .build();
    }
}
