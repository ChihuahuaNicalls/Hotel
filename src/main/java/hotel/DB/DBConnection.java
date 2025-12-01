package hotel.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public abstract class DBConnection {
    protected String url;
    private Connection conn;
    protected final Properties props = new Properties();
    
    public Connection getConn() throws SQLException{
        conn = DriverManager.getConnection(url,props);
        return conn;
    }
}
