#!/usr/bin/env bash
# Crea etiquetas, milestones (sprints) e issues (historias de usuario) en GitHub.
# Requisitos: GitHub CLI (brew install gh) y `gh auth login`.
# Uso: bash scripts/crear_issues.sh
set -euo pipefail

REPO="Danna531/Parcial-Pr-ctico"

# >>> Cambien por los usuarios de GitHub reales <<<
U_Martin="usuario-martin"
U_Miguel="usuario-miguel"
U_Danna="Danna531"
U_Juan="usuario-juan"

lbl() { gh label create "$1" --repo "$REPO" --color "$2" --description "$3" --force; }
lbl "historia-usuario" "1D76DB" "Historia de usuario"
lbl "prioridad: alta" "B60205" "Prioridad alta"
lbl "prioridad: media" "FBCA04" "Prioridad media"
lbl "prioridad: baja" "0E8A16" "Prioridad baja"
for sp in 1 2 3 5 8; do lbl "SP: $sp" "C5DEF5" "Estimación: $sp puntos de historia"; done
lbl "modulo: candidatos" "D4C5F9" "Módulo candidatos"
lbl "modulo: configuracion" "D4C5F9" "Módulo configuracion"
lbl "modulo: escrutinio" "D4C5F9" "Módulo escrutinio"
lbl "modulo: jornada" "D4C5F9" "Módulo jornada"
lbl "modulo: territorio" "D4C5F9" "Módulo territorio"

ms() { gh api "repos/$REPO/milestones" -f title="$1" -f description="$3" -f due_on="$2T23:59:59Z" >/dev/null || true; }
ms "Sprint 1 - Base y territorio" "2026-09-25" "HU-00 a HU-04: estructura, procesos, territorio, partidos y candidatos"
ms "Sprint 2 - Jornada electoral" "2026-09-28" "HU-05 a HU-07: jurados, actas e incidentes"
ms "Sprint 3 - Escrutinio y análisis" "2026-10-01" "HU-08 a HU-10: reclamaciones, resultados y auditoría"

hu() { gh issue create --repo "$REPO" --title "$1" --assignee "$2" --milestone "$3" --label "$4" --body "$5"; }

hu "HU-00 Estructura del proyecto del grupo" "$U_Danna" "Sprint 1 - Base y territorio" "historia-usuario,prioridad: alta,SP: 1,modulo: configuracion" "**Como equipo de desarrollo, quiero una carpeta del grupo con estructura Java, README y .gitignore, para trabajar sobre una base común.**

**Criterios de aceptación**
- [ ] Carpeta grupo-sie-2026 con src/main/java/sie/{modelo,servicio} y docs/
- [ ] README con instrucciones de compilación y ejecución

**Clases:** README.md, .gitignore, docs/
**Estimación:** 1 SP · **Prioridad:** Alta"
hu "HU-01 Registrar procesos electorales" "$U_Martin" "Sprint 1 - Base y territorio" "historia-usuario,prioridad: alta,SP: 3,modulo: configuracion" "**Como funcionario de la autoridad electoral, quiero registrar cada proceso con su tipo de elección, fecha y estado, para organizar el calendario electoral.**

**Criterios de aceptación**
- [ ] No se permiten dos procesos con el mismo id
- [ ] Todo proceso nuevo inicia en PROGRAMADO
- [ ] Un proceso FINALIZADO o ANULADO no puede cambiar de estado

**Clases:** ProcesoElectoral, TipoEleccion, EstadoProceso, SistemaElectoral
**Estimación:** 3 SP · **Prioridad:** Alta"
hu "HU-02 Registrar división territorial" "$U_Miguel" "Sprint 1 - Base y territorio" "historia-usuario,prioridad: alta,SP: 3,modulo: territorio" "**Como funcionario, quiero registrar departamentos y municipios con código DANE, para conocer dónde se desarrollará la votación.**

**Criterios de aceptación**
- [ ] Cada municipio pertenece a un único departamento
- [ ] Se puede buscar un municipio por nombre

**Clases:** Departamento, Municipio
**Estimación:** 3 SP · **Prioridad:** Alta"
hu "HU-03 Registrar puestos y mesas de votación" "$U_Miguel" "Sprint 1 - Base y territorio" "historia-usuario,prioridad: alta,SP: 5,modulo: territorio" "**Como funcionario de logística, quiero registrar los puestos (nombre, dirección, zona) y sus mesas con número y censo, para identificar dónde vota cada ciudadano.**

**Criterios de aceptación**
- [ ] No se repite el número de mesa dentro de un puesto
- [ ] El censo no puede ser negativo
- [ ] El censo del puesto es la suma del de sus mesas

