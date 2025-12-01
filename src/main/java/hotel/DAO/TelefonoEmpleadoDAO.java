package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.TelefonoEmpleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefonoEmpleadoDAO {
    private final DBConnection db;

    public TelefonoEmpleadoDAO(DBConnection db) { this.db = db; }

    public boolean insert(TelefonoEmpleado t) throws SQLException {
        final String sql = "INSERT INTO public.telefonoempleado (cedulaempleado, telefonoempleado) VALUES (?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, t.getCedulaEmpleado());
            ps.setString(2, t.getTelefonoEmpleado());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean delete(Integer cedulaEmpleado, String telefono) throws SQLException {
        final String sql = "DELETE FROM public.telefonoempleado WHERE cedulaempleado = ? AND telefonoempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaEmpleado);
            ps.setString(2, telefono);
            ps.executeUpdate();
        }
        return true;
    }

    public List<TelefonoEmpleado> findByEmpleado(Integer cedulaEmpleado) throws SQLException {
        final String sql = "SELECT * FROM public.telefonoempleado WHERE cedulaempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaEmpleado);
            try (ResultSet rs = ps.executeQuery()) {
                List<TelefonoEmpleado> lista = new ArrayList<>();
                while (rs.next()) lista.add(new TelefonoEmpleado(rs.getObject("cedulaempleado", Integer.class), rs.getString("telefonoempleado")));
                return lista;
            }
        }
    }
}
