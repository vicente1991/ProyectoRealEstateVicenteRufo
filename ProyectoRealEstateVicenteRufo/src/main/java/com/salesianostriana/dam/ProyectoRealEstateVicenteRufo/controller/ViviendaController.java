package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.controller;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.interesa.*;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.PropietarioConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.CreateViviendaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.GetViviendaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.GetViviendaPropietarioDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.vivienda.ViviendaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.*;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.*;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.UserDtoConverter;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserRoles;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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
    private final PropietarioService propietarioService;
    private final InteresaConverterDTO interesaConverterDTO;
    private final ViviendaConverterDTO viviendaConverterDTO;
    private final UserDtoConverter userDtoConverter;


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
    public ResponseEntity<?> finAll(@PageableDefault(page = 0, size = 10) Pageable pageable, @AuthenticationPrincipal UserEntity user){
        Page<Vivienda> v= viviendaService.findAll(pageable);

        if(v.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Page<GetViviendaDTO> result= v
                    .map(viviendaConverterDTO::createViviendainViviendaDto);
            return ResponseEntity.ok().body(result);
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
    public ResponseEntity<GetViviendaDTO> createVivienda(@RequestBody CreateViviendaDTO c, @AuthenticationPrincipal UserEntity u){

        GetViviendaDTO get= saveGetViviendaDto(c,u);
       Vivienda v= viviendaConverterDTO.createViviendaDTOtoVivienda(c,u);
       viviendaService.saveGetViviendaDtoToVivienda(get,u);
       return ResponseEntity.status(HttpStatus.CREATED).body(get);
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
    public ResponseEntity<?> findOne(@PathVariable Long id, @AuthenticationPrincipal UserEntity user){

        if(viviendaService.findById(id).isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(viviendaConverterDTO.createViviendainViviendaDto(viviendaService.findById(id).get()));
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
            viviendaService.deleteById(id);
            return ResponseEntity.noContent().build();
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
    public ResponseEntity<?> edit(@RequestBody CreateViviendaDTO v,@PathVariable Long id,@AuthenticationPrincipal UserEntity user){

        Optional<Vivienda> viv= viviendaService.findById(id);

        if (viv.isEmpty() && id.equals(viv.get().getId())) {
            return ResponseEntity.notFound().build();
        }else{
            Vivienda vNew= new Vivienda();
            vNew= viviendaConverterDTO.createViviendaDTOtoVivienda(v,user);
            viviendaService.edit(vNew);
            return ResponseEntity.status(HttpStatus.CREATED).body(vNew);
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
    public ResponseEntity<GetInteresadoDTO> createInteresado(@RequestBody GetInteresadoDTO interesadodto, GetInteresaDTO interesadto ,@PathVariable Long id){

        Interesado in = interesadoConverterDTO.createInteresadoDTOinInteresado(interesadodto);
        Interesa interesa = interesaConverterDTO.createInteresaDTOinInteresa(interesadto);

        interesadoService.save(in);
        interesa.addInteresado(in);
        interesa.setVivienda(viviendaService.findById(id).get());

        interesaService.save(interesa);
        in.getInteresaList().add(interesa);
        interesadoService.save(in);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(interesadoConverterDTO.createInteresadoDTOtoInteresado(in));

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
    @PostMapping("/{id1}/meinteresa/{id2}")
    public ResponseEntity<GetInteresadoInteresViviendaDTO> createInteresaVivienda(@PathVariable Long id1, @RequestBody CreateInteresadoInteresaDTO interesadto, @PathVariable Long id2){

        Optional<Vivienda> v= viviendaService.findById(id1);
        Optional<Interesado> i= interesadoService.findById(id2);
        if(viviendaService.findById(id1).isEmpty() || interesadoService.findById(id2).isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            Vivienda vivienda= v.get();
            Interesado interesado= i.get();
            Interesa interesa= Interesa.builder()
                    .mensaje(interesadto.getMensaje())
                    .build();
            interesa.addInteresado(interesado);
            interesa.addVivienda(vivienda);
            interesadoService.save(interesado);
            interesaService.save(interesa);
            GetInteresadoInteresViviendaDTO interesViviendaDTO= interesadoConverterDTO.dto(interesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(interesViviendaDTO);
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


    public GetViviendaDTO saveGetViviendaDto(CreateViviendaDTO createViviendaDto, UserEntity user){
        GetViviendaDTO getViviendaDto = GetViviendaDTO.builder()
                .titulo(createViviendaDto.getTitulo())
                .descripcion(createViviendaDto.getDescripcion())
                .latlng(createViviendaDto.getLatlng())
                .codigoPostal(createViviendaDto.getCodigoPostal())
                .tienePiscina(createViviendaDto.isTienePiscina())
                .tieneAscensor(createViviendaDto.isTieneAscensor())
                .tieneGaraje(createViviendaDto.isTieneGaraje())
                .precio(createViviendaDto.getPrecio())
                .poblacion(createViviendaDto.getPoblacion())
                .provincia(createViviendaDto.getProvincia())
                .avatar(createViviendaDto.getAvatar())
                .tipo(createViviendaDto.getTipo())
                .direccion(createViviendaDto.getDireccion())
                .numHabitaciones(createViviendaDto.getNumHabitaciones())
                .metrosCuadrados(createViviendaDto.getMCuadrados())
                .numBanos(createViviendaDto.getNumBanios())
                .propietario(userDtoConverter.UserEntityToGetUserDto(user))
                .build();

        return getViviendaDto;
    }

}
