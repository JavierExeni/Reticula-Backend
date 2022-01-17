package com.sd.reticula.repository;

import com.sd.reticula.model.Carpeta;
import com.sd.reticula.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarpetaRepository extends JpaRepository<Carpeta, Integer> {
    public Carpeta findByCliente(Cliente cliente);
}
