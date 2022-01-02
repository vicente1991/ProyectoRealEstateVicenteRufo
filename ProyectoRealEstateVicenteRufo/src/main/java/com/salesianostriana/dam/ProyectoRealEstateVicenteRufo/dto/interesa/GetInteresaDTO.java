package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@AllArgsConstructor @NoArgsConstructor
@Builder
public class GetInteresaDTO {

   private LocalDateTime dateTime;
   private String mensaje;
}
