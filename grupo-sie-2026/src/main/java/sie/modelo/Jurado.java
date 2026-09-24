package sie.modelo;

/** Ciudadano designado como jurado de votación (HU-05). */
public class Jurado extends Persona {
    private String telefono;

    public Jurado(String cedula, String nombres, String apellidos, String telefono) {
        super(cedula, nombres, apellidos);
        this.telefono = telefono;
    }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }
}
