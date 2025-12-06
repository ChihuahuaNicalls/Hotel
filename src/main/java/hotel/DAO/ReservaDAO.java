package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ReservaDAO {
    private final DBConnection db;

    public ReservaDAO(DBConnection db) { this.db = db; }

    public boolean insert(Reserva reserva) throws SQLException {
        final String sql = "INSERT INTO public.reserva (cedulacliente, idhabitacion, fechallegada, fechasalida) VALUES (?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, reserva.getCedulaCliente());
            ps.setObject(2, reserva.getIdHabitacion());
            ps.setObject(3, reserva.getFechaLlegada());
            ps.setObject(4, reserva.getFechaSalida());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Reserva reserva) throws SQLException {
        // Mantener compatibilidad: actualiza solo la fechaSalida usando las claves actuales
        final String sql = "UPDATE public.reserva SET fechasalida = ? WHERE cedulacliente = ? AND idhabitacion = ? AND fechallegada = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, reserva.getFechaSalida());
            ps.setObject(2, reserva.getCedulaCliente());
            ps.setObject(3, reserva.getIdHabitacion());
            ps.setObject(4, reserva.getFechaLlegada());
            ps.executeUpdate();
        }
        return true;
    }

    /**
     * Actualiza una reserva permitiendo cambiar las columnas que forman la clave primaria.
     * Usa los valores originales (orig...) en la cláusula WHERE para localizar la fila.
     */
    public boolean update(Reserva reserva, Integer origCedulaCliente, Integer origIdHabitacion, LocalDateTime origFechaLlegada) throws SQLException {
        final String sql = "UPDATE public.reserva SET cedulacliente = ?, idhabitacion = ?, fechallegada = ?, fechasalida = ? " +
                           "WHERE cedulacliente = ? AND idhabitacion = ? AND fechallegada = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, reserva.getCedulaCliente());
            ps.setObject(2, reserva.getIdHabitacion());
            ps.setObject(3, reserva.getFechaLlegada());
            ps.setObject(4, reserva.getFechaSalida());

            ps.setObject(5, origCedulaCliente);
            ps.setObject(6, origIdHabitacion);
            ps.setObject(7, origFechaLlegada);

            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteById(Integer cedulaCliente, Integer idHabitacion, LocalDateTime fechaLlegada) throws SQLException {
        final String sql = "DELETE FROM public.reserva WHERE cedulacliente = ? AND idhabitacion = ? AND fechallegada = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.setObject(2, idHabitacion);
            ps.setObject(3, fechaLlegada);
            ps.executeUpdate();
        }
        return true;
    }

    public Reserva findById(Integer cedulaCliente, Integer idHabitacion, LocalDateTime fechaLlegada) throws SQLException {
        final String sql = "SELECT * FROM public.reserva WHERE cedulacliente = ? AND idhabitacion = ? AND fechallegada = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.setObject(2, idHabitacion);
            ps.setObject(3, fechaLlegada);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Reserva> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.reserva ORDER BY fechallegada";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Reserva> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Reserva mapear(ResultSet rs) throws SQLException {
        return new Reserva(
            rs.getObject("cedulacliente", Integer.class),
            rs.getObject("idhabitacion", Integer.class),
            rs.getObject("fechallegada", LocalDateTime.class),
            rs.getObject("fechasalida", LocalDateTime.class)
        );
    }
}
