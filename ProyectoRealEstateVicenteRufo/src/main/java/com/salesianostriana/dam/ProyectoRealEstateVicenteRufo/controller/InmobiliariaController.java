package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.controller;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.GetInmobiliariaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.InmobiliariaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Inmobiliaria;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.InmobiliariaService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.paginacion.PaginationLinksUtil;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inmobiliaria")
@RequiredArgsConstructor
public class InmobiliariaController {

    private final PaginationLinksUtil paginationLinksUtil;
    private final InmobiliariaService inmobiliariaService;
    private final InmobiliariaConverterDTO inmobiliariaConverterDTO;

    @Operation(summary = "Listar todas las inmobiliarias existentes.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Muestra todas las inmobiliarias",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No ha encontrado ninguna inmobiliaria.",
                    content = @Content),
    })
    @GetMapping("/")
    public ResponseEntity<?> findAll(@PageableDefault(size = 10,page = 0) Pageable pageable,
                                     HttpServletRequest request){
        Page<Inmobiliaria> inmo = inmobiliariaService.findAll(pageable);

        if(inmo.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Page<GetInmobiliariaDTO> g = inmo.map(inmobiliariaConverterDTO::inmobiliariaDTOToInmobiliaria);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok().header("Link", paginationLinksUtil.createLinkHeader(g,uriBuilder)).body(g);
        }
    }

    @Operation(summary = "Crea una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado la inmobiliaria",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Inmobiliaria> create(@RequestBody Inmobiliaria inmobiliaria){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inmobiliariaService.save(inmobiliaria));

    }

    @Operation(summary = "Obtiene una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado la inmobiliaria",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<GetInmobiliariaDTO>> findOne(@PathVariable Long id){
        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);
        if(inmobiliariaService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetInmobiliariaDTO> inmobiliariaDTOS = inmobiliaria.stream()
                    .map(inmobiliariaConverterDTO::inmobiliariaDTOToInmobiliaria)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(inmobiliariaDTOS);
        }
    }

    @Operation(summary = "Borra una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la inmobiliaria",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<GetInmobiliariaDTO> delete(@PathVariable Long id){
        if(inmobiliariaService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            inmobiliariaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}
