package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.dto.request.MedicoRequest;
import com.init_coding.hackacode_3_backend.dto.response.MedicoResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.InvalidEspecialidadException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.service.IMedicoService;
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
@RequestMapping("/api/medicos")
@Tag(name = "Médicos", description = "Operaciones realizadas con médicos")
public class MedicoController {

    @Autowired
    IMedicoService medicoService;

    @Operation(summary = "Obtiene todos los medicos registrados",
    description = "Este endpoint permite obtener los detalles de todos los médicos, con la posibilidad de filtrar por especialidad")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de médicos"),
                @ApiResponse(responseCode = "400", description = "Peticion mal formada o especialidad inválida")
            }
    )
    @GetMapping
    public ResponseEntity<List<MedicoResponse>> getAll(
            @Parameter(description = "ID de la especialidad")
            @RequestParam(name = "especialidadId", required = false) Long especialidadId) throws InvalidEspecialidadException{
        if (especialidadId != null)
            return ResponseEntity.ok(medicoService.findByEspecialidad(especialidadId));
        else
            return ResponseEntity.ok(medicoService.findAll());
    }

    @Operation(summary = "Obtiene todos los medicos inactivos",
    description = "Este endpoint permite obtener los detalles de todos los médicos inactivos")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de médicos inactivos")
    )
    @GetMapping("/inactivos")
    public ResponseEntity<List<MedicoResponse>> getAllInactivos(){
        return ResponseEntity.ok(medicoService.findAllInactivos());
    }

    @Operation(summary = "Obtiene un médico por su ID",
    description = "Este endpoint permite obtener los detalles un médico")
    @ApiResponses(
            value = {
            @ApiResponse(responseCode = "200", description = "Se obtuvo el médico"),
            @ApiResponse(responseCode = "404", description = "No se encontró el médico")
            }
    )
    @GetMapping("/{medicoId}")
    public ResponseEntity<MedicoResponse> findOne(
            @Parameter(description = "ID del médico", required = true)
            @PathVariable(name = "medicoId") Long medicoId) throws ResourceNotFoundException {
        return ResponseEntity.ok(medicoService.findById(medicoId));
    }

    @Operation(summary = "Crea un nuevo médico",
    description = "Este endpoint permite guardar un médico")
    @ApiResponses(
            value = {
            @ApiResponse(responseCode = "201", description = "Se creó el médico"),
            @ApiResponse(responseCode = "400", description = "Peticion mal formada o especialidad inválida")
            }
    )
    @PostMapping
    public ResponseEntity<MedicoResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del medico a crear",
                                                                    required = true)
            @RequestBody MedicoRequest medico) throws InvalidEspecialidadException{
        return ResponseEntity.status(HttpStatus.CREATED).body(medicoService.create(medico));
    }

    @Operation(summary = "Modifica un médico",
    description = "Este endpoint permite modificar un médico existente")
    @ApiResponses(
            value = {
            @ApiResponse(responseCode = "200", description = "Se modificó el médico"),
            @ApiResponse(responseCode = "404", description = "No se encontró el médico"),
            @ApiResponse(responseCode = "400", description = "Peticion mal formada o especialidad inválida")
            }
    )
    @PutMapping("/{medicoId}")
    public ResponseEntity<MedicoResponse> update(
            @Parameter(description = "ID del médico")
            @PathVariable(name = "medicoId") Long medicoId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del medico a modificar",
                                                                    required = true)
            @RequestBody MedicoRequest medico) throws InvalidEspecialidadException, ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(medicoService.update(medicoId, medico));
    }

    @Operation(summary = "Elimina un médico",
    description = "Este endpoint permite eliminar un médico existente de manera lógica")
    @ApiResponses(
            value = {
            @ApiResponse(responseCode = "204", description = "Se eliminó el médico"),
            @ApiResponse(responseCode = "404", description = "No se encontró el médico")
            }
    )
    @DeleteMapping("/{medicoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(
            @Parameter(description = "ID del médico")
            @PathVariable(name = "medicoId") Long medicoId) throws ResourceNotFoundException, EntityAlreadyActivaException{
        medicoService.updateActivo(medicoId, false);
    }

    @Operation(summary = "Reactiva un médico",
    description = "Este endpoint permite reactivar un médico eliminado de manera lógica")
    @ApiResponses(
            value = {
            @ApiResponse(responseCode = "200", description = "Se reactivó el médico"),
            @ApiResponse(responseCode = "404", description = "No se encontró el médico"),
            @ApiResponse(responseCode = "409", description = "Conflicto o el médico ya se encontraba activo")
            }
    )
    @PatchMapping("/{medicoId}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivar(
            @Parameter(description = "ID del médico")
            @PathVariable(name = "medicoId") Long medicoId) throws ResourceNotFoundException, EntityAlreadyActivaException {
        medicoService.updateActivo(medicoId, true);
    }
}
