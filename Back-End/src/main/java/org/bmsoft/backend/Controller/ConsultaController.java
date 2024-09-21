package org.bmsoft.backend.Controller;

import org.bmsoft.backend.entity.CuentaAhorro;
import org.bmsoft.backend.entity.Transaccion;
import org.bmsoft.backend.service.CuentaAhorroService;
import org.bmsoft.backend.service.TransaccionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.Optional;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "http://localhost:4200")
public class ConsultaController {

    @Autowired
    private CuentaAhorroService cuentaAhorroService;

    @Autowired
    private TransaccionService transaccionService;

    @GetMapping("/saldo/{cuentaId}")
    public ResponseEntity<?> consultarSaldo(@PathVariable Long cuentaId) {
        Optional<CuentaAhorro> cuentaOpt = cuentaAhorroService.findById(cuentaId);
        if (!cuentaOpt.isPresent()) {
            return ResponseEntity.badRequest().body("La cuenta no existe.");
        }

        CuentaAhorro cuenta = cuentaOpt.get();
        Optional<Transaccion> ultimaTransaccion = transaccionService.findLastTransactionByCuentaId(cuentaId);

        SaldoResponse saldoResponse = new SaldoResponse();
        saldoResponse.setCuentaId(cuenta.getCuentaId());
        saldoResponse.setSaldo(cuenta.getSaldo());
        saldoResponse.setUltimaTransaccion(ultimaTransaccion.isPresent() ?
                ultimaTransaccion.get().getTipoTransaccion() + " de " + ultimaTransaccion.get().getMonto() + " en " + ultimaTransaccion.get().getFechaHora()
                : "No hay transacciones.");

        return ResponseEntity.ok(saldoResponse);
    }

    // Clase interna para la respuesta
    public static class SaldoResponse {
        private Long cuentaId;
        private BigDecimal saldo;
        private String ultimaTransaccion;

        // Getters y Setters
        public Long getCuentaId() {
            return cuentaId;
        }

        public void setCuentaId(Long cuentaId) {
            this.cuentaId = cuentaId;
        }

        public BigDecimal getSaldo() {
            return saldo;
        }

        public void setSaldo(BigDecimal saldo) {
            this.saldo = saldo;
        }

        public String getUltimaTransaccion() {
            return ultimaTransaccion;
        }

        public void setUltimaTransaccion(String ultimaTransaccion) {
            this.ultimaTransaccion = ultimaTransaccion;
        }
    }
}

