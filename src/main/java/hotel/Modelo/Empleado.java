package hotel.Modelo;

public class Empleado {
    private Integer cedulaEmpleado;
    private String primerNombre;
    private String segundoNombre;
    private String primerApellido;
    private String segundoApellido;
    private String calle;
    private String carrera;
    private String numero;
    private String complemento;
    private String cargo;
    private Integer idArea;

    public Empleado() {}

    public Empleado(Integer cedulaEmpleado, String primerNombre, String segundoNombre, String primerApellido, String segundoApellido, String calle, String carrera, String numero, String complemento, String cargo, Integer idArea) {
        this.cedulaEmpleado = cedulaEmpleado;
        this.primerNombre = primerNombre;
        this.segundoNombre = segundoNombre;
        this.primerApellido = primerApellido;
        this.segundoApellido = segundoApellido;
        this.calle = calle;
        this.carrera = carrera;
        this.numero = numero;
        this.complemento = complemento;
        this.cargo = cargo;
        this.idArea = idArea;
    }

    public Integer getCedulaEmpleado() { return cedulaEmpleado; }
    public void setCedulaEmpleado(Integer cedulaEmpleado) { this.cedulaEmpleado = cedulaEmpleado; }
    public String getPrimerNombre() { return primerNombre; }
    public void setPrimerNombre(String primerNombre) { this.primerNombre = primerNombre; }
    public String getSegundoNombre() { return segundoNombre; }
    public void setSegundoNombre(String segundoNombre) { this.segundoNombre = segundoNombre; }
    public String getPrimerApellido() { return primerApellido; }
    public void setPrimerApellido(String primerApellido) { this.primerApellido = primerApellido; }
    public String getSegundoApellido() { return segundoApellido; }
    public void setSegundoApellido(String segundoApellido) { this.segundoApellido = segundoApellido; }
    public String getCalle() { return calle; }
    public void setCalle(String calle) { this.calle = calle; }
    public String getCarrera() { return carrera; }
    public void setCarrera(String carrera) { this.carrera = carrera; }
    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }
    public String getComplemento() { return complemento; }
    public void setComplemento(String complemento) { this.complemento = complemento; }
    public String getCargo() { return cargo; }
    public void setCargo(String cargo) { this.cargo = cargo; }
    public Integer getIdArea() { return idArea; }
    public void setIdArea(Integer idArea) { this.idArea = idArea; }
}
