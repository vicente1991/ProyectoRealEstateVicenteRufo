package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesado;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InteresadoConverterDTO {

    public Interesado createInteresadoDTOinInteresado(GetInteresadoDTO g){
        return Interesado.builder()
                .id(g.getId())
                .nombre(g.getNombre())
                .apellidos(g.getApellidos())
                .email(g.getEmail())
                .telefono(g.getTelefono())
                .avatar(g.getAvatar())
                .direccion(g.getDireccion())
                .build();
    }

    public GetInteresadoDTO createInteresadoDTOtoInteresado(Interesado in){
        return GetInteresadoDTO.builder()
                .id(in.getId())
                .creaDate(LocalDateTime.now())
                .nombre(in.getNombre())
                .apellidos(in.getApellidos())
                .email(in.getEmail())
                .telefono(in.getTelefono())
                .avatar(in.getAvatar())
                .direccion(in.getDireccion())
                .build();
    }

}
