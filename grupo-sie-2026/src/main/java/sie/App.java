package sie;

import sie.modelo.*;
import sie.servicio.AnalisisResultados;
import sie.servicio.AuditoriaElectoral;
import sie.servicio.SistemaElectoral;

import java.time.LocalDate;
import java.util.Map;

/** Demostración de extremo a extremo con datos de ejemplo. */
public class App {
    public static void main(String[] args) {
        SistemaElectoral sistema = new SistemaElectoral();

        // HU-01: proceso electoral
        ProcesoElectoral pres = sistema.registrarProceso("PRES-2026-1",
                TipoEleccion.PRESIDENCIAL_PRIMERA_VUELTA, LocalDate.of(2026, 5, 31));
        pres.cambiarEstado(EstadoProceso.INSCRIPCIONES_ABIERTAS);

        // HU-02 / HU-03: territorio, puestos y mesas
        Departamento cund = sistema.registrarDepartamento("25", "Cundinamarca");
        Departamento ant = sistema.registrarDepartamento("05", "Antioquia");
        Municipio bogota = cund.agregarMunicipio("11001", "Bogotá D.C.");
        Municipio medellin = ant.agregarMunicipio("05001", "Medellín");

        PuestoVotacion javeriana = bogota.habilitarPuesto("BOG-001", "Universidad Javeriana", "Cra 7 #40-62", "Chapinero");
        PuestoVotacion plazaMayor = medellin.habilitarPuesto("MED-001", "Plaza Mayor", "Cl 41 #55-80", "La Candelaria");
        MesaVotacion m1 = javeriana.agregarMesa(1, 350);
        MesaVotacion m2 = javeriana.agregarMesa(2, 320);
        MesaVotacion m3 = plazaMayor.agregarMesa(1, 360);
        pres.habilitarPuesto(javeriana);
        pres.habilitarPuesto(plazaMayor);

        // HU-04: partidos y candidatos
        PartidoPolitico pA = sistema.registrarPartido("P01", "Partido Alianza Ciudadana", "PAC");
        PartidoPolitico pB = sistema.registrarPartido("P02", "Movimiento Renovación", "MR");
        Candidato c1 = new Candidato("10101010", "Ana", "Gómez", pA, "Presidencia", 1);
        Candidato c2 = new Candidato("20202020", "Carlos", "Pérez", pB, "Presidencia", 2);
        pres.inscribirCandidato(c1);
        pres.inscribirCandidato(c2);
        pres.cambiarEstado(EstadoProceso.EN_JORNADA);

        // HU-05: jurados
        MesaVotacion[] mesas = {m1, m2, m3};
        int cc = 30000001;
        for (MesaVotacion m : mesas) {
            sistema.designarJurado(new Jurado(String.valueOf(cc++), "Jurado", "Pres " + m.getNumero(), "3000000000"), m, RolJurado.PRESIDENTE);
            sistema.designarJurado(new Jurado(String.valueOf(cc++), "Jurado", "Vocal " + m.getNumero(), "3000000001"), m, RolJurado.VOCAL);
            m.instalar();
        }

        // HU-07: incidente durante la jornada
        Incidente inc = sistema.reportarIncidente(TipoIncidente.FALTA_DE_MATERIAL,
                "Faltan tarjetones en la mesa 2", javeriana, m2);

        // HU-06: actas de cierre {c1, c2, blanco, nulos, no marcados}
        int[][] votos = {{150, 120, 10, 5, 3}, {100, 140, 8, 4, 2}, {90, 230, 12, 6, 1}};
        for (int i = 0; i < mesas.length; i++) {
            ActaEscrutinio acta = mesas[i].abrirActa("E14-" + (i + 1));
            acta.registrarVotos(c1, votos[i][0]);
            acta.registrarVotos(c2, votos[i][1]);
            acta.registrarEspeciales(votos[i][2], votos[i][3], votos[i][4]);
            acta.cerrar();
        }
        inc.resolver("Se enviaron 200 tarjetones desde la registraduría local");
        pres.cambiarEstado(EstadoProceso.EN_ESCRUTINIO);

        // HU-08: reclamación
        Testigo t = new Testigo("40404040", "Laura", "Ríos", pA);
        Reclamacion rec = sistema.radicarReclamacion(m3.getActa(), pA, t, "Posible error de suma en votos del candidato 2");
        rec.iniciarRevision();
        rec.resolver(false, "Reconteo confirma los valores del acta");

        // HU-09: análisis de resultados
        AnalisisResultados analisis = new AnalisisResultados(pres);
        System.out.println("=== " + pres + " ===");
        for (Map.Entry<Candidato, Integer> e : analisis.votosPorCandidato().entrySet()) {
            System.out.printf("%-32s %5d votos (%.2f%%)%n", e.getKey(), e.getValue(), analisis.porcentaje(e.getKey()));
        }
        System.out.println("Ganador: " + analisis.ganador());
        System.out.printf("Participación: %.2f%%%n", analisis.participacionGeneral());
        System.out.println("Votos de " + c1.getNombreCompleto() + " por departamento: " + analisis.votosPorDepartamento(c1));
        System.out.println("Ganador en Antioquia: " + analisis.ganadorEnDepartamento("Antioquia"));

        // HU-10: auditoría
        AuditoriaElectoral auditoria = new AuditoriaElectoral(sistema, pres);
        System.out.println("Mesas para revisión (>85%): " + auditoria.mesasParaRevision(85));
        System.out.println("Mesas con reclamación aceptada: " + auditoria.mesasConReclamacionAceptada());
        System.out.println("Incidentes en " + javeriana.getNombre() + ": " + auditoria.incidentesEnPuesto(javeriana));
        System.out.println("Incidentes: " + sistema.getIncidentes());
        System.out.println("Reclamaciones: " + sistema.getReclamaciones());
        pres.cambiarEstado(EstadoProceso.FINALIZADO);
        System.out.println("Estado final: " + pres.getEstado());
    }
}
