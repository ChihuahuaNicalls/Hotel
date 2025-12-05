package hotel;

public enum UserType {
    RECEPCION("Recepción"),
    PERSONAL_SERVICIO("Personal de Servicio"),
    ADMINISTRACION("Administración"),
    DB_ADMIN("Administrador DB");

    private final String displayName;

    UserType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    public static UserType fromUsername(String username) {
        if (username == null) return null;
        
        if (username.contains("recepcion")) {
            return RECEPCION;
        } else if (username.contains("servicio")) {
            return PERSONAL_SERVICIO;
        } else if (username.contains("administracion") || username.contains("admin")) {
            return ADMINISTRACION;
        } else if (username.contains("db_admin")) {
            return DB_ADMIN;
        }
        return null;
    }
}
