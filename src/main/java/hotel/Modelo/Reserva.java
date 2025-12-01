package hotel.Modelo;

import java.time.LocalDateTime;

public class Reserva {
    private Integer cedulaCliente;
    private Integer idHabitacion;
    private LocalDateTime fechaLlegada;
    private LocalDateTime fechaSalida;

    public Reserva() {}

    public Reserva(Integer cedulaCliente, Integer idHabitacion, LocalDateTime fechaLlegada, LocalDateTime fechaSalida) {
        this.cedulaCliente = cedulaCliente;
        this.idHabitacion = idHabitacion;
        this.fechaLlegada = fechaLlegada;
        this.fechaSalida = fechaSalida;
    }

    public Integer getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(Integer cedulaCliente) { this.cedulaCliente = cedulaCliente; }
    public Integer getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(Integer idHabitacion) { this.idHabitacion = idHabitacion; }
    public LocalDateTime getFechaLlegada() { return fechaLlegada; }
    public void setFechaLlegada(LocalDateTime fechaLlegada) { this.fechaLlegada = fechaLlegada; }
    public LocalDateTime getFechaSalida() { return fechaSalida; }
    public void setFechaSalida(LocalDateTime fechaSalida) { this.fechaSalida = fechaSalida; }
}
