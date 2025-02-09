package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.dto.request.ConsultaRequest;
import com.init_coding.hackacode_3_backend.dto.response.ConsultaResponse;
import com.init_coding.hackacode_3_backend.exception.InvalidArgumentException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.service.IConsultaService;
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
@RequestMapping("/api/consultas")
@Tag(name = "Consultas", description = "Operaciones realizadas con consultas")
public class ConsultaController {

    @Autowired
    private IConsultaService consultaService;

    @Operation(summary = "Obtiene todas las consultas",
            description = "Este endpoint permite obtener los detalles de todas las consultas")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de consultas")
    )
    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> getAll(){
        return ResponseEntity.ok(consultaService.findAll());
    }

    @Operation(summary = "Obtiene una consulta por su ID",
            description = "Este endpoint permite obtener los detalles de una consulta")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo la consulta"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la consulta")
            }
    )
    @GetMapping("/{consultaId}")
    public ResponseEntity<ConsultaResponse> findOne(
            @Parameter(description = "ID de la consulta", required = true)
            @PathVariable(name = "consultaId") Long consultaId) throws ResourceNotFoundException {
        return ResponseEntity.ok(consultaService.findById(consultaId));
    }

    @Operation(summary = "Crea una nueva consulta",
            description = "Este endpoint permite guardar una consulta")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Se creó la consulta"),
                    @ApiResponse(responseCode = "400", description = "Petición mal formada o medico/paciente/servicio inválido")
            }
    )
    @PostMapping
    public ResponseEntity<ConsultaResponse> create(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la consulta a crear",
                    required = true)
            @RequestBody ConsultaRequest consulta) throws InvalidArgumentException {
        return ResponseEntity.status(HttpStatus.CREATED).body(consultaService.create(consulta));
    }

    @Operation(summary = "Modifica una consulta",
            description = "Este endpoint permite modificar una consulta existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se modificó la consulta"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la consulta"),
                    @ApiResponse(responseCode = "400", description = "Petición mal formada o medico/paciente/servicio inválido")
            }
    )
    @PutMapping("/{consultaId}")
    public ResponseEntity<ConsultaResponse> update(
            @Parameter(description = "ID de la consulta")
            @PathVariable(name = "consultaId") Long consultaId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos de la consulta a modificar",
                    required = true)
            @RequestBody ConsultaRequest consulta) throws  ResourceNotFoundException, InvalidArgumentException{
        return ResponseEntity.status(HttpStatus.OK).body(consultaService.update(consultaId, consulta));
    }

    @Operation(summary = "Elimina una consulta",
            description = "Este endpoint permite eliminar una consulta existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Se eliminó la consulta"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la consulta")
            }
    )
    @DeleteMapping("/{consultaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @Parameter(description = "ID de la consulta")
            @PathVariable(name = "consultaId") Long consultaId) throws ResourceNotFoundException{
        consultaService.delete(consultaId);
    }


}
