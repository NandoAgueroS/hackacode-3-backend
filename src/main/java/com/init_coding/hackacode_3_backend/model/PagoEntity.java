package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "pago")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long codigo;

    private String metodo;

    private boolean esPagado;

    private boolean activo;

    private BigDecimal total;

    @OneToOne(mappedBy = "pago")
    private ConsultaEntity consulta;

/*
    private int cantidadPagos;
    @OneToMany(mappedBy = "pago", cascade = CascadeType.ALL)
    private List<DetallePagoEntity> detallePagos;*/

}
