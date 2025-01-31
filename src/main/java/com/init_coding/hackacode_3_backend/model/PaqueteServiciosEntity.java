package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "paquete_servicios")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PaqueteServiciosEntity extends ServicioMedico{
    @ManyToMany
    @JoinTable(name = "servicios_del_paquete",
            joinColumns = @JoinColumn(name = "paquete_id"),
            inverseJoinColumns = @JoinColumn(name = "servicio_individual_id"),
            uniqueConstraints = @UniqueConstraint(columnNames = {"paquete_id", "servicio_individual_id"}))
    private List<ServicioIndividualEntity> servicios;
}
