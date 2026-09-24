package sie.modelo;

/** Clase de asociación Jurado–Mesa con la función que cumple (HU-05). */
public class AsignacionJurado {
    private final Jurado jurado;
    private final MesaVotacion mesa;
    private final RolJurado rol;
    private boolean asistio;

    public AsignacionJurado(Jurado jurado, MesaVotacion mesa, RolJurado rol) {
        this.jurado = jurado;
        this.mesa = mesa;
        this.rol = rol;
    }

    public void registrarAsistencia() { this.asistio = true; }

    public Jurado getJurado() { return jurado; }
    public MesaVotacion getMesa() { return mesa; }
    public RolJurado getRol() { return rol; }
    public boolean isAsistio() { return asistio; }

    @Override
    public String toString() { return rol + ": " + jurado.getNombreCompleto(); }
}
