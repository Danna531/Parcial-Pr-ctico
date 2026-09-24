package sie.modelo;

import java.time.LocalDateTime;

/** Novedad ocurrida en un puesto o mesa durante la jornada (HU-07). */
public class Incidente {
    private final String id;
    private final TipoIncidente tipo;
    private final String descripcion;
    private final LocalDateTime fechaHora;
    private final PuestoVotacion puesto;
    private final MesaVotacion mesa; // opcional: puede afectar todo el puesto
    private boolean resuelto;
    private String solucion;

    public Incidente(String id, TipoIncidente tipo, String descripcion, PuestoVotacion puesto, MesaVotacion mesa) {
        if (puesto == null) throw new IllegalArgumentException("El incidente debe asociarse a un puesto");
        this.id = id;
        this.tipo = tipo;
        this.descripcion = descripcion;
        this.puesto = puesto;
        this.mesa = mesa;
        this.fechaHora = LocalDateTime.now();
    }

    public void resolver(String solucion) {
        this.solucion = solucion;
        this.resuelto = true;
    }

    public String getId() { return id; }
    public TipoIncidente getTipo() { return tipo; }
    public String getDescripcion() { return descripcion; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public PuestoVotacion getPuesto() { return puesto; }
    public MesaVotacion getMesa() { return mesa; }
    public boolean isResuelto() { return resuelto; }
    public String getSolucion() { return solucion; }

    @Override
    public String toString() {
        return "[" + tipo + "] " + descripcion + " @ " + puesto.getNombre() + (resuelto ? " (resuelto)" : " (abierto)");
    }
}
