package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.controller;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.GetInteresadoDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.InteresadoConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Interesado;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.InteresadoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/interesado")
public class InteresadoController {

    private final InteresadoService interesadoService;
    private final InteresadoConverterDTO interesadoConverterDTO;

    @Operation(summary = "Obtiene todos los interesado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el interesado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Interesado.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado ningun interesado",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<List<GetInteresadoDTO>> findAll(){
        if(interesadoService.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
             List<GetInteresadoDTO> list= interesadoService.findAll().stream()
                     .map(interesadoConverterDTO::createInteresadoDTOtoInteresado)
                     .collect(Collectors.toList());
            return ResponseEntity.ok().body(list);
        }

    }



    @Operation(summary = "Obtiene un interesado creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el interesado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Interesado.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se han encontrado el interesado",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<GetInteresadoDTO> findOne(@PathVariable Long id){

        if(interesadoService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok().body(interesadoConverterDTO.createInteresadoDTOtoInteresado(interesadoService.findById(id).get()));
        }
    }
}
