package com.importservice.data_import_service.validator;

import com.importservice.data_import_service.entity.Localidad;
import java.util.ArrayList;
import java.util.List;

public class LocalidadValidator {
    
    public static List<String> validate(Localidad localidad, int rowNumber) {
        List<String> errors = new ArrayList<>();
        
        if (localidad.getNombre() == null || localidad.getNombre().trim().isEmpty()) {
            errors.add("Fila " + rowNumber + ": Campo 'nombre' es obligatorio");
        } else if (localidad.getNombre().length() > 100) {
            errors.add("Fila " + rowNumber + ": Campo 'nombre' excede 100 caracteres");
        }
        
        return errors;
    }
}