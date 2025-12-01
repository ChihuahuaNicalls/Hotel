package hotel.Modelo;

import java.math.BigDecimal;

public class Servicio {
    private Integer idServicio;
    private String nombreServicio;
    private String detalleServicio;
    private BigDecimal precioServicio;

    public Servicio() {}

    public Servicio(Integer idServicio, String nombreServicio, String detalleServicio, BigDecimal precioServicio) {
        this.idServicio = idServicio;
        this.nombreServicio = nombreServicio;
        this.detalleServicio = detalleServicio;
        this.precioServicio = precioServicio;
    }

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public String getNombreServicio() { return nombreServicio; }
    public void setNombreServicio(String nombreServicio) { this.nombreServicio = nombreServicio; }
    public String getDetalleServicio() { return detalleServicio; }
    public void setDetalleServicio(String detalleServicio) { this.detalleServicio = detalleServicio; }
    public BigDecimal getPrecioServicio() { return precioServicio; }
    public void setPrecioServicio(BigDecimal precioServicio) { this.precioServicio = precioServicio; }
}
