package com.importservice.data_import_service.controller;

import com.importservice.dto.ImportResponse;
import com.importservice.data_import_service.service.ImportService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@RestController
@RequestMapping("/import")
@CrossOrigin(origins = "*")
public class ImportController {

    @Autowired
    private ImportService importService;

    @PostMapping("/equipo/csv")
    public ResponseEntity<ImportResponse> importEquiposCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "equipo");
    }

    @PostMapping("/equipo/json")
    public ResponseEntity<ImportResponse> importEquiposJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "equipo");
    }

    @PostMapping("/jugador/csv")
    public ResponseEntity<ImportResponse> importJugadoresCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "jugador");
    }

    @PostMapping("/jugador/json")
    public ResponseEntity<ImportResponse> importJugadoresJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "jugador");
    }

    @PostMapping("/localidad/csv")
    public ResponseEntity<ImportResponse> importLocalidadesCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "localidad");
    }

    @PostMapping("/localidad/json")
    public ResponseEntity<ImportResponse> importLocalidadesJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "localidad");
    }

    @PostMapping("/partido/csv")
    public ResponseEntity<ImportResponse> importPartidosCSV(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, true, "partido");
    }

    @PostMapping("/partido/json")
    public ResponseEntity<ImportResponse> importPartidosJSON(@RequestParam("file") MultipartFile file) {
        return handleFileImport(file, false, "partido");
    }

    // ===============================================================
    private ResponseEntity<ImportResponse> handleFileImport(MultipartFile file, boolean isCSV, String tipo) {
        try {
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
}
