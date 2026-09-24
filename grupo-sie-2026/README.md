# Grupo SIE 2026 – Sistema de Información Electoral

Parcial Práctico 1 – Fundamentos de Ingeniería de Software.
Modelo de clases en Java para la gestión, transparencia y análisis del proceso electoral colombiano 2026.

**Integrantes:** Martin Rodriguez · Miguel Ovalle · Danna · Juan Calderon

## Estructura
```
grupo-sie-2026/
├── docs/
│   ├── HISTORIAS_USUARIO.md   # HU-00 a HU-10 con prioridad, estimación y responsable
│   ├── DIAGRAMA_CLASES.md     # Diagrama UML (Mermaid, se ve en GitHub)
│   ├── diagrama_clases.png
│   ├── GUIA_GITFLOW.md        # Ramas, commits por integrante y merge a develop
│   └── salida-ejemplo.txt
├── scripts/crear_issues.sh    # Crea etiquetas, milestones e issues con GitHub CLI
└── src/main/java/sie/
    ├── App.java               # Demostración de extremo a extremo
    ├── modelo/                # Entidades del dominio
    └── servicio/              # SistemaElectoral, AnalisisResultados, AuditoriaElectoral
```

## Compilar y ejecutar (Java 11+)
```bash
javac -encoding UTF-8 -d out $(find src -name "*.java")
java -cp out sie.App
```

## Clases
| Paquete | Clases |
|---------|--------|
| `sie.modelo` | ProcesoElectoral, Departamento, Municipio, PuestoVotacion, MesaVotacion, Persona (abstracta), Candidato, Jurado, Testigo, PartidoPolitico, AsignacionJurado, ActaEscrutinio, Incidente, Reclamacion + enums TipoEleccion, EstadoProceso, RolJurado, TipoIncidente, EstadoReclamacion |
| `sie.servicio` | SistemaElectoral, AnalisisResultados, AuditoriaElectoral |
