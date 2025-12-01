package hotel.Modelo;

public class Habitacion {
    private Integer idHabitacion;
    private Integer idCategoria;
    private Integer numeroHabitacion;
    private Integer piso;
    private String descripcion;
    private Boolean mantenimiento;

    public Habitacion() {}

    public Habitacion(Integer idHabitacion, Integer idCategoria, Integer numeroHabitacion, Integer piso, String descripcion, Boolean mantenimiento) {
        this.idHabitacion = idHabitacion;
        this.idCategoria = idCategoria;
        this.numeroHabitacion = numeroHabitacion;
        this.piso = piso;
        this.descripcion = descripcion;
        this.mantenimiento = mantenimiento;
    }

    public Integer getIdHabitacion() { return idHabitacion; }
    public void setIdHabitacion(Integer idHabitacion) { this.idHabitacion = idHabitacion; }
    public Integer getIdCategoria() { return idCategoria; }
    public void setIdCategoria(Integer idCategoria) { this.idCategoria = idCategoria; }
    public Integer getNumeroHabitacion() { return numeroHabitacion; }
    public void setNumeroHabitacion(Integer numeroHabitacion) { this.numeroHabitacion = numeroHabitacion; }
    public Integer getPiso() { return piso; }
    public void setPiso(Integer piso) { this.piso = piso; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Boolean getMantenimiento() { return mantenimiento; }
    public void setMantenimiento(Boolean mantenimiento) { this.mantenimiento = mantenimiento; }
}
