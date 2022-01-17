package com.sd.reticula.controller;

import com.sd.reticula.model.Carpeta;
import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.service.CarpetaService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/cliente/{id}")
    public Object getById(@PathVariable int id){
        JSONObject obje = new JSONObject();
        Carpeta folder = carpetaService.getByClient(id);
        if (folder != null) {
            obje.put("res", "success");
            obje.put("data", folder);
            return new ResponseEntity<>(obje, HttpStatus.OK);
        } else {
            obje.put("res", "error");
            obje.put("data", "No existe una carpeta asociada al cliente");
            return new ResponseEntity<>(obje, HttpStatus.OK);
        }
    }

    @PostMapping("/insert")
    public Object crearCarpeta(@RequestBody Carpeta objCarpeta){
        System.out.println(objCarpeta.toString());
        JSONObject obje = new JSONObject();
        try {
            Carpeta newFolder = carpetaService.createCarpeta(objCarpeta);
            if (newFolder != null) {
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
