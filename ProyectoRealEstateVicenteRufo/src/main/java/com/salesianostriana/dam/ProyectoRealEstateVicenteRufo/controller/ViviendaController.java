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

    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha modificado la vivienda seleccionada",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha modificado la vivienda seleccionada",
                    content = @Content),
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vivienda> edit(@RequestBody Vivienda v,@PathVariable Long id){
        if (viviendaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.of(
                    viviendaService.findById(id).map(mod ->{
                        mod.setTitulo(v.getTitulo());
                        mod.setDescripcion(v.getDescripcion());
                        mod.setDireccion(v.getDireccion());
                        mod.setAvatar(v.getAvatar());
                        mod.setCodigoPostal(v.getCodigoPostal());
                        mod.setLatlng(v.getLatlng());
                        mod.setNBanios(v.getNBanios());
                        mod.setMCuadrados(v.getMCuadrados());
                        mod.setNHabitaciones(v.getNHabitaciones());
                        mod.setProvincia(v.getProvincia());
                        mod.setPoblacion(v.getPoblacion());
                        mod.setPrecio(v.getPrecio());
                        mod.setTipoVivienda(v.getTipoVivienda());
                        mod.setTienePiscina(v.isTienePiscina());
                        mod.setTieneGaraje(v.isTieneGaraje());
                        mod.setTieneAscensor(v.isTieneAscensor());
                        viviendaService.save(mod);
                        return mod;
                    })
            );
        }
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
    @Operation(summary = "Se añade una inmobiliaria a una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se añade la inmobiliaria en la vivienda",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se encuentra la vivienda o la inmobiliaria",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Los datos introducidos son erroneos",
                    content = @Content)
    })
    @PostMapping("/{id1}/inmobiliaria/{id2}")
    public ResponseEntity<?> addViviendaToInmobiliaria(@PathVariable Long id1,@PathVariable Long id2){
        Optional<Vivienda> v= viviendaService.findById(id1);
        Optional<Inmobiliaria> i= inmobiliariaService.findById(id2);
        if(v.isEmpty() || i.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Vivienda vi= v.get();
            Inmobiliaria inmo = i.get();
            vi.addToInmobiliaria(inmo);
            viviendaService.save(vi);
            GetViviendaDTO viviendaDTO= viviendaConverterDTO.createViviendaToGetViviendaInmobiliariaDTO(vi,inmo);
            return ResponseEntity.ok().body(viviendaDTO);
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

    @Operation(summary = "Elimina el interés de un interesado por una vivienda")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha eliminado el interés",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado una vivienda con dicho id",
                    content = @Content),
    })
    @DeleteMapping("/{id1}/meInteresa/{id2}")
    public ResponseEntity<?> deleteInteresaToVivienda(@PathVariable Long id1,@PathVariable Long id2){
        if(viviendaService.findById(id1).isEmpty() || interesadoService.findById(id2).isEmpty() || interesaService.findByInteresa(id1,id2)==null) {
            return ResponseEntity.notFound().build();
        }else {
            interesaService.deleteInteresa(id1,id2);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

    }

    @Operation(summary = "Obtiene un top de las 10 viviendas con más interesados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado las viviendas",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Vivienda.class))})
    })
    @GetMapping("/top")
    public ResponseEntity<List<GetViviendaDTO>> tops(@RequestParam("n") int n){
        List<Vivienda> top= viviendaService.findTopViviendas();
        List<GetViviendaDTO> list = top.stream()
                .map(viviendaConverterDTO::createViviendainViviendaDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(list);
    }



}
