package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "servicio_individual")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ServicioIndividualEntity extends ServicioMedico{

    private String descripcion;

}
