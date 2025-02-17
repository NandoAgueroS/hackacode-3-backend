package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.dto.request.PacienteRequest;
import com.init_coding.hackacode_3_backend.dto.response.PacienteResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.service.IPacienteService;
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
@RequestMapping("/api/pacientes")
@Tag(name = "Pacientes", description = "Operaciones realizadas con pacientes")
public class PacienteController {

    @Autowired
    IPacienteService pacienteService;

    @Operation(summary = "Obtiene todos los pacientes registrados",
            description = "Este endpoint permite obtener los detalles de todos los pacientes")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de pacientes")
    )
    @GetMapping
    public ResponseEntity<List<PacienteResponse>> getAll(){
        return ResponseEntity.ok(pacienteService.findAll());
    }

    @Operation(summary = "Obtiene todos los pacientes inactivos",
            description = "Este endpoint permite obtener los detalles de todos los pacientes inactivos")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de pacientes inactivos")
    )
    @GetMapping("/inactivos")
    public ResponseEntity<List<PacienteResponse>> getAllInactivos(){
        return ResponseEntity.ok(pacienteService.findAllInactivos());
    }


    @Operation(summary = "Obtiene un paciente por su ID",
            description = "Este endpoint permite obtener los detalles un paciente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo el paciente"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el paciente")
            }
    )
    @GetMapping("/{pacienteId}")
    public ResponseEntity<PacienteResponse> findOne(
            @Parameter(description = "ID del paciente", required = true)
            @PathVariable(name = "pacienteId") Long pacienteId) throws ResourceNotFoundException {
        return ResponseEntity.ok(pacienteService.findById(pacienteId));
    }

    @Operation(summary = "Crea un nuevo paciente",
            description = "Este endpoint permite guardar un paciente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Se creó el paciente"),
            }
    )
    @PostMapping
    public ResponseEntity<PacienteResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del paciente a crear",
                    required = true)
            @RequestBody PacienteRequest paciente) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pacienteService.create(paciente));
    }

    @Operation(summary = "Modifica un paciente",
            description = "Este endpoint permite modificar un paciente existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se modificó el paciente"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el paciente"),
            }
    )
    @PutMapping("/{pacienteId}")
    public ResponseEntity<PacienteResponse> update(
            @Parameter(description = "ID del paciente")
            @PathVariable(name = "pacienteId") Long pacienteId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del paciente a modificar",
                    required = true)
            @RequestBody PacienteRequest paciente) throws ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(pacienteService.update(pacienteId, paciente));
    }

    @Operation(summary = "Elimina un paciente",
            description = "Este endpoint permite eliminar un paciente existente de manera lógica")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Se eliminó el paciente"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el paciente")
            }
    )
    @DeleteMapping("/{pacienteId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(
            @Parameter(description = "ID del paciente")
            @PathVariable(name = "pacienteId") Long pacienteId) throws ResourceNotFoundException, EntityAlreadyActivaException{
        pacienteService.updateActivo(pacienteId, false);
    }

    @Operation(summary = "Reactiva un paciente",
            description = "Este endpoint permite reactivar un paciente eliminado de manera lógica")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se reactivó el paciente"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el paciente"),
                    @ApiResponse(responseCode = "409", description = "Conflicto o el paciente ya se encontraba activo")
            }
    )
    @PatchMapping("/{pacienteId}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivar(
            @Parameter(description = "ID del paciente")
            @PathVariable(name = "pacienteId") Long pacienteId) throws ResourceNotFoundException, EntityAlreadyActivaException {
        pacienteService.updateActivo(pacienteId, true);
    }
    
}
