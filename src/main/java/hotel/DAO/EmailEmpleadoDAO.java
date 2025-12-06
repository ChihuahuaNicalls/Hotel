package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.EmailEmpleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmailEmpleadoDAO {
    private final DBConnection db;

    public EmailEmpleadoDAO(DBConnection db) { this.db = db; }

    public boolean insert(EmailEmpleado e) throws SQLException {
        final String sql = "INSERT INTO public.emailempleado (cedulaempleado, correoempleado) VALUES (?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, e.getCedulaEmpleado());
            ps.setString(2, e.getCorreoEmpleado());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean delete(Integer cedulaEmpleado, String correo) throws SQLException {
        final String sql = "DELETE FROM public.emailempleado WHERE cedulaempleado = ? AND correoempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaEmpleado);
            ps.setString(2, correo);
            ps.executeUpdate();
        }
        return true;
    }

    public List<EmailEmpleado> findByEmpleado(Integer cedulaEmpleado) throws SQLException {
        final String sql = "SELECT * FROM public.emailempleado WHERE cedulaempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                List<EmailEmpleado> lista = new ArrayList<>();
                while (rs.next()) lista.add(new EmailEmpleado(rs.getObject("cedulaempleado", Integer.class), rs.getString("correoempleado")));
                return lista;
            }
        }
    }

    public List<EmailEmpleado> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.emailempleado";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<EmailEmpleado> lista = new ArrayList<>();
            while (rs.next()) {
                lista.add(new EmailEmpleado(rs.getObject("cedulaempleado", Integer.class), rs.getString("correoempleado")));
            }
            return lista;
        }
    }

    public boolean deleteByEmpleado(Integer cedulaEmpleado) throws SQLException {
        final String sql = "DELETE FROM public.emailempleado WHERE cedulaempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaEmpleado);
            ps.executeUpdate();
        }
        return true;
    }
}
