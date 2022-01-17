package com.sd.reticula.repository;

import com.sd.reticula.model.Anexo;
import com.sd.reticula.model.Cliente;
import com.sd.reticula.model.Tarea;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnexoRepository extends JpaRepository<Anexo, Integer> {

    public List<Anexo> findAllByTarea(Tarea tarea);

}
