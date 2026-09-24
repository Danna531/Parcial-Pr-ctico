package sie.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Departamento de la división territorial (HU-02). */
public class Departamento {
    private final String codigoDane;
    private final String nombre;
    private final List<Municipio> municipios = new ArrayList<>();

    public Departamento(String codigoDane, String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("El nombre es obligatorio");
        this.codigoDane = codigoDane;
        this.nombre = nombre;
    }

    /** Crea el municipio y lo deja asociado a este departamento. */
    public Municipio agregarMunicipio(String codigoDane, String nombre) {
        Municipio m = new Municipio(codigoDane, nombre, this);
        municipios.add(m);
        return m;
    }

    public Municipio buscarMunicipio(String nombre) {
        return municipios.stream()
                .filter(m -> m.getNombre().equalsIgnoreCase(nombre))
                .findFirst().orElse(null);
    }

    public String getCodigoDane() { return codigoDane; }
    public String getNombre() { return nombre; }
    public List<Municipio> getMunicipios() { return Collections.unmodifiableList(municipios); }

    @Override
    public String toString() { return nombre + " [" + codigoDane + "]"; }
}
