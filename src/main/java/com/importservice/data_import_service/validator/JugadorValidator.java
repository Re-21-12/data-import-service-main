package com.importservice.data_import_service.validator;

import com.importservice.data_import_service.entity.Jugador;
import java.util.ArrayList;
import java.util.List;

public class JugadorValidator {
    
    public static List<String> validate(Jugador jugador, int rowNumber) {
        List<String> errors = new ArrayList<>();
        
        if (jugador.getNombre() == null || jugador.getNombre().trim().isEmpty()) {
            errors.add("Fila " + rowNumber + ": Campo 'nombre' es obligatorio");
        }
        
        if (jugador.getApellido() == null || jugador.getApellido().trim().isEmpty()) {
            errors.add("Fila " + rowNumber + ": Campo 'apellido' es obligatorio");
        }
        
        if (jugador.getNumeroJugador() == null || jugador.getNumeroJugador() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'Numero_jugador' debe ser un número positivo");
        }
        
        if (jugador.getPosicion() == null || jugador.getPosicion().trim().isEmpty()) {
            errors.add("Fila " + rowNumber + ": Campo 'posicion' es obligatorio");
        }
        
        if (jugador.getEstatura() == null || jugador.getEstatura() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'estatura' debe ser un número positivo");
        }
        
        if (jugador.getEdad() == null || jugador.getEdad() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'edad' debe ser un número positivo");
        }
        
        if (jugador.getIdEquipo() == null || jugador.getIdEquipo() <= 0) {
            errors.add("Fila " + rowNumber + ": Campo 'id_Equipo' debe ser un número positivo");
        }
        
        return errors;
    }
}