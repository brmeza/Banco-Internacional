package org.bmsoft.backend.repository;

import org.bmsoft.backend.entity.Transaccion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TransaccionRep extends JpaRepository<Transaccion, Long> {
    List<Transaccion> findTop1ByCuentaAhorro_CuentaIdOrderByFechaHoraDesc(Long cuentaId);
}
