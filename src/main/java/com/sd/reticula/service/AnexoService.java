package com.sd.reticula.service;

import com.sd.reticula.model.Anexo;
import com.sd.reticula.model.Tarea;
import com.sd.reticula.repository.AnexoRepository;
import com.sd.reticula.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnexoService {

    @Autowired
    AnexoRepository annexedRepository;

    @Autowired
    TareaRepository taskRepository;

    public List<Anexo> getAll(){
        return annexedRepository.findAll();
    }

    public List<Anexo> getByTaskId(int taskId){
        Tarea task = taskRepository.findById(taskId);
        if (task != null) {
            return annexedRepository.findAllByTarea(task);
        }
        return null;
    }

    public Anexo saveAnnexed(Anexo objAnnexed) throws Exception {
        if (validateAnnexed(objAnnexed)) {
            return annexedRepository.saveAndFlush(objAnnexed);
        }
        return null;
    }

    public boolean validateAnnexed(Anexo objAnnexed) {
        return objAnnexed != null && !objAnnexed.getTitulo().equals("") && objAnnexed.getCarpeta() != null;
    }
}
