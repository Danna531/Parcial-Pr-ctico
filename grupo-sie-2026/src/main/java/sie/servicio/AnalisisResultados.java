package sie.servicio;

import sie.modelo.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Consultas analíticas sobre las actas cerradas de un proceso (HU-09).
 * Solo se consolidan actas cerradas para no mezclar datos preliminares.
 */
public class AnalisisResultados {
    private final ProcesoElectoral proceso;

    public AnalisisResultados(ProcesoElectoral proceso) {
        if (proceso == null) throw new IllegalArgumentException("El proceso es obligatorio");
        this.proceso = proceso;
    }

    private List<ActaEscrutinio> actasCerradas() {
        return proceso.getMesas().stream()
                .map(MesaVotacion::getActa)
                .filter(a -> a != null && a.isCerrada())
                .collect(Collectors.toList());
    }

    /** Total por candidato, ordenado de mayor a menor. */
    public LinkedHashMap<Candidato, Integer> votosPorCandidato() {
        Map<Candidato, Integer> total = new HashMap<>();
        for (Candidato c : proceso.getCandidatos()) total.put(c, 0);
        for (ActaEscrutinio a : actasCerradas()) {
            a.getVotosPorCandidato().forEach((c, v) -> total.merge(c, v, Integer::sum));
        }
        return ordenar(total);
    }

    /** Votos de un candidato agrupados por municipio. */
    public Map<String, Integer> votosPorMunicipio(Candidato c) {
        Map<String, Integer> r = new TreeMap<>();
        for (ActaEscrutinio a : actasCerradas()) {
            String municipio = a.getMesa().getPuesto().getMunicipio().getNombre();
            r.merge(municipio, a.getVotos(c), Integer::sum);
        }
        return r;
    }

    /** Votos de un candidato agrupados por departamento. */
    public Map<String, Integer> votosPorDepartamento(Candidato c) {
        Map<String, Integer> r = new TreeMap<>();
        for (ActaEscrutinio a : actasCerradas()) {
            String depto = a.getMesa().getPuesto().getMunicipio().getDepartamento().getNombre();
            r.merge(depto, a.getVotos(c), Integer::sum);
        }
        return r;
    }

    /** Candidato con más votos en un departamento (para comparar regiones). */
    public Candidato ganadorEnDepartamento(String departamento) {
        Map<Candidato, Integer> total = new HashMap<>();
        for (ActaEscrutinio a : actasCerradas()) {
            if (a.getMesa().getPuesto().getMunicipio().getDepartamento().getNombre().equalsIgnoreCase(departamento)) {
                a.getVotosPorCandidato().forEach((c, v) -> total.merge(c, v, Integer::sum));
            }
        }
        return total.entrySet().stream().max(Map.Entry.comparingByValue()).map(Map.Entry::getKey).orElse(null);
    }

    public Candidato ganador() {
        return votosPorCandidato().keySet().stream().findFirst().orElse(null);
    }

    /** Porcentaje del candidato sobre los votos válidos. */
    public double porcentaje(Candidato c) {
        int validos = actasCerradas().stream().mapToInt(ActaEscrutinio::getVotosValidos).sum();
        return validos == 0 ? 0 : votosPorCandidato().getOrDefault(c, 0) * 100.0 / validos;
    }

    /** Participación sobre el censo de las mesas con acta cerrada. */
    public double participacionGeneral() {
        List<ActaEscrutinio> actas = actasCerradas();
        int votantes = actas.stream().mapToInt(ActaEscrutinio::getTotalVotos).sum();
        int censo = actas.stream().mapToInt(a -> a.getMesa().getCenso()).sum();
        return censo == 0 ? 0 : votantes * 100.0 / censo;
    }

    private static LinkedHashMap<Candidato, Integer> ordenar(Map<Candidato, Integer> m) {
        return m.entrySet().stream()
                .sorted(Map.Entry.<Candidato, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (a, b) -> a, LinkedHashMap::new));
    }
}
