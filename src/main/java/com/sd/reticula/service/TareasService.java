package com.sd.reticula.service;

import com.sd.reticula.model.Pfisica;
import com.sd.reticula.model.Tarea;
import com.sd.reticula.repository.PfisicaRepository;
import com.sd.reticula.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class TareasService {

    @Autowired
    TareaRepository tareaRepository;

    @Autowired
    PfisicaRepository pfisicaRepository;

    public List<Tarea> getAll() {
        List<Tarea> lista = tareaRepository.findAllByOrderByEstado();
        lista.removeIf(objTarea -> objTarea.getEstado() == 1);
        return lista;
    }

    public List<Tarea> findAllByEstado(int estado) {
        return tareaRepository.findAllByEstado(estado);
    }

    public Tarea findById(int codigo_id){
        Tarea objTarea = tareaRepository.findByCodigoId(codigo_id);
        if(objTarea != null){
            return objTarea;
        } else{
            new Exception("el id que estsa buscando no existe");
        }
        return null;
    }

    public List<Tarea> findAllByEstadoAndTipo(int estado, String tipo) {
        List<Tarea> lista = findAllByEstado(estado);
        List<Tarea> listaFiltrada = lista.stream().filter(
                objTarea -> objTarea.getTipo().equals(tipo)).collect(Collectors.toList()
        );
        return listaFiltrada;
    }

    public void saveTarea(Tarea objTarea) throws Exception {
        try {
            if (objTarea.getCodigoId() == 0) {
                objTarea.setFecha_registro(Calendar.getInstance().getTime());
                if (objTarea.getTipo().equals("Asistencia")) {
                    objTarea.setFecha_limite(Calendar.getInstance().getTime().toString());
                    objTarea.setEstado(1);
                }
            } else {
                objTarea.setFecha_proceso(Calendar.getInstance().getTime());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tareaRepository.saveAndFlush(objTarea);
    }

    public List<Tarea> getMantenimientosByCliente(int cliente_id) {
        Pfisica objAux = null;
        List<Pfisica> listaClientes = pfisicaRepository.findAll();
        for (Pfisica obj : listaClientes) {
            if (obj.getLpersona_id() == cliente_id) {
                objAux = obj;
                break;
            }
        }
        List<Tarea> lista = tareaRepository.findAllByPfisica(objAux);
        lista.removeIf(obj -> obj.getTipo().equals("Tarea") || obj.getTipo().equals("Asistencia"));
        return lista;
    }

    public List<Tarea> getAsistenciasAndTareasByCliente(int cliente_id) {
        Pfisica objAux = null;
        List<Pfisica> listaClientes = pfisicaRepository.findAll();
        for (Pfisica obj : listaClientes) {
            if (obj.getLpersona_id() == cliente_id) {
                objAux = obj;
                break;
            }
        }
        List<Tarea> lista = tareaRepository.findAllByPfisica(objAux);
        lista.removeIf(obj -> obj.getTipo().equals("Mantenimiento"));
        return lista;
    }
}
