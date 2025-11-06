package com.importservice.data_import_service.service;

import com.importservice.dto.ImportResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class ImportServiceTest {

    @Autowired
    private ImportService importService;

    @Test
    void testImportEquiposCSV_ValidData() {
        String csvContent = "nombre,id_Localidad,url_imagen\n" +
                           "Real Madrid,1,https://example.com/logo.png\n" +
                           "Barcelona,2,";

        ImportResponse response = importService.importEquipos(csvContent, true);

        assertEquals(2, response.getRecordsImported());
        assertEquals(0, response.getRecordsFailed());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    void testImportEquiposCSV_InvalidData() {
        String csvContent = "nombre,id_Localidad,url_imagen\n" +
                           ",1,https://example.com/logo.png\n" + // nombre vacío
                           "Barcelona,-5,"; // id_localidad negativo

        ImportResponse response = importService.importEquipos(csvContent, true);

        assertEquals(0, response.getRecordsImported());
        assertEquals(2, response.getRecordsFailed());
        assertFalse(response.getErrors().isEmpty());
    }

    @Test
    void testImportJugadoresJSON_ValidData() {
        String jsonContent = "[{" +
                "\"nombre\":\"Lionel\"," +
                "\"apellido\":\"Messi\"," +
                "\"Numero_jugador\":10," +
                "\"posicion\":\"Delantero\"," +
                "\"estatura\":1.70," +
                "\"nacionalidad\":\"Argentina\"," +
                "\"edad\":36," +
                "\"id_Equipo\":1" +
                "}]";

        ImportResponse response = importService.importJugadores(jsonContent, false);

        assertEquals(1, response.getRecordsImported());
        assertEquals(0, response.getRecordsFailed());
        assertTrue(response.getErrors().isEmpty());
    }

    @Test
    void testImportPartidosCSV_SameTeamError() {
        String csvContent = "FechaHora,id_Localidad,id_Local,id_Visitante\n" +
                           "2025-11-05T20:00:00,1,1,1"; // mismo equipo local y visitante

        ImportResponse response = importService.importPartidos(csvContent, true);

        assertEquals(0, response.getRecordsImported());
        assertEquals(1, response.getRecordsFailed());
        assertTrue(response.getErrors().stream()
                .anyMatch(e -> e.contains("no puede jugar contra sí mismo")));
    }
}