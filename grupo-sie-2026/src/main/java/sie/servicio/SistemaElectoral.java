package sie.servicio;

import sie.modelo.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Punto de entrada del sistema: registra la información administrativa,
 * logística y de la jornada, y aplica las reglas que cruzan varias clases.
 */
public class SistemaElectoral {
    private final Map<String, ProcesoElectoral> procesos;
    private final Map<String, Departamento> departamentos;
    private final Map<String, PartidoPolitico> partidos;
    private final Map<String, MesaVotacion> juradoEnMesa; // cédula -> mesa
    private final List<Incidente> incidentes;
    private final List<Reclamacion> reclamaciones;
    private int consecutivoIncidente;
    private int consecutivoReclamacion;

    public SistemaElectoral() {
        this.procesos = new LinkedHashMap<>();
        this.departamentos = new LinkedHashMap<>();
        this.partidos = new LinkedHashMap<>();
        this.juradoEnMesa = new HashMap<>();
        this.incidentes = new ArrayList<>();
        this.reclamaciones = new ArrayList<>();
        this.consecutivoIncidente = 1;
        this.consecutivoReclamacion = 1;
    }

    // ---------- HU-01 Procesos ----------
    public ProcesoElectoral registrarProceso(String id, TipoEleccion tipo, LocalDate fecha) {
        if (procesos.containsKey(id)) throw new IllegalArgumentException("Ya existe el proceso " + id);
        ProcesoElectoral p = new ProcesoElectoral(id, tipo, fecha);
        procesos.put(id, p);
        return p;
    }

    public ProcesoElectoral buscarProceso(String id) { return procesos.get(id); }

    // ---------- HU-02 Territorio ----------
    public Departamento registrarDepartamento(String codigoDane, String nombre) {
        return departamentos.computeIfAbsent(codigoDane, c -> new Departamento(c, nombre));
    }

    public Collection<Departamento> getDepartamentos() { return departamentos.values(); }

    // ---------- HU-04 Partidos ----------
    public PartidoPolitico registrarPartido(String codigo, String nombre, String sigla) {
        if (partidos.containsKey(codigo)) throw new IllegalArgumentException("Partido ya registrado");
        PartidoPolitico p = new PartidoPolitico(codigo, nombre, sigla);
        partidos.put(codigo, p);
        return p;
    }

    public Collection<PartidoPolitico> getPartidos() { return partidos.values(); }

    // ---------- HU-05 Jurados ----------
    /** Un ciudadano solo puede ser jurado en una mesa por jornada. */
    public AsignacionJurado designarJurado(Jurado jurado, MesaVotacion mesa, RolJurado rol) {
        MesaVotacion actual = juradoEnMesa.get(jurado.getCedula());
        if (actual != null) {
            throw new IllegalStateException(jurado.getNombreCompleto() + " ya es jurado en " + actual);
        }
        AsignacionJurado a = mesa.asignarJurado(jurado, rol);
        juradoEnMesa.put(jurado.getCedula(), mesa);
        return a;
    }

    // ---------- HU-07 Incidentes ----------
    public Incidente reportarIncidente(TipoIncidente tipo, String descripcion, PuestoVotacion puesto, MesaVotacion mesa) {
        Incidente i = new Incidente("INC-" + consecutivoIncidente++, tipo, descripcion, puesto, mesa);
        incidentes.add(i);
        return i;
    }

    public List<Incidente> incidentesAbiertos() {
        return incidentes.stream().filter(i -> !i.isResuelto()).collect(Collectors.toList());
    }

    // ---------- HU-08 Reclamaciones ----------
    public Reclamacion radicarReclamacion(ActaEscrutinio acta, PartidoPolitico partido, Testigo testigo, String motivo) {
        Reclamacion r = new Reclamacion("REC-" + consecutivoReclamacion++, acta, partido, testigo, motivo);
        reclamaciones.add(r);
        return r;
    }

    public List<Reclamacion> reclamacionesPorEstado(EstadoReclamacion estado) {
        return reclamaciones.stream().filter(r -> r.getEstado() == estado).collect(Collectors.toList());
    }

    public List<Incidente> getIncidentes() { return Collections.unmodifiableList(incidentes); }
    public List<Reclamacion> getReclamaciones() { return Collections.unmodifiableList(reclamaciones); }
}
