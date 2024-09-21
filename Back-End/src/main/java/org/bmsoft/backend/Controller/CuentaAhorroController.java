package org.bmsoft.backend.Controller;


import org.bmsoft.backend.entity.Cliente;
import org.bmsoft.backend.entity.CuentaAhorro;
import org.bmsoft.backend.service.ClienteService;
import org.bmsoft.backend.service.CuentaAhorroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cuentas")
@CrossOrigin(origins = "http://localhost:4200")
public class CuentaAhorroController {

    @Autowired
    private CuentaAhorroService cuentaAhorroService;

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<CuentaAhorro> getAllCuentas() {
        return cuentaAhorroService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaAhorro> getCuentaById(@PathVariable Long id) {
        Optional<CuentaAhorro> cuenta = cuentaAhorroService.findById(id);
        if (cuenta.isPresent()) {
            return ResponseEntity.ok(cuenta.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCuenta(@RequestBody CuentaAhorro cuentaAhorro) {
        // Validar existencia del cliente
        Long clienteId = cuentaAhorro.getCliente().getClienteId();
        Optional<Cliente> clienteOpt = clienteService.findById(clienteId);
        if (!clienteOpt.isPresent()) {
            return ResponseEntity.badRequest().body("El cliente no existe.");
        }

        CuentaAhorro nuevaCuenta = new CuentaAhorro();
        nuevaCuenta.setCliente(clienteOpt.get());
        nuevaCuenta.setSaldo(BigDecimal.ZERO);
        nuevaCuenta.setFechaCreacion(LocalDateTime.now());

        CuentaAhorro savedCuenta = cuentaAhorroService.save(nuevaCuenta);
        return ResponseEntity.ok(savedCuenta);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaAhorro> updateCuenta(@PathVariable Long id, @RequestBody CuentaAhorro cuentaDetails) {
        Optional<CuentaAhorro> optionalCuenta = cuentaAhorroService.findById(id);
        if (!optionalCuenta.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        CuentaAhorro cuenta = optionalCuenta.get();
        // Solo permitir actualizar ciertos campos si es necesario
        // Por ejemplo, no permitir actualizar el saldo directamente
        CuentaAhorro updatedCuenta = cuentaAhorroService.save(cuenta);
        return ResponseEntity.ok(updatedCuenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCuenta(@PathVariable Long id) {
        Optional<CuentaAhorro> cuenta = cuentaAhorroService.findById(id);
        if (!cuenta.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        cuentaAhorroService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}