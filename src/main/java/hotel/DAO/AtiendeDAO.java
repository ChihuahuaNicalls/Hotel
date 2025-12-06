package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Atiende;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class AtiendeDAO {
    private final DBConnection db;

    public AtiendeDAO(DBConnection db) { this.db = db; }

    public boolean insert(Atiende a) throws SQLException {
        final String sql = "INSERT INTO public.atiende (cedulacliente, cedulaempleado, idhabitacion, idservicio, fechallegada, fechauso) VALUES (?,?,?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, a.getCedulaCliente());
            ps.setObject(2, a.getCedulaEmpleado());
            ps.setObject(3, a.getIdHabitacion());
            ps.setObject(4, a.getIdServicio());
            ps.setObject(5, a.getFechaLlegada());
            ps.setObject(6, a.getFechaUso());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean delete(Integer cedulaCliente, Integer cedulaEmpleado, LocalDateTime fechaUso, LocalDateTime fechaLlegada, Integer idHabitacion, Integer idServicio) throws SQLException {
        final String sql = "DELETE FROM public.atiende WHERE cedulacliente = ? AND cedulaempleado = ? AND fechauso = ? AND fechallegada = ? AND idhabitacion = ? AND idservicio = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.setObject(2, cedulaEmpleado);
            ps.setObject(3, fechaUso);
            ps.setObject(4, fechaLlegada);
            ps.setObject(5, idHabitacion);
            ps.setObject(6, idServicio);
            int affected = ps.executeUpdate();
            return affected > 0;
        }
    }

    public List<Atiende> findByReserva(Integer cedulaCliente, Integer idHabitacion, LocalDateTime fechaLlegada) throws SQLException {
        final String sql = "SELECT * FROM public.atiende WHERE cedulacliente = ? AND idhabitacion = ? AND fechallegada = ? ORDER BY fechauso";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.setObject(2, idHabitacion);
            ps.setObject(3, fechaLlegada);
            try (ResultSet rs = ps.executeQuery()) {
                List<Atiende> lista = new ArrayList<>();
                while (rs.next()) lista.add(mapear(rs));
                return lista;
            }
        }
    }

    public List<Atiende> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.atiende ORDER BY cedulacliente, idhabitacion, fechallegada, fechauso";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Atiende> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Atiende mapear(ResultSet rs) throws SQLException {
        return new Atiende(
            rs.getObject("cedulacliente", Integer.class),
            rs.getObject("cedulaempleado", Integer.class),
            rs.getObject("idhabitacion", Integer.class),
            rs.getObject("idservicio", Integer.class),
            rs.getObject("fechallegada", LocalDateTime.class),
            rs.getObject("fechauso", LocalDateTime.class)
        );
    }
}
