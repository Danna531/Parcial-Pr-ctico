# Historias de usuario – Sistema de Información Electoral 2026

**Equipo:** Martin Rodriguez · Miguel Ovalle · Danna · Juan Calderon  
**Estimación:** puntos de historia (Fibonacci 1, 2, 3, 5, 8)

| ID | Historia | Prioridad | SP | Responsable | Sprint | Issue |
|----|----------|-----------|----|-------------|--------|-------|
| HU-00 | Estructura del proyecto del grupo | Alta | 1 | Danna | 1 | #1 |
| HU-01 | Registrar procesos electorales | Alta | 3 | Martin Rodriguez | 1 | #2 |
| HU-02 | Registrar división territorial | Alta | 3 | Miguel Ovalle | 1 | #3 |
| HU-03 | Registrar puestos y mesas de votación | Alta | 5 | Miguel Ovalle | 1 | #4 |
| HU-04 | Registrar partidos y candidatos | Alta | 5 | Danna | 1 | #5 |
| HU-05 | Designar jurados por mesa | Alta | 5 | Juan Calderon | 2 | #6 |
| HU-06 | Registrar acta de cierre (E-14) | Alta | 8 | Martin Rodriguez | 2 | #7 |
| HU-07 | Reportar incidentes de la jornada | Media | 3 | Juan Calderon | 2 | #8 |
| HU-08 | Radicar y resolver reclamaciones | Media | 5 | Danna | 3 | #9 |
| HU-09 | Consolidar y analizar resultados | Alta | 8 | Miguel Ovalle | 3 | #10 |
| HU-10 | Auditar mesas que requieren revisión | Media | 3 | Juan Calderon | 3 | #11 |

Carga: Danna 11 SP · Martin Rodriguez 11 SP · Miguel Ovalle 16 SP · Juan Calderon 11 SP (49 SP en total).

---

### HU-00 – Estructura del proyecto del grupo
Como equipo de desarrollo, quiero una carpeta del grupo con estructura Java, README y .gitignore, para trabajar sobre una base común.

**Criterios de aceptación**
- Carpeta grupo-sie-2026 con src/main/java/sie/{modelo,servicio} y docs/
- README con instrucciones de compilación y ejecución
- *Clases:* README.md, .gitignore, docs/

### HU-01 – Registrar procesos electorales
Como funcionario de la autoridad electoral, quiero registrar cada proceso con su tipo de elección, fecha y estado, para organizar el calendario electoral.

**Criterios de aceptación**
- No se permiten dos procesos con el mismo id
- Todo proceso nuevo inicia en PROGRAMADO
- Un proceso FINALIZADO o ANULADO no puede cambiar de estado
- *Clases:* ProcesoElectoral, TipoEleccion, EstadoProceso, SistemaElectoral

### HU-02 – Registrar división territorial
Como funcionario, quiero registrar departamentos y municipios con código DANE, para conocer dónde se desarrollará la votación.

**Criterios de aceptación**
- Cada municipio pertenece a un único departamento
- Se puede buscar un municipio por nombre
- *Clases:* Departamento, Municipio

### HU-03 – Registrar puestos y mesas de votación
Como funcionario de logística, quiero registrar los puestos (nombre, dirección, zona) y sus mesas con número y censo, para identificar dónde vota cada ciudadano.

**Criterios de aceptación**
- No se repite el número de mesa dentro de un puesto
- El censo no puede ser negativo
- El censo del puesto es la suma del de sus mesas
- *Clases:* PuestoVotacion, MesaVotacion

### HU-04 – Registrar partidos y candidatos
Como responsable del proceso, quiero registrar partidos y relacionar cada candidato con el partido que lo respalda, para saber quién compite y bajo qué organización.

**Criterios de aceptación**
- Un candidato siempre tiene partido
- Solo se inscriben candidatos con el proceso en INSCRIPCIONES_ABIERTAS
- No se repite el número de tarjetón
- La cédula es numérica de 6 a 10 dígitos
- *Clases:* Persona, PartidoPolitico, Candidato

### HU-05 – Designar jurados por mesa
Como registrador, quiero asignar jurados a cada mesa indicando su función, para dejar constancia de quién instala y atiende la mesa.

**Criterios de aceptación**
- Un ciudadano solo puede ser jurado de una mesa por jornada
- Cada mesa tiene un único presidente y máximo 6 jurados
- La mesa solo se instala si tiene presidente
- *Clases:* Jurado, RolJurado, AsignacionJurado

### HU-06 – Registrar acta de cierre (E-14)
Como jurado de mesa, quiero registrar en el acta los votos de cada candidato, en blanco, nulos y no marcados, para reportar los resultados de la mesa.

**Criterios de aceptación**
- Solo se abre acta en una mesa instalada y una por mesa
- No se aceptan cantidades negativas
- No se puede cerrar un acta cuyo total supere el censo
- Un acta cerrada no puede modificarse
- *Clases:* ActaEscrutinio

### HU-07 – Reportar incidentes de la jornada
Como coordinador de puesto, quiero registrar incidentes (logísticos, de orden público, falta de material…), para dejar trazabilidad de lo ocurrido.

**Criterios de aceptación**
- Todo incidente se asocia a un puesto y opcionalmente a una mesa
- Fecha/hora automática; se marca resuelto con su solución
- Se pueden consultar los incidentes abiertos
- *Clases:* Incidente, TipoIncidente

### HU-08 – Radicar y resolver reclamaciones
Como partido político o testigo electoral, quiero radicar una reclamación sobre un acta, para solicitar la revisión de sus resultados.

**Criterios de aceptación**
- La reclamación exige acta, partido y motivo
- Flujo RADICADA → EN_REVISION → ACEPTADA o RECHAZADA
- Se pueden filtrar reclamaciones por estado
- *Clases:* Testigo, Reclamacion, EstadoReclamacion

### HU-09 – Consolidar y analizar resultados
Como analista electoral, quiero consultar los votos por candidato, por municipio y por departamento, para comparar el desempeño entre regiones.

**Criterios de aceptación**
- Solo se consolidan actas cerradas
- Se obtiene el ganador general y el ganador por departamento
- Se calcula el porcentaje sobre votos válidos y la participación sobre el censo
- *Clases:* AnalisisResultados

### HU-10 – Auditar mesas que requieren revisión
Como auditor, quiero ver las mesas sin acta, con participación atípica o con reclamaciones aceptadas, para priorizar revisiones detalladas.

**Criterios de aceptación**
- El umbral de participación es configurable
- Se listan mesas con reclamación aceptada e incidentes por puesto
- *Clases:* AuditoriaElectoral, App
