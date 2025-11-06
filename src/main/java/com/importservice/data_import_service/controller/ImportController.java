package com.importservice.data_import_service.controller;

import com.importservice.dto.FileUpload;
import com.importservice.dto.ImportResponse;
import com.importservice.data_import_service.service.ImportService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/import")
@CrossOrigin(origins = "*")
public class ImportController {

    @Autowired
    private ImportService importService;

    private static final Logger logger = LoggerFactory.getLogger(ImportController.class);

    @PostMapping("/equipo/csv")
    @Operation(
        summary = "Importar equipos desde CSV",
        description = "Estructura requerida: nombre,id_Localidad,url_imagen (url_imagen es opcional)",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importEquiposCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "equipo");
    }
    
    @PostMapping("/equipo/json")
    @Operation(
        summary = "Importar equipos desde JSON",
        description = "Estructura requerida: [{\"nombre\": string, \"id_Localidad\": number, \"url_imagen\": string (opcional)}]",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importEquiposJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "equipo");
    }
    
    @PostMapping("/jugador/csv")
    @Operation(
        summary = "Importar jugadores desde CSV",
        description = "Estructura requerida: nombre,apellido,Numero_jugador,posicion,estatura,nacionalidad,edad,id_Equipo (nacionalidad opcional)",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importJugadoresCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "jugador");
    }
    
    @PostMapping("/jugador/json")
    @Operation(
        summary = "Importar jugadores desde JSON",
        description = "Estructura requerida: [{\"nombre\": string, \"apellido\": string, \"Numero_jugador\": number, \"posicion\": string, \"estatura\":    number, \"nacionalidad\": string (opcional), \"edad\": number, \"id_Equipo\": number}]",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importJugadoresJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "jugador");
    }
    
    @PostMapping("/localidad/csv")
    @Operation(
        summary = "Importar localidades desde CSV",
        description = "Estructura requerida: nombre",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importLocalidadesCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "localidad");
    }
    
    @PostMapping("/localidad/json")
    @Operation(
        summary = "Importar localidades desde JSON",
        description = "Estructura requerida: [{\"nombre\": string}]",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importLocalidadesJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "localidad");
    }
    
    @PostMapping("/partido/csv")
    @Operation(
        summary = "Importar partidos desde CSV",
        description = "Estructura requerida: FechaHora (ISO 8601),id_Localidad,id_Local,id_Visitante",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importPartidosCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "partido");
    }
    
    @PostMapping("/partido/json")
    @Operation(
        summary = "Importar partidos desde JSON",
        description = "Estructura requerida: [{\"FechaHora\": \"YYYY-MM-DDTHH:mm:ss\", \"id_Localidad\": number, \"id_Local\": number, \"id_Visitante\":    number}]",
        requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class)))
    )
    public ResponseEntity<ImportResponse> importPartidosJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "partido");
    }

    private ResponseEntity<ImportResponse> handleFileImport(MultipartFile file, boolean isCSV, String tipo) {
        try {
            logger.info("Request received: tipo='{}', isCSV={}, filename='{}', size={} bytes", tipo, isCSV, file == null ? "null" : file.getOriginalFilename(), file == null ? 0 : file.getSize());
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body(
                        new ImportResponse(0, 1, List.of("El archivo está vacío"))
                );
            }

            String content = new String(file.getBytes());
            ImportResponse response;

            switch (tipo) {
                case "equipo" -> response = importService.importEquipos(content, isCSV);
                case "jugador" -> response = importService.importJugadores(content, isCSV);
                case "localidad" -> response = importService.importLocalidades(content, isCSV);
                case "partido" -> response = importService.importPartidos(content, isCSV);
                default -> throw new IllegalArgumentException("Tipo no válido");
            }

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(
                    new ImportResponse(0, 1, List.of("Error procesando archivo: " + e.getMessage()))
            );
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("Microservicio funcionando correctamente");
    }

    @PostMapping("/upload")
    @Operation(summary = "Importar archivo (CSV o JSON) via multipart/form-data", requestBody = @RequestBody(content = @Content(mediaType = "multipart/form-data", schema = @Schema(implementation = FileUpload.class))))
    public ResponseEntity<ImportResponse> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam(name = "tipo") String tipo) {
        try {
            if (file == null || file.isEmpty()) {
                return ResponseEntity.badRequest().body(new ImportResponse(0, 1, List.of("El archivo está vacío")));
            }

            // Detectar si es CSV o JSON por extensión / content-type / contenido
            boolean isCSV = false;
            String filename = file.getOriginalFilename();
            String contentType = file.getContentType();
            if (filename != null && filename.toLowerCase().endsWith(".csv")) {
                isCSV = true;
            } else if (contentType != null && contentType.toLowerCase().contains("csv")) {
                isCSV = true;
            } else {
                // Leer inicio del archivo para heurística simple
                String content = new String(file.getBytes());
                String trimmed = content.trim();
                if (trimmed.startsWith("[") || trimmed.startsWith("{")) {
                    isCSV = false;
                } else {
                    isCSV = true;
                }
            }

            return handleFileImport(file, isCSV, tipo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ImportResponse(0, 1, List.of("Error procesando archivo: " + e.getMessage())));
        }
    }
}
