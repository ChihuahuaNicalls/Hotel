package hotel;

import hotel.DAO.*;
import hotel.DB.PostgreSQLConnection;
import hotel.Modelo.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

public class MenuController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab tabClientes;
    @FXML
    private Tab tabReservas;
    @FXML
    private Tab tabServicios;
    @FXML
    private Tab tabEmpleados;
    @FXML
    private Tab tabAsignarEmpleados;
    @FXML
    private Tab tabHabitaciones;
    @FXML
    private Tab tabAdicionServicios;
    @FXML
    private Tab tabConsultas;

    @FXML
    private TextField txtClienteCedula;
    @FXML
    private TextField txtClientePrimerNombre;
    @FXML
    private TextField txtClienteSegundoNombre;
    @FXML
    private TextField txtClientePrimerApellido;
    @FXML
    private TextField txtClienteSegundoApellido;
    @FXML
    private TextField txtClienteCalle;
    @FXML
    private TextField txtClienteCarrera;
    @FXML
    private TextField txtClienteNumero;
    @FXML
    private TextField txtClienteComplemento;
    @FXML
    private TextField txtClienteTelefonos;
    @FXML
    private TextField txtClienteEmails;
    @FXML
    private Button btnClienteCrear;
    @FXML
    private Button btnClienteActualizar;
    @FXML
    private ScrollPane scrollClientes;

    @FXML
    private TextField txtReservaCedulaCliente;
    @FXML
    private TextField txtReservaFechaLlegada;
    @FXML
    private TextField txtReservaIdHabitacion;
    @FXML
    private TextField txtReservaCarrera;
    @FXML
    private Button btnReservaCrear;
    @FXML
    private Button btnReservaActualizar;
    @FXML
    private Button btnReservaTerminar;
    @FXML
    private ScrollPane scrollReservas;

    @FXML
    private TextField txtEmpleadoCedula;
    @FXML
    private TextField txtEmpleadoPrimerNombre;
    @FXML
    private TextField txtEmpleadoSegundoNombre;
    @FXML
    private TextField txtEmpleadoPrimerApellido;
    @FXML
    private TextField txtEmpleadoSegundoApellido;
    @FXML
    private TextField txtEmpleadoCalle;
    @FXML
    private TextField txtEmpleadoCarrera;
    @FXML
    private TextField txtEmpleadoNumero;
    @FXML
    private TextField txtEmpleadoComplemento;
    @FXML
    private TextField txtEmpleadoTelefonos;
    @FXML
    private TextField txtEmpleadoEmails;
    @FXML
    private TextField txtEmpleadoIdArea;
    @FXML
    private Button btnEmpleadoCrear;
    @FXML
    private Button btnEmpleadoActualizar;
    @FXML
    private Button btnEmpleadoEliminar;
    @FXML
    private ScrollPane scrollEmpleados;

    @FXML
    private TextField txtAsignarCedulaCliente;
    @FXML
    private TextField txtAsignarIdHabitacion;
    @FXML
    private TextField txtAsignarCedulaEmpleado;
    @FXML
    private TextField txtAsignarIdServicio;
    @FXML
    private TextField txtAsignarFechaLlegada;
    @FXML
    private Button btnAsignarEmpleado;
    @FXML
    private Button btnDesasignarEmpleado;
    @FXML
    private ScrollPane scrollAsignarReservas;
    @FXML
    private ScrollPane scrollAsignarReservasEmpleados;

    @FXML
    private TextField txtHabitacionId;
    @FXML
    private Button btnLiberarHabitacion;
    @FXML
    private Button btnTerminarMantenimiento;
    @FXML
    private ScrollPane scrollHabitaciones;

    @FXML
    private TextField txtServicioCedulaCliente;
    @FXML
    private TextField txtServicioIdHabitacion;
    @FXML
    private TextField txtServicioFechaLlegada;
    @FXML
    private TextField txtServicioIdServicio;
    @FXML
    private Button btnAnadirServicio;
    @FXML
    private ScrollPane scrollListaServicios;
    @FXML
    private ScrollPane scrollServiciosReservaciones;

    @FXML
    private Button btnDisponibilidadHabitaciones;
    @FXML
    private Button btnConsultaGeneral;
    @FXML
    private ScrollPane scrollConsultas;

    private ClienteDAO clienteDAO;
    private ReservaDAO reservaDAO;
    private EmpleadoDAO empleadoDAO;
    private HabitacionDAO habitacionDAO;
    private ServicioDAO servicioDAO;
    private AtiendeDAO atiendeDAO;
    private ReservaServicioDAO reservaServicioDAO;
    private TelefonoClienteDAO telefonoClienteDAO;
    private EmailClienteDAO emailClienteDAO;
    private TelefonoEmpleadoDAO telefonoEmpleadoDAO;
    private EmailEmpleadoDAO emailEmpleadoDAO;

    /**
     * Método que se ejecuta al inicializar el controlador
     */
    @FXML
    public void initialize() {
        try {
            PostgreSQLConnection db = PostgreSQLConnection.getConnector();
            clienteDAO = new ClienteDAO(db);
            reservaDAO = new ReservaDAO(db);
            empleadoDAO = new EmpleadoDAO(db);
            habitacionDAO = new HabitacionDAO(db);
            servicioDAO = new ServicioDAO(db);
            atiendeDAO = new AtiendeDAO(db);
            reservaServicioDAO = new ReservaServicioDAO(db);
            telefonoClienteDAO = new TelefonoClienteDAO(db);
            emailClienteDAO = new EmailClienteDAO(db);
            telefonoEmpleadoDAO = new TelefonoEmpleadoDAO(db);
            emailEmpleadoDAO = new EmailEmpleadoDAO(db);

            configurarTabsSegunUsuario();

            cargarDatosIniciales();
        } catch (Exception e) {
            mostrarError("Error al inicializar: " + e.getMessage());
        }
    }

    /**
     * Configura qué pestañas se muestran según el tipo de usuario logueado
     */
    private void configurarTabsSegunUsuario() {
        UserSession session = UserSession.getInstance();
        UserType userType = session.getUserType();

        tabPane.getTabs().clear();

        switch (userType) {
            case RECEPCION:

                tabPane.getTabs().addAll(tabClientes, tabReservas, tabConsultas);
                break;

            case PERSONAL_SERVICIO:

                tabPane.getTabs().addAll(tabHabitaciones, tabAdicionServicios);
                break;

            case ADMINISTRACION:
            case DB_ADMIN:

                tabPane.getTabs().addAll(tabEmpleados, tabAsignarEmpleados, tabServicios, tabConsultas);
                break;
        }
    }

    /**
     * Carga los datos iniciales en las tablas
     */
    private void cargarDatosIniciales() {
        try {
            UserSession session = UserSession.getInstance();
            UserType userType = session.getUserType();

            switch (userType) {
                case RECEPCION:
                    cargarClientes();
                    cargarReservas();
                    break;

                case PERSONAL_SERVICIO:
                    cargarHabitaciones();
                    break;

                case ADMINISTRACION:
                case DB_ADMIN:
                    cargarEmpleados();
                    cargarServicios();
                    break;
            }
        } catch (SQLException e) {
            mostrarError("Error al cargar datos iniciales: " + e.getMessage());
        }
    }

    @FXML
    private void handleClienteCrear() {
        try {
            Cliente cliente = new Cliente();
            cliente.setCedulaCliente(Integer.parseInt(txtClienteCedula.getText()));
            cliente.setPrimerNombre(txtClientePrimerNombre.getText());
            cliente.setSegundoNombre(txtClienteSegundoNombre.getText());
            cliente.setPrimerApellido(txtClientePrimerApellido.getText());
            cliente.setSegundoApellido(txtClienteSegundoApellido.getText());
            cliente.setCalle(txtClienteCalle.getText());
            cliente.setCarrera(txtClienteCarrera.getText());
            cliente.setNumero(txtClienteNumero.getText());
            cliente.setComplemento(txtClienteComplemento.getText());

            if (clienteDAO.insert(cliente)) {
                mostrarInfo("Cliente creado exitosamente");

                procesarTelefonosCliente(cliente.getCedulaCliente(), txtClienteTelefonos.getText());
                procesarEmailsCliente(cliente.getCedulaCliente(), txtClienteEmails.getText());

                limpiarCamposCliente();
                cargarClientes();
            }
        } catch (Exception e) {
            mostrarError("Error al crear cliente: " + e.getMessage());
        }
    }

    @FXML
    private void handleClienteActualizar() {
        try {
            Cliente cliente = new Cliente();
            cliente.setCedulaCliente(Integer.parseInt(txtClienteCedula.getText()));
            cliente.setPrimerNombre(txtClientePrimerNombre.getText());
            cliente.setSegundoNombre(txtClienteSegundoNombre.getText());
            cliente.setPrimerApellido(txtClientePrimerApellido.getText());
            cliente.setSegundoApellido(txtClienteSegundoApellido.getText());
            cliente.setCalle(txtClienteCalle.getText());
            cliente.setCarrera(txtClienteCarrera.getText());
            cliente.setNumero(txtClienteNumero.getText());
            cliente.setComplemento(txtClienteComplemento.getText());

            if (clienteDAO.update(cliente)) {
                mostrarInfo("Cliente actualizado exitosamente");
                limpiarCamposCliente();
                cargarClientes();
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar cliente: " + e.getMessage());
        }
    }

    private void cargarClientes() throws SQLException {
        List<Cliente> clientes = clienteDAO.findAll();
        VBox vbox = new VBox(5);

        for (Cliente cliente : clientes) {
            Label label = new Label(cliente.getCedulaCliente() + " - " +
                    cliente.getPrimerNombre() + " " +
                    cliente.getPrimerApellido());
            vbox.getChildren().add(label);
        }

        scrollClientes.setContent(vbox);
    }

    private void limpiarCamposCliente() {
        txtClienteCedula.clear();
        txtClientePrimerNombre.clear();
        txtClienteSegundoNombre.clear();
        txtClientePrimerApellido.clear();
        txtClienteSegundoApellido.clear();
        txtClienteCalle.clear();
        txtClienteCarrera.clear();
        txtClienteNumero.clear();
        txtClienteComplemento.clear();
        txtClienteTelefonos.clear();
        txtClienteEmails.clear();
    }

    private void procesarTelefonosCliente(Integer cedula, String telefonos) throws SQLException {
        if (telefonos != null && !telefonos.trim().isEmpty()) {
            String[] tels = telefonos.split(",");
            for (String tel : tels) {
                TelefonoCliente telefono = new TelefonoCliente();
                telefono.setCedulaCliente(cedula);
                telefono.setTelefonoCliente(tel.trim());
                telefonoClienteDAO.insert(telefono);
            }
        }
    }

    private void procesarEmailsCliente(Integer cedula, String emails) throws SQLException {
        if (emails != null && !emails.trim().isEmpty()) {
            String[] mails = emails.split(",");
            for (String mail : mails) {
                EmailCliente email = new EmailCliente();
                email.setCedulaCliente(cedula);
                email.setCorreoCliente(mail.trim());
                emailClienteDAO.insert(email);
            }
        }
    }

    @FXML
    private void handleReservaCrear() {
        try {
            Reserva reserva = new Reserva();
            reserva.setCedulaCliente(Integer.parseInt(txtReservaCedulaCliente.getText()));
            reserva.setFechaLlegada(java.time.LocalDateTime.parse(txtReservaFechaLlegada.getText() + "T00:00:00"));
            reserva.setIdHabitacion(Integer.parseInt(txtReservaIdHabitacion.getText()));

            if (!txtReservaCarrera.getText().isEmpty()) {
                reserva.setFechaSalida(java.time.LocalDateTime.parse(txtReservaCarrera.getText() + "T00:00:00"));
            }

            if (reservaDAO.insert(reserva)) {
                mostrarInfo("Reserva creada exitosamente");
                limpiarCamposReserva();
                cargarReservas();
            }
        } catch (Exception e) {
            mostrarError("Error al crear reserva: " + e.getMessage());
        }
    }

    @FXML
    private void handleReservaActualizar() {
        try {
            Reserva reserva = new Reserva();
            reserva.setCedulaCliente(Integer.parseInt(txtReservaCedulaCliente.getText()));
            reserva.setFechaLlegada(java.time.LocalDateTime.parse(txtReservaFechaLlegada.getText() + "T00:00:00"));
            reserva.setIdHabitacion(Integer.parseInt(txtReservaIdHabitacion.getText()));
            if (!txtReservaCarrera.getText().isEmpty()) {
                reserva.setFechaSalida(java.time.LocalDateTime.parse(txtReservaCarrera.getText() + "T00:00:00"));
            }

            if (reservaDAO.update(reserva)) {
                mostrarInfo("Reserva actualizada exitosamente");
                limpiarCamposReserva();
                cargarReservas();
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar reserva: " + e.getMessage());
        }
    }

    @FXML
    private void handleReservaTerminar() {
        try {
            Integer cedula = Integer.parseInt(txtReservaCedulaCliente.getText());
            Integer idHabitacion = Integer.parseInt(txtReservaIdHabitacion.getText());
            java.time.LocalDateTime fechaLlegada = java.time.LocalDateTime
                    .parse(txtReservaFechaLlegada.getText() + "T00:00:00");

            if (reservaDAO.deleteById(cedula, idHabitacion, fechaLlegada)) {
                mostrarInfo("Reserva terminada exitosamente");
                limpiarCamposReserva();
                cargarReservas();
            }
        } catch (Exception e) {
            mostrarError("Error al terminar reserva: " + e.getMessage());
        }
    }

    private void cargarReservas() throws SQLException {
        List<Reserva> reservas = reservaDAO.findAll();
        VBox vbox = new VBox(5);

        for (Reserva reserva : reservas) {
            Label label = new Label("Cliente: " + reserva.getCedulaCliente() +
                    " | Habitación: " + reserva.getIdHabitacion() +
                    " | Llegada: " + reserva.getFechaLlegada());
            vbox.getChildren().add(label);
        }

        scrollReservas.setContent(vbox);
    }

    private void limpiarCamposReserva() {
        txtReservaCedulaCliente.clear();
        txtReservaFechaLlegada.clear();
        txtReservaIdHabitacion.clear();
        txtReservaCarrera.clear();
    }

    @FXML
    private void handleEmpleadoCrear() {
        try {
            Empleado empleado = new Empleado();
            empleado.setCedulaEmpleado(Integer.parseInt(txtEmpleadoCedula.getText()));
            empleado.setPrimerNombre(txtEmpleadoPrimerNombre.getText());
            empleado.setSegundoNombre(txtEmpleadoSegundoNombre.getText());
            empleado.setPrimerApellido(txtEmpleadoPrimerApellido.getText());
            empleado.setSegundoApellido(txtEmpleadoSegundoApellido.getText());
            empleado.setCalle(txtEmpleadoCalle.getText());
            empleado.setCarrera(txtEmpleadoCarrera.getText());
            empleado.setNumero(txtEmpleadoNumero.getText());
            empleado.setComplemento(txtEmpleadoComplemento.getText());
            empleado.setCargo("");
            empleado.setIdArea(Integer.parseInt(txtEmpleadoIdArea.getText()));

            if (empleadoDAO.insert(empleado)) {
                mostrarInfo("Empleado creado exitosamente");

                procesarTelefonosEmpleado(empleado.getCedulaEmpleado(), txtEmpleadoTelefonos.getText());
                procesarEmailsEmpleado(empleado.getCedulaEmpleado(), txtEmpleadoEmails.getText());

                limpiarCamposEmpleado();
                cargarEmpleados();
            }
        } catch (Exception e) {
            mostrarError("Error al crear empleado: " + e.getMessage());
        }
    }

    @FXML
    private void handleEmpleadoActualizar() {
        try {
            Empleado empleado = new Empleado();
            empleado.setCedulaEmpleado(Integer.parseInt(txtEmpleadoCedula.getText()));
            empleado.setPrimerNombre(txtEmpleadoPrimerNombre.getText());
            empleado.setSegundoNombre(txtEmpleadoSegundoNombre.getText());
            empleado.setPrimerApellido(txtEmpleadoPrimerApellido.getText());
            empleado.setSegundoApellido(txtEmpleadoSegundoApellido.getText());
            empleado.setCalle(txtEmpleadoCalle.getText());
            empleado.setCarrera(txtEmpleadoCarrera.getText());
            empleado.setNumero(txtEmpleadoNumero.getText());
            empleado.setComplemento(txtEmpleadoComplemento.getText());
            empleado.setCargo("");
            empleado.setIdArea(Integer.parseInt(txtEmpleadoIdArea.getText()));

            if (empleadoDAO.update(empleado)) {
                mostrarInfo("Empleado actualizado exitosamente");
                limpiarCamposEmpleado();
                cargarEmpleados();
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar empleado: " + e.getMessage());
        }
    }

    @FXML
    private void handleEmpleadoEliminar() {
        try {
            Integer cedula = Integer.parseInt(txtEmpleadoCedula.getText());

            if (empleadoDAO.deleteById(cedula)) {
                mostrarInfo("Empleado eliminado exitosamente");
                limpiarCamposEmpleado();
                cargarEmpleados();
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar empleado: " + e.getMessage());
        }
    }

    private void cargarEmpleados() throws SQLException {
        List<Empleado> empleados = empleadoDAO.findAll();
        VBox vbox = new VBox(5);

        for (Empleado empleado : empleados) {
            Label label = new Label(empleado.getCedulaEmpleado() + " - " +
                    empleado.getPrimerNombre() + " " +
                    empleado.getPrimerApellido() +
                    " | Área: " + empleado.getIdArea());
            vbox.getChildren().add(label);
        }

        scrollEmpleados.setContent(vbox);
    }

    private void limpiarCamposEmpleado() {
        txtEmpleadoCedula.clear();
        txtEmpleadoPrimerNombre.clear();
        txtEmpleadoSegundoNombre.clear();
        txtEmpleadoPrimerApellido.clear();
        txtEmpleadoSegundoApellido.clear();
        txtEmpleadoCalle.clear();
        txtEmpleadoCarrera.clear();
        txtEmpleadoNumero.clear();
        txtEmpleadoComplemento.clear();
        txtEmpleadoIdArea.clear();
        txtEmpleadoTelefonos.clear();
        txtEmpleadoEmails.clear();
    }

    private void procesarTelefonosEmpleado(Integer cedula, String telefonos) throws SQLException {
        if (telefonos != null && !telefonos.trim().isEmpty()) {
            String[] tels = telefonos.split(",");
            for (String tel : tels) {
                TelefonoEmpleado telefono = new TelefonoEmpleado();
                telefono.setCedulaEmpleado(cedula);
                telefono.setTelefonoEmpleado(tel.trim());
                telefonoEmpleadoDAO.insert(telefono);
            }
        }
    }

    private void procesarEmailsEmpleado(Integer cedula, String emails) throws SQLException {
        if (emails != null && !emails.trim().isEmpty()) {
            String[] mails = emails.split(",");
            for (String mail : mails) {
                EmailEmpleado email = new EmailEmpleado();
                email.setCedulaEmpleado(cedula);
                email.setCorreoEmpleado(mail.trim());
                emailEmpleadoDAO.insert(email);
            }
        }
    }

    @FXML
    private void handleAsignarEmpleado() {
        try {
            Atiende atiende = new Atiende();
            atiende.setCedulaCliente(Integer.parseInt(txtAsignarCedulaCliente.getText()));
            atiende.setIdHabitacion(Integer.parseInt(txtAsignarIdHabitacion.getText()));
            atiende.setFechaLlegada(java.time.LocalDateTime.parse(txtAsignarFechaLlegada.getText() + "T00:00:00"));
            atiende.setCedulaEmpleado(Integer.parseInt(txtAsignarCedulaEmpleado.getText()));
            atiende.setIdServicio(Integer.parseInt(txtAsignarIdServicio.getText()));
            atiende.setFechaUso(java.time.LocalDateTime.now());

            if (atiendeDAO.insert(atiende)) {
                mostrarInfo("Empleado asignado exitosamente");
                limpiarCamposAsignar();
                cargarAsignaciones();
            }
        } catch (Exception e) {
            mostrarError("Error al asignar empleado: " + e.getMessage());
        }
    }

    @FXML
    private void handleDesasignarEmpleado() {
        try {
            Integer cedulaCliente = Integer.parseInt(txtAsignarCedulaCliente.getText());
            Integer cedulaEmpleado = Integer.parseInt(txtAsignarCedulaEmpleado.getText());
            java.time.LocalDateTime fechaLlegada = java.time.LocalDateTime
                    .parse(txtAsignarFechaLlegada.getText() + "T00:00:00");
            java.time.LocalDateTime fechaUso = java.time.LocalDateTime.now();
            Integer idHabitacion = Integer.parseInt(txtAsignarIdHabitacion.getText());
            Integer idServicio = Integer.parseInt(txtAsignarIdServicio.getText());

            if (atiendeDAO.delete(cedulaCliente, cedulaEmpleado, fechaUso, fechaLlegada, idHabitacion, idServicio)) {
                mostrarInfo("Empleado desasignado exitosamente");
                limpiarCamposAsignar();
                cargarAsignaciones();
            }
        } catch (Exception e) {
            mostrarError("Error al desasignar empleado: " + e.getMessage());
        }
    }

    private void cargarAsignaciones() throws SQLException {

        VBox vbox = new VBox(5);
        Label label = new Label("Ingrese datos de reserva para ver asignaciones");
        vbox.getChildren().add(label);
        scrollAsignarReservasEmpleados.setContent(vbox);
    }

    private void limpiarCamposAsignar() {
        txtAsignarCedulaCliente.clear();
        txtAsignarIdHabitacion.clear();
        txtAsignarFechaLlegada.clear();
        txtAsignarCedulaEmpleado.clear();
        txtAsignarIdServicio.clear();
    }

    @FXML
    private void handleLiberarHabitacion() {
        try {
            Integer idHabitacion = Integer.parseInt(txtHabitacionId.getText());

            Habitacion habitacion = habitacionDAO.findById(idHabitacion);
            if (habitacion != null) {
                habitacion.setMantenimiento(false);
                if (habitacionDAO.update(habitacion)) {
                    mostrarInfo("Habitación liberada exitosamente");
                    txtHabitacionId.clear();
                    cargarHabitaciones();
                }
            }
        } catch (Exception e) {
            mostrarError("Error al liberar habitación: " + e.getMessage());
        }
    }

    @FXML
    private void handleTerminarMantenimiento() {
        try {
            Integer idHabitacion = Integer.parseInt(txtHabitacionId.getText());

            Habitacion habitacion = habitacionDAO.findById(idHabitacion);
            if (habitacion != null) {
                habitacion.setMantenimiento(false);
                if (habitacionDAO.update(habitacion)) {
                    mostrarInfo("Mantenimiento terminado exitosamente");
                    txtHabitacionId.clear();
                    cargarHabitaciones();
                }
            }
        } catch (Exception e) {
            mostrarError("Error al terminar mantenimiento: " + e.getMessage());
        }
    }

    private void cargarHabitaciones() throws SQLException {
        List<Habitacion> habitaciones = habitacionDAO.findAll();
        VBox vbox = new VBox(5);

        for (Habitacion habitacion : habitaciones) {
            String estado = habitacion.getMantenimiento() ? "Mantenimiento" : "Disponible";
            Label label = new Label("Habitación: " + habitacion.getIdHabitacion() +
                    " | Estado: " + estado +
                    " | Categoría: " + habitacion.getIdCategoria());
            vbox.getChildren().add(label);
        }

        scrollHabitaciones.setContent(vbox);
    }

    private void cargarServicios() throws SQLException {
        List<Servicio> servicios = servicioDAO.findAll();
        VBox vbox = new VBox(5);

        for (Servicio servicio : servicios) {
            Label label = new Label("ID: " + servicio.getIdServicio() +
                    " | " + servicio.getNombreServicio() +
                    " | Precio: $" + servicio.getPrecioServicio());
            vbox.getChildren().add(label);
        }

    }

    @FXML
    private void handleAnadirServicio() {
        try {
            ReservaServicio reservaServicio = new ReservaServicio();
            reservaServicio.setCedulaCliente(Integer.parseInt(txtServicioCedulaCliente.getText()));
            reservaServicio.setIdHabitacion(Integer.parseInt(txtServicioIdHabitacion.getText()));
            reservaServicio.setFechaLlegada(LocalDateTime.parse(txtServicioFechaLlegada.getText()));
            reservaServicio.setIdServicio(Integer.parseInt(txtServicioIdServicio.getText()));
            reservaServicio.setFechaUso(LocalDateTime.now());

            if (reservaServicioDAO.insert(reservaServicio)) {
                mostrarInfo("Servicio añadido exitosamente");
                limpiarCamposServicio();
                cargarReservasServicios();
            }
        } catch (Exception e) {
            mostrarError("Error al añadir servicio: " + e.getMessage());
        }
    }

    private void cargarReservasServicios() throws SQLException {

        VBox vbox = new VBox(5);
        Label label = new Label("Use findByReserva() para buscar servicios de una reserva específica");
        vbox.getChildren().add(label);
        scrollServiciosReservaciones.setContent(vbox);
    }

    private void limpiarCamposServicio() {
        txtServicioCedulaCliente.clear();
        txtServicioIdHabitacion.clear();
        txtServicioFechaLlegada.clear();
        txtServicioIdServicio.clear();
    }

    private void mostrarInfo(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Información");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

}
