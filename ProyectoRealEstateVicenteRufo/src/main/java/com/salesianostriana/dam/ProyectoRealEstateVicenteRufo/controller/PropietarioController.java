package com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.controller;

import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.GetPropietarioDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.GetPropietarioViviendaDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.dto.propietario.PropietarioConverterDTO;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.model.Propietario;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.repository.PropietarioRepository;
import com.salesianostriana.dam.ProyectoRealEstateVicenteRufo.services.PropietarioService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/propietario")
public class PropietarioController {

    private final PropietarioService propietarioService;
    private final PropietarioConverterDTO propietarioConverterDTO;
    private final UserEntityService entityService;
    private final UserDtoConverter userDtoConverter;


    @Operation(summary = "Borra un Propietario creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el propietario",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))})
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user){
        UserEntity p= entityService.loadUserById(id);
        if(p!=null && id.equals(user.getId()) || user.getUserRoles().equals(UserRoles.ADMIN)){
            entityService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    }

    @Operation(summary = "Obtiene un Propietario creado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha encontrado todos los propietarios",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Propietario.class))})
    })
    @GetMapping("/")
    public ResponseEntity<List<GetUserDto>> findAll(){

        List<UserEntity> prop = entityService.loadUserByRole(UserRoles.PROPIETARIO);

        if(prop.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            List<UserEntity> u= prop.stream().collect(Collectors.toList());
            List<GetUserDto> list= u.stream().map(userDtoConverter::UserEntityToGetUserDto).collect(Collectors.toList());
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
    public ResponseEntity<GetUserDto> findOne(@PathVariable UUID id, @AuthenticationPrincipal UserEntity user){

        UserEntity p= entityService.loadUserById(id);
        if(p!= null && id.equals(user.getId()) || user.getUserRoles().equals(UserRoles.ADMIN)){
            return ResponseEntity.ok().body(userDtoConverter.UserEntityToGetUserDto(p));
        }else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }


}
