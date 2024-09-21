package org.bmsoft.backend.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.List;

@Entity
@Table(name = "Clientes")
@Data
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clienteId;

    @Column(name = "tipo_identificacion", nullable = false, length = 50)
    private String tipoIdentificacion;

    @Column(name = "numero_identificacion", nullable = false, unique = true, length = 20)
    @NotBlank(message = "El número de identificación es obligatorio.")
    @Size(max = 20, message = "El número de identificación no debe exceder 20 caracteres.")
    private String numeroIdentificacion;

    @Column(name = "nombres", length = 100)
    private String nombres;

    @Column(name = "apellidos", length = 100)
    private String apellidos;

    @Column(name = "razon_social", length = 200)
    private String razonSocial;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CuentaAhorro> cuentasAhorro;
}