package sie.modelo;

/** Candidato inscrito, respaldado por un partido (HU-04). */
public class Candidato extends Persona {
    private final PartidoPolitico partido;
    private final String cargo;
    private final int numeroTarjeton;

    public Candidato(String cedula, String nombres, String apellidos,
                     PartidoPolitico partido, String cargo, int numeroTarjeton) {
        super(cedula, nombres, apellidos);
        if (partido == null) throw new IllegalArgumentException("El candidato debe tener partido");
        this.partido = partido;
        this.cargo = cargo;
        this.numeroTarjeton = numeroTarjeton;
        partido.agregarCandidato(this);
    }

    public PartidoPolitico getPartido() { return partido; }
    public String getCargo() { return cargo; }
    public int getNumeroTarjeton() { return numeroTarjeton; }

    @Override
    public String toString() {
        return "#" + numeroTarjeton + " " + getNombreCompleto() + " - " + partido.getSigla();
    }
}
