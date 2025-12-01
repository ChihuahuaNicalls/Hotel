package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.TelefonoCliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TelefonoClienteDAO {
    private final DBConnection db;

    public TelefonoClienteDAO(DBConnection db) { this.db = db; }

    public boolean insert(TelefonoCliente t) throws SQLException {
        final String sql = "INSERT INTO public.telefonocliente (cedulacliente, telefonocliente) VALUES (?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, t.getCedulaCliente());
            ps.setString(2, t.getTelefonoCliente());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean delete(Integer cedulaCliente, String telefono) throws SQLException {
        final String sql = "DELETE FROM public.telefonocliente WHERE cedulacliente = ? AND telefonocliente = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.setString(2, telefono);
            ps.executeUpdate();
        }
        return true;
    }

    public List<TelefonoCliente> findByCliente(Integer cedulaCliente) throws SQLException {
        final String sql = "SELECT * FROM public.telefonocliente WHERE cedulacliente = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            try (ResultSet rs = ps.executeQuery()) {
                List<TelefonoCliente> lista = new ArrayList<>();
                while (rs.next()) lista.add(new TelefonoCliente(rs.getObject("cedulacliente", Integer.class), rs.getString("telefonocliente")));
                return lista;
            }
        }
    }
}
