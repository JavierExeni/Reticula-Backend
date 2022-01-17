package com.sd.reticula.service;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.model.Tarea;
import com.sd.reticula.repository.ClienteRepository;
import com.sd.reticula.repository.TareaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Transient;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TareasService {

    // type task
    @Transient
    public final int ASISTENCIA = 0;
    @Transient
    public final int MANTENIMIENTO = 1;
    @Transient
    public final int TAREA = 2;

    // State task
    @Transient
    public final int PENDIENTE = 0;
    @Transient
    public final int ANALISIS = 1;
    @Transient
    public final int FINALIZADO = 2;
    @Transient
    public final int ENTREGADO = 3;
    @Transient
    public final int RETRASADO = -1;


    @Autowired
    TareaRepository tareaRepository;

    @Autowired
    ClienteRepository clienteRepository;

    public List<Tarea> getAll() {
        List<Tarea> lista = tareaRepository.findAllByOrderByEstado();
        lista.removeIf(objTarea -> objTarea.getEstado() == ANALISIS);
        return lista;
    }

    public List<Tarea> findAllByEstado(String state) {
        if (validateStateTask(state)) {
            int valueState = getValueStateTask(state);
            return tareaRepository.findAllByEstado(valueState);
        } else {
            return null;
        }
    }

    public List<Tarea> findAllByType(String state) {
        if (validateTypeTask(state)) {
            int valueState = getValueTypeTask(state);
            return tareaRepository.findAllByTipo(valueState);
        } else {
            return null;
        }
    }

    public Tarea findById(int codigo_id){ return tareaRepository.findById(codigo_id); }

    public List<Tarea> findAllByEstadoAndTipo(String state, String type) {
        if (validateStateTask(state) && validateTypeTask(type)) {
            List<Tarea> lista = findAllByEstado(state);
            if (lista != null) {
                List<Tarea> listaFiltrada = lista.stream().filter(
                        objTarea -> objTarea.getTipo() == getValueTypeTask(type)).collect(Collectors.toList()
                );
                return listaFiltrada;
            }
        }
        return null;
    }

    private int getValueStateTask(String state) {
        state = state.toLowerCase();
        switch (state) {
            case "pendiente":
                return PENDIENTE;
            case "analisis":
                return ANALISIS;
            case "finalizado":
                return FINALIZADO;
            case "entregado":
                return ENTREGADO;
            case "retrasado":
                return RETRASADO;
            default:
                return 0;
        }
    }

    private boolean validateStateTask(String state) {
        state = state.toLowerCase();
        if (state.equals("pendiente") || state.equals("analisis") ||
                state.equals("finalizado") || state.equals("entregado") ||
                state.equals("retrasado")) {
            return true;
        }
        return false;
    }

    private int getValueTypeTask(String type) {
        type = type.toLowerCase();
        switch (type) {
            case "asistencia":
                return this.ASISTENCIA;
            case "mantenimiento":
                return this.MANTENIMIENTO;
            case "tarea":
                return this.TAREA;
            default:
                return 0;
        }
    }

    private boolean validateTypeTask(String type) {
        type = type.toLowerCase();
        if (type.equals("asistencia") || type.equals("mantenimiento")
                || type.equals("tarea")) {
            return true;
        }
        return false;
    }

    @Transactional
    public Tarea saveTask(Tarea objTarea) {
        // Al parecer inserta y actualiza los datos
        try {
            if (objTarea.getId() == 0) {
                objTarea.setFecha_registro(Calendar.getInstance().getTime());
                if (this.getTypeAssistanceTask(objTarea.getTipo())) {
                    objTarea.setFecha_limite(Calendar.getInstance().getTime());
                    objTarea.setEstado(this.ANALISIS);
                }
            } else {
                objTarea.setFecha_proceso(Calendar.getInstance().getTime());
            }
            return tareaRepository.saveAndFlush(objTarea);
        } catch (Exception ex){
            ex.printStackTrace();
            return null;
        }
    }

    @Transactional
    public Boolean deleteTask(int id) {
        Tarea task = findById(id);
        if (task != null) {
            tareaRepository.delete(task);
            return true;
        }
        return false;
    }

    public List<Tarea> getMantenimientosByCliente(int cliente_id) {
        Cliente objAux = null;
        List<Cliente> listaClientes = clienteRepository.findAll();
        for (Cliente obj : listaClientes) {
            if (obj.getId() == cliente_id) {
                objAux = obj;
                break;
            }
        }
        List<Tarea> lista = tareaRepository.findAllByCliente(objAux);
        lista.removeIf(obj -> obj.getTipo() == ASISTENCIA || obj.getTipo() == TAREA);
        return lista;
    }

    public List<Tarea> getAsistenciasAndTareasByCliente(int cliente_id) {
        Cliente objAux = null;
        List<Cliente> listaClientes = clienteRepository.findAll();
        for (Cliente obj : listaClientes) {
            if (obj.getId() == cliente_id) {
                objAux = obj;
                break;
            }
        }
        List<Tarea> lista = tareaRepository.findAllByCliente(objAux);
        lista.removeIf(obj -> obj.getTipo() == MANTENIMIENTO);
        return lista;
    }

    public boolean getTypeAssistanceTask(int type) {
        return type == this.ASISTENCIA;
    }

    public boolean getTypeMaintenanceTask(int type) {
        return type == this.MANTENIMIENTO;
    }

    public boolean getTypeTask(int type) {
        return type == this.TAREA;
    }
}
