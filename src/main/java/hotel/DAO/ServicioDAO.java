package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Servicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServicioDAO {
    private final DBConnection db;

    public ServicioDAO(DBConnection db) { this.db = db; }

    public boolean insert(Servicio servicio) throws SQLException {
        final String sql = "INSERT INTO public.servicio (idservicio, nombreservicio, detalleservicio, precioservicio) VALUES (?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, servicio.getIdServicio());
            ps.setString(2, servicio.getNombreServicio());
            ps.setString(3, servicio.getDetalleServicio());
            ps.setBigDecimal(4, servicio.getPrecioServicio());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Servicio servicio) throws SQLException {
        final String sql = "UPDATE public.servicio SET nombreservicio = ?, detalleservicio = ?, precioservicio = ? WHERE idservicio = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, servicio.getNombreServicio());
            ps.setString(2, servicio.getDetalleServicio());
            ps.setBigDecimal(3, servicio.getPrecioServicio());
            ps.setObject(4, servicio.getIdServicio());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteById(Integer idServicio) throws SQLException {
        final String sql = "DELETE FROM public.servicio WHERE idservicio = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idServicio);
            ps.executeUpdate();
        }
        return true;
    }

    public Servicio findById(Integer idServicio) throws SQLException {
        final String sql = "SELECT * FROM public.servicio WHERE idservicio = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idServicio);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Servicio> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.servicio ORDER BY idservicio";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Servicio> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Servicio mapear(ResultSet rs) throws SQLException { return new Servicio(rs.getObject("idservicio", Integer.class), rs.getString("nombreservicio"), rs.getString("detalleservicio"), rs.getBigDecimal("precioservicio")); }
}
