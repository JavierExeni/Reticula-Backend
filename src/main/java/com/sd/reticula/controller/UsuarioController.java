package com.sd.reticula.controller;

import com.sd.reticula.model.Usuario;
import com.sd.reticula.service.UsuarioService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    public Optional<Usuario> getById(@PathVariable int id) throws NotFoundException {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping("/insert")
    public void saveUser(@RequestBody Usuario objUsuario){
        System.out.println(objUsuario.toString());
        try {
            usuarioService.saveUsuario(objUsuario);
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @GetMapping("/{username}/{password}")
    public Usuario getUsuarioLogin(@PathVariable String username, @PathVariable String password){
        Usuario user = usuarioService.getUsusarioLogin(username, password);
        if(user == null){
            return null;
        }
        return user;
    }
}
