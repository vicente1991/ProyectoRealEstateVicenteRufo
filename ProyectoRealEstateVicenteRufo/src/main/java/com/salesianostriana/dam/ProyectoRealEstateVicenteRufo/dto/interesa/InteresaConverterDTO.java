package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesa;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class InteresaConverterDTO {

    public Interesa createInteresaDTOinInteresa(GetInteresaDTO g){
        return Interesa.builder()
                .creDateTime(LocalDateTime.now())
                .mensaje(g.getMensaje())
                .build();

    }
}
