package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Area;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AreaDAO {
    private final DBConnection db;

    public AreaDAO(DBConnection db) { this.db = db; }

    public boolean insert(Area area) throws SQLException {
        final String sql = "INSERT INTO public.area (idarea, nombrearea) VALUES (?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, area.getIdArea());
            ps.setString(2, area.getNombreArea());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Area area) throws SQLException {
        final String sql = "UPDATE public.area SET nombrearea = ? WHERE idarea = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, area.getNombreArea());
            ps.setObject(2, area.getIdArea());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteById(Integer idArea) throws SQLException {
        final String sql = "DELETE FROM public.area WHERE idarea = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idArea);
            ps.executeUpdate();
        }
        return true;
    }

    public Area findById(Integer idArea) throws SQLException {
        final String sql = "SELECT * FROM public.area WHERE idarea = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idArea);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Area> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.area ORDER BY idarea";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Area> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Area mapear(ResultSet rs) throws SQLException { return new Area(rs.getObject("idarea", Integer.class), rs.getString("nombrearea")); }
}
