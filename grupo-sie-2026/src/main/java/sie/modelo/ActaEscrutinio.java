package sie.modelo;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Acta E-14 con los resultados de una mesa (HU-06).
 * Mientras está abierta se registran votos; al cerrarla queda inmutable.
 */
public class ActaEscrutinio {
    private final String id;
    private final MesaVotacion mesa;
    private final Map<Candidato, Integer> votosPorCandidato = new LinkedHashMap<>();
    private int votosEnBlanco;
    private int votosNulos;
    private int votosNoMarcados;
    private LocalDateTime fechaCierre;
    private boolean cerrada;

    public ActaEscrutinio(String id, MesaVotacion mesa) {
        this.id = id;
        this.mesa = mesa;
    }

    public void registrarVotos(Candidato candidato, int cantidad) {
        verificarAbierta();
        if (cantidad < 0) throw new IllegalArgumentException("La cantidad de votos no puede ser negativa");
        votosPorCandidato.merge(candidato, cantidad, Integer::sum);
    }

    public void registrarEspeciales(int blanco, int nulos, int noMarcados) {
        verificarAbierta();
        if (blanco < 0 || nulos < 0 || noMarcados < 0) throw new IllegalArgumentException("Valores negativos");
        this.votosEnBlanco = blanco;
        this.votosNulos = nulos;
        this.votosNoMarcados = noMarcados;
    }

    /** Cierra el acta; no se permite cerrar si el total supera el censo de la mesa. */
    public void cerrar() {
        verificarAbierta();
        if (superaCenso()) {
            throw new IllegalStateException("El total de votos (" + getTotalVotos()
                    + ") supera el censo de la mesa (" + mesa.getCenso() + ")");
        }
        this.fechaCierre = LocalDateTime.now();
        this.cerrada = true;
    }

    private void verificarAbierta() {
        if (cerrada) throw new IllegalStateException("El acta " + id + " ya está cerrada");
    }

    public int getVotos(Candidato c) { return votosPorCandidato.getOrDefault(c, 0); }

    public int getVotosValidos() {
        return votosPorCandidato.values().stream().mapToInt(Integer::intValue).sum() + votosEnBlanco;
    }

    public int getTotalVotos() { return getVotosValidos() + votosNulos + votosNoMarcados; }

    public boolean superaCenso() { return getTotalVotos() > mesa.getCenso(); }

    /** Porcentaje de participación de la mesa (votantes / censo). */
    public double getParticipacion() {
        return mesa.getCenso() == 0 ? 0 : (getTotalVotos() * 100.0) / mesa.getCenso();
    }

    public String getId() { return id; }
    public MesaVotacion getMesa() { return mesa; }
    public Map<Candidato, Integer> getVotosPorCandidato() { return Collections.unmodifiableMap(votosPorCandidato); }
    public int getVotosEnBlanco() { return votosEnBlanco; }
    public int getVotosNulos() { return votosNulos; }
    public int getVotosNoMarcados() { return votosNoMarcados; }
    public LocalDateTime getFechaCierre() { return fechaCierre; }
    public boolean isCerrada() { return cerrada; }
}
