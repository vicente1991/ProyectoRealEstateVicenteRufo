package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesado;
import org.springframework.stereotype.Component;

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

}
