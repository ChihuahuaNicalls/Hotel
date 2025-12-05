package hotel;

import hotel.DB.PostgreSQLConnection;

import java.sql.Connection;
import java.sql.SQLException;

public class AuthenticationService {

    public static boolean authenticate(String username, String password) {
        try {
            PostgreSQLConnection connector = PostgreSQLConnection.getConnector();
            Connection connection = connector.getConnectionWithCredentials(username, password);
            
            if (connection != null) {
                connection.close();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error durante la autenticación: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static UserType getUserType(String username) {
        return UserType.fromUsername(username);
    }
}
