package edu.dosw.parcial.service;
import edu.dosw.parcial.model.UsuarioEnlaceModel;
import java.util.Optional;

/**
 * Sistema académico Enlace. Recibe el correo institucional y entrega la
 * información del usuario. TutoECI no guarda esta información localmente.
 */
public interface EnlaceService {

    /**
     * Obtiene la información de un usuario de Enlace a partir de su correo.
     * @param correo correo institucional del estudiante o del profesor
     * @return el usuario, o vacío si Enlace no lo conoce
     */
    Optional<UsuarioEnlaceModel> consultarPorCorreo(String correo);
}
