package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa;

import lombok.*;

@Data
@Builder @AllArgsConstructor @NoArgsConstructor
@Getter @Setter
public class CreateInteresadoInteresaDTO {

    private Long interesado_id;
    private Long vivienda_id;
    private String mensaje;
}
