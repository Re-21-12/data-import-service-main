package com.importservice.data_import_service.validator;

import com.importservice.data_import_service.entity.Partido;
import java.util.ArrayList;
import java.util.List;

public class PartidoValidator {
    
    public static List<String> validate(Partido partido, int rowNumber) {
        List<String> errors = new ArrayList<>();
        
        if (partido.getFechaHora() == null) {
            errors.add("Fila " + rowNumber + ": Campo 'FechaHora' es obligatorio");
        }
        
        if (partido.getIdLocalidad() == null || partido.getIdLocalidad() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'id_Localidad' debe ser un número positivo");
        }
        
        if (partido.getIdLocal() == null || partido.getIdLocal() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'id_Local' debe ser un número positivo");
        }
        
        if (partido.getIdVisitante() == null || partido.getIdVisitante() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'id_Visitante' debe ser un número positivo");
        }
        
        if (partido.getIdLocal() != null && partido.getIdVisitante() != null 
            && partido.getIdLocal().equals(partido.getIdVisitante())) {
            errors.add("Fila " + rowNumber + ": Un equipo no puede jugar contra sí mismo");
        }
        
        return errors;
    }
}