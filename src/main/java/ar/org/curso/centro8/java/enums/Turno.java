package ar.org.curso.centro8.java.enums;

public enum Turno {
    MAÑANA("Mañana"), 
    TARDE("Tarde"), 
    JORNADA_COMPLETA("Jornada completa");

    private final String dbValue;

    Turno(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

        public static Turno fromDb(String dbValue) {
        for (Turno t : values()) {
            if (t.dbValue.equals(dbValue)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Valor BD inválido: " + dbValue);
    }
}
