package hotel;

import hotel.DAO.*;
import hotel.DB.PostgreSQLConnection;
import hotel.Modelo.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
    private TableView<Cliente> tableClientes;

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
    private TableView<Reserva> tableReservas;

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
    private TableView<Empleado> tableEmpleados;

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
    private TableView<Atiende> tableAsignaciones;

    @FXML
    private TextField txtHabitacionId;
    @FXML
    private Button btnLiberarHabitacion;
    @FXML
    private Button btnTerminarMantenimiento;
    @FXML
    private TableView<Habitacion> tableHabitaciones;

    @FXML
    private TextField txtServicioId;
    @FXML
    private TextField txtServicioNombre;
    @FXML
    private TextField txtServicioDetalle;
    @FXML
    private TextField txtServicioPrecio;
    @FXML
    private TextField txtServicioCedulaCliente;
    @FXML
    private TextField txtServicioIdHabitacion;
    @FXML
    private TextField txtServicioFechaLlegada;
    @FXML
    private TextField txtServicioIdServicio;
    @FXML
    private Button btnServicioCrear;
    @FXML
    private Button btnServicioActualizar;
    @FXML
    private Button btnServicioEliminar;
    @FXML
    private Button btnAnadirServicio;
    @FXML
    private TableView<Servicio> tableServicios;
    @FXML
    private TableView<ReservaServicio> tableServiciosReservaciones;

    @FXML
    private Button btnDisponibilidadHabitaciones;
    @FXML
    private Button btnConsultaGeneral;
    @FXML
    private TableView<Object> tableConsultas;

    @FXML
    private Button btnCerrarSesion;

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

    private boolean listenersConfigurados = false;
    
    // IDs originales para updates
    private Integer cedulaClienteOriginal;
    private Integer cedulaEmpleadoOriginal;
    private Integer idServicioOriginal;
    
    // Control de clicks para deselección
    private Cliente lastSelectedCliente = null;
    private Reserva lastSelectedReserva = null;
    private Empleado lastSelectedEmpleado = null;
    private Servicio lastSelectedServicio = null;

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

            configurarSeleccionTablas();
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
                // Recepción: Gestiona clientes, reservas y consulta disponibilidad
                tabPane.getTabs().addAll(tabClientes, tabReservas, tabConsultas);
                break;

            case PERSONAL_SERVICIO:
                // Personal de Servicio: Actualiza habitaciones y registra consumos adicionales
                tabPane.getTabs().addAll(tabHabitaciones, tabAdicionServicios);
                break;

            case ADMINISTRACION:
                // Administración: Gestiona empleados, servicios y tiene acceso a consultas
                tabPane.getTabs().addAll(tabEmpleados, tabAsignarEmpleados, tabServicios, tabConsultas);
                break;

            case DB_ADMIN:
                // DB_ADMIN: Acceso completo a todas las funcionalidades
                tabPane.getTabs().addAll(tabClientes, tabReservas, tabServicios, tabEmpleados, 
                                        tabAsignarEmpleados, tabHabitaciones, tabAdicionServicios, tabConsultas);
                break;
        }
    }

    private void configurarSeleccionTablas() {
        if (listenersConfigurados) return;

        if (tableClientes != null) {
            tableClientes.setOnMouseClicked(event -> {
                Cliente seleccionado = tableClientes.getSelectionModel().getSelectedItem();
                if (seleccionado != null && event.getClickCount() == 1) {
                    if (seleccionado == lastSelectedCliente) {
                        tableClientes.getSelectionModel().clearSelection();
                        limpiarCamposCliente();
                        lastSelectedCliente = null;
                    } else {
                        lastSelectedCliente = seleccionado;
                    }
                }
            });
            
            tableClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldV, nuevo) -> {
                if (nuevo != null) {
                    cedulaClienteOriginal = nuevo.getCedulaCliente();
                    txtClienteCedula.setText(String.valueOf(nuevo.getCedulaCliente()));
                    txtClienteCedula.setDisable(true);
                    txtClientePrimerNombre.setText(nuevo.getPrimerNombre());
                    txtClienteSegundoNombre.setText(nuevo.getSegundoNombre());
                    txtClientePrimerApellido.setText(nuevo.getPrimerApellido());
                    txtClienteSegundoApellido.setText(nuevo.getSegundoApellido());
                    txtClienteCalle.setText(nuevo.getCalle());
                    txtClienteCarrera.setText(nuevo.getCarrera());
                    txtClienteNumero.setText(nuevo.getNumero());
                    txtClienteComplemento.setText(nuevo.getComplemento());
                } else {
                    cedulaClienteOriginal = null;
                    txtClienteCedula.setDisable(false);
                }
            });
        }

        if (tableReservas != null) {
            tableReservas.setOnMouseClicked(event -> {
                Reserva seleccionada = tableReservas.getSelectionModel().getSelectedItem();
                if (seleccionada != null && event.getClickCount() == 1) {
                    if (seleccionada == lastSelectedReserva) {
                        tableReservas.getSelectionModel().clearSelection();
                        limpiarCamposReserva();
                        lastSelectedReserva = null;
                    } else {
                        lastSelectedReserva = seleccionada;
                    }
                }
            });
            
            tableReservas.getSelectionModel().selectedItemProperty().addListener((obs, oldV, nuevo) -> {
                if (nuevo != null) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    txtReservaCedulaCliente.setText(String.valueOf(nuevo.getCedulaCliente()));
                    txtReservaIdHabitacion.setText(String.valueOf(nuevo.getIdHabitacion()));
                    txtReservaFechaLlegada.setText(nuevo.getFechaLlegada() != null ? nuevo.getFechaLlegada().toLocalDate().format(formatter) : "");
                    txtReservaCarrera.setText(nuevo.getFechaSalida() != null ? nuevo.getFechaSalida().toLocalDate().format(formatter) : "");
                }
            });
        }

        if (tableEmpleados != null) {
            tableEmpleados.setOnMouseClicked(event -> {
                Empleado seleccionado = tableEmpleados.getSelectionModel().getSelectedItem();
                if (seleccionado != null && event.getClickCount() == 1) {
                    if (seleccionado == lastSelectedEmpleado) {
                        tableEmpleados.getSelectionModel().clearSelection();
                        limpiarCamposEmpleado();
                        lastSelectedEmpleado = null;
                    } else {
                        lastSelectedEmpleado = seleccionado;
                    }
                }
            });
            
            tableEmpleados.getSelectionModel().selectedItemProperty().addListener((obs, oldV, nuevo) -> {
                if (nuevo != null) {
                    cedulaEmpleadoOriginal = nuevo.getCedulaEmpleado();
                    txtEmpleadoCedula.setText(String.valueOf(nuevo.getCedulaEmpleado()));
                    txtEmpleadoCedula.setDisable(true);
                    txtEmpleadoPrimerNombre.setText(nuevo.getPrimerNombre());
                    txtEmpleadoSegundoNombre.setText(nuevo.getSegundoNombre());
                    txtEmpleadoPrimerApellido.setText(nuevo.getPrimerApellido());
                    txtEmpleadoSegundoApellido.setText(nuevo.getSegundoApellido());
                    txtEmpleadoCalle.setText(nuevo.getCalle());
                    txtEmpleadoCarrera.setText(nuevo.getCarrera());
                    txtEmpleadoNumero.setText(nuevo.getNumero());
                    txtEmpleadoComplemento.setText(nuevo.getComplemento());
                    txtEmpleadoIdArea.setText(nuevo.getIdArea() != null ? String.valueOf(nuevo.getIdArea()) : "");
                } else {
                    cedulaEmpleadoOriginal = null;
                    txtEmpleadoCedula.setDisable(false);
                }
            });
        }

        if (tableServicios != null) {
            tableServicios.setOnMouseClicked(event -> {
                Servicio seleccionado = tableServicios.getSelectionModel().getSelectedItem();
                if (seleccionado != null && event.getClickCount() == 1) {
                    if (seleccionado == lastSelectedServicio) {
                        tableServicios.getSelectionModel().clearSelection();
                        limpiarCamposServicioCrud();
                        lastSelectedServicio = null;
                    } else {
                        lastSelectedServicio = seleccionado;
                    }
                }
            });
            
            tableServicios.getSelectionModel().selectedItemProperty().addListener((obs, oldV, nuevo) -> {
                if (nuevo != null) {
                    idServicioOriginal = nuevo.getIdServicio();
                    txtServicioId.setText(nuevo.getIdServicio() != null ? String.valueOf(nuevo.getIdServicio()) : "");
                    txtServicioId.setDisable(true);
                    txtServicioNombre.setText(nuevo.getNombreServicio());
                    txtServicioDetalle.setText(nuevo.getDetalleServicio());
                    txtServicioPrecio.setText(nuevo.getPrecioServicio() != null ? nuevo.getPrecioServicio().toPlainString() : "");
                } else {
                    idServicioOriginal = null;
                    txtServicioId.setDisable(false);
                }
            });
        }

        listenersConfigurados = true;
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
                    // Recepción: Carga clientes y reservas
                    cargarClientes();
                    cargarReservas();
                    break;

                case PERSONAL_SERVICIO:
                    // Personal de Servicio: Solo carga habitaciones y servicios
                    cargarHabitaciones();
                    cargarReservasServicios();
                    break;

                case ADMINISTRACION:
                    // Administración: Carga empleados, servicios y datos para consultas
                    cargarEmpleados();
                    cargarServicios();
                    cargarAsignaciones();
                    break;

                case DB_ADMIN:
                    // DB_ADMIN: Carga todos los datos
                    cargarClientes();
                    cargarReservas();
                    cargarEmpleados();
                    cargarHabitaciones();
                    cargarServicios();
                    cargarAsignaciones();
                    cargarReservasServicios();
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
            cliente.setSegundoNombre(nullIfBlank(txtClienteSegundoNombre.getText()));
            cliente.setPrimerApellido(txtClientePrimerApellido.getText());
            cliente.setSegundoApellido(nullIfBlank(txtClienteSegundoApellido.getText()));
            cliente.setCalle(txtClienteCalle.getText());
            cliente.setCarrera(txtClienteCarrera.getText());
            cliente.setNumero(txtClienteNumero.getText());
            cliente.setComplemento(nullIfBlank(txtClienteComplemento.getText()));

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
            Cliente seleccionado = tableClientes.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarError("Seleccione un cliente en la tabla para modificar.");
                return;
            }

            if (!camposClienteLlenos()) {
                mostrarError("Complete todos los campos del cliente antes de modificar.");
                return;
            }

            Cliente cliente = new Cliente();
            cliente.setCedulaCliente(cedulaClienteOriginal != null ? cedulaClienteOriginal : Integer.parseInt(txtClienteCedula.getText()));
            cliente.setPrimerNombre(txtClientePrimerNombre.getText());
            cliente.setSegundoNombre(nullIfBlank(txtClienteSegundoNombre.getText()));
            cliente.setPrimerApellido(txtClientePrimerApellido.getText());
            cliente.setSegundoApellido(nullIfBlank(txtClienteSegundoApellido.getText()));
            cliente.setCalle(txtClienteCalle.getText());
            cliente.setCarrera(txtClienteCarrera.getText());
            cliente.setNumero(txtClienteNumero.getText());
            cliente.setComplemento(nullIfBlank(txtClienteComplemento.getText()));

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
        if (tableClientes == null) {
            System.out.println("ADVERTENCIA: tableClientes es null");
            return;
        }
        
        System.out.println("Cargando clientes...");
        tableClientes.getColumns().clear();
        
        TableColumn<Cliente, Integer> colCedula = new TableColumn<>("Cédula");
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        colCedula.setPrefWidth(100);
        
        TableColumn<Cliente, String> colPrimerNombre = new TableColumn<>("Primer Nombre");
        colPrimerNombre.setCellValueFactory(new PropertyValueFactory<>("primerNombre"));
        colPrimerNombre.setPrefWidth(120);
        
        TableColumn<Cliente, String> colSegundoNombre = new TableColumn<>("Segundo Nombre");
        colSegundoNombre.setCellValueFactory(new PropertyValueFactory<>("segundoNombre"));
        colSegundoNombre.setPrefWidth(120);
        
        TableColumn<Cliente, String> colPrimerApellido = new TableColumn<>("Primer Apellido");
        colPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        colPrimerApellido.setPrefWidth(120);
        
        TableColumn<Cliente, String> colSegundoApellido = new TableColumn<>("Segundo Apellido");
        colSegundoApellido.setCellValueFactory(new PropertyValueFactory<>("segundoApellido"));
        colSegundoApellido.setPrefWidth(120);
        
        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            String direccion = c.getCalle() + " # " + c.getCarrera() + " - " + c.getNumero();
            if (c.getComplemento() != null && !c.getComplemento().isEmpty()) {
                direccion += " " + c.getComplemento();
            }
            return new SimpleStringProperty(direccion);
        });
        colDireccion.setPrefWidth(200);
        
        // Cargar todos los clientes primero
        List<Cliente> clientes = clienteDAO.findAll();
        System.out.println("Clientes encontrados: " + clientes.size());
        
        // Cargar TODOS los teléfonos con UN SOLO findAll
        java.util.Map<Integer, List<String>> telefonosPorCedula = new java.util.HashMap<>();
        try {
            List<TelefonoCliente> todosTelefonos = telefonoClienteDAO.findAll();
            for (TelefonoCliente tel : todosTelefonos) {
                telefonosPorCedula.computeIfAbsent(tel.getCedulaCliente(), k -> new ArrayList<>())
                    .add(tel.getTelefonoCliente());
            }
        } catch (Exception e) {
            System.out.println("Error cargando teléfonos: " + e.getMessage());
        }
        
        // Cargar TODOS los emails con UN SOLO findAll
        java.util.Map<Integer, List<String>> emailsPorCedula = new java.util.HashMap<>();
        try {
            List<EmailCliente> todosEmails = emailClienteDAO.findAll();
            for (EmailCliente email : todosEmails) {
                emailsPorCedula.computeIfAbsent(email.getCedulaCliente(), k -> new ArrayList<>())
                    .add(email.getCorreoCliente());
            }
        } catch (Exception e) {
            System.out.println("Error cargando emails: " + e.getMessage());
        }
        
        // Columna de teléfonos (ahora desde memoria)
        TableColumn<Cliente, String> colTelefonos = new TableColumn<>("Teléfonos");
        colTelefonos.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            List<String> telefonos = telefonosPorCedula.getOrDefault(c.getCedulaCliente(), new java.util.ArrayList<>());
            String resultado = String.join(", ", telefonos);
            return new SimpleStringProperty(resultado.isEmpty() ? "N/A" : resultado);
        });
        colTelefonos.setPrefWidth(150);
        
        // Columna de emails (ahora desde memoria)
        TableColumn<Cliente, String> colEmails = new TableColumn<>("Emails");
        colEmails.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            List<String> emails = emailsPorCedula.getOrDefault(c.getCedulaCliente(), new java.util.ArrayList<>());
            String resultado = String.join(", ", emails);
            return new SimpleStringProperty(resultado.isEmpty() ? "N/A" : resultado);
        });
        colEmails.setPrefWidth(200);
        
        tableClientes.getColumns().addAll(colCedula, colPrimerNombre, colSegundoNombre, 
                                         colPrimerApellido, colSegundoApellido, colDireccion, colTelefonos, colEmails);
        
        if (!clientes.isEmpty()) {
            Cliente primerCliente = clientes.get(0);
            System.out.println("Primer cliente: " + primerCliente.getPrimerNombre() + " " + primerCliente.getPrimerApellido());
        }
        ObservableList<Cliente> data = FXCollections.observableArrayList(clientes);
        tableClientes.setItems(data);
        System.out.println("Clientes cargados en la tabla");
    }

    private void limpiarCamposCliente() {
        txtClienteCedula.clear();
        txtClienteCedula.setDisable(false);
        cedulaClienteOriginal = null;
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
            reserva.setFechaLlegada(parseFecha(txtReservaFechaLlegada.getText()));
            reserva.setIdHabitacion(Integer.parseInt(txtReservaIdHabitacion.getText()));

            if (!txtReservaCarrera.getText().isEmpty()) {
                reserva.setFechaSalida(parseFecha(txtReservaCarrera.getText()));
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
            Reserva seleccionada = tableReservas.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                mostrarError("Seleccione una reserva en la tabla para modificar.");
                return;
            }

            if (!camposReservaLlenos()) {
                mostrarError("Complete todos los campos de la reserva antes de modificar.");
                return;
            }

            Reserva reserva = new Reserva();
            reserva.setCedulaCliente(Integer.parseInt(txtReservaCedulaCliente.getText()));
            reserva.setFechaLlegada(parseFecha(txtReservaFechaLlegada.getText()));
            reserva.setIdHabitacion(Integer.parseInt(txtReservaIdHabitacion.getText()));
            if (!txtReservaCarrera.getText().isEmpty()) {
                reserva.setFechaSalida(parseFecha(txtReservaCarrera.getText()));
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
            Reserva seleccionada = tableReservas.getSelectionModel().getSelectedItem();
            if (seleccionada == null) {
                mostrarError("Seleccione una reserva en la tabla para eliminar.");
                return;
            }

            Integer cedula = seleccionada.getCedulaCliente();
            Integer idHabitacion = seleccionada.getIdHabitacion();
            java.time.LocalDateTime fechaLlegada = seleccionada.getFechaLlegada();

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
        if (tableReservas == null) {
            System.out.println("ADVERTENCIA: tableReservas es null");
            return;
        }
        System.out.println("Cargando reservas...");
        tableReservas.getColumns().clear();
        
        TableColumn<Reserva, Integer> colCedulaCliente = new TableColumn<>("Cédula Cliente");
        colCedulaCliente.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        colCedulaCliente.setPrefWidth(120);
        
        TableColumn<Reserva, Integer> colIdHabitacion = new TableColumn<>("ID Habitación");
        colIdHabitacion.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
        colIdHabitacion.setPrefWidth(120);
        
        TableColumn<Reserva, LocalDateTime> colFechaLlegada = new TableColumn<>("Fecha Llegada");
        colFechaLlegada.setCellValueFactory(new PropertyValueFactory<>("fechaLlegada"));
        colFechaLlegada.setPrefWidth(180);
        
        TableColumn<Reserva, LocalDateTime> colFechaSalida = new TableColumn<>("Fecha Salida");
        colFechaSalida.setCellValueFactory(new PropertyValueFactory<>("fechaSalida"));
        colFechaSalida.setPrefWidth(180);
        
        tableReservas.getColumns().addAll(colCedulaCliente, colIdHabitacion, 
                                         colFechaLlegada, colFechaSalida);
        
        System.out.println("Columnas de reservas agregadas: " + tableReservas.getColumns().size());
        
        List<Reserva> reservas = reservaDAO.findAll();
        System.out.println("Reservas encontradas: " + reservas.size());
        if (!reservas.isEmpty()) {
            Reserva primeraReserva = reservas.get(0);
            System.out.println("Primera reserva - Cliente: " + primeraReserva.getCedulaCliente() + 
                             ", Habitación: " + primeraReserva.getIdHabitacion());
        }
        ObservableList<Reserva> data = FXCollections.observableArrayList(reservas);
        tableReservas.setItems(data);
        System.out.println("Reservas cargadas en la tabla");
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
            empleado.setSegundoNombre(nullIfBlank(txtEmpleadoSegundoNombre.getText()));
            empleado.setPrimerApellido(txtEmpleadoPrimerApellido.getText());
            empleado.setSegundoApellido(nullIfBlank(txtEmpleadoSegundoApellido.getText()));
            empleado.setCalle(txtEmpleadoCalle.getText());
            empleado.setCarrera(txtEmpleadoCarrera.getText());
            empleado.setNumero(txtEmpleadoNumero.getText());
            empleado.setComplemento(nullIfBlank(txtEmpleadoComplemento.getText()));
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
            Empleado seleccionado = tableEmpleados.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarError("Seleccione un empleado en la tabla para modificar.");
                return;
            }

            if (!camposEmpleadoLlenos()) {
                mostrarError("Complete todos los campos del empleado antes de modificar.");
                return;
            }

            Empleado empleado = new Empleado();
            empleado.setCedulaEmpleado(cedulaEmpleadoOriginal != null ? cedulaEmpleadoOriginal : Integer.parseInt(txtEmpleadoCedula.getText()));
            empleado.setPrimerNombre(txtEmpleadoPrimerNombre.getText());
            empleado.setSegundoNombre(nullIfBlank(txtEmpleadoSegundoNombre.getText()));
            empleado.setPrimerApellido(txtEmpleadoPrimerApellido.getText());
            empleado.setSegundoApellido(nullIfBlank(txtEmpleadoSegundoApellido.getText()));
            empleado.setCalle(txtEmpleadoCalle.getText());
            empleado.setCarrera(txtEmpleadoCarrera.getText());
            empleado.setNumero(txtEmpleadoNumero.getText());
            empleado.setComplemento(nullIfBlank(txtEmpleadoComplemento.getText()));
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
            Empleado seleccionado = tableEmpleados.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarError("Seleccione un empleado en la tabla para eliminar.");
                return;
            }

            if (empleadoDAO.deleteById(seleccionado.getCedulaEmpleado())) {
                mostrarInfo("Empleado eliminado exitosamente");
                limpiarCamposEmpleado();
                cargarEmpleados();
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar empleado: " + e.getMessage());
        }
    }

    private void cargarEmpleados() throws SQLException {
        if (tableEmpleados == null) return;
        tableEmpleados.getColumns().clear();
        
        TableColumn<Empleado, Integer> colCedula = new TableColumn<>("Cédula");
        colCedula.setCellValueFactory(new PropertyValueFactory<>("cedulaEmpleado"));
        colCedula.setPrefWidth(100);
        
        TableColumn<Empleado, String> colPrimerNombre = new TableColumn<>("Primer Nombre");
        colPrimerNombre.setCellValueFactory(new PropertyValueFactory<>("primerNombre"));
        colPrimerNombre.setPrefWidth(120);
        
        TableColumn<Empleado, String> colPrimerApellido = new TableColumn<>("Primer Apellido");
        colPrimerApellido.setCellValueFactory(new PropertyValueFactory<>("primerApellido"));
        colPrimerApellido.setPrefWidth(120);
        
        TableColumn<Empleado, String> colCargo = new TableColumn<>("Cargo");
        colCargo.setCellValueFactory(new PropertyValueFactory<>("cargo"));
        colCargo.setPrefWidth(120);
        
        TableColumn<Empleado, Integer> colIdArea = new TableColumn<>("ID Área");
        colIdArea.setCellValueFactory(new PropertyValueFactory<>("idArea"));
        colIdArea.setPrefWidth(80);
        
        TableColumn<Empleado, String> colDireccion = new TableColumn<>("Dirección");
        colDireccion.setCellValueFactory(cellData -> {
            Empleado e = cellData.getValue();
            String direccion = "Calle " + e.getCalle() + " # " + e.getCarrera() + " - " + e.getNumero();
            if (e.getComplemento() != null && !e.getComplemento().isEmpty()) {
                direccion += " " + e.getComplemento();
            }
            return new SimpleStringProperty(direccion);
        });
        colDireccion.setPrefWidth(200);
        
        tableEmpleados.getColumns().addAll(colCedula, colPrimerNombre, colPrimerApellido, 
                                          colCargo, colIdArea, colDireccion);
        
        List<Empleado> empleados = empleadoDAO.findAll();
        ObservableList<Empleado> data = FXCollections.observableArrayList(empleados);
        tableEmpleados.setItems(data);
    }

    private void limpiarCamposEmpleado() {
        txtEmpleadoCedula.clear();
        txtEmpleadoCedula.setDisable(false);
        cedulaEmpleadoOriginal = null;
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
        if (tableAsignaciones == null) return;
        tableAsignaciones.getColumns().clear();
        
        TableColumn<Atiende, Integer> colCedulaCliente = new TableColumn<>("Cédula Cliente");
        colCedulaCliente.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        colCedulaCliente.setPrefWidth(120);
        
        TableColumn<Atiende, Integer> colCedulaEmpleado = new TableColumn<>("Cédula Empleado");
        colCedulaEmpleado.setCellValueFactory(new PropertyValueFactory<>("cedulaEmpleado"));
        colCedulaEmpleado.setPrefWidth(140);
        
        TableColumn<Atiende, Integer> colIdHabitacion = new TableColumn<>("ID Habitación");
        colIdHabitacion.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
        colIdHabitacion.setPrefWidth(120);
        
        TableColumn<Atiende, Integer> colIdServicio = new TableColumn<>("ID Servicio");
        colIdServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        colIdServicio.setPrefWidth(100);
        
        TableColumn<Atiende, LocalDateTime> colFechaUso = new TableColumn<>("Fecha Uso");
        colFechaUso.setCellValueFactory(new PropertyValueFactory<>("fechaUso"));
        colFechaUso.setPrefWidth(180);
        
        tableAsignaciones.getColumns().addAll(colCedulaCliente, colCedulaEmpleado, 
                                             colIdHabitacion, colIdServicio, colFechaUso);
        
        // Mensaje inicial
        tableAsignaciones.setPlaceholder(new Label("Ingrese datos de reserva para ver asignaciones"));
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
        if (tableHabitaciones == null) return;
        tableHabitaciones.getColumns().clear();
        
        TableColumn<Habitacion, Integer> colId = new TableColumn<>("ID Habitación");
        colId.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
        colId.setPrefWidth(120);
        
        TableColumn<Habitacion, Integer> colCategoria = new TableColumn<>("ID Categoría");
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("idCategoria"));
        colCategoria.setPrefWidth(120);
        
        TableColumn<Habitacion, Boolean> colMantenimiento = new TableColumn<>("Mantenimiento");
        colMantenimiento.setCellValueFactory(new PropertyValueFactory<>("mantenimiento"));
        colMantenimiento.setPrefWidth(120);
        
        TableColumn<Habitacion, String> colEstado = new TableColumn<>("Estado");
        colEstado.setCellValueFactory(cellData -> {
            String estado = cellData.getValue().getMantenimiento() ? "En Mantenimiento" : "Disponible";
            return new SimpleStringProperty(estado);
        });
        colEstado.setPrefWidth(150);
        
        tableHabitaciones.getColumns().addAll(colId, colCategoria, colMantenimiento, colEstado);
        
        List<Habitacion> habitaciones = habitacionDAO.findAll();
        ObservableList<Habitacion> data = FXCollections.observableArrayList(habitaciones);
        tableHabitaciones.setItems(data);
    }

    private void cargarServicios() throws SQLException {
        if (tableServicios == null) return;
        tableServicios.getColumns().clear();
        
        TableColumn<Servicio, Integer> colId = new TableColumn<>("ID Servicio");
        colId.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        colId.setPrefWidth(100);
        
        TableColumn<Servicio, String> colNombre = new TableColumn<>("Nombre");
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombreServicio"));
        colNombre.setPrefWidth(200);
        
        TableColumn<Servicio, String> colDetalle = new TableColumn<>("Detalle");
        colDetalle.setCellValueFactory(new PropertyValueFactory<>("detalleServicio"));
        colDetalle.setPrefWidth(250);
        
        TableColumn<Servicio, BigDecimal> colPrecio = new TableColumn<>("Precio");
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precioServicio"));
        colPrecio.setPrefWidth(120);
        
        tableServicios.getColumns().addAll(colId, colNombre, colDetalle, colPrecio);
        
        List<Servicio> servicios = servicioDAO.findAll();
        ObservableList<Servicio> data = FXCollections.observableArrayList(servicios);
        tableServicios.setItems(data);
    }

    @FXML
    private void handleServicioCrear() {
        try {
            Servicio servicio = new Servicio();
            servicio.setIdServicio(Integer.parseInt(txtServicioId.getText()));
            servicio.setNombreServicio(txtServicioNombre.getText());
            servicio.setDetalleServicio(txtServicioDetalle.getText());
            servicio.setPrecioServicio(new BigDecimal(txtServicioPrecio.getText()));

            if (servicioDAO.insert(servicio)) {
                mostrarInfo("Servicio creado exitosamente");
                limpiarCamposServicioCrud();
                cargarServicios();
            }
        } catch (Exception e) {
            mostrarError("Error al crear servicio: " + e.getMessage());
        }
    }

    @FXML
    private void handleServicioActualizar() {
        try {
            Servicio seleccionado = tableServicios.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarError("Seleccione un servicio en la tabla para modificar.");
                return;
            }

            if (!camposServicioLlenos()) {
                mostrarError("Complete todos los campos del servicio antes de modificar.");
                return;
            }

            Servicio servicio = new Servicio();
            servicio.setIdServicio(idServicioOriginal != null ? idServicioOriginal : Integer.parseInt(txtServicioId.getText()));
            servicio.setNombreServicio(txtServicioNombre.getText());
            servicio.setDetalleServicio(txtServicioDetalle.getText());
            servicio.setPrecioServicio(new BigDecimal(txtServicioPrecio.getText()));

            if (servicioDAO.update(servicio)) {
                mostrarInfo("Servicio actualizado exitosamente");
                limpiarCamposServicioCrud();
                cargarServicios();
            }
        } catch (Exception e) {
            mostrarError("Error al actualizar servicio: " + e.getMessage());
        }
    }

    @FXML
    private void handleServicioEliminar() {
        try {
            Servicio seleccionado = tableServicios.getSelectionModel().getSelectedItem();
            if (seleccionado == null) {
                mostrarError("Seleccione un servicio en la tabla para eliminar.");
                return;
            }

            if (servicioDAO.deleteById(seleccionado.getIdServicio())) {
                mostrarInfo("Servicio eliminado exitosamente");
                limpiarCamposServicioCrud();
                cargarServicios();
            }
        } catch (Exception e) {
            mostrarError("Error al eliminar servicio: " + e.getMessage());
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
        if (tableServiciosReservaciones == null) return;
        tableServiciosReservaciones.getColumns().clear();
        
        TableColumn<ReservaServicio, Integer> colCedulaCliente = new TableColumn<>("Cédula Cliente");
        colCedulaCliente.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        colCedulaCliente.setPrefWidth(120);
        
        TableColumn<ReservaServicio, Integer> colIdHabitacion = new TableColumn<>("ID Habitación");
        colIdHabitacion.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
        colIdHabitacion.setPrefWidth(120);
        
        TableColumn<ReservaServicio, Integer> colIdServicio = new TableColumn<>("ID Servicio");
        colIdServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        colIdServicio.setPrefWidth(100);
        
        TableColumn<ReservaServicio, LocalDateTime> colFechaLlegada = new TableColumn<>("Fecha Llegada");
        colFechaLlegada.setCellValueFactory(new PropertyValueFactory<>("fechaLlegada"));
        colFechaLlegada.setPrefWidth(180);
        
        TableColumn<ReservaServicio, LocalDateTime> colFechaUso = new TableColumn<>("Fecha Uso");
        colFechaUso.setCellValueFactory(new PropertyValueFactory<>("fechaUso"));
        colFechaUso.setPrefWidth(180);
        
        tableServiciosReservaciones.getColumns().addAll(colCedulaCliente, colIdHabitacion, 
                                                        colIdServicio, colFechaLlegada, colFechaUso);
        
        // Mensaje inicial
        tableServiciosReservaciones.setPlaceholder(new Label("Añada servicios para ver la lista"));
    }

    private void limpiarCamposServicio() {
        txtServicioCedulaCliente.clear();
        txtServicioIdHabitacion.clear();
        txtServicioFechaLlegada.clear();
        txtServicioIdServicio.clear();
    }

    private void limpiarCamposServicioCrud() {
        txtServicioId.clear();
        txtServicioId.setDisable(false);
        idServicioOriginal = null;
        txtServicioNombre.clear();
        txtServicioDetalle.clear();
        txtServicioPrecio.clear();
    }

    private String nullIfBlank(String value) {
        if (value == null) return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private LocalDateTime parseFecha(String fecha) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = LocalDate.parse(fecha, formatter);
        return date.atStartOfDay();
    }

    private boolean camposClienteLlenos() {
        return !txtClienteCedula.getText().isEmpty()
                && !txtClientePrimerNombre.getText().isEmpty()
                && !txtClientePrimerApellido.getText().isEmpty()
                && !txtClienteCalle.getText().isEmpty()
                && !txtClienteCarrera.getText().isEmpty()
                && !txtClienteNumero.getText().isEmpty();
    }

    private boolean camposReservaLlenos() {
        return !txtReservaCedulaCliente.getText().isEmpty()
                && !txtReservaFechaLlegada.getText().isEmpty()
                && !txtReservaIdHabitacion.getText().isEmpty();
    }

    private boolean camposEmpleadoLlenos() {
        return !txtEmpleadoCedula.getText().isEmpty()
                && !txtEmpleadoPrimerNombre.getText().isEmpty()
                && !txtEmpleadoPrimerApellido.getText().isEmpty()
                && !txtEmpleadoCalle.getText().isEmpty()
                && !txtEmpleadoCarrera.getText().isEmpty()
                && !txtEmpleadoNumero.getText().isEmpty()
                && !txtEmpleadoIdArea.getText().isEmpty();
    }

    private boolean camposServicioLlenos() {
        return !txtServicioId.getText().isEmpty()
                && !txtServicioNombre.getText().isEmpty()
                && !txtServicioDetalle.getText().isEmpty()
                && !txtServicioPrecio.getText().isEmpty();
    }

    @FXML
    private void handleCerrarSesion() {
        try {
            UserSession.getInstance().logout();
            App.setRoot("hotel/login");
        } catch (Exception e) {
            mostrarError("No se pudo volver al login: " + e.getMessage());
        }
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
