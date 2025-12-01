package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Habitacion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HabitacionDAO {
    private final DBConnection db;

    public HabitacionDAO(DBConnection db) { this.db = db; }

    public boolean insert(Habitacion habitacion) throws SQLException {
        final String sql = "INSERT INTO public.habitacion (idhabitacion, idcategoria, numerohabitacion, piso, descripcion, mantenimiento) VALUES (?,?,?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, habitacion.getIdHabitacion());
            ps.setObject(2, habitacion.getIdCategoria());
            ps.setObject(3, habitacion.getNumeroHabitacion());
            ps.setObject(4, habitacion.getPiso());
            ps.setString(5, habitacion.getDescripcion());
            ps.setObject(6, habitacion.getMantenimiento());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Habitacion habitacion) throws SQLException {
        final String sql = "UPDATE public.habitacion SET idcategoria = ?, numerohabitacion = ?, piso = ?, descripcion = ?, mantenimiento = ? WHERE idhabitacion = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, habitacion.getIdCategoria());
            ps.setObject(2, habitacion.getNumeroHabitacion());
            ps.setObject(3, habitacion.getPiso());
            ps.setString(4, habitacion.getDescripcion());
            ps.setObject(5, habitacion.getMantenimiento());
            ps.setObject(6, habitacion.getIdHabitacion());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteById(Integer idHabitacion) throws SQLException {
        final String sql = "DELETE FROM public.habitacion WHERE idhabitacion = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idHabitacion);
            ps.executeUpdate();
        }
        return true;
    }

    public Habitacion findById(Integer idHabitacion) throws SQLException {
        final String sql = "SELECT * FROM public.habitacion WHERE idhabitacion = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idHabitacion);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Habitacion> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.habitacion ORDER BY idhabitacion";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Habitacion> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Habitacion mapear(ResultSet rs) throws SQLException {
        return new Habitacion(
            rs.getObject("idhabitacion", Integer.class),
            rs.getObject("idcategoria", Integer.class),
            rs.getObject("numerohabitacion", Integer.class),
            rs.getObject("piso", Integer.class),
            rs.getString("descripcion"),
            rs.getObject("mantenimiento", Boolean.class)
        );
    }
}
