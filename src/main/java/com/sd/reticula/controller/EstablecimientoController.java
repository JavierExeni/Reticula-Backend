package com.sd.reticula.controller;

import com.sd.reticula.model.Establecimiento;
import com.sd.reticula.model.Pfisica;
import com.sd.reticula.service.EstablecimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/establecimientos")
public class EstablecimientoController {

    @Autowired
    private EstablecimientoService establecimientoService;

    @GetMapping
    public List<Establecimiento> getAll(){
        return establecimientoService.getAll();
    }

    @GetMapping("/{cliente_id}")
    public List<Establecimiento> getAllByCliente(@PathVariable int cliente_id){
        return establecimientoService.getRecordsByCliente(cliente_id);
    }

    @PostMapping("/insert")
    public void save(@RequestBody Establecimiento objEstablecimiento){
        try {
            System.out.println(objEstablecimiento.toString());
            establecimientoService.saveEstablecimiento(objEstablecimiento);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
