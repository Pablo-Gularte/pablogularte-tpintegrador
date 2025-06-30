package ar.org.curso.centro8.java.enums;

public enum Bimestre {
    PRIMERO("Primer bimestre"), 
    SEGUNDO("Segundo bimestre"),
    TERCERO("Tercer bimestre"), 
    CUARTO("Cuarto bimestre");

    private final String dbValue;

    Bimestre(String dbValue) {
        this.dbValue = dbValue;
    }

    public String getDbValue() {
        return dbValue;
    }

    public static Bimestre fromDb(String dbValue) {
        for (Bimestre t : values()) {
            if (t.dbValue.equals(dbValue)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Valor BD inválido: " + dbValue);
    }
}
