package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.EmailCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailClienteDAO {
    private final DBConnection db;

    public EmailClienteDAO(DBConnection db) { this.db = db; }

    public boolean insert(EmailCliente e) throws SQLException {
        final String sql = "INSERT INTO public.emailcliente (cedulacliente, correocliente) VALUES (?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, e.getCedulaCliente());
            ps.setString(2, e.getCorreoCliente());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean delete(Integer cedulaCliente, String correo) throws SQLException {
        final String sql = "DELETE FROM public.emailcliente WHERE cedulacliente = ? AND correocliente = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.setString(2, correo);
            ps.executeUpdate();
        }
        return true;
    }

    public List<EmailCliente> findByCliente(Integer cedulaCliente) throws SQLException {
        final String sql = "SELECT * FROM public.emailcliente WHERE cedulacliente = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            try (ResultSet rs = ps.executeQuery()) {
                List<EmailCliente> lista = new ArrayList<>();
                while (rs.next()) lista.add(new EmailCliente(rs.getObject("cedulacliente", Integer.class), rs.getString("correocliente")));
                return lista;
            }
        }
    }
}
