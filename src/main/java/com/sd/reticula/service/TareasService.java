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

    public Tarea findById(int codigo_id){
        Tarea objTarea = tareaRepository.findById(codigo_id);
        return objTarea;
    }

    public List<Tarea> findAllByEstadoAndTipo(String state, String type) {
        if (validateStateTask(state) && validateTypeTast(type)) {
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
        switch (state) {
            case "Pendiente":
                return PENDIENTE;
            case "Analisis":
                return ANALISIS;
            case "Finalizado":
                return FINALIZADO;
            case "Entregado":
                return ENTREGADO;
            case "Retrasado":
                return RETRASADO;
            default:
                return 0;
        }
    }

    private boolean validateStateTask(String state) {
        if (state.equals("Pendiente") || state.equals("Analisis") ||
                state.equals("Finalizado") || state.equals("Entregado") || state.equals("Retrasado")) {
            return true;
        }
        return false;
    }

    private int getValueTypeTask(String task) {
        switch (task) {
            case "Asistencia":
                return this.ASISTENCIA;
            case "Mantenimiento":
                return this.MANTENIMIENTO;
            case "Tarea":
                return this.TAREA;
            default:
                return 0;
        }
    }

    private boolean validateTypeTast(String task) {
        if (task.equals("Asistencia") || task.equals("Mantenimiento") || task.equals("Tarea")) {
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
        lista.removeIf(obj -> obj.getTipo() == 2 || obj.getTipo() == 0);
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
        lista.removeIf(obj -> obj.getTipo() == MANTENIMIENTO || obj.getTipo() == TAREA);
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
