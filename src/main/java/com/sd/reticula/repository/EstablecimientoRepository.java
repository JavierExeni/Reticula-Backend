package com.sd.reticula.repository;

import com.sd.reticula.model.Establecimiento;
import com.sd.reticula.model.Pfisica;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Integer> {

    public List<Establecimiento> findAllByCliente(Pfisica cliente);

}
