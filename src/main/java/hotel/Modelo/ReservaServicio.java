package hotel.Modelo;

import java.time.LocalDateTime;

public class ReservaServicio {
    private Integer idServicio;
    private LocalDateTime fechaLlegada;
    private Integer cedulaCliente;
    private Integer idHabitacion;
    private LocalDateTime fechaUso;

    public ReservaServicio() {}

    public ReservaServicio(Integer idServicio, LocalDateTime fechaLlegada, Integer cedulaCliente, Integer idHabitacion, LocalDateTime fechaUso) {
        this.idServicio = idServicio;
        this.fechaLlegada = fechaLlegada;
        this.cedulaCliente = cedulaCliente;
        this.idHabitacion = idHabitacion;
        this.fechaUso = fechaUso;
    }

    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public LocalDateTime getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(LocalDateTime fechaLlegada) { this.fechaLlegada = fechaLlegada; }
    public Integer getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(Integer cedulaCliente) { this.cedulaCliente = cedulaCliente; }
    public Integer getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(Integer idHabitacion) { this.idHabitacion = idHabitacion; }
    public LocalDateTime getFechaUso() { return fechaUso; }
    public void setFechaUso(LocalDateTime fechaUso) { this.fechaUso = fechaUso; }
}