**Clases:** PuestoVotacion, MesaVotacion
**Estimación:** 5 SP · **Prioridad:** Alta"
hu "HU-04 Registrar partidos y candidatos" "$U_Danna" "Sprint 1 - Base y territorio" "historia-usuario,prioridad: alta,SP: 5,modulo: candidatos" "**Como responsable del proceso, quiero registrar partidos y relacionar cada candidato con el partido que lo respalda, para saber quién compite y bajo qué organización.**

**Criterios de aceptación**
- [ ] Un candidato siempre tiene partido
- [ ] Solo se inscriben candidatos con el proceso en INSCRIPCIONES_ABIERTAS
- [ ] No se repite el número de tarjetón
- [ ] La cédula es numérica de 6 a 10 dígitos

**Clases:** Persona, PartidoPolitico, Candidato
**Estimación:** 5 SP · **Prioridad:** Alta"
hu "HU-05 Designar jurados por mesa" "$U_Juan" "Sprint 2 - Jornada electoral" "historia-usuario,prioridad: alta,SP: 5,modulo: jornada" "**Como registrador, quiero asignar jurados a cada mesa indicando su función, para dejar constancia de quién instala y atiende la mesa.**

**Criterios de aceptación**
- [ ] Un ciudadano solo puede ser jurado de una mesa por jornada
- [ ] Cada mesa tiene un único presidente y máximo 6 jurados
- [ ] La mesa solo se instala si tiene presidente

**Clases:** Jurado, RolJurado, AsignacionJurado
**Estimación:** 5 SP · **Prioridad:** Alta"
hu "HU-06 Registrar acta de cierre (E-14)" "$U_Martin" "Sprint 2 - Jornada electoral" "historia-usuario,prioridad: alta,SP: 8,modulo: jornada" "**Como jurado de mesa, quiero registrar en el acta los votos de cada candidato, en blanco, nulos y no marcados, para reportar los resultados de la mesa.**

**Criterios de aceptación**
- [ ] Solo se abre acta en una mesa instalada y una por mesa
- [ ] No se aceptan cantidades negativas
- [ ] No se puede cerrar un acta cuyo total supere el censo
- [ ] Un acta cerrada no puede modificarse

**Clases:** ActaEscrutinio
**Estimación:** 8 SP · **Prioridad:** Alta"
hu "HU-07 Reportar incidentes de la jornada" "$U_Juan" "Sprint 2 - Jornada electoral" "historia-usuario,prioridad: media,SP: 3,modulo: jornada" "**Como coordinador de puesto, quiero registrar incidentes (logísticos, de orden público, falta de material…), para dejar trazabilidad de lo ocurrido.**

**Criterios de aceptación**
- [ ] Todo incidente se asocia a un puesto y opcionalmente a una mesa
- [ ] Fecha/hora automática; se marca resuelto con su solución
- [ ] Se pueden consultar los incidentes abiertos

**Clases:** Incidente, TipoIncidente
**Estimación:** 3 SP · **Prioridad:** Media"
hu "HU-08 Radicar y resolver reclamaciones" "$U_Danna" "Sprint 3 - Escrutinio y análisis" "historia-usuario,prioridad: media,SP: 5,modulo: escrutinio" "**Como partido político o testigo electoral, quiero radicar una reclamación sobre un acta, para solicitar la revisión de sus resultados.**

**Criterios de aceptación**
- [ ] La reclamación exige acta, partido y motivo
- [ ] Flujo RADICADA → EN_REVISION → ACEPTADA o RECHAZADA
- [ ] Se pueden filtrar reclamaciones por estado

**Clases:** Testigo, Reclamacion, EstadoReclamacion
**Estimación:** 5 SP · **Prioridad:** Media"
hu "HU-09 Consolidar y analizar resultados" "$U_Miguel" "Sprint 3 - Escrutinio y análisis" "historia-usuario,prioridad: alta,SP: 8,modulo: escrutinio" "**Como analista electoral, quiero consultar los votos por candidato, por municipio y por departamento, para comparar el desempeño entre regiones.**

**Criterios de aceptación**
- [ ] Solo se consolidan actas cerradas
- [ ] Se obtiene el ganador general y el ganador por departamento
- [ ] Se calcula el porcentaje sobre votos válidos y la participación sobre el censo

**Clases:** AnalisisResultados
**Estimación:** 8 SP · **Prioridad:** Alta"
hu "HU-10 Auditar mesas que requieren revisión" "$U_Juan" "Sprint 3 - Escrutinio y análisis" "historia-usuario,prioridad: media,SP: 3,modulo: escrutinio" "**Como auditor, quiero ver las mesas sin acta, con participación atípica o con reclamaciones aceptadas, para priorizar revisiones detalladas.**

**Criterios de aceptación**
- [ ] El umbral de participación es configurable
- [ ] Se listan mesas con reclamación aceptada e incidentes por puesto

**Clases:** AuditoriaElectoral, App
**Estimación:** 3 SP · **Prioridad:** Media"

echo "Listo: https://github.com/$REPO/issues"
