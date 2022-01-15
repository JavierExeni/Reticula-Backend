package com.sd.reticula.controller;

import com.sd.reticula.model.Pfisica;
import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.service.TrabajoTallerService;
import org.hibernate.tool.schema.internal.exec.ScriptTargetOutputToUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Calendar;
import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/taller")
public class TrabajoTallerController {

    @Autowired
    TrabajoTallerService trabajoTallerService;

    @GetMapping
    public List<TrabajoTaller> getAll(){
        return trabajoTallerService.getAll();
    }

    @GetMapping("/{estado}")
    public List<TrabajoTaller> getAllByEstado(@PathVariable int estado){
        return trabajoTallerService.getAllByEstado(estado);
    }

//    @GetMapping("/{trabajo_id}")
//    public List<TrabajoTaller> getAllById(@PathVariable int trabajo_id){
//        return trabajoTallerService.getAllByEstado(trabajo_id);
//    }

    @GetMapping("cliente/{cliente_id}")
    public List<TrabajoTaller> getAllByCliente(@PathVariable int cliente_id){
        return trabajoTallerService.getAllByCliente(cliente_id);
    }

    @PostMapping("/insert")
    public void saveTrabajoTaller(@RequestBody TrabajoTaller obj){
        try {
            trabajoTallerService.saveTrabajoTaller(obj);
            System.out.println("Se guardo!!!");
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @PostMapping("/uploadImage")
    public String uploadImage(@RequestParam("imageFile") MultipartFile imageFile){
        String returnValue = "";


        return returnValue;
    }

}
