package hotel.Modelo;

public class EmailCliente {
    private Integer cedulaCliente;
    private String correoCliente;

    public EmailCliente() {}

    public EmailCliente(Integer cedulaCliente, String correoCliente) {
        this.cedulaCliente = cedulaCliente;
        this.correoCliente = correoCliente;
    }

    public Integer getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(Integer cedulaCliente) { this.cedulaCliente = cedulaCliente; }
    public String getCorreoCliente() { return correoCliente; }
    public void setCorreoCliente(String correoCliente) { this.correoCliente = correoCliente; }
}
