package com.sd.reticula.controller;

import com.sd.reticula.model.Usuario;
import com.sd.reticula.service.UsuarioService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.minidev.json.JSONObject;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @GetMapping
    public List<Usuario> getAll(){
        return usuarioService.getAll();
    }

    @GetMapping("/{id}")
    public Object getById(@PathVariable int id) throws NotFoundException {
        JSONObject obje = new JSONObject();
        Optional<Usuario> objUser = usuarioService.getUserById(id);
        if (objUser.isPresent()) {
            Usuario user = objUser.get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        }else {
            obje.put("res", "error");
            obje.put("data", "No se encontró el registro");
            return new ResponseEntity<>(obje, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/insert")
    public Object saveUser(@RequestBody Usuario objUsuario){
        System.out.println(objUsuario.toString());
        JSONObject obje = new JSONObject();
        try {
            Usuario newUser = usuarioService.saveUser(objUsuario);
            if (newUser != null) {
                obje.put("res", "success");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            }else {
                obje.put("res", "error");
                obje.put("data", "Credenciales incorrectas");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            }
        } catch (Exception ex){
            ex.printStackTrace();
            obje.put("res", "error");
            obje.put("data", ex.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public Object loginUser(@RequestBody Usuario usuario){
        System.out.println(usuario);
        JSONObject obje = new JSONObject();
        Optional<Usuario> userVerified = usuarioService.getLoginUser(usuario);
        if (!userVerified.isPresent()) {
            obje.put("res", "error");
            obje.put("data", "[]");
            return new ResponseEntity<>(obje, HttpStatus.UNAUTHORIZED);
        }

        try {
            int userId = userVerified.get().getCodigo_id();
            Optional<Usuario> optionalUser = usuarioService.getUserById(userId);
            Usuario user = optionalUser.get();
            obje.put("res", "success");
            obje.put("data", user);
            return new ResponseEntity<>(obje, HttpStatus.OK);
        } catch (NotFoundException e) {
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
