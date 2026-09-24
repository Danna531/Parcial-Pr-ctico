package sie.modelo;

import java.time.LocalDateTime;

/** Reclamación de un partido o testigo sobre un acta (HU-08). */
public class Reclamacion {
    private final String id;
    private final ActaEscrutinio acta;
    private final PartidoPolitico partido;
    private final Testigo testigo; // opcional
    private final String motivo;
    private final LocalDateTime fechaRadicacion;
    private EstadoReclamacion estado;
    private String respuesta;

    public Reclamacion(String id, ActaEscrutinio acta, PartidoPolitico partido, Testigo testigo, String motivo) {
        if (acta == null || partido == null) throw new IllegalArgumentException("Acta y partido son obligatorios");
        if (motivo == null || motivo.isBlank()) throw new IllegalArgumentException("Debe indicar el motivo");
        this.id = id;
        this.acta = acta;
        this.partido = partido;
        this.testigo = testigo;
        this.motivo = motivo;
        this.fechaRadicacion = LocalDateTime.now();
        this.estado = EstadoReclamacion.RADICADA;
    }

    public void iniciarRevision() {
        if (estado != EstadoReclamacion.RADICADA) throw new IllegalStateException("Solo se revisan reclamaciones radicadas");
        estado = EstadoReclamacion.EN_REVISION;
    }

    /** Resuelve la reclamación; debe estar en revisión. */
    public void resolver(boolean aceptada, String respuesta) {
        if (estado != EstadoReclamacion.EN_REVISION) throw new IllegalStateException("La reclamación no está en revisión");
        this.estado = aceptada ? EstadoReclamacion.ACEPTADA : EstadoReclamacion.RECHAZADA;
        this.respuesta = respuesta;
    }

    public String getId() { return id; }
    public ActaEscrutinio getActa() { return acta; }
    public PartidoPolitico getPartido() { return partido; }
    public Testigo getTestigo() { return testigo; }
    public String getMotivo() { return motivo; }
    public LocalDateTime getFechaRadicacion() { return fechaRadicacion; }
    public EstadoReclamacion getEstado() { return estado; }
    public String getRespuesta() { return respuesta; }

    @Override
    public String toString() {
        return id + " - " + partido.getSigla() + " sobre acta " + acta.getId() + ": " + motivo + " [" + estado + "]";
    }
}
