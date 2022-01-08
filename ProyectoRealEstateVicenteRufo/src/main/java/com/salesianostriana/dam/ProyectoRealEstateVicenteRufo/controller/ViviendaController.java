package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.controller;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.GetInteresaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.GetInteresadoDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.InteresaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.InteresadoConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.GetViviendaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.ViviendaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Inmobiliaria;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesa;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesado;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Vivienda;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/vivienda")
public class ViviendaController {

    private final ViviendaService viviendaService;
    private final InmobiliariaService inmobiliariaService;
    private final InteresadoService interesadoService;
    private final InteresaService interesaService;
    private final InteresadoConverterDTO interesadoConverterDTO;
    private final InteresaConverterDTO interesaConverterDTO;
    private final PropietarioService propietarioService;
    private final ViviendaConverterDTO viviendaConverterDTO;


    @Operation(summary = "Se listan todas las viviendas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las viviendas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado las viviendas",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetViviendaDTO>> finAll(@PageableDefault(page = 0, size = 10) Pageable pageable){
        Page<Vivienda> v= viviendaService.findAll(pageable);

        if(v.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetViviendaDTO> list = v.stream()
                    .map(viviendaConverterDTO::createViviendainViviendaDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se crea una vivienda con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "La estructura de la petición estaba mal formulada",
                    content = @Content)
    })
    @PostMapping("/")
    public ResponseEntity<Vivienda> createVivienda(@RequestBody Vivienda v){
        if(!propietarioService.findById(v.getPropietario().getId()).isPresent()){
            propietarioService.save(v.getPropietario());
        }return ResponseEntity.status(HttpStatus.CREATED)
                .body(viviendaService.save(v));
    }

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el interesado y el interesa asociado a una vivienda",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado el interesado y el interesa asociado a una vivienda",
                    content = @Content),
    })
    @PostMapping("/{id}/meinteresa")
    public ResponseEntity<Interesado> createInteresado(@RequestBody GetInteresadoDTO interesadodto, GetInteresaDTO interesadto ,@PathVariable Long id){

        Interesado in = interesadoConverterDTO.createInteresadoDTOinInteresado(interesadodto);
        Interesa interesa = interesaConverterDTO.createInteresaDTOinInteresa(interesadto);

        interesadoService.save(in);
        interesa.addInteresado(in);
        interesa.setVivienda(viviendaService.findById(id).get());

        interesaService.save(interesa);
        in.getInteresaList().add(interesa);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(interesadoService.save(in));

    }
    @Operation(summary = "Crea un interesa asociado a una vivienda y a un interesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado el interesa asociado a una vivienda y a un interesado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha creado el interesa",
                    content = @Content),
    })
    @PostMapping("/{id}/meInteresa/{id2}")
    public ResponseEntity<Interesa> create(@PathVariable Long id1,@RequestBody GetInteresaDTO interesadto,@PathVariable Long id2){

        if(viviendaService.findById(id1).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Interesa interesa = interesaConverterDTO.createInteresaDTOinInteresa(interesadto);
            Interesado interesado= interesadoService.getById(id2);
            Vivienda vivienda= viviendaService.getById(id1);
            interesa.addVivienda(vivienda);
            interesa.addInteresado(interesado);
            Interesa inte = interesaService.save(interesa);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(inte);


        }
    }

    @Operation(summary = "Eliminación de una vivienda por su id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Borrar vivienda con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado una vivienda con ese id.",
                    content = @Content),})
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){

        if(viviendaService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            viviendaService.findById(id).get().removeViviendasToIntereses();
            viviendaService.findById(id);
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Eliminación de la inmobiliaria asociada a la vivienda según su id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Borrado de la inmobiliaria con éxito",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado una inmobiliaria en esta vivienda con ese id.",
                    content = @Content),
    })
    @DeleteMapping("/{id}/inmobiliaria/")
    public ResponseEntity<?> deleteInmobiliariaToVivienda(@PathVariable Long id){

        Optional<Vivienda> optionalVivienda= viviendaService.findById(id);

        if(optionalVivienda.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Vivienda v= optionalVivienda.get();
            Inmobiliaria i= new Inmobiliaria();
            v.setInmobiliaria(i);
            v.removeFromInmobiliaria(i);
            viviendaService.save(v);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @Operation(summary = "Conseguir una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se encuentra la vivienda por id",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la vivienda con el id proporcionado.",
                    content = @Content),
    })
    @GetMapping("{id}")
    public ResponseEntity<GetViviendaDTO> findOne(@PathVariable Long id){

        Optional<Vivienda> v= viviendaService.findById(id);

        if(v.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            GetViviendaDTO viviendaDTO = viviendaConverterDTO.createViviendainViviendaDto(v.get());
            return ResponseEntity.ok().body(viviendaDTO);
        }
    }

}
