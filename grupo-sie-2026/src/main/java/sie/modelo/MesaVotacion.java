package sie.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Mesa de votación con su censo, jurados y acta de cierre (HU-03, HU-05, HU-06). */
public class MesaVotacion {
    public static final int MAX_JURADOS = 6;

    private final int numero;
    private final int censo;
    private final PuestoVotacion puesto;
    private boolean instalada;
    private final List<AsignacionJurado> jurados = new ArrayList<>();
    private ActaEscrutinio acta;

    public MesaVotacion(int numero, int censo, PuestoVotacion puesto) {
        if (numero <= 0) throw new IllegalArgumentException("El número de mesa debe ser positivo");
        if (censo < 0) throw new IllegalArgumentException("El censo no puede ser negativo");
        this.numero = numero;
        this.censo = censo;
        this.puesto = puesto;
    }

    /** Un jurado no puede repetirse en la misma mesa y solo hay un presidente. */
    public AsignacionJurado asignarJurado(Jurado jurado, RolJurado rol) {
        if (jurados.size() >= MAX_JURADOS) throw new IllegalStateException("La mesa ya tiene " + MAX_JURADOS + " jurados");
        if (jurados.stream().anyMatch(a -> a.getJurado().equals(jurado))) {
            throw new IllegalArgumentException("El jurado ya está asignado a esta mesa");
        }
        if (rol == RolJurado.PRESIDENTE && getPresidente() != null) {
            throw new IllegalArgumentException("La mesa ya tiene presidente");
        }
        AsignacionJurado a = new AsignacionJurado(jurado, this, rol);
        jurados.add(a);
        return a;
    }

    public Jurado getPresidente() {
        return jurados.stream().filter(a -> a.getRol() == RolJurado.PRESIDENTE)
                .map(AsignacionJurado::getJurado).findFirst().orElse(null);
    }

    /** La mesa se instala solo si tiene presidente asignado. */
    public void instalar() {
        if (getPresidente() == null) throw new IllegalStateException("No se puede instalar sin presidente");
        instalada = true;
    }

    /** Abre el acta de la mesa; exige que la mesa esté instalada y no tenga acta previa. */
    public ActaEscrutinio abrirActa(String idActa) {
        if (!instalada) throw new IllegalStateException("La mesa " + numero + " no ha sido instalada");
        if (acta != null) throw new IllegalStateException("La mesa ya tiene acta");
        acta = new ActaEscrutinio(idActa, this);
        return acta;
    }

    public int getNumero() { return numero; }
    public int getCenso() { return censo; }
    public PuestoVotacion getPuesto() { return puesto; }
    public boolean isInstalada() { return instalada; }
    public List<AsignacionJurado> getJurados() { return Collections.unmodifiableList(jurados); }
    public ActaEscrutinio getActa() { return acta; }

    @Override
    public String toString() { return "Mesa " + numero + " - " + puesto.getNombre() + " (censo " + censo + ")"; }
}
