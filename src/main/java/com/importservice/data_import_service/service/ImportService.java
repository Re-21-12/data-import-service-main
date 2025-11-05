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

    private final ObjectMapper objectMapper = new ObjectMapper();

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
                            Equipo e = new Equipo(r[0], Long.parseLong(r[1]));
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
                for (Equipo e : equipos) {
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
                            Jugador j = new Jugador();
                            j.setNombre(r[0]);
                            j.setApellido(r[1]);
                            j.setEstatura(Double.parseDouble(r[2]));
                            j.setPosicion(r[3]);
                            j.setNacionalidad(r[4]);
                            j.setEdad(Integer.parseInt(r[5]));
                            j.setIdEquipo(Long.parseLong(r[6]));
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
                for (Jugador j : jugadores) {
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
                            Localidad l = new Localidad(r[0]);
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
                for (Localidad l : localidades) {
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
                            Partido p = new Partido(
                                LocalDateTime.parse(r[0]),
                                Long.parseLong(r[1]),
                                Long.parseLong(r[2])
                            );
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
                for (Partido p : partidos) {
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
