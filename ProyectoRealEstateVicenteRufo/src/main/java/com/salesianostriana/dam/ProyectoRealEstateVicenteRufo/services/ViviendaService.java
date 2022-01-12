package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.GetViviendaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.repository.ViviendaRepository;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.base.BaseService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ViviendaService extends BaseService<Vivienda,Long, ViviendaRepository> {

    public List<Vivienda> findTopViviendas(){
        return repository.top10ViviendasInteresas();
    }

    public Vivienda saveGetViviendaDtoToVivienda(GetViviendaDTO getViviendaDto, UserEntity user){
        Vivienda vivienda = Vivienda.builder()
                .id(getViviendaDto.getId())
                .titulo(getViviendaDto.getTitulo())
                .descripcion(getViviendaDto.getDescripcion())
                .avatar(getViviendaDto.getAvatar())
                .latlng(getViviendaDto.getLatlng())
                .direccion(getViviendaDto.getDireccion())
                .codigoPostal(getViviendaDto.getCodigoPostal())
                .poblacion(getViviendaDto.getPoblacion())
                .provincia(getViviendaDto.getProvincia())
                .tipoVivienda(getViviendaDto.getTipo())
                .precio(getViviendaDto.getPrecio())
                .numHabitaciones(getViviendaDto.getNumHabitaciones())
                .mCuadrados(getViviendaDto.getMetrosCuadrados())
                .numBanios(getViviendaDto.getNumBanos())
                .tienePiscina(getViviendaDto.isTienePiscina())
                .tieneAscensor(getViviendaDto.isTieneAscensor())
                .tieneGaraje(getViviendaDto.isTieneGaraje())
                .propietario(user)
                .build();

        return save(vivienda);
    }
}
