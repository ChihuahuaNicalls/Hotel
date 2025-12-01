package hotel.Modelo;

import java.time.LocalDateTime;

public class Atiende {
    private Integer cedulaCliente;
    private Integer cedulaEmpleado;
    private Integer idHabitacion;
    private Integer idServicio;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaUso;

    public Atiende() {}

    public Atiende(Integer cedulaCliente, Integer cedulaEmpleado, Integer idHabitacion, Integer idServicio, LocalDateTime fechaLlegada, LocalDateTime fechaUso) {
        this.cedulaCliente = cedulaCliente;
        this.cedulaEmpleado = cedulaEmpleado;
        this.idHabitacion = idHabitacion;
        this.idServicio = idServicio;
        this.fechaLlegada = fechaLlegada;
        this.fechaUso = fechaUso;
    }

    public Integer getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(Integer cedulaCliente) { this.cedulaCliente = cedulaCliente; }
    public Integer getCedulaEmpleado() { return cedulaEmpleado; }
    public void setCedulaEmpleado(Integer cedulaEmpleado) { this.cedulaEmpleado = cedulaEmpleado; }
    public Integer getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(Integer idHabitacion) { this.idHabitacion = idHabitacion; }
    public Integer getIdServicio() { return idServicio; }
    public void setIdServicio(Integer idServicio) { this.idServicio = idServicio; }
    public LocalDateTime getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(LocalDateTime fechaLlegada) { this.fechaLlegada = fechaLlegada; }
    public LocalDateTime getFechaUso() { return fechaUso; }
    public void setFechaUso(LocalDateTime fechaUso) { this.fechaUso = fechaUso; }
}
