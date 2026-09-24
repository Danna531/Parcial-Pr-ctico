package sie.modelo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Proceso electoral registrado por la autoridad (HU-01).
 * Agrupa los candidatos que compiten y los puestos donde se vota.
 */
public class ProcesoElectoral {
    private final String id;
    private final TipoEleccion tipo;
    private LocalDate fecha;
    private EstadoProceso estado;
    private final List<Candidato> candidatos = new ArrayList<>();
    private final List<PuestoVotacion> puestos = new ArrayList<>();

    public ProcesoElectoral(String id, TipoEleccion tipo, LocalDate fecha) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("El id es obligatorio");
        if (tipo == null || fecha == null) throw new IllegalArgumentException("Tipo y fecha son obligatorios");
        this.id = id;
        this.tipo = tipo;
        this.fecha = fecha;
        this.estado = EstadoProceso.PROGRAMADO;
    }

    /** Cambia el estado; un proceso finalizado o anulado ya no puede cambiar. */
    public void cambiarEstado(EstadoProceso nuevo) {
        if (estado == EstadoProceso.FINALIZADO || estado == EstadoProceso.ANULADO) {
            throw new IllegalStateException("El proceso " + id + " ya está cerrado (" + estado + ")");
        }
        this.estado = nuevo;
    }

    /** Solo se inscriben candidatos mientras las inscripciones estén abiertas. */
    public void inscribirCandidato(Candidato c) {
        if (estado != EstadoProceso.INSCRIPCIONES_ABIERTAS) {
            throw new IllegalStateException("Las inscripciones no están abiertas");
        }
        boolean tarjetonRepetido = candidatos.stream()
                .anyMatch(x -> x.getNumeroTarjeton() == c.getNumeroTarjeton());
        if (tarjetonRepetido) throw new IllegalArgumentException("Número de tarjetón repetido");
        candidatos.add(c);
    }

    public void habilitarPuesto(PuestoVotacion p) {
        if (!puestos.contains(p)) puestos.add(p);
    }

    /** Todas las mesas del proceso, recorriendo sus puestos. */
    public List<MesaVotacion> getMesas() {
        List<MesaVotacion> mesas = new ArrayList<>();
        for (PuestoVotacion p : puestos) mesas.addAll(p.getMesas());
        return mesas;
    }

    public int getCensoTotal() {
        return getMesas().stream().mapToInt(MesaVotacion::getCenso).sum();
    }

    public String getId() { return id; }
    public TipoEleccion getTipo() { return tipo; }
    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }
    public EstadoProceso getEstado() { return estado; }
    public List<Candidato> getCandidatos() { return Collections.unmodifiableList(candidatos); }
    public List<PuestoVotacion> getPuestos() { return Collections.unmodifiableList(puestos); }

    @Override
    public String toString() {
        return tipo + " (" + fecha + ") - " + estado;
    }
}
