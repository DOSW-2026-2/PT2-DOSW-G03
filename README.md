# Parcial Práctico DOSW – TutoECI

**Escuela Colombiana de Ingeniería Julio Garavito – DOSW**  
Segundo tercio · Parte 2 · Trabajo en parejas · Duración: 2 horas y 30 minutos

TutoECI es una plataforma para gestionar y asignar tutorías: los tutores publican disponibilidades y los estudiantes de pregrado las reservan mediante solicitudes. Su misión es construir el API REST que lo permite.

> **Antes de empezar, lea el [documento de requerimientos](docs/requirements/TutoECI-Requirements.md).** Allí están los actores, los requerimientos funcionales (RF-01 a RF-09), sus flujos y las reglas de negocio que debe implementar.

## 1. Instrucciones generales

- Lea el enunciado completo antes de empezar a programar.
- Ambos integrantes deben hacer commits: el aporte de cada uno se verifica en el historial de Git.
- Recursos permitidos: la [API de Java 17](https://docs.oracle.com/en/java/javase/17/docs/api/) y los repositorios de sus laboratorios.
- La autenticación y la autorización ya están implementadas y no se evalúan.
- Recibirán las clases `EnlaceService` y `NotifyMeService` con sus implementaciones simuladas.
- **Prerrequisitos (5 %).** Deben estar listos al empezar: enviadas previamente.

| Componente | Peso |
| --- | --- |
| Parte 1. Teórico | 30 % |
| Parte 2.1. Prerrequisitos | 5 % |
| Parte 2.2. Caso de estudio TutoECI | 65 % |

| Bloque de la Parte 2.2 | Peso |
| --- | --- |
| 1. Diseño (ER y clases) | 6 |
| 2. Entidades, modelos y mappers | 10 |
| 3. Base de datos | 6 |
| 4. Repositorios | 3 |
| 5. TDD | 10 |
| 6. Servicios | 10 |
| 7. Controladores | 6 |
| 8. Swagger | 3 |
| 9. Logging | 3 |
| 10. Postman y evidencias | 5 |
| 11. Git y README | 3 |
| **Total** | **65** |

## 2. Sistemas externos

TutoECI no guarda información de estudiantes ni de tutores: la consulta a Enlace. No implementen la comunicación con los sistemas externos; usan las interfaces del proyecto base, que se inyectarán por constructor.

### Enlace

`EnlaceService.consultarPorCorreo(String correo)` devuelve un `Optional<UsuarioEnlaceModel>` con estos campos:

| Campo | Valores | Notas |
| --- | --- | --- |
| `id` | UUID | |
| `nombre` | Texto | |
| `programa` | Texto | |
| `estado` | `ACTIVO`, `INACTIVO` | |
| `rol` | `PROFESOR`, `ESTUDIANTE` | Aplica a profesores y a estudiantes de pregrado y posgrado |
| `nivel` | `PREGRADO`, `POSGRADO` | Solo estudiantes; el profesor no lo tiene |
| `materiasInscritas` | Lista de siglas | Profesores y estudiantes de pregrado; vacía para posgrado |

Con `rol` y `nivel` se identifica el tipo de usuario: un profesor tiene `rol = PROFESOR`, y un estudiante de posgrado tiene `rol = ESTUDIANTE` y `nivel = POSGRADO`.

**Usuarios simulados disponibles:**

| Correo | Rol | Nivel | Estado | Materias |
| --- | --- | --- | --- | --- |
| `juan.perez@mail.escuelaing.edu.co` | ESTUDIANTE | PREGRADO | ACTIVO | DOSW, FUPR, AYPR |
| `maria.gomez@mail.escuelaing.edu.co` | ESTUDIANTE | PREGRADO | INACTIVO | DOSW |
| `laura.herrera@escuelaing.edu.co` | PROFESOR | — | ACTIVO | DOSW, DOPO |
| `carlos.rojas@escuelaing.edu.co` | PROFESOR | — | ACTIVO | FUPR |
| `laura.herrera-c@mail.escuelaing.edu.co` | ESTUDIANTE | POSGRADO | ACTIVO | — |
| `andres.mora@mail.escuelaing.edu.co` | ESTUDIANTE | POSGRADO | INACTIVO | — |

### NotifyMe

`NotifyMeService.enviar(String mensaje)` recibe una cadena con el formato estricto `correo@mail.escuelaing.edu.co|Mensaje_de_confirmacion`. La implementación simulada lanza `IllegalArgumentException` si la cadena no cumple el formato.

## 3. Reglas de negocio para los datos requeridos

* Los ids son autogenerados, y los estados y la modalidad se guardan como texto (`@Enumerated(EnumType.STRING)`).
* Solo se puede asignar una disponibilidad a una solicitud.

**De las `disponibilidades` se requiere conocer:**

*  id
*  fecha
*  Hora inicial
*  Hora final
*  Lugar (sala o salón, si es presencial)
*  Modalidad (Virtual o presencial)
*  Estado (Libre u Ocupado)
*  Correo del tutor
*  Materia (Sigla de la materia o GENERAL)


**De las `solicitudes` se requirere conocer:**

* id
* Disponibilidad asociada.
* Correo del solicitante.
* Estado (Activa, En Progreso, Cancelada, Rechazada o Finalizada).


## 4. Puntos a desarrollar

Son diez bloques de trabajo, cada uno en su rama `feature/` y mezclado en `develop` en este orden.

| # | Bloque | Rama | Qué se pide | Evidencia en el README |
| --- | --- | --- | --- | --- |
| 1 | Diseño | `feature/design` | Diagrama ER y diagrama de clases en la carpeta `/uml` | Captura de cada diagrama |
| 2 | Entidades, modelos y mappers | `feature/entities-models` | Entities con anotaciones JPA y la relación entre ellas; Models con sufijo `Model`; mappers con MapStruct | Código en la rama |
| 3 | Base de datos | `feature/database` | Tablas generadas por JPA en PostgreSQL y al menos 3 registros en cada una | Captura de cada tabla con sus registros |
| 4 | Repositorios | `feature/repositories` | Un `JpaRepository` por entidad y una consulta derivada por correo del tutor (la usan RF-03 y RF-09) | Código en la rama |
| 5 | TDD | `feature/tdd-disponibilidades` | RF-09: contar las disponibilidades de un tutor (detalle abajo) | Cuatro capturas, una por paso |
| 6 | Servicios | `feature/services` | Los requerimientos RF-01 a RF-08, con sus reglas; repositorios, mappers, `EnlaceService` y `NotifyMeService` inyectados por constructor; excepciones propias | Código en la rama |
| 7 | Controladores | `feature/controllers` | Endpoints REST de RF-01 a RF-07, con buenas prácticas (detalle abajo). RF-08 es interno y RF-09 puede quedar solo como método del servicio | Código en la rama |
| 8 | Swagger | `feature/swagger` | Dependencia de documentación, `@Tag` en los controladores y `@Operation` en cada endpoint | Captura de la interfaz de Swagger |
| 9 | Logging | `feature/logging` | SLF4J en las consultas de disponibilidades (RF-02, RF-03 y RF-04), con el id consultado cuando aplique; archivo `logs/parcial-t2.log` | Código en la rama |
| 10 | Postman y evidencias | `feature/postman-evidence` | Colección `Parcial T2` exportada a `docs/postman/`; ejecutar la consulta de una disponibilidad por id (RF-04) con uno de los registros del bloque 3 | Captura de la respuesta (método, URL y código) y captura del registro en `logs/parcial-t2.log` |

**Bloque 5. TDD.** En `DisponibilidadService`, el método `long contarPorTutor(String correoTutor)` implementa RF-09 y cuenta todas las disponibilidades de un tutor. El repositorio se simula con Mockito. Sigan los cuatro pasos y tomen una captura de cada uno:

1. Definan el encabezado del método.
2. Escriban los casos de prueba y ejecútenlos: deben fallar.
3. Construyan la lógica del método.
4. Ejecuten de nuevo los casos: deben pasar.

Los casos mínimos son cuatro: un tutor sin disponibilidades devuelve 0, un tutor con varias devuelve la cantidad correcta, las disponibilidades de otros tutores no se cuentan, y un correo nulo o vacío lanza `IllegalArgumentException`. Hagan un commit para el paso 2 y otro para el paso 4.

**Bloque 7. Buenas prácticas de endpoints.**

- Rutas con sustantivos en plural, sin verbos, con versión (por ejemplo `/api/v1/...`) y el id dentro de la ruta.
- Controladores y servicios trabajan con modelos y DTOs, nunca con entidades.
- Códigos HTTP correctos: 201 al crear, 200 al consultar o actualizar, 204 al eliminar, y 400, 404 o 409 según la regla incumplida.
- Los controladores no contienen lógica de negocio ni acceden a los repositorios.

## 5. Entrega, Git y README

La entrega es el repositorio de la pareja con los diez bloques mezclados en `develop` y un PR final de `develop` a `main`.

- **Commits.** Cada commit sigue el template `feat: Parcial parte 2 - Acción realizada`. Ambos integrantes deben tener commits.
- **Ramas.** Cada bloque se desarrolla en la rama indicada en la tabla y se mezcla en `develop` mediante un PR que revisa el otro integrante.
- **README.** Use la plantilla: nombres de la pareja, instrucciones para ejecutar el proyecto y una sección por bloque, en orden, con la evidencia que pide la tabla.
- **`.gitignore`.** Debe excluir `target/`, `logs/` y los archivos del IDE.
- **Un proyecto sin README o sin `.gitignore` no será calificado.**

**Bono (+0,5 sobre 5,0).** Autenticación con JWT y filtros sobre los endpoints del parcial: un login simulado que entrega un token y un filtro que protege las rutas `/api/v1/*`. Solo se evalúa si los diez bloques están entregados.
