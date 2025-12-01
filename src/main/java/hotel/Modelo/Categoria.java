package hotel.Modelo;

import java.math.BigDecimal;

public class Categoria {
    private Integer idCategoria;
    private String nombreCategoria;
    private String descCategoria;
    private BigDecimal precioCategoria;

    public Categoria() {}

    public Categoria(Integer idCategoria, String nombreCategoria, String descCategoria, BigDecimal precioCategoria) {
        this.idCategoria = idCategoria;
        this.nombreCategoria = nombreCategoria;
        this.descCategoria = descCategoria;
        this.precioCategoria = precioCategoria;
    }

    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    public String getNombreCategoria() { return nombreCategoria; }
    public void setNombreCategoria(String nombreCategoria) { this.nombreCategoria = nombreCategoria; }
    public String getDescCategoria() { return descCategoria; }
    public void setDescCategoria(String descCategoria) { this.descCategoria = descCategoria; }
    public BigDecimal getPrecioCategoria() { return precioCategoria; }
    public void setPrecioCategoria(BigDecimal precioCategoria) { this.precioCategoria = precioCategoria; }
}
