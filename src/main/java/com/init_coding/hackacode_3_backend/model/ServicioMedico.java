package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public abstract class ServicioMedico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private BigDecimal precio;

    private String nombre;

    @Column(name = "tipo_servicio")

    private String tipoServicio;

    @PrePersist
    @PreUpdate
    private void setTipoServicio(){
        if (this instanceof ServicioIndividualEntity) tipoServicio = "INDIVIDUAL";
        else if (this instanceof PaqueteServiciosEntity) tipoServicio = "PAQUETE";
    }

}
