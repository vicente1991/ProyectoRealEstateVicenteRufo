package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.controller;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.GetPropietarioDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.GetPropietarioViviendaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.PropietarioConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Propietario;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.repository.PropietarioRepository;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.PropietarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
public class PropietarioController {

    private final PropietarioService propietarioService;
    private final PropietarioConverterDTO propietarioConverterDTO;


    @Operation(summary = "Borra un Propietario creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el propietario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        if(propietarioService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            propietarioService.deleteById(id);

            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Obtiene un Propietario creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha encontrado todos los propietarios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))})
    })
    @GetMapping("/")
    public ResponseEntity<List<GetPropietarioDTO>> findAll(){

        if(propietarioService.findAll().isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetPropietarioDTO> list= propietarioService.findAll().stream()
                    .map(propietarioConverterDTO::createPropietarioinPropietarioDTO)
                    .collect(Collectors.toList());

            return ResponseEntity.ok().body(list);
        }
    }

    @Operation(summary = "Obtiene un Propietario creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha encontrado un propietario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))})
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<GetPropietarioViviendaDTO>> findOne(@PathVariable Long id){

        Optional<Propietario> propietario= propietarioService.findById(id);
        if(propietarioService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<GetPropietarioViviendaDTO> prop= propietario.stream()
                    .map(propietarioConverterDTO::createPropietarioinPropietarioViviendaDTO)
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(prop);
        }
    }


}
