package ar.org.curso.centro8.java.models.enums;

public enum TipoAsistencia {
    PRESENTE("Presente"),
    AUSENTE("Ausente"),
    LLEGADA_TARDE("Llegada tarde");

    private final String dbValue;

    TipoAsistencia(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static TipoAsistencia fromDb(String dbValue) {
        for (TipoAsistencia t : values()) {
            if (t.dbValue.equals(dbValue)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Valor BD inválido: " + dbValue);
    }
}