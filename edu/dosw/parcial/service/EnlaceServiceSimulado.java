package edu.dosw.parcial.service;

import edu.dosw.parcial.model.UsuarioEnlaceModel;
import edu.dosw.parcial.model.UsuarioEnlaceModel.Estado;
import edu.dosw.parcial.model.UsuarioEnlaceModel.Nivel;
import edu.dosw.parcial.model.UsuarioEnlaceModel.Rol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Implementación simulada de Enlace: devuelve usuarios fijos en memoria.
 * No realiza ninguna comunicación real.
 */
@Service
public class EnlaceServiceSimulado implements EnlaceService {

    private static final Logger log = LoggerFactory.getLogger(EnlaceServiceSimulado.class);

    private static final Map<String, UsuarioEnlaceModel> USUARIOS = Map.of(
            // Estudiante de pregrado activo
            "juan.perez@mail.escuelaing.edu.co",
            new UsuarioEnlaceModel(
                    "7C4E2C56-1D0E-4909-A346-A5C601C92447", "Juan Pérez",
                    "Ingeniería de Sistemas", Estado.ACTIVO, Rol.ESTUDIANTE, Nivel.PREGRADO,
                    List.of("DOSW", "FUPR", "AYPR")),

            // Estudiante de pregrado inactivo
            "maria.gomez@mail.escuelaing.edu.co",
            new UsuarioEnlaceModel(
                    "2D9A5B7E-3C41-4F6A-8B12-6E0C9F1A7D35", "María Gómez",
                    "Ingeniería de Sistemas", Estado.INACTIVO, Rol.ESTUDIANTE, Nivel.PREGRADO,
                    List.of("DOSW")),

            // Profesor con DOSW y DOPO
            "laura.herrera@escuelaing.edu.co",
            new UsuarioEnlaceModel(
                    "49F87F8E-DA5C-4B8D-9665-9D984B24F118", "Laura Herrera",
                    "Ingeniería de Sistemas", Estado.ACTIVO, Rol.PROFESOR, null,
                    List.of("DOSW", "DOPO")),

            // Profesor que no tiene asignada DOSW
            "carlos.rojas@escuelaing.edu.co",
            new UsuarioEnlaceModel(
                    "8A3F1C2D-5E47-4B90-A6D8-0F2B7C9E4A61", "Carlos Rojas",
                    "Ingeniería de Sistemas", Estado.ACTIVO, Rol.PROFESOR, null,
                    List.of("FUPR")),

            // Estudiante de posgrado activo
            "laura.herrera-c@mail.escuelaing.edu.co",
            new UsuarioEnlaceModel(
                    "4BB31577-0B2B-4109-8C8A-BD2C2A071594", "Laura Herrera",
                    "Maestría en Desarrollo y Gerencia Integral de Proyectos",
                    Estado.ACTIVO, Rol.ESTUDIANTE, Nivel.POSGRADO, List.of()),

            // Estudiante de posgrado inactivo
            "andres.mora@mail.escuelaing.edu.co",
            new UsuarioEnlaceModel(
                    "9E6B4D8F-1A25-4C73-B0E9-3D7A5F2C8B14", "Andrés Mora",
                    "Maestría en Ingeniería de Software",
                    Estado.INACTIVO, Rol.ESTUDIANTE, Nivel.POSGRADO, List.of()));

    @Override
    public Optional<UsuarioEnlaceModel> consultarPorCorreo(String correo) {
        log.debug("Enlace simulado: consultando usuario con correo {}", correo);
        if (correo == null || correo.isBlank()) {
            return Optional.empty();
        }
        return Optional.ofNullable(USUARIOS.get(correo.trim().toLowerCase()));
    }
}
