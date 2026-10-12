package edu.dosw.parcial.model;

import java.util.List;

/**
 * Respuesta de Enlace para un usuario (estudiante o profesor).
 *
 * <ul>
 *   <li>El profesor tiene rol PROFESOR y no tiene nivel.</li>
 *   <li>El estudiante tiene rol ESTUDIANTE y nivel PREGRADO o POSGRADO.</li>
 *   <li>El estudiante de posgrado no tiene materias inscritas (lista vacía).</li>
 * </ul>
 */
public record UsuarioEnlaceModel(
        String id,
        String nombre,
        String programa,
        Estado estado,
        Rol rol,
        Nivel nivel,
        List<String> materiasInscritas) {

    public enum Estado { ACTIVO, INACTIVO }

    public enum Rol { PROFESOR, ESTUDIANTE }

    public enum Nivel { PREGRADO, POSGRADO }

    public UsuarioEnlaceModel {
        materiasInscritas = materiasInscritas == null ? List.of() : List.copyOf(materiasInscritas);
    }
}
