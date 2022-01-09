package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.PropietarioConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.*;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.InmobiliariaService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.PropietarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViviendaConverterDTO {


    @Autowired
    private final InmobiliariaService inmobiliariaService;
    @Autowired
    private final PropietarioService propietarioService;
    @Autowired
    private final PropietarioConverterDTO propietarioConverterDTO;

    TipoVivienda tipo= TipoVivienda.ALQUILER;

    public ViviendaConverterDTO(InmobiliariaService inmobiliariaService, PropietarioService propietarioService, PropietarioConverterDTO propietarioConverterDTO) {
        this.inmobiliariaService = inmobiliariaService;
        this.propietarioService = propietarioService;
        this.propietarioConverterDTO = propietarioConverterDTO;
    }


    public GetViviendaDTO createViviendainViviendaDto(Vivienda v){
        return GetViviendaDTO.builder()
                .id(v.getId())
                .titulo(v.getTitulo())
                .poblacion(v.getPoblacion())
                .provincia(v.getProvincia())
                .avatar(v.getAvatar())
                .codigoPostal(v.getCodigoPostal())
                .descripcion(v.getDescripcion())
                .direccion(v.getDireccion())
                .latlng(v.getLatlng())
                .numHabitaciones(v.getNumHabitaciones())
                .metrosCuadrados(v.getMCuadrados())
                .numBanos(v.getNumBanios())
                .precio(v.getPrecio())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .tienePiscina(v.isTienePiscina())
                .propietario(propietarioConverterDTO.createPropietarioinPropietarioDTO(v.getPropietario()))
                .tipo(v.getTipoVivienda())
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
        g.setDescripcion(v.getDescripcion());
        g.setCodigoPostal(v.getCodigoPostal());
        g.setAvatar(v.getAvatar());
        g.setLatlng(v.getLatlng());
        g.setMetrosCuadrados(v.getMCuadrados());
        g.setNumHabitaciones(v.getNumHabitaciones());
        g.setTipo(v.getTipoVivienda());
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
    v.setNumHabitaciones(c.getNumHabitaciones());
    v.setMCuadrados(c.getMCuadrados());
    v.setNumBanios(c.getNumBanios());
    v.setTieneAscensor(c.isTieneAscensor());
    v.setTieneGaraje(c.isTieneGaraje());
    v.setTienePiscina(c.isTienePiscina());
    v.setInmobiliaria(inmobiliariaService.findById(c.getInmobiliaria().getId()).get());
    v.setPropietario(propietarioService.findById(c.getPropietario().getId()).get());
    return v;
    }

    public Vivienda getViviendaPropietario (GetViviendaPropietarioDTO gv){
        return Vivienda.builder()
                .titulo(gv.getTitulo())
                .descripcion(gv.getDescripcion())
                .direccion(gv.getDireccion())
                .codigoPostal(gv.getCodigoPostal())
                .precio(gv.getPrecio())
                .poblacion(gv.getPoblacion())
                .provincia(gv.getProvincia())
                .latlng(gv.getLatlng())
                .tipoVivienda(tipo)
                .mCuadrados(gv.getMetrosCuadrados())
                .numBanios(gv.getNumBanios())
                .numHabitaciones(gv.getNumHabitaciones())
                .avatar(gv.getAvatar())
                .tieneAscensor(gv.isTieneAscensor())
                .tieneGaraje(gv.isTieneGaraje())
                .tienePiscina(gv.isTienePiscina())
                .build();
    }

    public Propietario getPropietarioVivienda (GetViviendaPropietarioDTO gv){
        return Propietario.builder()
                .id(gv.getId())
                .nombre(gv.getNombre())
                .apellidos(gv.getApellidos())
                .email(gv.getEmail())
                .telefono(gv.getTelefono())
                .avatar(gv.getAvatar2())
                .build();
    }

    public GetViviendaPropietarioDTO createViviendaPropietarioDTO (Vivienda v){
        return GetViviendaPropietarioDTO
                .builder()
                .id(v.getId())
                .descripcion(v.getDescripcion())
                .avatar(v.getAvatar())
                .direccion(v.getDireccion())
                .codigoPostal(v.getCodigoPostal())
                .numBanios(v.getNumBanios())
                .numHabitaciones(v.getNumHabitaciones())
                .provincia(v.getProvincia())
                .poblacion(v.getPoblacion())
                .titulo(v.getTitulo())
                .codigoPostal(v.getCodigoPostal())
                .tieneGaraje(v.isTieneGaraje())
                .latlng(v.getLatlng())
                .tipo(v.getTipoVivienda())
                .tienePiscina(v.isTienePiscina())
                .tieneAscensor(v.isTieneAscensor())
                .metrosCuadrados(v.getMCuadrados())
                .precio(v.getPrecio())
                .nombre(v.getPropietario().getNombre())
                .apellidos(v.getPropietario().getApellidos())
                .email(v.getPropietario().getEmail())
                .telefono(v.getPropietario().getTelefono())
                .avatar2(v.getPropietario().getAvatar())
                .build();
    }

}

