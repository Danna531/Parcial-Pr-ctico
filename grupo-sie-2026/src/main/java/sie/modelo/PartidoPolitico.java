package sie.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Organización política que avala candidatos y puede radicar reclamaciones (HU-04). */
public class PartidoPolitico {
    private final String codigo;
    private final String nombre;
    private final String sigla;
    private final List<Candidato> candidatos = new ArrayList<>();

    public PartidoPolitico(String codigo, String nombre, String sigla) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        this.codigo = codigo;
        this.nombre = nombre;
        this.sigla = sigla;
    }

    /** Relación bidireccional: la invoca el constructor de Candidato. */
    void agregarCandidato(Candidato c) {
        if (!candidatos.contains(c)) candidatos.add(c);
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getSigla() { return sigla; }
    public List<Candidato> getCandidatos() { return Collections.unmodifiableList(candidatos); }

    @Override
    public String toString() { return nombre + " (" + sigla + ")"; }
}
