package com.sd.reticula.controller;

import com.sd.reticula.model.Tarea;
import com.sd.reticula.model.Usuario;
import com.sd.reticula.service.TareasService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/tareas")
public class TareaController {

    @Autowired
    TareasService taskService;

    @GetMapping
    public List<Tarea> getAll(){
        return taskService.getAll();
    }

    @GetMapping("/{estado}")
    public Object getAllbyEstado(@PathVariable String estado){
        List<Tarea> taskList = taskService.findAllByEstado(estado);
        if (taskList != null) {
            return taskList;
        } else {
            JSONObject obje = new JSONObject();
            obje.put("res", "error");
            obje.put("data", "Parámetros incorrectos");
            return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{codigo_id}")
    public Tarea getTareaById(@PathVariable int codigo_id){
        return taskService.findById(codigo_id);
    }

    // Recive los estados y tipos en texto
    @GetMapping("/{estado}/{tipo}")
    public Object getAllbyEstadoAndTipo(@PathVariable String estado, @PathVariable String tipo){
        List<Tarea> taskList = taskService.findAllByEstadoAndTipo(estado, tipo);
        if (taskList != null) {
            return taskList;
        } else {
            JSONObject obje = new JSONObject();
            obje.put("res", "error");
            obje.put("data", "Parámetros incorrectos");
            return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("mantenimientos/cliente/{cliente_id}")
    public List<Tarea> getMantenimientosByCliente(@PathVariable int cliente_id){
        return taskService.getMantenimientosByCliente(cliente_id);
    }

    @GetMapping("asistencias/cliente/{cliente_id}")
    public List<Tarea> getAsistenciasAndTareasByCliente(@PathVariable int cliente_id){
        return taskService.getAsistenciasAndTareasByCliente(cliente_id);
    }

    @PostMapping("/insert")
    public Object saveTarea(@RequestBody Tarea objTask){
        System.out.println(objTask);
        JSONObject obje = new JSONObject();

        try {
            Tarea task = taskService.saveTask(objTask);
            if (task != null) {
                obje.put("res", "success");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            } else {
                obje.put("res", "error");
                obje.put("data", "Ocurrio un error al registrar los datos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
    
