package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.InmobiliariaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.InteresaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.InteresadoConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.PropietarioConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import org.springframework.stereotype.Component;

@Component
public class ViviendaConverterDTO {

    private final InteresaConverterDTO interesaConverterDTO = new InteresaConverterDTO();
    private final PropietarioConverterDTO propietarioConverterDTO = new PropietarioConverterDTO();
    private final InmobiliariaConverterDTO inmobiliariaConverterDTO = new InmobiliariaConverterDTO();


    public GetViviendaDTO createViviendainViviendaDto(Vivienda v){
        return GetViviendaDTO.builder()
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .avatar(v.getAvatar())
                .codigoPostal(v.getCodigoPostal())
                .descripcion(v.getDescripcion())
                .direccion(v.getDireccion())
                .latlng(v.getLatlng())
                .metrosCuadrados(v.getMCuadrados())
                .numBanos(v.getNBanios())
                .precio(v.getPrecio())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .tienePiscina(v.isTienePiscina())
                .tipo(v.getTipoVivienda().getTipo())
                .build();
    }

}

