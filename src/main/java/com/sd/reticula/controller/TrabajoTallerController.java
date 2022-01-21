package com.sd.reticula.controller;

import com.sd.reticula.model.TrabajoTaller;
import com.sd.reticula.service.TrabajoTallerService;
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

@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.DELETE })
@RestController
@RequestMapping("/taller")
public class TrabajoTallerController {

    @Autowired
    TrabajoTallerService trabajoTallerService;

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

    @DeleteMapping("/{id}")
    public Object deleteById(@PathVariable int id){
        JSONObject obje = new JSONObject();
        Boolean eliminado = trabajoTallerService.deleteById(id);
        if (eliminado) {
            obje.put("res", "success");
            return new ResponseEntity<>(obje, HttpStatus.OK);
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
    public Object uploadImage(@RequestParam("File") MultipartFile file, @RequestParam("tallerId") int tallerId) {
        JSONObject obje = new JSONObject();

        String path = new File(".").getAbsolutePath();
        path = path.substring(0, path.length() - 1);
        path = path + "files";
        createFolder(path);

        path = path + "/taller";
        createFolder(path);

        try {
            TrabajoTaller objTrabajo = trabajoTallerService.getById(tallerId);
            if (objTrabajo != null) {
                path = path + "/" + objTrabajo.getCliente().getLpersona_id();
                createFolder(path);

                String relativePath = FILE_DIRECTORY + "/taller/" + objTrabajo.getCliente().getLpersona_id() + "/" + file.getOriginalFilename();
                String pathCompleto = new File(".").getAbsolutePath();
                String ultimatePath = pathCompleto.substring(0, pathCompleto.length()-1) + relativePath;

                objTrabajo.setImagen(relativePath);
                File myFile = new File(ultimatePath);
                myFile.createNewFile();
                FileOutputStream fos = new FileOutputStream(myFile);
                fos.write(file.getBytes());
                fos.close();

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

    private void createFolder(String path) {
        File f = new File(path);
        if (!f.exists()){
            f.mkdir();
        }
    }
}
