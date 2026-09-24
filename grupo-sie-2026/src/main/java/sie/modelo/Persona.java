package sie.modelo;

import java.util.Objects;

/** Datos comunes de cualquier persona del sistema, identificada por cédula. */
public abstract class Persona {
    protected final String cedula;
    protected String nombres;
    protected String apellidos;

    protected Persona(String cedula, String nombres, String apellidos) {
        if (cedula == null || !cedula.matches("\\d{6,10}")) {
            throw new IllegalArgumentException("Cédula inválida: " + cedula);
        }
        this.cedula = cedula;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

    public String getCedula() { return cedula; }
    public String getNombres() { return nombres; }
    public String getApellidos() { return apellidos; }
    public String getNombreCompleto() { return nombres + " " + apellidos; }

    /** Dos personas son la misma si tienen la misma cédula. */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Persona)) return false;
        return cedula.equals(((Persona) o).cedula);
    }

    @Override
    public int hashCode() { return Objects.hash(cedula); }

    @Override
    public String toString() { return getNombreCompleto() + " (CC " + cedula + ")"; }
}
