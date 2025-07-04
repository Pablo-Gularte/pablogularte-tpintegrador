package ar.org.curso.centro8.java.models.enums;

public enum NombreGrado {
    PRIMERO("Primero"),
    SEGUNDO("Segundo"), 
    TERCERO("Tercero"), 
    CUARTO("Cuarto"), 
    QUINTO("Quinto"), 
    SEXTO("Sexto"), 
    SEPTIMO("Séptimo");

    private final String dbValue;

    NombreGrado(String dbValue) {
        this.dbValue = dbValue;
    }
    
    public String getDbValue() {
        return dbValue;
    }

    public static NombreGrado fromDb(String dbValue) {
        for (NombreGrado t : values()) {
            if (t.dbValue.equals(dbValue)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Valor BD inválido: " + dbValue);
    }
}
