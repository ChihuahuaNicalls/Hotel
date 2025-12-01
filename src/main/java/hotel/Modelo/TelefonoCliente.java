package hotel.Modelo;

public class TelefonoCliente {
    private Integer cedulaCliente;
    private String telefonoCliente;

    public TelefonoCliente() {}

    public TelefonoCliente(Integer cedulaCliente, String telefonoCliente) {
        this.cedulaCliente = cedulaCliente;
        this.telefonoCliente = telefonoCliente;
    }

    public Integer getCedulaCliente() { return cedulaCliente; }
    public void setCedulaCliente(Integer cedulaCliente) { this.cedulaCliente = cedulaCliente; }
    public String getTelefonoCliente() { return telefonoCliente; }
    public void setTelefonoCliente(String telefonoCliente) { this.telefonoCliente = telefonoCliente; }
}
