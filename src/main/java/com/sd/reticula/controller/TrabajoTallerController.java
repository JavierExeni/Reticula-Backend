package com.sd.reticula.controller;

import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.model.UploadFileResponse;
import com.sd.reticula.service.FileStorageService;
import com.sd.reticula.service.TrabajoTallerService;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST })
@RestController
@RequestMapping("/taller")
public class TrabajoTallerController {

    @Autowired
    TrabajoTallerService trabajoTallerService;

    private FileStorageService fileStorageService;

    @GetMapping
    public List<TrabajoTaller> getAll(){
        return trabajoTallerService.getAll();
    }

    @GetMapping("/estado/{estado}")
    public Object getAllByEstado(@PathVariable String estado){
        List<TrabajoTaller> workList = trabajoTallerService.getAllByEstado(estado);
        if (workList != null) {
            return workList;
        } else {
            JSONObject obje = new JSONObject();
            obje.put("res", "error");
            obje.put("data", "Parámetros incorrectos");
            return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/tipo/{tipo}")
    public Object getAllByType(@PathVariable String tipo){
        List<TrabajoTaller> workList = trabajoTallerService.getAllByType(tipo);
        if (workList != null) {
            return workList;
        } else {
            JSONObject obje = new JSONObject();
            obje.put("res", "error");
            obje.put("data", "Parámetros incorrectos");
            return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{trabajo_id}")
    public Object getAllById(@PathVariable int trabajo_id){
        JSONObject obje = new JSONObject();
        TrabajoTaller objTrabajo = trabajoTallerService.getById(trabajo_id);
        if (objTrabajo != null) {
            return new ResponseEntity<>(objTrabajo, HttpStatus.OK);
        } else {
            obje.put("res", "error");
            obje.put("data", "Parámetros incorrectos");
            return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("cliente/{cliente_id}")
    public Object getAllByCliente(@PathVariable int cliente_id){
        JSONObject obje = new JSONObject();
        try {
            List<TrabajoTaller> workList = trabajoTallerService.getAllByCliente(cliente_id);
            if (workList != null) {
                return new ResponseEntity<>(workList, HttpStatus.OK);
            } else {
                obje.put("res", "error");
                obje.put("data", "Parámetros incorrectos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            e.printStackTrace();
            obje.put("res", "error");
            obje.put("data", e.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PostMapping("/insert")
    public Object saveTrabajoTaller(@RequestBody TrabajoTaller obj){
        System.out.println(obj);
        JSONObject obje = new JSONObject();
        try {
            TrabajoTaller objTrabajo = trabajoTallerService.saveTrabajoTaller(obj);
            if (objTrabajo != null) {
                obje.put("res", "success");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            } else {
                obje.put("res", "error");
                obje.put("data", "Ocurrio un error al registrar los datos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception ex){
            ex.printStackTrace();
            obje.put("res", "error");
            obje.put("data", ex.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @Value("${file.upload-dir}")
//    String FILE_DIRECTORY;
//    @PostMapping("/insert")
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
//    @Temporal(TemporalType.DATE)
//    public Object uploadImage(@RequestParam("File") MultipartFile file, @ModelAttribute TrabajoTaller obj) throws IOException {
//        System.out.println(obj);
//        System.out.println(FILE_DIRECTORY+file.getOriginalFilename());
//        String pathFile = FILE_DIRECTORY+file.getOriginalFilename();
//        JSONObject obje = new JSONObject();
//
//        try {
//            File myFile = new File(pathFile);
//            myFile.createNewFile();
//            FileOutputStream fos = new FileOutputStream(myFile);
//            fos.write(file.getBytes());
//            fos.close();
//
//            obj.setImagen(pathFile);
//            TrabajoTaller objTrabajo = trabajoTallerService.saveTrabajoTaller(obj);
//            if (objTrabajo != null) {
//                obje.put("res", "success");
//                return new ResponseEntity<>(obje, HttpStatus.OK);
//            } else {
//                obje.put("res", "error");
//                obje.put("data", "Ocurrio un error al registrar los datos");
//                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
//            }
//        } catch (Exception ex){
//            ex.printStackTrace();
//            obje.put("res", "error");
//            obje.put("data", ex.getMessage());
//            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @InitBinder
//    public void initBinder(WebDataBinder binder){
//        binder.registerCustomEditor(       Date.class,
//                new CustomDateEditor(new SimpleDateFormat("yyyy-mm-dd"), true, 10));
//    }

    @Value("${file.upload-dir}")
    String FILE_DIRECTORY;
    @PostMapping("/uploadImage")
//    @DateTimeFormat(pattern = "yyyy-mm-dd")
//    @Temporal(TemporalType.DATE)
    public Object uploadImage(@RequestParam("File") MultipartFile file, @RequestParam("tallerId") int tallerId) {

        String pathFile = FILE_DIRECTORY + "taller/";
        JSONObject obje = new JSONObject();

        try {
            File myFile = new File(pathFile);
            myFile.createNewFile();
            FileOutputStream fos = new FileOutputStream(myFile);
            fos.write(file.getBytes());
            fos.close();

            TrabajoTaller objTrabajo = trabajoTallerService.getById(tallerId);
            if (objTrabajo != null) {
                pathFile += objTrabajo.getCliente().getId() + file.getOriginalFilename();
                System.out.println(pathFile);
                objTrabajo.setImagen(pathFile);
                trabajoTallerService.saveTrabajoTaller(objTrabajo);
                obje.put("res", "success");
                return new ResponseEntity<>(obje, HttpStatus.OK);
            } else {
                obje.put("res", "error");
                obje.put("data", "Ocurrio un error al registrar los datos");
                return new ResponseEntity<>(obje, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            obje.put("res", "error");
            obje.put("data", ex.getMessage());
            return new ResponseEntity<>(obje, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
