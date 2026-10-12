package edu.dosw.parcial.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Pattern;

/**
 * Implementación simulada de NotifyMe: valida el formato de la cadena y la
 * guarda en memoria. No envía nada realmente.
 */
@Service
public class NotifyMeServiceSimulado implements NotifyMeService {

    private static final Logger log = LoggerFactory.getLogger(NotifyMeServiceSimulado.class);

    /** correo@mail.escuelaing.edu.co|Mensaje_de_confirmacion */
    private static final Pattern FORMATO =
            Pattern.compile("^[^@|\\s]+@mail\\.escuelaing\\.edu\\.co\\|\\S.*$");

    private final List<String> enviados = new CopyOnWriteArrayList<>();

    @Override
    public void enviar(String mensaje) {
        if (mensaje == null || !FORMATO.matcher(mensaje).matches()) {
            throw new IllegalArgumentException(
                    "Formato inválido. Se espera: correo@mail.escuelaing.edu.co|Mensaje_de_confirmacion");
        }
        enviados.add(mensaje);
        log.debug("NotifyMe simulado: notificación enviada -> {}", mensaje);
    }

    /** Útil para verificar en las pruebas qué notificaciones se enviaron. */
    public List<String> getEnviados() {
        return List.copyOf(enviados);
    }
}
