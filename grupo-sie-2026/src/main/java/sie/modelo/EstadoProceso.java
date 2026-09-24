package sie.modelo;

/** Estado de un proceso electoral dentro del calendario. */
public enum EstadoProceso {
    PROGRAMADO,
    INSCRIPCIONES_ABIERTAS,
    EN_JORNADA,
    EN_ESCRUTINIO,
    FINALIZADO,
    ANULADO
}
