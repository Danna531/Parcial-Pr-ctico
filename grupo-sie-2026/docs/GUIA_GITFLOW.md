# Guía GitFlow y Scrum del grupo

Repo: https://github.com/Danna531/Parcial-Pr-ctico  
Rama del grupo: `feature/grupo-sie-2026` · Carpeta del grupo: `grupo-sie-2026/`

> Si el repo no tiene issues previos, el script crea HU-00 = **#1**, HU-01 = **#2** … HU-10 = **#11**. Si hay otros issues, ajusten los números en los commits.

## 0. Preparación (una vez)
1. **Danna** (dueña del repo) agrega a Martin, Miguel y Juan como colaboradores: *Settings → Collaborators*.
2. Instalar GitHub CLI y autenticarse: `brew install gh && gh auth login`.
3. Editar los usuarios en `scripts/crear_issues.sh` y ejecutarlo: `bash scripts/crear_issues.sh`.
4. Tablero: *Projects → New project → Roadmap*. Agregar los 11 issues; crear campos **Priority**, **Estimate** (número) e **Iteration** (Sprint 1/2/3); abrir la vista Roadmap. → captura.

## 1. Crear `develop` y la rama del grupo (Danna)
```bash
git clone https://github.com/Danna531/Parcial-Pr-ctico.git && cd Parcial-Pr-ctico
git checkout -b develop
git push -u origin develop
git checkout -b feature/grupo-sie-2026 develop
git push -u origin feature/grupo-sie-2026
```
📸 Captura: la terminal con estos comandos y `git branch -a`.

## 2. Commits por integrante (conventional commits + issue)
Cada integrante, desde **su** cuenta y en este orden:
```bash
git checkout feature/grupo-sie-2026 && git pull
# copiar SUS archivos dentro de grupo-sie-2026/...
git add <archivos>
git commit -m "<mensaje de la tabla>"
git push
```

| # | Quién | Archivos | Mensaje de commit |
|---|-------|----------|-------------------|
| 1 | Danna | `README.md, .gitignore, docs/, scripts/` | `chore(grupo): crea estructura inicial del proyecto (#1)` |
| 2 | Martin | `modelo/TipoEleccion.java, modelo/EstadoProceso.java, modelo/ProcesoElectoral.java` | `feat(proceso): agrega ProcesoElectoral con tipo, fecha y estado (#2)` |
| 3 | Miguel | `modelo/Departamento.java, modelo/Municipio.java` | `feat(territorio): agrega Departamento y Municipio (#3)` |
| 4 | Miguel | `modelo/PuestoVotacion.java, modelo/MesaVotacion.java` | `feat(territorio): agrega puestos y mesas de votación con censo (#4)` |
| 5 | Danna | `modelo/Persona.java, modelo/PartidoPolitico.java, modelo/Candidato.java` | `feat(candidatos): agrega partidos y candidatos (#5)` |
| 6 | Martin | `servicio/SistemaElectoral.java` | `feat(core): agrega SistemaElectoral para registro central (#2)` |
| 7 | Juan | `modelo/Jurado.java, modelo/RolJurado.java, modelo/AsignacionJurado.java` | `feat(jornada): designación de jurados por mesa (#6)` |
| 8 | Martin | `modelo/ActaEscrutinio.java` | `feat(jornada): registro y cierre de acta E-14 (#7)` |
| 9 | Juan | `modelo/Incidente.java, modelo/TipoIncidente.java` | `feat(jornada): reporte de incidentes (#8)` |
| 10 | Danna | `modelo/Testigo.java, modelo/Reclamacion.java, modelo/EstadoReclamacion.java` | `feat(escrutinio): radicación y resolución de reclamaciones (#9)` |
| 11 | Miguel | `servicio/AnalisisResultados.java` | `feat(analitica): consolida resultados por candidato y territorio (#10)` |
| 12 | Juan | `servicio/AuditoriaElectoral.java, App.java, docs/salida-ejemplo.txt` | `feat(auditoria): alertas de mesas y demo de extremo a extremo (#11)` |

Rutas: `modelo/` y `servicio/` = `grupo-sie-2026/src/main/java/sie/modelo|servicio/`; `App.java` va en `grupo-sie-2026/src/main/java/sie/`.

Formato: `tipo(alcance): descripción (#issue)`. Tipos: `feat`, `fix`, `docs`, `refactor`, `test`, `chore`.

📸 Captura: pestaña *Commits* de `feature/grupo-sie-2026` y un issue mostrando el commit que lo referencia.

## 3. Merge a `develop`
**Opción A – Pull Request (recomendado):** *Pull requests → New* · base `develop` ← compare `feature/grupo-sie-2026` · título `feat(grupo-sie-2026): modelo de clases del sistema electoral` · cuerpo `Closes #1, closes #2, … closes #11` · un compañero aprueba → **Create a merge commit**.

**Opción B – terminal:**
```bash
git checkout develop && git pull
git merge --no-ff feature/grupo-sie-2026 -m "merge: integra feature/grupo-sie-2026 en develop"
git push origin develop
```
📸 Capturas: PR fusionado y `git log --oneline --graph --all` (o *Insights → Network*).

## 4. Verificar
```bash
cd grupo-sie-2026
javac -encoding UTF-8 -d out $(find src -name "*.java") && java -cp out sie.App
```
