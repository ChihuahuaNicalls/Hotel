package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoDAO {
    private final DBConnection db;

    public EmpleadoDAO(DBConnection db) { this.db = db; }

    public boolean insert(Empleado empleado) throws SQLException {
        final String sql = "INSERT INTO public.empleado (cedulaempleado, primernombre, segundonombre, primerapellido, segundoapellido, calle, carrera, numero, complemento, cargo, idarea) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, empleado.getCedulaEmpleado());
            ps.setString(2, empleado.getPrimerNombre());
            ps.setString(3, empleado.getSegundoNombre());
            ps.setString(4, empleado.getPrimerApellido());
            ps.setString(5, empleado.getSegundoApellido());
            ps.setString(6, empleado.getCalle());
            ps.setString(7, empleado.getCarrera());
            ps.setString(8, empleado.getNumero());
            ps.setString(9, empleado.getComplemento());
            ps.setString(10, empleado.getCargo());
            ps.setObject(11, empleado.getIdArea());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Empleado empleado) throws SQLException {
        final String sql = "UPDATE public.empleado SET primernombre = ?, segundonombre = ?, primerapellido = ?, segundoapellido = ?, calle = ?, carrera = ?, numero = ?, complemento = ?, cargo = ?, idarea = ? WHERE cedulaempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, empleado.getPrimerNombre());
            ps.setString(2, empleado.getSegundoNombre());
            ps.setString(3, empleado.getPrimerApellido());
            ps.setString(4, empleado.getSegundoApellido());
            ps.setString(5, empleado.getCalle());
            ps.setString(6, empleado.getCarrera());
            ps.setString(7, empleado.getNumero());
            ps.setString(8, empleado.getComplemento());
            ps.setString(9, empleado.getCargo());
            ps.setObject(10, empleado.getIdArea());
            ps.setObject(11, empleado.getCedulaEmpleado());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteById(Integer cedulaEmpleado) throws SQLException {
        final String sql = "DELETE FROM public.empleado WHERE cedulaempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaEmpleado);
            ps.executeUpdate();
        }
        return true;
    }

    public Empleado findById(Integer cedulaEmpleado) throws SQLException {
        final String sql = "SELECT * FROM public.empleado WHERE cedulaempleado = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaEmpleado);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Empleado> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.empleado ORDER BY cedulaempleado";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Empleado> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Empleado mapear(ResultSet rs) throws SQLException {
        return new Empleado(
            rs.getObject("cedulaempleado", Integer.class),
            rs.getString("primernombre"),
            rs.getString("segundonombre"),
            rs.getString("primerapellido"),
            rs.getString("segundoapellido"),
            rs.getString("calle"),
            rs.getString("carrera"),
            rs.getString("numero"),
            rs.getString("complemento"),
            rs.getString("cargo"),
            rs.getObject("idarea", Integer.class)
        );
    }
}
