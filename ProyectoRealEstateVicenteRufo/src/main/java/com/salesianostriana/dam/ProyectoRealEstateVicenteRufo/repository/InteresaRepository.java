package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.repository;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InteresaRepository extends JpaRepository<Interesa,Long> {

    Interesa findByViviendaAndInteresado(Long id1,Long id2);
}
