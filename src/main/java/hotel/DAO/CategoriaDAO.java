package hotel.DAO;

import hotel.DB.DBConnection;
import hotel.Modelo.Categoria;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {
    private final DBConnection db;

    public CategoriaDAO(DBConnection db) { this.db = db; }

    public boolean insert(Categoria categoria) throws SQLException {
        final String sql = "INSERT INTO public.categoria (idcategoria, nombrecategoria, desccategoria, preciocategoria) VALUES (?,?,?,?)";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, categoria.getIdCategoria());
            ps.setString(2, categoria.getNombreCategoria());
            ps.setString(3, categoria.getDescCategoria());
            ps.setBigDecimal(4, categoria.getPrecioCategoria());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean update(Categoria categoria) throws SQLException {
        final String sql = "UPDATE public.categoria SET nombrecategoria = ?, desccategoria = ?, preciocategoria = ? WHERE idcategoria = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, categoria.getNombreCategoria());
            ps.setString(2, categoria.getDescCategoria());
            ps.setBigDecimal(3, categoria.getPrecioCategoria());
            ps.setObject(4, categoria.getIdCategoria());
            ps.executeUpdate();
        }
        return true;
    }

    public boolean deleteById(Integer idCategoria) throws SQLException {
        final String sql = "DELETE FROM public.categoria WHERE idcategoria = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idCategoria);
            ps.executeUpdate();
        }
        return true;
    }

    public Categoria findById(Integer idCategoria) throws SQLException {
        final String sql = "SELECT * FROM public.categoria WHERE idcategoria = ?";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setObject(1, idCategoria);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? mapear(rs) : null; }
        }
    }

    public List<Categoria> findAll() throws SQLException {
        final String sql = "SELECT * FROM public.categoria ORDER BY idcategoria";
        try (Connection c = db.getConn(); PreparedStatement ps = c.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {
            List<Categoria> lista = new ArrayList<>();
            while (rs.next()) lista.add(mapear(rs));
            return lista;
        }
    }

    private Categoria mapear(ResultSet rs) throws SQLException { return new Categoria(rs.getObject("idcategoria", Integer.class), rs.getString("nombrecategoria"), rs.getString("desccategoria"), rs.getBigDecimal("preciocategoria")); }
}
