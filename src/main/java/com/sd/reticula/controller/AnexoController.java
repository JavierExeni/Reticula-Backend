package com.sd.reticula.controller;

import com.sd.reticula.model.Anexo;
import com.sd.reticula.service.AnexoService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
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

    @GetMapping("/tarea/{tareaId}")
    public Object getAllByTask(@PathVariable int tareaId){
        JSONObject obje = new JSONObject();
        try {
            List<Anexo> annexedList = annexedService.getByTaskId(tareaId);
            if (annexedList != null) {
                return new ResponseEntity<>(annexedList, HttpStatus.OK);
            }else {
                obje.put("res", "error");
                obje.put("data", "Parámetros incorrectos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public Object getAllByClient(@PathVariable int clienteId){
        JSONObject obje = new JSONObject();
        try {
            List<Anexo> annexedList = annexedService.getByClientId(clienteId);
            if (annexedList != null) {
                return new ResponseEntity<>(annexedList, HttpStatus.OK);
            }else {
                obje.put("res", "error");
                obje.put("data", "Parámetros incorrectos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    @PostMapping("/insert")
    public Object saveAnexo(@RequestParam("File") MultipartFile file, @ModelAttribute Anexo objAnnexed){
        System.out.println(objAnnexed.toString());
        JSONObject obje = new JSONObject();

        String path = new File(".").getAbsolutePath();
        path = path.substring(0, path.length() - 1);
        path = path + "files";
        createFolder(path);

        path = path + "/anexo";
        createFolder(path);

        path = path + "/" + objAnnexed.getCarpeta().getCodigo_id();
        createFolder(path);
        try {
            String relativePath = FILE_DIRECTORY + "/anexo/" + objAnnexed.getCarpeta().getCodigo_id() + "/" + file.getOriginalFilename();
            String pathCompleto = new File(".").getAbsolutePath();
            String ultimatePath = pathCompleto.substring(0, pathCompleto.length()-1) + relativePath;

            System.out.println(relativePath);
            System.out.println(pathCompleto);
            System.out.println(ultimatePath);

            objAnnexed.setTitulo(file.getOriginalFilename());
            objAnnexed.setPath(relativePath);

            File myFile = new File(ultimatePath);
            myFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.write(file.getBytes());
            fos.close();

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

    private void createFolder(String path) {
        File f = new File(path);
        if (!f.exists()){
            f.mkdir();
        }
    }
}
