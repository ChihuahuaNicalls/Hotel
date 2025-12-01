package hotel.Modelo;

public class EmailEmpleado {
    private Integer cedulaEmpleado;
    private String correoEmpleado;

    public EmailEmpleado() {}

    public EmailEmpleado(Integer cedulaEmpleado, String correoEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
        this.correoEmpleado = correoEmpleado;
    }

    public Integer getCedulaEmpleado() { return cedulaEmpleado; }
    public void setCedulaEmpleado(Integer cedulaEmpleado) { this.cedulaEmpleado = cedulaEmpleado; }
    public String getCorreoEmpleado() { return correoEmpleado; }
    public void setCorreoEmpleado(String correoEmpleado) { this.correoEmpleado = correoEmpleado; }
}
