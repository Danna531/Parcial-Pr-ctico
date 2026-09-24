package sie.modelo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** Lugar físico donde se instalan las mesas (HU-03). */
public class PuestoVotacion {
    private final String codigo;
    private final String nombre;
    private final String direccion;
    private final String zona;
    private final Municipio municipio;
    private final List<MesaVotacion> mesas = new ArrayList<>();

    public PuestoVotacion(String codigo, String nombre, String direccion, String zona, Municipio municipio) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.direccion = direccion;
        this.zona = zona;
        this.municipio = municipio;
    }

    /** Agrega una mesa validando que su número no se repita dentro del puesto. */
    public MesaVotacion agregarMesa(int numero, int censo) {
        if (mesas.stream().anyMatch(m -> m.getNumero() == numero)) {
            throw new IllegalArgumentException("La mesa " + numero + " ya existe en " + nombre);
        }
        MesaVotacion mesa = new MesaVotacion(numero, censo, this);
        mesas.add(mesa);
        return mesa;
    }

    public int getCensoTotal() {
        return mesas.stream().mapToInt(MesaVotacion::getCenso).sum();
    }

    public String getCodigo() { return codigo; }
    public String getNombre() { return nombre; }
    public String getDireccion() { return direccion; }
    public String getZona() { return zona; }
    public Municipio getMunicipio() { return municipio; }
    public List<MesaVotacion> getMesas() { return Collections.unmodifiableList(mesas); }

    @Override
    public String toString() { return nombre + " - " + direccion + " (" + municipio.getNombre() + ")"; }
}
