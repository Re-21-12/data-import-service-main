# Estructura Fija de Archivos para Importación

## Formato CSV

### Equipo
```
nombre,id_Localidad,url_imagen
Real Madrid,1,https://example.com/logo.png
```

**Campos obligatorios:**
- `nombre` (string, max 100 caracteres)
- `id_Localidad` (integer positivo)

**Campos opcionales:**
- `url_imagen` (string, max 500 caracteres)

---

### Jugador
```
nombre,apellido,Numero_jugador,posicion,estatura,nacionalidad,edad,id_Equipo
Lionel,Messi,10,Delantero,1.70,Argentina,36,1
```

**Campos obligatorios:**
- `nombre` (string, max 50 caracteres)
- `apellido` (string, max 50 caracteres)
- `Numero_jugador` (integer positivo)
- `posicion` (string, max 50 caracteres)
- `estatura` (decimal positivo, en metros)
- `edad` (integer positivo)
- `id_Equipo` (integer positivo)

**Campos opcionales:**
- `nacionalidad` (string, max 50 caracteres)

**Campos automáticos:**
- `CreatedAt` (timestamp, generado automáticamente)
- `CreatedBy` (integer, valor por defecto: 1)
- `UpdatedAt` (timestamp, null en importación)
- `UpdatedBy` (integer, null en importación)

---

### Localidad
```
nombre
Madrid
```

**Campos obligatorios:**
- `nombre` (string, max 100 caracteres)

---

### Partido
```
FechaHora,id_Localidad,id_Local,id_Visitante
2025-11-05T20:00:00,1,1,2
```

**Campos obligatorios:**
- `FechaHora` (datetime ISO 8601: YYYY-MM-DDTHH:mm:ss)
- `id_Localidad` (integer positivo)
- `id_Local` (integer positivo)
- `id_Visitante` (integer positivo)

**Validaciones adicionales:**
- `id_Local` ≠ `id_Visitante` (un equipo no puede jugar contra sí mismo)

---

## Formato JSON

### Equipo
```json
[
  [
    {
      "nombre": "Real Madrid",
      "id_Localidad": 1,
      "url_imagen": "https://example.com/logo.png"
    }
  ]
  [
    {
      "nombre": "Lionel",
      "apellido": "Messi",
      "Numero_jugador": 10,
      "posicion": "Delantero",
      "estatura": 1.70,
      "nacionalidad": "Argentina",
      "edad": 36,
      "id_Equipo": 1
    }
  ]
  [
    {
      "nombre": "Madrid"
    }
  ]
  [
    {
      "FechaHora": "2025-11-05T20:00:00",
      "id_Localidad": 1,
      "id_Local": 1,
      "id_Visitante": 2
    }
  ]
]

```
## Reglas Generales

1. **CSV**: Primera fila debe contener los nombres de columnas
2. **JSON**: Debe ser un array de objetos
3. **Encoding**: UTF-8
4. **Fechas**: Formato ISO 8601 (YYYY-MM-DDTHH:mm:ss)
5. **Decimales**: Usar punto (.) como separador
6. **Valores nulos**: Campos opcionales pueden omitirse en JSON o dejarse vacíos en CSV