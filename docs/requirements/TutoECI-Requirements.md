# 📄 Requerimientos del Sistema

## 1. Lista general de requerimientos

El sistema de TutoECI tiene los siguientes requerimientos (descripción a alto nivel):

### 1.1 Requerimientos funcionales

El sistema de TutoECI debe tener la capacidad de:

1. Crear disponibilidades de tutoría, validando la duración máxima y la materia según el tipo de tutor.
2. Consultar el listado de disponibilidades.
3. Consultar una disponibilidad a partir de su id.
4. Actualizar una disponibilidad existente.
5. Eliminar una disponibilidad.
6. Crear solicitudes de tutoría, validando al solicitante y la disponibilidad.
7. Notificar al solicitante cuando su solicitud sea creada.
8. Calcular la cantidad de disponibilidades de un tutor.

### 1.2 Requerimientos no funcionales

El sistema de TutoECI debe tener:

1. Persistencia de disponibilidades y solicitudes en PostgreSQL mediante JPA.
2. Interoperabilidad con Enlace y NotifyMe, sin almacenar localmente información de estudiantes ni de tutores.
3. Una API REST documentada con Swagger.
4. Registro de eventos con SLF4J en `logs/parcial-t2.log` para las operaciones de consulta.
5. Autenticación con el correo institucional (`@escuelaing.edu.co` para profesores y administrador, `@mail.escuelaing.edu.co` para estudiantes). Para el parcial se asume ya implementada.

### 1.3 Actores y sistemas externos

| Actor | Descripción |
| ----- | ----------- |
| **Administrador** | Gestiona disponibilidades y consulta tutores y solicitantes. |
| **Tutor** | Profesor o estudiante de posgrado que publica disponibilidades. |
| **Solicitante** | Estudiante de pregrado que solicita tutorías. |
| **Enlace** (sistema externo) | Sistema académico que entrega la información de estudiantes, profesores y posgrado a partir del correo. |
| **NotifyMe** (sistema externo) | Sistema que envía las notificaciones a partir de una cadena con formato estricto. |

## 2. Requerimientos Funcionales

### Casos de Uso

<img width="561" height="683" alt="image" src="https://github.com/user-attachments/assets/11e5a5a7-89b5-4093-a1ab-e27a36b789bf" />

### 2.1 Requerimiento Funcional 1

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-01 |
| **Nombre del requerimiento** | Crear disponibilidad |
| **Descripción**              | El sistema debe permitir registrar una disponibilidad de tutoría con fecha, hora inicial, hora final, lugar, modalidad (virtual o presencial), correo del tutor y materia, creándola con estado LIBRE. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado y a Enlace disponible para validar al tutor. |
| **Actor**                    | Tutor, Administrador |
| **Flujo principal**          | 1. El actor envía los datos de la disponibilidad.<br>2. El sistema determina el tipo de tutor por el dominio del correo: `@escuelaing.edu.co` es profesor y `@mail.escuelaing.edu.co` es estudiante de posgrado.<br>3. El sistema valida que la hora final sea posterior a la inicial y que la duración no supere 30 minutos (profesor) o 60 minutos (posgrado).<br>4. El sistema consulta a Enlace. Si el tutor es profesor, valida que la materia esté entre sus materias asignadas. Si es de posgrado, valida que esté ACTIVO y que la materia sea una sigla o `GENERAL`.<br>5. El sistema guarda la disponibilidad con estado LIBRE.<br>6. El sistema retorna la disponibilidad creada.<br>7. Si alguna validación falla, el sistema rechaza la operación e informa el motivo. |
| **Poscondiciones**           | Se espera como resultado una nueva disponibilidad con estado LIBRE, asociada al tutor y a la materia indicados. |

### 2.2 Requerimiento Funcional 2

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-02 |
| **Nombre del requerimiento** | Consultar disponibilidades |
| **Descripción**              | El sistema debe permitir consultar el listado de las disponibilidades registradas. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado. |
| **Actor**                    | Administrador |
| **Flujo principal**          | 1. El actor solicita el listado de disponibilidades.<br>2. El sistema consulta las disponibilidades almacenadas.<br>3. El sistema registra la consulta en el log.<br>4. El sistema retorna el listado, vacío si no hay disponibilidades. |
| **Poscondiciones**           | Se espera como resultado que el actor reciba el listado de disponibilidades y que la consulta quede registrada en `logs/parcial-t2.log`. |

### 2.3 Requerimiento Funcional 3

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-03 |
| **Nombre del requerimiento** | Consultar mis disponibilidades |
| **Descripción**              | El sistema debe permitir consultar el listado de las disponibilidades registradas por el actor. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado. |
| **Actor**                    | Tutor |
| **Flujo principal**          | 1. El actor solicita el listado de disponibilidades.<br>2. El sistema consulta las disponibilidades almacenadas filtrando por el actor quien la creó.<br>3. El sistema registra la consulta en el log.<br>4. El sistema retorna el listado, vacío si no hay disponibilidades. |
| **Poscondiciones**           | Se espera como resultado que el actor reciba el listado de disponibilidades y que la consulta quede registrada en `logs/parcial-t2.log`. |

