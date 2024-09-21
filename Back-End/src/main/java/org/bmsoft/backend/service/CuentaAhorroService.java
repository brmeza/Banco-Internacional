package org.bmsoft.backend.service;

import org.bmsoft.backend.entity.CuentaAhorro;
import org.bmsoft.backend.repository.CuentaAhorroRep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaAhorroService {

    @Autowired
    private CuentaAhorroRep cuentaAhorroRepository;

    public List<CuentaAhorro> findAll() {
        return cuentaAhorroRepository.findAll();
    }

    public Optional<CuentaAhorro> findById(Long id) {
        return cuentaAhorroRepository.findById(id);
    }

    public CuentaAhorro save(CuentaAhorro cuentaAhorro) {
        return cuentaAhorroRepository.save(cuentaAhorro);
    }

    public void deleteById(Long id) {
        cuentaAhorroRepository.deleteById(id);
    }
}