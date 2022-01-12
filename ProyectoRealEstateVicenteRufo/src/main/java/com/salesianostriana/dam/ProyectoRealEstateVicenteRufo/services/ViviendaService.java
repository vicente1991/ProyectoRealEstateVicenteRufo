package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.GetViviendaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.TipoVivienda;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.repository.ViviendaRepository;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.base.BaseService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;
import java.util.Optional;

@Service
public class ViviendaService extends BaseService<Vivienda,Long, ViviendaRepository> {

    public List<Vivienda> findTopViviendas(){
        return repository.top10ViviendasInteresas();
    }

    public Page<Vivienda> findByArgs (final Optional<Float> tamanio,
                                      final Optional<Float> numHabs,
                                      final Optional<TipoVivienda> tipo,
                                      Pageable pageable) {
        String vacio="";


    Specification<Vivienda> tamanioMetros = new Specification<Vivienda>() {
        @Override
        public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if (tamanio.isPresent()){
                return criteriaBuilder.lessThanOrEqualTo((root.get("metrosCuadrados")), tamanio.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

        }
    };

    Specification<Vivienda> filtroHabs = new Specification<Vivienda>() {
        @Override
        public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if (numHabs.isPresent()){
                return criteriaBuilder.equal(root.get("numHabitaciones"), numHabs.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

        }
    };

    Specification<Vivienda> filtroTipo = new Specification<Vivienda>() {
        @Override
        public Predicate toPredicate(Root<Vivienda> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
            if (tipo.isPresent()){
                return criteriaBuilder.equal(root.get("tipo"), tipo.get());
            } else {
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

        }
    };
    Specification<Vivienda> todas = tamanioMetros.and(filtroHabs).and(filtroTipo);

        return this.repository.findAll(todas,pageable);

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
                .metrosCuadrados(getViviendaDto.getMetrosCuadrados())
                .numBanos(getViviendaDto.getNumBanos())
                .tienePiscina(getViviendaDto.isTienePiscina())
                .tieneAscensor(getViviendaDto.isTieneAscensor())
                .tieneGaraje(getViviendaDto.isTieneGaraje())
                .propietario(user)
                .build();

        return save(vivienda);
    }
}
