package org.bmsoft.backend.Controller;

import org.bmsoft.backend.entity.CuentaAhorro;
import org.bmsoft.backend.entity.Transaccion;
import org.bmsoft.backend.service.CuentaAhorroService;
import org.bmsoft.backend.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/api/transacciones")
public class TransaccionController {

    @Autowired
    private TransaccionService transaccionService;

    @Autowired
    private CuentaAhorroService cuentaAhorroService;

    @PostMapping
    public ResponseEntity<?> createTransaccion(@RequestBody Transaccion transaccionRequest) {
        Long cuentaId = transaccionRequest.getCuentaAhorro().getCuentaId();
        Optional<CuentaAhorro> cuentaOpt = cuentaAhorroService.findById(cuentaId);
        if (!cuentaOpt.isPresent()) {
            return ResponseEntity.badRequest().body("La cuenta no existe.");
        }

        CuentaAhorro cuenta = cuentaOpt.get();

        String tipo = transaccionRequest.getTipoTransaccion();
        BigDecimal monto = transaccionRequest.getMonto();

        if (monto.compareTo(BigDecimal.ZERO) <= 0) {
            return ResponseEntity.badRequest().body("El monto debe ser mayor que cero.");
        }

        if (tipo.equalsIgnoreCase("Retiro")) {
            if (cuenta.getSaldo().compareTo(monto) < 0) {
                return ResponseEntity.badRequest().body("Saldo insuficiente para el retiro.");
            }
            cuenta.setSaldo(cuenta.getSaldo().subtract(monto));
        } else if (tipo.equalsIgnoreCase("Consignacion")) {
            cuenta.setSaldo(cuenta.getSaldo().add(monto));
        } else {
            return ResponseEntity.badRequest().body("Tipo de transacción inválido.");
        }

        cuentaAhorroService.save(cuenta);

        Transaccion transaccion = new Transaccion();
        transaccion.setCuentaAhorro(cuenta);
        transaccion.setTipoTransaccion(tipo);
        transaccion.setMonto(monto);
        transaccion.setFechaHora(LocalDateTime.now());

        Transaccion savedTransaccion = transaccionService.save(transaccion);

        return ResponseEntity.ok(savedTransaccion);
    }

    @GetMapping("/ultima/{cuentaId}")
    public ResponseEntity<?> getUltimaTransaccion(@PathVariable Long cuentaId) {
        Optional<Transaccion> transaccionOpt = transaccionService.findLastTransactionByCuentaId(cuentaId);
        if (transaccionOpt.isPresent()) {
            return ResponseEntity.ok(transaccionOpt.get());
        } else {
            return ResponseEntity.ok("No hay transacciones para esta cuenta.");
        }
    }
}