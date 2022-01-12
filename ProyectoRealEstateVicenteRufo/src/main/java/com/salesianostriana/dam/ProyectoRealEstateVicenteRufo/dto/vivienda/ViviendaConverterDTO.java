package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.PropietarioConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.*;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.InmobiliariaService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.PropietarioService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.ViviendaService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.UserDtoConverter;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class ViviendaConverterDTO {


    @Autowired
    private final InmobiliariaService inmobiliariaService;
    @Autowired
    private final PropietarioService propietarioService;
    @Autowired
    private final UserDtoConverter userDtoConverter;
    @Autowired
    private final ViviendaService viviendaService;
    @Autowired
    private final PropietarioConverterDTO propietarioConverterDTO;

    TipoVivienda tipo= TipoVivienda.ALQUILER;

    public ViviendaConverterDTO(InmobiliariaService inmobiliariaService, PropietarioService propietarioService, UserDtoConverter userDtoConverter, ViviendaService viviendaService, PropietarioConverterDTO propietarioConverterDTO) {
        this.inmobiliariaService = inmobiliariaService;
        this.propietarioService = propietarioService;
        this.userDtoConverter = userDtoConverter;
        this.viviendaService = viviendaService;
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
                .metrosCuadrados(v.getMetrosCuadrados())
                .numBanos(v.getNumBanos())
                .precio(v.getPrecio())
                .tieneAscensor(v.isTieneAscensor())
                .tieneGaraje(v.isTieneGaraje())
                .tienePiscina(v.isTienePiscina())
                .propietario(userDtoConverter.UserEntityToGetUserDto(v.getPropietario()))
                .tipo(v.getTipoVivienda())
                .build();
    }

    public GetViviendaInmobiliariaDTO createViviendaToGetViviendaInmobiliariaDTO(Vivienda v, Inmobiliaria i){

        GetViviendaInmobiliariaDTO g = new GetViviendaInmobiliariaDTO();
        g.setId(v.getId());
        g.setTitulo(v.getTitulo());
        g.setPoblacion(v.getPoblacion());
        g.setDireccion(v.getDireccion());
        g.setProvincia(v.getProvincia());
        g.setPrecio(v.getPrecio());
        g.setDescripcion(v.getDescripcion());
        g.setCodigoPostal(v.getCodigoPostal());
        g.setAvatar(v.getAvatar());
        g.setLatlng(v.getLatlng());
        g.setMetrosCuadrados(v.getMetrosCuadrados());
        g.setNumBanos(v.getNumBanos());
        g.setNumHabitaciones(v.getNumHabitaciones());
        g.setTipo(v.getTipoVivienda());
        g.setMeInteresas(v.getInteresaList().size());
        g.setIdInmobiliaria(i.getId());
        g.setNombreInmo(i.getNombre());

        return g;

    }

    public Vivienda createViviendaDTOtoVivienda(CreateViviendaDTO c, UserEntity user){

    Vivienda v = new Vivienda();

    v.setId(c.getId());
    v.setTitulo(c.getTitulo());
    v.setDescripcion(c.getDescripcion());
    v.setAvatar(c.getAvatar());
    v.setLatlng(c.getLatlng());
    v.setDireccion(c.getDireccion());
    v.setCodigoPostal(c.getCodigoPostal());
    v.setPoblacion(c.getPoblacion());
    v.setProvincia(c.getProvincia());
    v.setTipoVivienda(v.getTipoVivienda());
    v.setCodigoPostal(c.getCodigoPostal());
    v.setPrecio(c.getPrecio());
    v.setNumHabitaciones(c.getNumHabitaciones());
    v.setMetrosCuadrados(c.getMetrosCuadrados());
    v.setNumBanos(c.getNumBanos());
    v.setTieneAscensor(c.isTieneAscensor());
    v.setTieneGaraje(c.isTieneGaraje());
    v.setTienePiscina(c.isTienePiscina());
    v.setPropietario(user);
    return v;
    }

    public Optional<Vivienda> edit(Long id, CreateViviendaDTO c,UserEntity user){
        return viviendaService.findById(id).map(nuevo ->{
            nuevo.setTitulo(c.getTitulo());
            nuevo.setDescripcion(c.getDescripcion());
            nuevo.setAvatar(c.getAvatar());
            nuevo.setLatlng(c.getLatlng());
            nuevo.setDireccion(c.getDireccion());
            nuevo.setCodigoPostal(c.getCodigoPostal());
            nuevo.setPoblacion(c.getPoblacion());
            nuevo.setProvincia(c.getProvincia());
            nuevo.setTipoVivienda(c.getTipo());
            nuevo.setCodigoPostal(c.getCodigoPostal());
            nuevo.setPrecio(c.getPrecio());
            nuevo.setNumHabitaciones(c.getNumHabitaciones());
            nuevo.setMetrosCuadrados(c.getMetrosCuadrados());
            nuevo.setNumBanos(c.getNumBanos());
            nuevo.setTieneAscensor(c.isTieneAscensor());
            nuevo.setTieneGaraje(c.isTieneGaraje());
            nuevo.setTienePiscina(c.isTienePiscina());
            nuevo.setPropietario(user);
            viviendaService.save(nuevo);
            return nuevo;
        });
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
                .metrosCuadrados(gv.getMetrosCuadrados())
                .numBanos(gv.getNumBanios())
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
                .numBanios(v.getNumBanos())
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
                .metrosCuadrados(v.getMetrosCuadrados())
                .precio(v.getPrecio())
                .nombre(v.getPropietario().getNombre())
                .apellidos(v.getPropietario().getApellidos())
                .email(v.getPropietario().getEmail())
                .telefono(v.getPropietario().getTelefono())
                .avatar2(v.getPropietario().getAvatar())
                .build();
    }

}

