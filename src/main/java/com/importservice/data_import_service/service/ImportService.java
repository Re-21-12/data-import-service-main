package com.importservice.data_import_service.service;


import com.importservice.dto.ImportResponse;
import com.importservice.data_import_service.entity.*;
import com.importservice.data_import_service.repository.*;
import com.opencsv.CSVReader;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.StringReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ImportService {

    private static final Logger logger = LoggerFactory.getLogger(ImportService.class);

    @Autowired private EquipoRepository equipoRepository;
    @Autowired private JugadorRepository jugadorRepository;
    @Autowired private LocalidadRepository localidadRepository;
    @Autowired private PartidoRepository partidoRepository;
    @Autowired private ObjectMapper objectMapper;

    // ========================= EQUIPO =========================
    public ImportResponse importEquipos(String content, boolean isCSV) {
        List<String> errors = new ArrayList<>();
        int success = 0, failed = 0;
        try {
            if (isCSV) {
                try (CSVReader reader = new CSVReader(new StringReader(content))) {
                    List<String[]> rows = reader.readAll();
                    for (int i = 1; i < rows.size(); i++) { // Saltar encabezado
                        try {
                            String[] r = rows.get(i);
                            if (r.length < 2) {
                                failed++;
                                errors.add("Fila " + i + ": Estructura incorrecta, se esperan al menos 2 columnas (nombre, id_localidad)");
                                continue;
                            }

                            Equipo e = new Equipo();
                            e.setNombre(r[0].trim());
                            e.setIdLocalidad(Long.parseLong(r[1].trim()));
                            if (r.length > 2 && r[2] != null && !r[2].trim().isEmpty()) {
                                e.setUrlImagen(r[2].trim());
                            }

                            // Validar estructura
                            List<String> validationErrors = com.importservice.data_import_service.validator.EquipoValidator.validate(e, i);
                            if (!validationErrors.isEmpty()) {
                                failed++;
                                errors.addAll(validationErrors);
                                continue;
                            }

                            equipoRepository.save(e);
                            logger.info("Inserted Equipo -> nombre='{}', idLocalidad={}", e.getNombre(), e.getIdLocalidad());
                            success++;
                        } catch (Exception ex) {
                            failed++;
                            errors.add("Fila " + i + ": " + ex.getMessage());
                        }
                    }
                }
            } else {
                Equipo[] equipos = objectMapper.readValue(content, Equipo[].class);
                for (int idx = 0; idx < equipos.length; idx++) {
                    Equipo e = equipos[idx];

                    List<String> validationErrors = com.importservice.data_import_service.validator.EquipoValidator.validate(e, idx + 1);
                    if (!validationErrors.isEmpty()) {
                        failed++;
                        errors.addAll(validationErrors);
                        continue;
                    }

                    equipoRepository.save(e);
                    logger.info("Inserted Equipo -> nombre='{}', idLocalidad={}", e.getNombre(), e.getIdLocalidad());
                    success++;
                }
            }
        } catch (Exception e) {
            failed++;
            errors.add("Error general: " + e.getMessage());
        }
        return new ImportResponse(success, failed, errors);
    }

    // ========================= JUGADOR =========================
    public ImportResponse importJugadores(String content, boolean isCSV) {
        List<String> errors = new ArrayList<>();
        int success = 0, failed = 0;
        try {
            if (isCSV) {
                try (CSVReader reader = new CSVReader(new StringReader(content))) {
                    List<String[]> rows = reader.readAll();
                    for (int i = 1; i < rows.size(); i++) {
                        try {
                            String[] r = rows.get(i);
                            if (r.length < 7) {
                                failed++;
                                errors.add("Fila " + i + ": Estructura incorrecta, se esperan al menos 7 columnas");
                                continue;
                            }

                            Jugador j = new Jugador();
                            j.setNombre(r[0].trim());
                            j.setApellido(r[1].trim());
                            j.setNumeroJugador(Integer.parseInt(r[2].trim()));
                            j.setPosicion(r[3].trim());
                            j.setEstatura(Double.parseDouble(r[4].trim()));
                            if (r.length > 5 && r[5] != null && !r[5].trim().isEmpty()) {
                                j.setNacionalidad(r[5].trim());
                            }
                            j.setEdad(Integer.parseInt(r[6].trim()));
                            j.setIdEquipo(Long.parseLong(r[7].trim()));

                            j.setCreatedAt(java.time.LocalDateTime.now());
                            j.setCreatedBy(1); // Sistema

                            List<String> validationErrors = com.importservice.data_import_service.validator.JugadorValidator.validate(j, i);
                            if (!validationErrors.isEmpty()) {
                                failed++;
                                errors.addAll(validationErrors);
                                continue;
                            }

                            jugadorRepository.save(j);
                            logger.info("Inserted Jugador -> nombre='{}', apellido='{}', idEquipo={}", j.getNombre(), j.getApellido(), j.getIdEquipo());
                            success++;
                        } catch (Exception ex) {
                            failed++;
                            errors.add("Fila " + i + ": " + ex.getMessage());
                        }
                    }
                }
            } else {
                Jugador[] jugadores = objectMapper.readValue(content, Jugador[].class);
                for (int idx = 0; idx < jugadores.length; idx++) {
                    Jugador j = jugadores[idx];

                    if (j.getCreatedAt() == null) {
                        j.setCreatedAt(java.time.LocalDateTime.now());
                    }
                    if (j.getCreatedBy() == null) {
                        j.setCreatedBy(1);
                    }

                    List<String> validationErrors = com.importservice.data_import_service.validator.JugadorValidator.validate(j, idx + 1);
                    if (!validationErrors.isEmpty()) {
                        failed++;
                        errors.addAll(validationErrors);
                        continue;
                    }

                    jugadorRepository.save(j);
                    logger.info("Inserted Jugador -> nombre='{}', apellido='{}', idEquipo={}", j.getNombre(), j.getApellido(), j.getIdEquipo());
                    success++;
                }
            }
        } catch (Exception e) {
            failed++;
            errors.add("Error general: " + e.getMessage());
        }
        return new ImportResponse(success, failed, errors);
    }

    // ========================= LOCALIDAD =========================
    public ImportResponse importLocalidades(String content, boolean isCSV) {
        List<String> errors = new ArrayList<>();
        int success = 0, failed = 0;
        try {
            if (isCSV) {
                try (CSVReader reader = new CSVReader(new StringReader(content))) {
                    List<String[]> rows = reader.readAll();
                    for (int i = 1; i < rows.size(); i++) {
                        try {
                            String[] r = rows.get(i);
                            if (r.length < 1) {
                                failed++;
                                errors.add("Fila " + i + ": Estructura incorrecta, se espera al menos 1 columna (nombre)");
                                continue;
                            }

                            Localidad l = new Localidad();
                            l.setNombre(r[0].trim());

                            List<String> validationErrors = com.importservice.data_import_service.validator.LocalidadValidator.validate(l, i);
                            if (!validationErrors.isEmpty()) {
                                failed++;
                                errors.addAll(validationErrors);
                                continue;
                            }

                            localidadRepository.save(l);
                            logger.info("Inserted Localidad -> nombre='{}'", l.getNombre());
                            success++;
                        } catch (Exception ex) {
                            failed++;
                            errors.add("Fila " + i + ": " + ex.getMessage());
                        }
                    }
                }
            } else {
                Localidad[] localidades = objectMapper.readValue(content, Localidad[].class);
                for (int idx = 0; idx < localidades.length; idx++) {
                    Localidad l = localidades[idx];

                    List<String> validationErrors = com.importservice.data_import_service.validator.LocalidadValidator.validate(l, idx + 1);
                    if (!validationErrors.isEmpty()) {
                        failed++;
                        errors.addAll(validationErrors);
                        continue;
                    }

                    localidadRepository.save(l);
                    logger.info("Inserted Localidad -> nombre='{}'", l.getNombre());
                    success++;
                }
            }
        } catch (Exception e) {
            failed++;
            errors.add("Error general: " + e.getMessage());
        }
        return new ImportResponse(success, failed, errors);
    }

    // ========================= PARTIDO =========================
    public ImportResponse importPartidos(String content, boolean isCSV) {
        List<String> errors = new ArrayList<>();
        int success = 0, failed = 0;
        try {
            if (isCSV) {
                try (CSVReader reader = new CSVReader(new StringReader(content))) {
                    List<String[]> rows = reader.readAll();
                    for (int i = 1; i < rows.size(); i++) {
                        try {
                            String[] r = rows.get(i);
                            if (r.length < 4) {
                                failed++;
                                errors.add("Fila " + i + ": Estructura incorrecta, se esperan al menos 4 columnas");
                                continue;
                            }

                            Partido p = new Partido();
                            p.setFechaHora(java.time.LocalDateTime.parse(r[0].trim()));
                            p.setIdLocalidad(Long.parseLong(r[1].trim()));
                            p.setIdLocal(Long.parseLong(r[2].trim()));
                            p.setIdVisitante(Long.parseLong(r[3].trim()));

                            List<String> validationErrors = com.importservice.data_import_service.validator.PartidoValidator.validate(p, i);
                            if (!validationErrors.isEmpty()) {
                                failed++;
                                errors.addAll(validationErrors);
                                continue;
                            }

                            partidoRepository.save(p);
                            logger.info("Inserted Partido -> fechaHora='{}', idLocal={}, idVisitante={}", p.getFechaHora(), p.getIdLocal(), p.getIdVisitante());
                            success++;
                        } catch (Exception ex) {
                            failed++;
                            errors.add("Fila " + i + ": " + ex.getMessage());
                        }
                    }
                }
            } else {
                Partido[] partidos = objectMapper.readValue(content, Partido[].class);
                for (int idx = 0; idx < partidos.length; idx++) {
                    Partido p = partidos[idx];

                    List<String> validationErrors = com.importservice.data_import_service.validator.PartidoValidator.validate(p, idx + 1);
                    if (!validationErrors.isEmpty()) {
                        failed++;
                        errors.addAll(validationErrors);
                        continue;
                    }

                    partidoRepository.save(p);
                    logger.info("Inserted Partido -> fechaHora='{}', idLocal={}, idVisitante={}", p.getFechaHora(), p.getIdLocal(), p.getIdVisitante());
                    success++;
                }
            }
        } catch (Exception e) {
            failed++;
            errors.add("Error general: " + e.getMessage());
        }
        return new ImportResponse(success, failed, errors);
    }
}
