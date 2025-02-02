package com.init_coding.hackacode_3_backend.model;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "detalle_pago")
public class DetallePagoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "codigo_pago", referencedColumnName = "codigo", unique = true)
    private PagoEntity pago;

    private String estado;

    private BigDecimal monto;

    @Column(name = "fecha_inicio")
    private LocalDate fechaInicio;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;

    private double recargo;

    @Column(unique = true)
    private int nroPago;

}
