
package hotel.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class PostgreSQLConnection extends DBConnection{
    private static PostgreSQLConnection instancia;
    private static final String HOST = System.getenv("HOST") != null ? System.getenv("HOST") : "localhost";
    private static final String PORT = System.getenv("PORT") != null ? System.getenv("PORT") : "5432";
    private static final String DATABASE = System.getenv("DATABASE") != null ? System.getenv("DATABASE") : "hotel";

    private PostgreSQLConnection() {
        url = "jdbc:postgresql://"+HOST+":"+PORT+"/"+DATABASE;
        // No se configuran credenciales aquí
    }

    public static PostgreSQLConnection getConnector (){
        if(instancia==null)
            instancia = new PostgreSQLConnection();
        return instancia;
    }
    
    /**
     * Obtiene una conexión con credenciales específicas
     */
    public Connection getConnectionWithCredentials(String username, String password) throws SQLException {
        Properties connectionProps = new Properties();
        connectionProps.setProperty("user", username);
        connectionProps.setProperty("password", password);
        return DriverManager.getConnection(url, connectionProps);
    }
}
