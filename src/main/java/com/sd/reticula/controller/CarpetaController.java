package com.sd.reticula.controller;

import com.sd.reticula.model.Carpeta;
import com.sd.reticula.service.CarpetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/carpetas")
public class CarpetaController {

    @Autowired
    private CarpetaService carpetaService;

    @GetMapping
    public List<Carpeta> getAll(){
        return carpetaService.getAll();
    }

    @PostMapping("/insert")
    public void crearCarpeta(@RequestBody Carpeta objCarpeta){
        try {
            System.out.println(objCarpeta);
            carpetaService.createCarpeta(objCarpeta);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
