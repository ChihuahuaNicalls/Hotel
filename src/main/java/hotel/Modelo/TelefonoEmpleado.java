package hotel.Modelo;

public class TelefonoEmpleado {
    private Integer cedulaEmpleado;
    private String telefonoEmpleado;

    public TelefonoEmpleado() {}

    public TelefonoEmpleado(Integer cedulaEmpleado, String telefonoEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
        this.telefonoEmpleado = telefonoEmpleado;
    }

    public Integer getCedulaEmpleado() { return cedulaEmpleado; }
    public void setCedulaEmpleado(Integer cedulaEmpleado) { this.cedulaEmpleado = cedulaEmpleado; }
    public String getTelefonoEmpleado() { return telefonoEmpleado; }
    public void setTelefonoEmpleado(String telefonoEmpleado) { this.telefonoEmpleado = telefonoEmpleado; }
}
