package ar.org.curso.centro8.java.enums;

public enum Ciclo {
    PRIMERO("Primer ciclo"),
    SEGUNDO("Segundo ciclo");

    private final String dbValue;

    Ciclo(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static Ciclo fromDb(String dbValue) {
        for (Ciclo t : values()) {
            if (t.dbValue.equals(dbValue)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Valor BD inválido: " + dbValue);
    }

}
