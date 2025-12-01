
package hotel.DB;

public class PostgreSQLConnection extends DBConnection{
    private static PostgreSQLConnection instancia;
    private static final String HOST = System.getenv("HOST");
    private static final String PORT = System.getenv("PORT");
    private static final String USER = System.getenv("USER");
    private static final String PASSWORD = System.getenv("PASSWORD");
    private static final String DATABASE = System.getenv("DATABASE");

    private PostgreSQLConnection() {
        url = "jdbc:postgresql://"+HOST+":"+PORT+"/"+DATABASE;
        props.setProperty("user", USER);
        props.setProperty("password", PASSWORD);
    }

    public static PostgreSQLConnection getConnector (){
        if(instancia==null)
            instancia = new PostgreSQLConnection();
        return instancia;
    }
}
