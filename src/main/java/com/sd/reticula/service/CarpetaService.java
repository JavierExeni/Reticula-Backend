package com.sd.reticula.service;

import com.sd.reticula.model.Carpeta;
import com.sd.reticula.model.Cliente;
import com.sd.reticula.repository.CarpetaRepository;
import com.sd.reticula.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarpetaService {

    @Autowired
    private CarpetaRepository carpetaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public List<Carpeta> getAll(){
        return carpetaRepository.findAll();
    }

    public Carpeta getById(int id){
        Optional<Carpeta> folder = carpetaRepository.findById(id);
        if (folder.isPresent()) {
            return folder.get();
        }
        return null;
    }

    public Carpeta getByClient(int id){
        Optional<Cliente> client = clienteRepository.findById(id);
        if (client.isPresent()) {
            Cliente objClient = client.get();
            Carpeta folder = carpetaRepository.findByCliente(objClient);
            return folder;
        }
        return null;
    }

    public Carpeta createCarpeta(Carpeta carpeta) {
        if (validateFolder(carpeta)) {
            return carpetaRepository.saveAndFlush(carpeta);
        } else {
            return null;
        }
    }

    private boolean validateFolder(Carpeta carpeta) {
        if (!carpeta.getNombre().equals("") && carpeta.getCliente() != null) {
            List<Carpeta> folderList = getAll();
            List<Carpeta> listaFiltrada = folderList.stream().filter(
                    folder -> folder.getCliente().getId() == carpeta.getCliente().getId()).collect(Collectors.toList()
            );
            if (listaFiltrada.isEmpty()) {
                return true;
            }
        }
        return false;
    }
}
