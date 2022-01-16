package com.sd.reticula.controller;

import com.sd.reticula.model.Anexo;
import com.sd.reticula.model.Establecimiento;
import com.sd.reticula.service.EstablecimientoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establishmentService;

    @GetMapping
    public List<Establecimiento> getAll(){
        return establishmentService.getAll();
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable int id){
        JSONObject obje = new JSONObject();
        try {
            Establecimiento establishment = establishmentService.getById(id);
            if (establishment != null) {
                return new ResponseEntity<>(establishment, HttpStatus.OK);
            }else {
                obje.put("res", "error");
                obje.put("data", "Parámetros incorrectos");
                return new ResponseEntity<>(obje, HttpStatus.NOT_FOUND);
            }
        }catch (Exception e){
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cliente/{cliente_id}")
    public Object getAllByCliente(@PathVariable int cliente_id){
        JSONObject obje = new JSONObject();
        try {
            List<Establecimiento> establishmentList = establishmentService.getRecordsByCliente(cliente_id);
            if (establishmentList != null) {
                return new ResponseEntity<>(establishmentList, HttpStatus.OK);
            }else {
                obje.put("res", "error");
                obje.put("data", "Parámetros incorrectos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/insert")
    public Object save(@RequestBody Establecimiento establishment){
        System.out.println(establishment.toString());
        JSONObject obje = new JSONObject();
        try {
            Establecimiento newEstablishment = establishmentService.saveEstablishment(establishment);
            if (newEstablishment != null) {
                obje.put("res", "success");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            }else {
                obje.put("res", "error");
                obje.put("data", "Ocurrio un error al registrar los datos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
