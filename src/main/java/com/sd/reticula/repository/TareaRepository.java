package com.sd.reticula.repository;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TareaRepository extends JpaRepository<Tarea, Integer> {

    public List<Tarea> findAllByOrderByEstado();
    public List<Tarea> findAllByEstado(int estado);
    public List<Tarea> findAllByCliente(Cliente cliente);
    public Tarea findById(int codigo_id);
}
