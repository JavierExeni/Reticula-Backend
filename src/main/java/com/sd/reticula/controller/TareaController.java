package com.sd.reticula.controller;

import com.sd.reticula.model.Tarea;
import com.sd.reticula.service.TareasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    TareasService tareasService;

    @GetMapping
    public List<Tarea> getAll(){
        return tareasService.getAll();
    }

    @GetMapping("/{estado}")
    public List<Tarea> getAllbyEstado(@PathVariable int estado){
        return  tareasService.findAllByEstado(estado);
    }

    @GetMapping("id/{codigo_id}")
    public Tarea getTareaById(@PathVariable int codigo_id){
        return tareasService.findById(codigo_id);
    }

    @GetMapping("/{estado}/{tipo}")
    public List<Tarea> getAllbyEstadoAndTipo(@PathVariable int estado, @PathVariable String tipo){
        return tareasService.findAllByEstadoAndTipo(estado, tipo);
    }

    @GetMapping("mantenimientos/cliente/{cliente_id}")
    public List<Tarea> getMantenimientosByCliente(@PathVariable int cliente_id){
        return tareasService.getMantenimientosByCliente(cliente_id);
    }

    @GetMapping("asistencias/cliente/{cliente_id}")
    public List<Tarea> getAsistenciasAndTareasByCliente(@PathVariable int cliente_id){
        return tareasService.getAsistenciasAndTareasByCliente(cliente_id);
    }

    @PostMapping("/insert")
    public void saveTarea(@RequestBody Tarea objTarea){
        try {
            System.out.println(objTarea);
            tareasService.saveTarea(objTarea);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
    
