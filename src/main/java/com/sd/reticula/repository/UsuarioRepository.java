package com.sd.reticula.repository;

import com.sd.reticula.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsernameAndSpassword(String userName, String password);

}
