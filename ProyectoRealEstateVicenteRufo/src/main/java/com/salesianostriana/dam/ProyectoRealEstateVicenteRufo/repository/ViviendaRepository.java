package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.repository;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ViviendaRepository extends JpaRepository<Vivienda,Long> {

    @Query(value = """
            SELECT * FROM Vivienda v
            WHERE v.id IN (SELECT  v1.id
            FROM Vivienda v1.id JOIN Interesa i ON v1.id= i.vivienda_id
            GROUP BY v1.id
            ORDER BY COUNT(*) DESC
            LIMIT 10)
            """,nativeQuery = true)
    List<Vivienda> top10ViviendasInteresas();
}
