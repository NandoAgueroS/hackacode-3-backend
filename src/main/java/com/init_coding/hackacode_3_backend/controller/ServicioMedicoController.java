package com.init_coding.hackacode_3_backend.controller;

import com.init_coding.hackacode_3_backend.dto.request.PaqueteServiciosRequest;
import com.init_coding.hackacode_3_backend.dto.request.ServicioIndividualRequest;
import com.init_coding.hackacode_3_backend.dto.response.PaqueteServiciosResponse;
import com.init_coding.hackacode_3_backend.dto.response.ServicioIndividualResponse;
import com.init_coding.hackacode_3_backend.exception.InvalidServicioException;
import com.init_coding.hackacode_3_backend.exception.ResourceNotFoundException;
import com.init_coding.hackacode_3_backend.service.IPaqueteServiciosService;
import com.init_coding.hackacode_3_backend.service.IServicioIndividualService;
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
@RequestMapping("/api/servicios")
@Tag(name = "Servicios Médicos", description = "Operaciones con servicios medicos individuales y paquetes")
public class ServicioMedicoController {

    @Autowired
    IServicioIndividualService servicioIndividualService;
    
    @Autowired
    IPaqueteServiciosService paqueteServiciosService;

    @Operation(summary = "Obtiene todas los servicios individuales",
            description = "Este endpoint permite obtener los detalles de todos los servicios individuales")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de servicios individuales")
    )
    @GetMapping("/individuales")
    public ResponseEntity<List<ServicioIndividualResponse>> getAllServiciosIndividuales(){
        return ResponseEntity.ok(servicioIndividualService.findAll());
    }

    @Operation(summary = "Obtiene un servicio individual por su ID",
            description = "Este endpoint permite obtener los detalles de un servicio individual")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo el servicio individual"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el servicio individual")
            }
    )
    @GetMapping("/individuales/{servicioIndividualId}")
    public ResponseEntity<ServicioIndividualResponse> findOneServicioIndividuale(
            @Parameter(description = "ID de el servicio individual", required = true)
            @PathVariable(name = "servicioIndividualId") Long servicioIndividualId) throws ResourceNotFoundException {
        return ResponseEntity.ok(servicioIndividualService.findById(servicioIndividualId));
    }

    @Operation(summary = "Crea un nuevo servicio individual",
            description = "Este endpoint permite guardar un servicio individual")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Se creó el servicio individual")
            }
    )
    @PostMapping("/individuales")
    public ResponseEntity<ServicioIndividualResponse> createServicioIndividuale(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del servicio individual a crear",
                    required = true)
            @RequestBody ServicioIndividualRequest servicioIndividual){
        return ResponseEntity.status(HttpStatus.CREATED).body(servicioIndividualService.create(servicioIndividual));
    }

    @Operation(summary = "Modifica un servicio individual",
            description = "Este endpoint permite modificar un servicio individual existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se modificó el servicio individual"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el servicio individual")
            }
    )
    @PutMapping("/individuales/{servicioIndividualId}")
    public ResponseEntity<ServicioIndividualResponse> updateServicioIndividuale(
            @Parameter(description = "ID del servicio individual")
            @PathVariable(name = "servicioIndividualId") Long servicioIndividualId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del servicio individual a modificar",
                    required = true)
            @RequestBody ServicioIndividualRequest servicioIndividual) throws  ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(servicioIndividualService.update(servicioIndividualId, servicioIndividual));
    }

    @Operation(summary = "Elimina un servicio individual",
            description = "Este endpoint permite eliminar un servicio individual existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Se eliminó el servicio individual"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el servicio individual")
            }
    )
    @DeleteMapping("/individuales/{servicioIndividualId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteServicioIndividuale(
            @Parameter(description = "ID del servicio individual")
            @PathVariable(name = "servicioIndividualId") Long servicioIndividualId) throws ResourceNotFoundException{
        servicioIndividualService.delete(servicioIndividualId);
    }

    @Operation(summary = "Obtiene todos los paquetes de servicios registrados",
            description = "Este endpoint permite obtener los detalles de todos los paquetes de servicios")
    @ApiResponses(
            @ApiResponse(responseCode = "200", description = "Se obtuvo la lista de paquetes de servicios")
    )
    @GetMapping("/paquetes")
    public ResponseEntity<List<PaqueteServiciosResponse>> getAllPaquetesServicios(){
        return ResponseEntity.ok(paqueteServiciosService.findAll());
    }


    @Operation(summary = "Obtiene un paquete de servicios por su ID",
            description = "Este endpoint permite obtener los detalles un paquete de servicios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se obtuvo el paquete de servicios"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el paquete de servicios")
            }
    )
    @GetMapping("/paquetes/{paqueteServiciosId}")
    public ResponseEntity<PaqueteServiciosResponse> findOnePaqueteServicios(
            @Parameter(description = "ID del paquete de servicios", required = true)
            @PathVariable(name = "paqueteServiciosId") Long paqueteServiciosId) throws ResourceNotFoundException {
        return ResponseEntity.ok(paqueteServiciosService.findById(paqueteServiciosId));
    }

    @Operation(summary = "Crea un nuevo paquete de servicios",
            description = "Este endpoint permite guardar un paquete de servicios")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Se creó el paquete de servicios"),
                    @ApiResponse(responseCode = "400", description = "Peticion mal formada o servicio individual inválido")
            }
    )
    @PostMapping("/paquetes")
    public ResponseEntity<PaqueteServiciosResponse> createPaqueteServicios(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del paquete de servicios a crear",
                    required = true)
            @RequestBody PaqueteServiciosRequest paqueteServicios) throws InvalidServicioException {
        return ResponseEntity.status(HttpStatus.CREATED).body(paqueteServiciosService.create(paqueteServicios));
    }

    @Operation(summary = "Modifica un paquete de servicios",
            description = "Este endpoint permite modificar un paquete de servicios existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Se modificó el paquete de servicios"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el paquete de servicios"),
                    @ApiResponse(responseCode = "400", description = "Peticion mal formada o servicio individual inválido")
            }
    )
    @PutMapping("/paquetes/{paqueteServiciosId}")
    public ResponseEntity<PaqueteServiciosResponse> updatePaqueteServicios(
            @Parameter(description = "ID del paquete de servicios")
            @PathVariable(name = "paqueteServiciosId") Long paqueteServiciosId,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos del paquete de servicios a modificar",
                    required = true)
            @RequestBody PaqueteServiciosRequest paqueteServicios) throws InvalidServicioException, ResourceNotFoundException{
        return ResponseEntity.status(HttpStatus.OK).body(paqueteServiciosService.update(paqueteServiciosId, paqueteServicios));
    }

    @Operation(summary = "Elimina un paquete de servicios",
            description = "Este endpoint permite eliminar un paquete de servicios existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Se eliminó el paquete de servicios"),
                    @ApiResponse(responseCode = "404", description = "No se encontró el paquete de servicios")
            }
    )
    @DeleteMapping("/paquetes/{paqueteServiciosId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePaqueteServicios(
            @Parameter(description = "ID del paquete de servicios")
            @PathVariable(name = "paqueteServiciosId") Long paqueteServiciosId) throws ResourceNotFoundException{
        paqueteServiciosService.delete(paqueteServiciosId);
    }
    
}