### 2.4 Requerimiento Funcional 4

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-04 |
| **Nombre del requerimiento** | Consultar disponibilidad por id |
| **Descripción**              | El sistema debe permitir consultar una disponibilidad a partir de su id. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado y la disponibilidad registrada. |
| **Actor**                    | Tutor, Administrador |
| **Flujo principal**          | 1. El actor envía el id de la disponibilidad.<br>2. El sistema busca la disponibilidad.<br>3. El sistema registra la consulta en el log, incluyendo el id consultado.<br>4. El sistema retorna la disponibilidad.<br>5. Si el id no existe, el sistema informa que la disponibilidad no fue encontrada. |
| **Poscondiciones**           | Se espera como resultado que el actor reciba los datos de la disponibilidad y que el id consultado quede registrado en `logs/parcial-t2.log`. |

### 2.5 Requerimiento Funcional 5

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-05 |
| **Nombre del requerimiento** | Actualizar disponibilidad |
| **Descripción**              | El sistema debe permitir modificar los datos de una disponibilidad existente. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado y la disponibilidad registrada. |
| **Actor**                    | Tutor, Administrador |
| **Flujo principal**          | 1. El actor envía el id de la disponibilidad y los nuevos datos.<br>2. El sistema verifica que la disponibilidad exista.<br>3. El sistema aplica las mismas validaciones de duración y de materia que en la creación. Solo se puede actualizar si la disponibilidad está LIBRE.<br>4. El sistema guarda los cambios.<br>5. El sistema retorna la disponibilidad actualizada.<br>6. Si el id no existe o alguna validación falla, el sistema rechaza la operación e informa el motivo. |
| **Poscondiciones**           | Se espera como resultado que la disponibilidad quede almacenada con los nuevos datos, cumpliendo las reglas de negocio. |

### 2.6 Requerimiento Funcional 6

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-06 |
| **Nombre del requerimiento** | Eliminar disponibilidad |
| **Descripción**              | El sistema debe permitir eliminar una disponibilidad existente. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado y la disponibilidad registrada. |
| **Actor**                    | Tutor, Administrador |
| **Flujo principal**          | 1. El actor envía el id de la disponibilidad.<br>2. El sistema verifica que la disponibilidad exista.<br>3. El sistema elimina la disponibilidad si su estado es LIBRE.<br>4. El sistema confirma la eliminación.<br>5. Si el id no existe, el sistema informa que la disponibilidad no fue encontrada. |
| **Poscondiciones**           | Se espera como resultado que la disponibilidad ya no exista en la base de datos. |

### 2.7 Requerimiento Funcional 7

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-07 |
| **Nombre del requerimiento** | Crear solicitud de tutoría |
| **Descripción**              | El sistema debe permitir que un estudiante de pregrado solicite una tutoría sobre una disponibilidad libre, siempre que esté activo y inscrito en la materia. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado, una disponibilidad con estado LIBRE y a Enlace y NotifyMe disponibles. |
| **Actor**                    | Solicitante |
| **Flujo principal**          | 1. El actor envía el id de la disponibilidad y su correo.<br>2. El sistema verifica que la disponibilidad exista y esté LIBRE.<br>3. El sistema consulta a Enlace con el correo del solicitante y valida que su estado sea ACTIVO.<br>4. El sistema valida que la sigla de la materia de la disponibilidad esté entre las materias inscritas del estudiante. Si la materia es `GENERAL`, solo exige que esté ACTIVO.<br>5. El sistema crea la solicitud con estado ACTIVA y cambia la disponibilidad a OCUPADO.<br>6. El sistema notifica al solicitante (RF-07).<br>7. El sistema retorna la solicitud creada.<br>8. Si alguna validación falla, el sistema rechaza la operación e informa el motivo. |
| **Poscondiciones**           | Se espera como resultado una solicitud ACTIVA asociada a la disponibilidad, la disponibilidad en estado OCUPADO y el solicitante notificado. |

### 2.8 Requerimiento Funcional 8

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-08 |
| **Nombre del requerimiento** | Notificar la creación de la solicitud |
| **Descripción**              | El sistema debe enviar una notificación al solicitante a través de NotifyMe cuando su solicitud sea creada con éxito. |
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente una solicitud creada (RF-07) y a NotifyMe disponible. |
| **Actor**                    | NotifyMe, Solicitante (destinatario) |
| **Flujo principal**          | 1. El sistema arma la cadena con el formato `correo@mail.escuelaing.edu.co\|Mensaje_de_confirmacion`, usando el correo del solicitante.<br>2. El sistema envía la cadena a NotifyMe.<br>3. NotifyMe entrega la notificación al solicitante. |
| **Poscondiciones**           | Se espera como resultado que el solicitante reciba la confirmación de su solicitud. |

### 2.9 Requerimiento Funcional 9

| Campo                        | Descripción |
| ---------------------------- | ----------- |
| **ID**                       | RF-09 |
| **Nombre del requerimiento** | Calcular la cantidad de disponibilidades por tutor |
| **Descripción**              | El sistema debe calcular cuántas disponibilidades tiene registradas un tutor.|
| **Precondiciones**           | Para que el sistema cumpla con este requerimiento, TutoECI debe tener previamente al actor autenticado y el correo del tutor. |
| **Actor**                    | Tutor, Administrador |
| **Flujo principal**          | 1. El actor indica el correo del tutor.<br>2. El sistema valida que el correo no sea nulo ni vacío.<br>3. El sistema cuenta todas las disponibilidades asociadas a ese correo.<br>4. El sistema retorna la cantidad, que es 0 si el tutor no tiene disponibilidades.<br>5. Si el correo es nulo o vacío, el sistema rechaza la operación e informa el motivo. |
| **Poscondiciones**           | Se espera como resultado la cantidad correcta de disponibilidades del tutor, sin contar las de otros tutores. |
