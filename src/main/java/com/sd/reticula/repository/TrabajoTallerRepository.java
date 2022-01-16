package com.sd.reticula.repository;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.model.TrabajoTaller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrabajoTallerRepository extends JpaRepository<TrabajoTaller, Integer> {

    public List<TrabajoTaller> findAllByOrderByEstado();
    public List<TrabajoTaller> findAllByEstado(int estado);
    public List<TrabajoTaller> findAllByClienteOrderByEstado(Cliente cliente);
    public List<TrabajoTaller> findAllByTipo(int estado);

}
