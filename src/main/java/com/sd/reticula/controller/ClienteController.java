package com.sd.reticula.controller;

import com.sd.reticula.model.Cliente;
import com.sd.reticula.service.ClienteService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping
    public List<Cliente> getAll(){
        return clienteService.getAll();
    }

    @PostMapping("/insert")
    public Object saveClient(@RequestBody Cliente objCliente){
        System.out.println(objCliente.toString());
        JSONObject obje = new JSONObject();
        try {
            Cliente newClient = clienteService.saveClient(objCliente);
            if (newClient != null) {
                obje.put("res", "success");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            } else {
                obje.put("res", "error");
                obje.put("data", "Ocurrio un error al registrar los datos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex){
            ex.printStackTrace();
            obje.put("res", "error");
            obje.put("data", ex.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
