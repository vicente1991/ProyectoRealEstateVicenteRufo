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
                int numViviendas=i.getVivienda().size();
                GetInmobiliariaDTO inmo= new GetInmobiliariaDTO();
                inmo.setId(i.getId());
                inmo.setNombre(i.getNombre());
                inmo.setEmail(i.getEmail());
                inmo.setTelefono(i.getTelefono());
                inmo.setNumViviendas(numViviendas);
                return inmo;
    }
}
