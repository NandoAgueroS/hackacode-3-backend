package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.dto.request.ConsultaRequest;
import com.init_coding.hackacode_3_backend.dto.response.ConsultaResponse;
import com.init_coding.hackacode_3_backend.dto.response.ConsultasMesResponse;
import com.init_coding.hackacode_3_backend.exception.EntityAlreadyActivaException;
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
            description = "Este endpoint permite obtener los detalles de todas las consultas, con la posibilidad de filtrar por paciente o médico")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de consultas"),
                @ApiResponse(responseCode = "400", description = "Petición mal formada o paciente/medico inválido")
            }
    )
    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> getAll(
            @Parameter(description = "ID del paciente")
            @RequestParam(name = "pacienteId", required = false) Long pacienteId,
            @Parameter(description = "ID del médico")
            @RequestParam(name = "medicoId", required = false) Long medicoId) throws InvalidArgumentException{
        if (pacienteId != null && medicoId == null)
            return ResponseEntity.ok(consultaService.findAllByPaciente(pacienteId));
        else if (medicoId != null && pacienteId == null)
            return ResponseEntity.ok(consultaService.findAllByMedico(medicoId));
        else
            return ResponseEntity.ok(consultaService.findAll());
    }

    @Operation(summary = "Obtiene las consultas de un mes y año, y su cantidad",
            description = "Este endpoint permite obtener las consultas y su cantidad en un determinado mes y año")
    @ApiResponses(
            value = {
                @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de consultas"),
                @ApiResponse(responseCode = "400", description = "Petición mal formada o mes inválido")
            }
    )

    @GetMapping("/cantidad")
    public ResponseEntity<ConsultasMesResponse> getCantidadYConsultas(
            @Parameter(description = "Año de las consultas", required = true) @RequestParam(name = "anio") Integer anio,
            @Parameter(description = "Mes de las consultas", required = true) @RequestParam(name = "mes") Integer mes) throws InvalidArgumentException{

        return ResponseEntity.ok(consultaService.findAllByMesAndAnio(mes, anio));
    }


    @Operation(summary = "Obtiene todas las consultas inactivas",
            description = "Este endpoint permite obtener los detalles de todas las consultas inactivas")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de consultas inactivas")
    )
    @GetMapping("/inactivos")
    public ResponseEntity<List<ConsultaResponse>> getAllInactivas(){
        return ResponseEntity.ok(consultaService.findAllInactivas());
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
            description = "Este endpoint permite eliminar una consulta existente de manera lógica")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Se eliminó la consulta"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la consulta")
            }
    )
    @DeleteMapping("/{consultaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void desactivar(
            @Parameter(description = "ID de la consulta")
            @PathVariable(name = "consultaId") Long consultaId) throws ResourceNotFoundException, EntityAlreadyActivaException{
        consultaService.updateActivo(consultaId, false);
    }

    @Operation(summary = "Reactiva una consulta",
            description = "Este endpoint permite reactivar una consulta eliminada de manera lógica")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se reactivó la consulta"),
                    @ApiResponse(responseCode = "404", description = "No se encontró la consulta"),
                    @ApiResponse(responseCode = "409", description = "Conflicto o la consulta ya se encontraba activa")
            }
    )
    @PatchMapping("/{consultaId}/reactivar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void reactivar(
            @Parameter(description = "ID de la consulta")
            @PathVariable(name = "consultaId") Long consultaId) throws ResourceNotFoundException, EntityAlreadyActivaException {
        consultaService.updateActivo(consultaId, true);
    }


}
