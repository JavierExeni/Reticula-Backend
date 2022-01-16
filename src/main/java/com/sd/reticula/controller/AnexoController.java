package com.sd.reticula.controller;

import com.sd.reticula.model.Anexo;
import com.sd.reticula.service.AnexoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/anexos")
public class AnexoController {

    @Autowired
    AnexoService annexedService;

    @GetMapping
    public List<Anexo> getAll(){
        return annexedService.getAll();
    }

    @PostMapping("/insert")
    public Object saveAnexo(@RequestBody Anexo objAnnexed){
        System.out.println(objAnnexed.toString());
        JSONObject obje = new JSONObject();
        try {
            Anexo newAnnexed = annexedService.saveAnnexed(objAnnexed);
            if (newAnnexed != null) {
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
