package org.bmsoft.backend.Controller;

import org.bmsoft.backend.entity.Cliente;
import org.bmsoft.backend.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/clientes")
@CrossOrigin(origins = "http://localhost:4200") // Configura según el puerto de Angular
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAllClientes() {
        return clienteService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cliente> getClienteById(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        if (cliente.isPresent()) {
            return ResponseEntity.ok(cliente.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<?> createCliente(@RequestBody Cliente cliente) {
        Optional<Cliente> existingCliente = clienteService.findByNumeroIdentificacion(cliente.getNumeroIdentificacion());
        if (existingCliente.isPresent()) {
            return ResponseEntity.badRequest().body("El cliente con este número de identificación ya existe.");
        }
        Cliente savedCliente = clienteService.save(cliente);
        return ResponseEntity.ok(savedCliente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cliente> updateCliente(@PathVariable Long id, @RequestBody Cliente clienteDetails) {
        Optional<Cliente> optionalCliente = clienteService.findById(id);
        if (!optionalCliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        Cliente cliente = optionalCliente.get();
        cliente.setTipoIdentificacion(clienteDetails.getTipoIdentificacion());
        cliente.setNumeroIdentificacion(clienteDetails.getNumeroIdentificacion());
        cliente.setNombres(clienteDetails.getNombres());
        cliente.setApellidos(clienteDetails.getApellidos());
        cliente.setRazonSocial(clienteDetails.getRazonSocial());
        cliente.setMunicipio(clienteDetails.getMunicipio());
        Cliente updatedCliente = clienteService.save(cliente);
        return ResponseEntity.ok(updatedCliente);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCliente(@PathVariable Long id) {
        Optional<Cliente> cliente = clienteService.findById(id);
        if (!cliente.isPresent()) {
            return ResponseEntity.notFound().build();
        }
        clienteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}