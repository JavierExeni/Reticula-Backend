package com.sd.reticula.repository;

import com.sd.reticula.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByUsernameAndSpassword(String userName, String password);

}
