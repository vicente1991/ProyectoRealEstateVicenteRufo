package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Propietario;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PropietarioConverterDTO {

    public Propietario createPropietarioDTOinPropietario(GetPropietarioDTO g){
        return new Propietario(
          g.getNombre(),
          g.getApellidos(),
          g.getTelefono(),
          g.getAvatar(),
          g.getEmail(),
          g.getViviendaList()
        );
    }

    public GetPropietarioDTO createPropietarioinPropietarioDTO(Propietario p){
        List<String> nombreVivienda = new ArrayList<>();
        for (int i=0; i<p.getViviendaList().size(); i++){
            nombreVivienda.add(p.getViviendaList().get(i).getTitulo());
        }

        return GetPropietarioDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .apellidos(p.getApellidos())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .avatar(p.getAvatar())
                .viviendaList(nombreVivienda)
                .build();
    }

    public GetPropietarioViviendaDTO createPropietarioinPropietarioViviendaDTO(Propietario p){

        return GetPropietarioViviendaDTO.builder()
                .id(p.getId())
                .nombre(p.getNombre())
                .apellidos(p.getApellidos())
                .email(p.getEmail())
                .telefono(p.getTelefono())
                .direccion(p.getDireccion())
                .build();
    }
}
