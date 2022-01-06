package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.InmobiliariaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.InteresaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.PropietarioConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Inmobiliaria;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.TipoVivienda;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.InmobiliariaService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViviendaConverterDTO {

    private final InteresaConverterDTO interesaConverterDTO = new InteresaConverterDTO();
    private final PropietarioConverterDTO propietarioConverterDTO = new PropietarioConverterDTO();
    private final InmobiliariaConverterDTO inmobiliariaConverterDTO = new InmobiliariaConverterDTO();
    @Autowired
    private final InmobiliariaService inmobiliariaService;
    @Autowired
    private final PropietarioService propietarioService;

    public ViviendaConverterDTO(InmobiliariaService inmobiliariaService, PropietarioService propietarioService) {
        this.inmobiliariaService = inmobiliariaService;
        this.propietarioService = propietarioService;
    }

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

    public GetViviendaInmobiliariaDTO createViviendaToGetViviendaInmobiliariaDTO(Vivienda v, Inmobiliaria i){

        GetViviendaInmobiliariaDTO g = new GetViviendaInmobiliariaDTO();
        g.setTitulo(v.getTitulo());
        g.setPoblacion(v.getPoblacion());
        g.setDireccion(v.getDireccion());
        g.setProvincia(v.getProvincia());
        g.setPrecio(v.getPrecio());
        g.setId(v.getId());
        g.setAvatar(v.getAvatar());
        g.setMetrosCuadrados(v.getMCuadrados());
        g.setNumHabitaciones(v.getNHabitaciones());
        g.setTipo(v.getTipoVivienda().toString());
        g.setMeInteresas(v.getInteresaList().size());
        g.setIdInmobiliaria(i.getId());
        g.setNombreInmo(i.getNombre());

        return g;

    }

    public Vivienda createViviendaDTOtoVivienda(CreateViviendaDTO c){

    TipoVivienda t = TipoVivienda.valueOf(c.getTipo());
    Vivienda v = new Vivienda();

    v.setTitulo(c.getTitulo());
    v.setDescripcion(c.getDescripcion());
    v.setAvatar(c.getAvatar());
    v.setLatlng(c.getLatlng());
    v.setDireccion(c.getDireccion());
    v.setCodigoPostal(c.getCodigoPostal());
    v.setPoblacion(c.getPoblacion());
    v.setProvincia(c.getProvincia());
    v.setTipoVivienda(t);
    v.setPrecio(c.getPrecio());
    v.setNHabitaciones(c.getNumHabitaciones());
    v.setMCuadrados(c.getMCuadrados());
    v.setNBanios(c.getNumBanios());
    v.setTieneAscensor(c.isTieneAscensor());
    v.setTieneGaraje(c.isTieneGaraje());
    v.setTienePiscina(c.isTienePiscina());
    v.setInmobiliaria(inmobiliariaService.findById(c.getInmobiliaria().getId()).get());
    v.setPropietario(propietarioService.findById(c.getPropietario().getId()).get());
    return v;
    }


}

