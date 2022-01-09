package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesa;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesado;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.repository.InteresaRepository;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.base.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InteresaService extends BaseService<Interesa,Long, InteresaRepository> {

    @Autowired
    private final ViviendaService viviendaService;
    @Autowired
    private final InteresadoService interesadoService;

    public InteresaService(ViviendaService viviendaService, InteresadoService interesadoService) {
        this.viviendaService = viviendaService;
        this.interesadoService = interesadoService;
    }


    public Interesa findByInteresa(Long id1,Long id2){
        Interesa i= new Interesa();
        i= repository.findByViviendaAndInteresado(id1, id2);
        return i;
    }

    public void deleteInteresa(Long id1,Long id2){
        Vivienda v= viviendaService.findById(id1).get();
        Interesado i= interesadoService.findById(id2).get();
        Interesa interesa= repository.findByViviendaAndInteresado(id1,id2);

        interesa.removeInteresado(i);
        interesa.removeVivienda(v);

        viviendaService.save(v);
        interesadoService.save(i);
        delete(interesa);
    }

}
