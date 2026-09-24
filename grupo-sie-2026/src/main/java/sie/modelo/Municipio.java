package sie.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Municipio donde se habilitan puestos de votación (HU-02). */
public class Municipio {
    private final String codigoDane;
    private final String nombre;
    private final Departamento departamento;
    private final List<PuestoVotacion> puestos = new ArrayList<>();

    public Municipio(String codigoDane, String nombre, Departamento departamento) {
        if (departamento == null) throw new IllegalArgumentException("El municipio debe tener departamento");
        this.codigoDane = codigoDane;
        this.nombre = nombre;
        this.departamento = departamento;
    }

    public PuestoVotacion habilitarPuesto(String codigo, String nombre, String direccion, String zona) {
        PuestoVotacion p = new PuestoVotacion(codigo, nombre, direccion, zona, this);
        puestos.add(p);
        return p;
    }

    public String getCodigoDane() { return codigoDane; }
    public String getNombre() { return nombre; }
    public Departamento getDepartamento() { return departamento; }
    public List<PuestoVotacion> getPuestos() { return Collections.unmodifiableList(puestos); }

    @Override
    public String toString() { return nombre + " (" + departamento.getNombre() + ")"; }
}
