package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.ReservaServicio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaServicioDAO {
    private final DBConnection db;

    public ReservaServicioDAO(DBConnection db) { this.db = db; }

    public boolean insert(ReservaServicio rsd) throws SQLException {
        final String sql = "INSERT INTO public.reserva_servicio (idservicio, fechallegada, cedulacliente, idhabitacion, fechauso) VALUES (?,?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, rsd.getIdServicio());
            ps.setObject(2, rsd.getFechaLlegada());
            ps.setObject(3, rsd.getCedulaCliente());
            ps.setObject(4, rsd.getIdHabitacion());
            ps.setObject(5, rsd.getFechaUso());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean delete(Integer idServicio, LocalDateTime fechaLlegada, Integer cedulaCliente, Integer idHabitacion, LocalDateTime fechaUso) throws SQLException {
        final String sql = "DELETE FROM public.reserva_servicio WHERE idservicio = ? AND fechallegada = ? AND cedulacliente = ? AND idhabitacion = ? AND fechauso = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idServicio);
            ps.setObject(2, fechaLlegada);
            ps.setObject(3, cedulaCliente);
            ps.setObject(4, idHabitacion);
            ps.setObject(5, fechaUso);
            ps.executeUpdate();
        }
        return true;
    }

    public List<ReservaServicio> findByReserva(Integer cedulaCliente, Integer idHabitacion, LocalDateTime fechaLlegada) throws SQLException {
        final String sql = "SELECT * FROM public.reserva_servicio WHERE cedulacliente = ? AND idhabitacion = ? AND fechallegada = ? ORDER BY fechauso";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.setObject(2, idHabitacion);
            ps.setObject(3, fechaLlegada);
            try (ResultSet rs = ps.executeQuery()) {
                List<ReservaServicio> lista = new ArrayList<>();
                while (rs.next()) lista.add(mapear(rs));
                return lista;
            }
        }
    }

    public List<ReservaServicio> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.reserva_servicio ORDER BY cedulacliente, idhabitacion, fechallegada, fechauso";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<ReservaServicio> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private ReservaServicio mapear(ResultSet rs) throws SQLException {
        return new ReservaServicio(
            rs.getObject("idservicio", Integer.class),
            rs.getObject("fechallegada", LocalDateTime.class),
            rs.getObject("cedulacliente", Integer.class),
            rs.getObject("idhabitacion", Integer.class),
            rs.getObject("fechauso", LocalDateTime.class)
        );
    }
}
