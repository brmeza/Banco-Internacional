package org.bmsoft.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "Transacciones")
@Data
public class Transaccion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaccionId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cuenta_id", nullable = false)
    @JsonBackReference
    private CuentaAhorro cuentaAhorro;

    @Column(name = "tipo_transaccion", nullable = false, length = 20)
    private String tipoTransaccion; // 'Retiro' o 'Consignación'

    @Column(name = "monto", nullable = false, precision = 18, scale = 2)
    private BigDecimal monto;

    @Column(name = "fecha_hora", nullable = false)
    private LocalDateTime fechaHora = LocalDateTime.now();
}