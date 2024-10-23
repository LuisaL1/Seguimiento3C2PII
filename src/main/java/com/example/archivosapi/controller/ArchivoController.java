package com.example.archivosapi.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@RestController
@RequestMapping("/apiarchivos")
public class ArchivoController {

    @GetMapping("/xml/{nombreXml}")
    public ResponseEntity<String> getXml(@PathVariable String nombreXml) throws IOException {
       String ruta = "src/main/resources/static/xml"+"/"+nombreXml+".xml";
       try{
           String contenido = Files.readString(Path.of(ruta));
           return ResponseEntity.ok().body(contenido);
       }catch (Exception e){
           return ResponseEntity.notFound().build();
       }
    }

    @GetMapping("/imagen/{nombreImagen}")
    public ResponseEntity<byte[]> getImagen(@PathVariable String nombreImagen) {
        String ruta = "src/main/resources/static/imagen/" + nombreImagen + ".jpg"; // Asegúrate de tener la barra diagonal "/"

        try {
            Path path = Path.of(ruta); // Obtener el objeto Path
            if (!Files.exists(path)) { // Verificar si el archivo existe
                return ResponseEntity.notFound().build();
            }

            byte[] contenido = Files.readAllBytes(path); // Leer los bytes de la imagen
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nombreImagen + ".jpg") // Mostrar la imagen en línea
                    .header(HttpHeaders.CONTENT_TYPE, "image/jpeg") // Establecer el tipo de contenido
                    .body(contenido);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build(); // Manejar errores de IO
        }
    }

    @GetMapping("/html/{nombreHtml}")
    public ResponseEntity<Resource> getHtml(@PathVariable String nombreHtml){
        String ruta = "src/main/resources/static/html/" + nombreHtml + ".html";
        try {
            File file = new File(ruta);
            Resource resource = new FileSystemResource(file);

            // Devolver el archivo HTML
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename="+nombreHtml+".html")
                    .header(HttpHeaders.CONTENT_TYPE, "text/html")
                    .body(resource);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }

    }

    @GetMapping("/json/{nombreJson}")
    public ResponseEntity<Resource> getJson(@PathVariable String nombreJson){
        // Ruta del archivo JSON
        String ruta = "src/main/resources/static/json/" + nombreJson + ".json";

        // Crear un recurso del sistema de archivos
        File file = new File(ruta);
        Resource resource = new FileSystemResource(file);

        // Devolver el archivo JSON
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=data.json")
                .header(HttpHeaders.CONTENT_TYPE, "application/json")
                .body(resource);
    }

    @GetMapping("/pdf/{nombrePdf}")
    public ResponseEntity<Object> getPdf(@PathVariable String nombrePdf){
        String ruta = "src/main/resources/static/pdf/" + nombrePdf + ".pdf";
        try {
            Path path = Path.of(ruta); // Obtener el objeto Path
            if (!Files.exists(path)) { // Verificar si el archivo existe
                return ResponseEntity.notFound().build();
            }

            byte[] contenido = Files.readAllBytes(path); // Leer los bytes de la imagen
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=" + nombrePdf + ".pdf") // Mostrar la imagen en línea
                    .header(HttpHeaders.CONTENT_TYPE, "pdf/pdf") // Establecer el tipo de contenido
                    .body(contenido);
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build(); // Manejar errores de IO
        }
    }


}
