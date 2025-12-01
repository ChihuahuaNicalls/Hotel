package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO {
    private final DBConnection db;

    public ClienteDAO(DBConnection db) { this.db = db; }

    public boolean insert(Cliente cliente) throws SQLException {
        final String sql = "INSERT INTO public.cliente (cedulaCliente, primerNombre, segundoNombre, primerApellido, segundoApellido, calle, carrera, numero, complemento) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cliente.getCedulaCliente());
            ps.setString(2, cliente.getPrimerNombre());
            ps.setString(3, cliente.getSegundoNombre());
            ps.setString(4, cliente.getPrimerApellido());
            ps.setString(5, cliente.getSegundoApellido());
            ps.setString(6, cliente.getCalle());
            ps.setString(7, cliente.getCarrera());
            ps.setString(8, cliente.getNumero());
            ps.setString(9, cliente.getComplemento());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Cliente cliente) throws SQLException {
        final String sql = "UPDATE public.cliente SET primerNombre = ?, segundoNombre = ?, primerApellido = ?, segundoApellido = ?, calle = ?, carrera = ?, numero = ?, complemento = ? WHERE cedulaCliente = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, cliente.getPrimerNombre());
            ps.setString(2, cliente.getSegundoNombre());
            ps.setString(3, cliente.getPrimerApellido());
            ps.setString(4, cliente.getSegundoApellido());
            ps.setString(5, cliente.getCalle());
            ps.setString(6, cliente.getCarrera());
            ps.setString(7, cliente.getNumero());
            ps.setString(8, cliente.getComplemento());
            ps.setObject(9, cliente.getCedulaCliente());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteById(Integer cedulaCliente) throws SQLException {
        final String sql = "DELETE FROM public.cliente WHERE cedulacliente = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            ps.executeUpdate();
        }
        return true;
    }

    public Cliente findById(Integer cedulaCliente) throws SQLException {
        final String sql = "SELECT * FROM public.cliente WHERE cedulacliente = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, cedulaCliente);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next() ? mapear(rs) : null;
            }
        }
    }

    public List<Cliente> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.cliente ORDER BY cedulacliente";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Cliente> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Cliente mapear(ResultSet rs) throws SQLException {
        return new Cliente(
            rs.getObject("cedulacliente", Integer.class),
            rs.getString("primernombre"),
            rs.getString("segundonombre"),
            rs.getString("primerapellido"),
            rs.getString("segundoapellido"),
            rs.getString("calle"),
            rs.getString("carrera"),
            rs.getString("numero"),
            rs.getString("complemento")
        );
    }
}
