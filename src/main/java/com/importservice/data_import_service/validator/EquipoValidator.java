package com.importservice.data_import_service.validator;

import com.importservice.data_import_service.entity.Equipo;
import java.util.ArrayList;
import java.util.List;

public class EquipoValidator {
    
    public static List<String> validate(Equipo equipo, int rowNumber) {
        List<String> errors = new ArrayList<>();
        
        if (equipo.getNombre() == null || equipo.getNombre().trim().isEmpty()) {
            errors.add("Fila " + rowNumber + ": Campo 'nombre' es obligatorio");
        } else if (equipo.getNombre().length() > 100) {
            errors.add("Fila " + rowNumber + ": Campo 'nombre' excede 100 caracteres");
        }
        
        if (equipo.getIdLocalidad() == null || equipo.getIdLocalidad() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'id_Localidad' debe ser un número positivo");
        }
        
        if (equipo.getUrlImagen() != null && equipo.getUrlImagen().length() > 500) {
            errors.add("Fila " + rowNumber + ": Campo 'url_imagen' excede 500 caracteres");
        }
        
        return errors;
    }
}