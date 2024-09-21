package org.bmsoft.backend.service;

import org.bmsoft.backend.entity.Transaccion;
import org.bmsoft.backend.repository.TransaccionRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransaccionService {

    @Autowired
    private TransaccionRep transaccionRepository;

    public List<Transaccion> findAll() {
        return transaccionRepository.findAll();
    }

    public Optional<Transaccion> findById(Long id) {
        return transaccionRepository.findById(id);
    }

    public Transaccion save(Transaccion transaccion) {
        return transaccionRepository.save(transaccion);
    }

    public void deleteById(Long id) {
        transaccionRepository.deleteById(id);
    }

    public Optional<Transaccion> findLastTransactionByCuentaId(Long cuentaId) {
        List<Transaccion> transacciones = transaccionRepository.findTop1ByCuentaAhorro_CuentaIdOrderByFechaHoraDesc(cuentaId);
        if (transacciones.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(transacciones.get(0));
    }
}
