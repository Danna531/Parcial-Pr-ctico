package sie.servicio;

import sie.modelo.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Revisión de trazabilidad (HU-10): señala mesas que merecen una revisión
 * detallada combinando actas, incidentes y reclamaciones.
 */
public class AuditoriaElectoral {
    private final SistemaElectoral sistema;
    private final ProcesoElectoral proceso;

    public AuditoriaElectoral(SistemaElectoral sistema, ProcesoElectoral proceso) {
        this.sistema = sistema;
        this.proceso = proceso;
    }

    /** Mesas sin acta cerrada o con participación por encima del umbral. */
    public List<String> mesasParaRevision(double umbralParticipacion) {
        List<String> alertas = new ArrayList<>();
        for (MesaVotacion m : proceso.getMesas()) {
            ActaEscrutinio a = m.getActa();
            if (a == null || !a.isCerrada()) {
                alertas.add(m + ": sin acta cerrada");
            } else if (a.getParticipacion() > umbralParticipacion) {
                alertas.add(String.format("%s: participación atípica %.1f%%", m, a.getParticipacion()));
            }
        }
        return alertas;
    }

    /** Mesas cuyas actas tienen al menos una reclamación aceptada. */
    public List<MesaVotacion> mesasConReclamacionAceptada() {
        List<MesaVotacion> r = new ArrayList<>();
        for (Reclamacion rec : sistema.reclamacionesPorEstado(EstadoReclamacion.ACEPTADA)) {
            MesaVotacion m = rec.getActa().getMesa();
            if (!r.contains(m)) r.add(m);
        }
        return r;
    }

    /** Número de incidentes reportados en un puesto. */
    public long incidentesEnPuesto(PuestoVotacion puesto) {
        return sistema.getIncidentes().stream().filter(i -> i.getPuesto().equals(puesto)).count();
    }
}
