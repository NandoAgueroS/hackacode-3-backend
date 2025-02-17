package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.dto.request.EspecialidadRequest;
import com.init_coding.hackacode_3_backend.dto.response.EspecialidadResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.service.IEspecialidadService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/especialidades")
@Tag(name = "Especialidades", description = "Operaciones realizadas con especialidades de los médicos")
public class EspecialidadController {

    @Autowired
    IEspecialidadService especialidadService;

    @Operation(summary = "Obtiene todas las especialidades",
            description = "Este endpoint permite obtener los detalles de todas las especialidades")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de especialidades")
    )
    @GetMapping
    public ResponseEntity<List<EspecialidadResponse>> getAll(){
        return ResponseEntity.ok(especialidadService.findAll());
    }

    @Operation(summary = "Obtiene todas las especialidades inactivas",
            description = "Este endpoint permite obtener los detalles de todas las especialidades inactivas")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de especialidades inactivas")
    )
    @GetMapping("/inactivos")
    public ResponseEntity<List<EspecialidadResponse>> getAllInactivas(){
        return ResponseEntity.ok(especialidadService.findAllInactivas());
    }

    @Operation(summary = "Obtiene una especialidad por su ID",
            description = "Este endpoint permite obtener los detalles de una especialidad")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo la especialidad"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la especialidad")
            }
    )
    @GetMapping("/{especialidadId}")
    public ResponseEntity<EspecialidadResponse> findOne(
            @Parameter(description = "ID de la especialidad", required = true)
            @PathVariable(name = "especialidadId") Long especialidadId) throws ResourceNotFoundException {
        return ResponseEntity.ok(especialidadService.findById(especialidadId));
    }

    @Operation(summary = "Crea una nueva especialidad",
            description = "Este endpoint permite guardar una especialidad")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Se creó la especialidad")
            }
    )
    @PostMapping
    public ResponseEntity<EspecialidadResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la especialidad a crear",
                    required = true)
            @RequestBody EspecialidadRequest especialidad){
        return ResponseEntity.status(HttpStatus.CREATED).body(especialidadService.create(especialidad));
    }

    @Operation(summary = "Modifica una especialidad",
            description = "Este endpoint permite modificar una especialidad existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se modificó la especialidad"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la especialidad")
            }
    )
    @PutMapping("/{especialidadId}")
    public ResponseEntity<EspecialidadResponse> update(
            @Parameter(description = "ID de la especialidad")
            @PathVariable(name = "especialidadId") Long especialidadId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la especialidad a modificar",
                    required = true)
            @RequestBody EspecialidadRequest especialidad) throws  ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(especialidadService.update(especialidadId, especialidad));
    }

    @Operation(summary = "Elimina una especialidad",
            description = "Este endpoint permite eliminar una especialidad existente de manera lógica")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Se eliminó la especialidad"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la especialidad")
            }
    )
    @DeleteMapping("/{especialidadId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(
            @Parameter(description = "ID de la especialidad")
            @PathVariable(name = "especialidadId") Long especialidadId) throws ResourceNotFoundException, EntityAlreadyActivaException{
        especialidadService.updateActivo(especialidadId, false);
    }

    @Operation(summary = "Reactiva una especialidad",
            description = "Este endpoint permite reactivar una especialidad eliminada de manera lógica")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se reactivó la especialidad"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la especialidad"),
                    @ApiResponse(responseCode = "409", description = "Conflicto o la especialidad ya se encontraba activa")
            }
    )
    @PatchMapping("/{especialidadId}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivar(
            @Parameter(description = "ID de la especialidad")
            @PathVariable(name = "especialidadId") Long especialidadId) throws ResourceNotFoundException, EntityAlreadyActivaException {
        especialidadService.updateActivo(especialidadId, true);
    }

}
