package sie.modelo;

/** Testigo electoral acreditado por un partido; puede radicar reclamaciones (HU-08). */
public class Testigo extends Persona {
    private final PartidoPolitico partido;

    public Testigo(String cedula, String nombres, String apellidos, PartidoPolitico partido) {
        super(cedula, nombres, apellidos);
        this.partido = partido;
    }

    public PartidoPolitico getPartido() { return partido; }
}
