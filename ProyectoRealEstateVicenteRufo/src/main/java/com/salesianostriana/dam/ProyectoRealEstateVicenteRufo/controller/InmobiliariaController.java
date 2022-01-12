package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.controller;


import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.GetInmobiliariaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.inmobiliaria.InmobiliariaConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Inmobiliaria;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.InmobiliariaService;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.paginacion.PaginationLinksUtil;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.CreateUserDto;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.GetUserDto;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.dto.UserDtoConverter;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserEntity;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.model.UserRoles;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.users.services.UserEntityService;
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
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/inmobiliaria")
@RequiredArgsConstructor
public class InmobiliariaController {

    private final PaginationLinksUtil paginationLinksUtil;
    private final InmobiliariaService inmobiliariaService;
    private final InmobiliariaConverterDTO inmobiliariaConverterDTO;
    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;

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
    public ResponseEntity<?> findAll(@PageableDefault(size = 10, page = 0) Pageable pageable,
                                     HttpServletRequest request) {
        Page<Inmobiliaria> inmo = inmobiliariaService.findAll(pageable);

        if (inmo.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            Page<GetInmobiliariaDTO> g = inmo.map(inmobiliariaConverterDTO::inmobiliariaDTOToInmobiliaria);
            UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString());
            return ResponseEntity.ok().header("Link", paginationLinksUtil.createLinkHeader(g, uriBuilder)).body(g);
        }
    }

    @Operation(summary = "Crea una inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la inmobiliaria",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha guardado la inmobiliaria",
                    content = @Content),
    })
    @PostMapping("/")
    public ResponseEntity<Inmobiliaria> create(@RequestBody Inmobiliaria inmobiliaria) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(inmobiliariaService.save(inmobiliaria));

    }

    @Operation(summary = "Obtiene una inmobiliaria creada")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la inmobiliaria",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se han encontrado la inmobiliaria",
                    content = @Content),
    })
    @GetMapping("/{id}")
    public ResponseEntity<List<GetInmobiliariaDTO>> findOne(@PathVariable Long id) {
        Optional<Inmobiliaria> inmobiliaria = inmobiliariaService.findById(id);
        if (inmobiliariaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
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
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<GetInmobiliariaDTO> delete(@PathVariable Long id) {
        if (inmobiliariaService.findById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            inmobiliariaService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Agregar una nueva inmobiliaria")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la inmobiliaria correctamente como gestor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido borrar",
                    content = @Content),
    })
    @DeleteMapping("/gestor/{id}")
    public ResponseEntity<?> deleteInmoG(@PathVariable UUID id,@AuthenticationPrincipal UserEntity user) {
        if (userEntityService.findById(user.getId()).isPresent()
                && userEntityService.findById(user.getId()).get().getUserRoles().equals(UserRoles.GESTOR)) {
            Inmobiliaria inmobiliaria = inmobiliariaService.findById(user.getInmobiliaria().getId()).get();
            inmobiliaria.setGestoresNull(user);
            inmobiliariaService.save(inmobiliaria);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        if (userEntityService.findById(user.getId()).get().getUserRoles().equals(UserRoles.ADMIN)
                && userEntityService.findById(user.getId()).isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.badRequest().build();
        }

    }

    @Operation(summary = "Obtener una nueva inmobiliaria como gestor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha obtenido la inmobiliaria correctamente como gestor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido borrar",
                    content = @Content),
    })
    @GetMapping("/{id}/gestor")
    public ResponseEntity<List<GetUserDto>> findUnGestor(@PathVariable Long id, @AuthenticationPrincipal UserEntity user){
        Optional<Inmobiliaria> i= inmobiliariaService.findById(id);
        List<UserEntity> pro= userEntityService.loadUserByRole(UserRoles.GESTOR);
        pro.stream().filter(g -> g.getInmobiliaria().getId().equals(id))
                .collect(Collectors.toList());
        if(inmobiliariaService.findById(id).isPresent() && userEntityService.findById(user.getId()).get().getUserRoles().equals(UserRoles.GESTOR)){
            return ResponseEntity.ok().body(pro.stream().map(userDtoConverter::UserEntityToGetUserDto).collect(Collectors.toList()));
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear una nueva inmobiliaria como gestor")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha creado la inmobiliaria correctamente como gestor",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Inmobiliaria.class))}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha podido crear",
                    content = @Content),
    })
    @PostMapping("{id}/gestor")
    public ResponseEntity<GetUserDto> nuevoGestor(@PathVariable Long id, @AuthenticationPrincipal UserEntity user, @RequestBody CreateUserDto dto) {
        UserEntity g = userEntityService.saveGestor(dto);
        if (g == null) {
            return ResponseEntity.badRequest().build();
        } else {
           return ResponseEntity.status(HttpStatus.CREATED).body(userDtoConverter.UserEntityToGetUserDto(g));
        }
    }
}
