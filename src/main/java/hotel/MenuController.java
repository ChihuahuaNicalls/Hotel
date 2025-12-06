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

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

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
    private TextField txtEmpleadoArea;
    @FXML
    private TextField txtEmpleadoCargo;
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
    private TextField txtAsignarFechaUso;
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
    private ReservaServicio reservaServicioSeleccionada = null;
    @FXML
    private TableView<ReservaServicio> tableReservasAsignar;

    @FXML
    private TextField txtHabitacionId;
    @FXML
    private Button btnLiberarHabitacion;
    @FXML
    private Button btnTerminarMantenimiento;
    @FXML
    private TableView<ObservableList<String>> tableHabitaciones;

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
    private TableView<Servicio> tableServiciosLista;

    @FXML
    private Button btnDisponibilidadHabitaciones;
    @FXML
    private Button btnConsultaGeneral;
    @FXML
    private TableView<ObservableList<String>> tableConsultas;

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

    private Integer cedulaClienteOriginal;
    private Integer cedulaEmpleadoOriginal;
    private Integer idServicioOriginal;

    private Cliente lastSelectedCliente = null;
    private Reserva lastSelectedReserva = null;
    private Empleado lastSelectedEmpleado = null;
    private Servicio lastSelectedServicio = null;

    private Map<Integer, List<String>> telefonosPorCedulaClientes = new HashMap<>();
    private Map<Integer, List<String>> emailsPorCedulaClientes = new HashMap<>();

    private Map<Integer, List<String>> telefonosPorCedulaEmpleados = new HashMap<>();
    private Map<Integer, List<String>> emailsPorCedulaEmpleados = new HashMap<>();

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

    private String formatFecha(LocalDateTime fecha) {
        if (fecha == null)
            return "";
        try {
            DateTimeFormatter fmtShort = DateTimeFormatter.ofPattern("dd/MM/yy");
            return fecha.toLocalDate().format(fmtShort);
        } catch (Exception e) {
            return fecha.toString();
        }
    }

    private void configurarTabsSegunUsuario() {
        UserSession session = UserSession.getInstance();
        UserType userType = session.getUserType();

        tabPane.getTabs().clear();

        switch (userType) {
            case RECEPCION:

                tabPane.getTabs().addAll(tabClientes, tabReservas, tabConsultas);
                break;

            case PERSONAL_SERVICIO:

                tabPane.getTabs().addAll(tabClientes, tabHabitaciones, tabAdicionServicios);
                break;

            case ADMINISTRACION:

                tabPane.getTabs().addAll(tabEmpleados, tabAsignarEmpleados, tabServicios, tabConsultas);
                break;

            case DB_ADMIN:

                tabPane.getTabs().addAll(tabClientes, tabReservas, tabServicios, tabEmpleados,
                        tabAsignarEmpleados, tabHabitaciones, tabAdicionServicios, tabConsultas);
                break;
        }

        if (btnDisponibilidadHabitaciones != null)
            btnDisponibilidadHabitaciones.setDisable(true);
        if (btnConsultaGeneral != null)
            btnConsultaGeneral.setDisable(true);

        switch (userType) {
            case RECEPCION:
            case PERSONAL_SERVICIO:
                if (btnDisponibilidadHabitaciones != null)
                    btnDisponibilidadHabitaciones.setDisable(false);
                if (btnConsultaGeneral != null)
                    btnConsultaGeneral.setDisable(true);
                break;
            case ADMINISTRACION:
                if (btnConsultaGeneral != null)
                    btnConsultaGeneral.setDisable(false);
                if (btnDisponibilidadHabitaciones != null)
                    btnDisponibilidadHabitaciones.setDisable(true);
                break;
            case DB_ADMIN:

                if (btnDisponibilidadHabitaciones != null)
                    btnDisponibilidadHabitaciones.setDisable(false);
                if (btnConsultaGeneral != null)
                    btnConsultaGeneral.setDisable(false);
                break;
        }

        if (userType == UserType.RECEPCION) {
            if (btnClienteCrear != null)
                btnClienteCrear.setDisable(false);
            if (btnClienteActualizar != null)
                btnClienteActualizar.setDisable(false);
            if (txtClienteTelefonos != null)
                txtClienteTelefonos.setDisable(false);
            if (txtClienteEmails != null)
                txtClienteEmails.setDisable(false);
            if (tableClientes != null)
                tableClientes.setEditable(true);
        }

        if (userType == UserType.PERSONAL_SERVICIO) {

            if (btnClienteCrear != null)
                btnClienteCrear.setDisable(true);
            if (btnClienteActualizar != null)
                btnClienteActualizar.setDisable(true);

            if (txtClienteCedula != null)
                txtClienteCedula.setDisable(true);
            if (txtClientePrimerNombre != null)
                txtClientePrimerNombre.setDisable(true);
            if (txtClienteSegundoNombre != null)
                txtClienteSegundoNombre.setDisable(true);
            if (txtClientePrimerApellido != null)
                txtClientePrimerApellido.setDisable(true);
            if (txtClienteSegundoApellido != null)
                txtClienteSegundoApellido.setDisable(true);
            if (txtClienteCalle != null)
                txtClienteCalle.setDisable(true);
            if (txtClienteCarrera != null)
                txtClienteCarrera.setDisable(true);
            if (txtClienteNumero != null)
                txtClienteNumero.setDisable(true);
            if (txtClienteComplemento != null)
                txtClienteComplemento.setDisable(true);
            if (txtClienteTelefonos != null)
                txtClienteTelefonos.setDisable(true);
            if (txtClienteEmails != null)
                txtClienteEmails.setDisable(true);

            if (tableClientes != null) {
                tableClientes.setEditable(false);
            }
        }
    }

    @FXML
    private void handleVerDisponibilidad() {
        String sql = "SELECT h.idHabitacion, (c.precioCategoria * GREATEST(DATE_PART('day', r.fechaSalida - r.fechaLlegada), 1)) AS precioEstadia, "
                + "CASE WHEN h.mantenimiento = TRUE THEN 'En mantenimiento' WHEN r.fechaLlegada <= CURRENT_DATE AND r.fechaSalida >= CURRENT_DATE THEN 'Ocupada' ELSE 'Disponible' END AS estadoActual "
                + "FROM Habitacion h "
                + "JOIN Categoria c ON h.idCategoria = c.idCategoria "
                + "LEFT JOIN Reserva r ON h.idHabitacion = r.idHabitacion "
                + "ORDER BY h.idHabitacion";
        try {
            populateTableFromQuery(sql);
        } catch (Exception e) {
            mostrarError("Error ejecutando consulta de disponibilidad: " + e.getMessage());
        }
    }

    @FXML
    private void handleConsultaGeneral() {
        UserSession session = UserSession.getInstance();
        if (session.getUserType() != UserType.ADMINISTRACION && session.getUserType() != UserType.DB_ADMIN) {
            mostrarError("Acceso denegado: consulta general solo para administracion.");
            return;
        }

        String sql = "SELECT c.cedulacliente AS cedula, c.primerapellido AS primerapellido, c.primernombre AS primernombre, "
                +
                "r.idhabitacion AS idhabitacion, r.fechallegada AS fechallegada, r.fechasalida AS fechasalida, " +
                "COALESCE(SUM(s.precioservicio),0) AS total_consumos " +
                "FROM cliente c " +
                "LEFT JOIN reserva r ON c.cedulacliente = r.cedulacliente " +
                "LEFT JOIN reserva_servicio rs ON rs.cedulacliente = c.cedulacliente AND rs.idhabitacion = r.idhabitacion AND rs.fechallegada = r.fechallegada "
                +
                "LEFT JOIN servicio s ON rs.idservicio = s.idservicio " +
                "GROUP BY c.cedulacliente, c.primerapellido, c.primernombre, r.idhabitacion, r.fechallegada, r.fechasalida "
                +
                "ORDER BY total_consumos DESC";
        try {
            populateTableFromQuery(sql);
        } catch (Exception e) {
            mostrarError("Error ejecutando consulta general: " + e.getMessage());
        }
    }

    private void populateTableFromQuery(String sql) throws SQLException {
        if (tableConsultas == null)
            throw new SQLException("Componente tableConsultas no inicializado");

        tableConsultas.getColumns().clear();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        PostgreSQLConnection db = PostgreSQLConnection.getConnector();
        try (Connection conn = db.getConn();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            for (int i = 1; i <= colCount; i++) {
                final int colIndex = i - 1;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(meta.getColumnLabel(i));
                col.setCellValueFactory(cellData -> {
                    ObservableList<String> row = cellData.getValue();
                    String value = "";
                    if (row != null && row.size() > colIndex && row.get(colIndex) != null)
                        value = row.get(colIndex);
                    return new SimpleStringProperty(value);
                });
                tableConsultas.getColumns().add(col);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= colCount; i++) {
                    Object obj = rs.getObject(i);
                    String val = "";
                    if (obj == null) {
                        val = "";
                    } else if (obj instanceof java.sql.Timestamp) {
                        val = formatFecha(((java.sql.Timestamp) obj).toLocalDateTime());
                    } else if (obj instanceof java.sql.Date) {
                        val = formatFecha(((java.sql.Date) obj).toLocalDate().atStartOfDay());
                    } else {
                        String s = rs.getString(i);
                        val = s != null ? s : "";
                    }
                    row.add(val);
                }
                data.add(row);
            }

            tableConsultas.setItems(data);
        }
    }

    private void configurarSeleccionTablas() {
        if (listenersConfigurados)
            return;

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

                    List<String> tels = telefonosPorCedulaClientes.getOrDefault(nuevo.getCedulaCliente(),
                            new ArrayList<>());
                    txtClienteTelefonos.setText(String.join(", ", tels));
                    List<String> ems = emailsPorCedulaClientes.getOrDefault(nuevo.getCedulaCliente(),
                            new ArrayList<>());
                    txtClienteEmails.setText(String.join(", ", ems));
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
                    txtReservaCedulaCliente.setText(String.valueOf(nuevo.getCedulaCliente()));
                    txtReservaIdHabitacion.setText(String.valueOf(nuevo.getIdHabitacion()));
                    txtReservaFechaLlegada.setText(formatFecha(nuevo.getFechaLlegada()));
                    txtReservaCarrera.setText(formatFecha(nuevo.getFechaSalida()));
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
                    txtEmpleadoArea.setText(nuevo.getIdArea() != null ? String.valueOf(nuevo.getIdArea()) : "");
                    txtEmpleadoCargo.setText(nuevo.getCargo() != null ? nuevo.getCargo() : "");

                    List<String> tels = telefonosPorCedulaEmpleados.getOrDefault(nuevo.getCedulaEmpleado(),
                            new ArrayList<>());
                    txtEmpleadoTelefonos.setText(String.join(", ", tels));
                    List<String> ems = emailsPorCedulaEmpleados.getOrDefault(nuevo.getCedulaEmpleado(),
                            new ArrayList<>());
                    txtEmpleadoEmails.setText(String.join(", ", ems));
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
                    txtServicioPrecio.setText(
                            nuevo.getPrecioServicio() != null ? nuevo.getPrecioServicio().toPlainString() : "");
                } else {
                    idServicioOriginal = null;
                    txtServicioId.setDisable(false);
                }
            });
        }

        listenersConfigurados = true;
    }

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

                    cargarClientes();
                    cargarHabitaciones();
                    cargarReservasServicios();
                    break;

                case ADMINISTRACION:

                    cargarEmpleados();
                    cargarServicios();
                    cargarAsignaciones();

                    cargarReservas();
                    break;

                case DB_ADMIN:

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
            cliente.setCedulaCliente(cedulaClienteOriginal != null ? cedulaClienteOriginal
                    : Integer.parseInt(txtClienteCedula.getText()));
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

                telefonoClienteDAO.deleteByCliente(cliente.getCedulaCliente());
                emailClienteDAO.deleteByCliente(cliente.getCedulaCliente());
                procesarTelefonosCliente(cliente.getCedulaCliente(), txtClienteTelefonos.getText());
                procesarEmailsCliente(cliente.getCedulaCliente(), txtClienteEmails.getText());
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

        TableColumn<Cliente, Integer> colCedula = new TableColumn<>("Cedula");
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

        TableColumn<Cliente, String> colDireccion = new TableColumn<>("Direccion");
        colDireccion.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            String direccion = c.getCalle() + " # " + c.getCarrera() + " - " + c.getNumero();
            if (c.getComplemento() != null && !c.getComplemento().isEmpty()) {
                direccion += " " + c.getComplemento();
            }
            return new SimpleStringProperty(direccion);
        });
        colDireccion.setPrefWidth(200);

        List<Cliente> clientes = clienteDAO.findAll();
        System.out.println("Clientes encontrados: " + clientes.size());

        java.util.Map<Integer, List<String>> telefonosPorCedula = new java.util.HashMap<>();
        try {
            List<TelefonoCliente> todosTelefonos = telefonoClienteDAO.findAll();
            for (TelefonoCliente tel : todosTelefonos) {
                telefonosPorCedula.computeIfAbsent(tel.getCedulaCliente(), k -> new ArrayList<>())
                        .add(tel.getTelefonoCliente());
            }
        } catch (Exception e) {
            System.out.println("Error cargando telefonos: " + e.getMessage());
        }

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

        TableColumn<Cliente, String> colTelefonos = new TableColumn<>("Telefonos");
        colTelefonos.setCellValueFactory(cellData -> {
            Cliente c = cellData.getValue();
            List<String> telefonos = telefonosPorCedula.getOrDefault(c.getCedulaCliente(), new java.util.ArrayList<>());
            String resultado = String.join(", ", telefonos);
            return new SimpleStringProperty(resultado.isEmpty() ? "N/A" : resultado);
        });
        colTelefonos.setPrefWidth(150);

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
            System.out.println(
                    "Primer cliente: " + primerCliente.getPrimerNombre() + " " + primerCliente.getPrimerApellido());
        }

        this.telefonosPorCedulaClientes = telefonosPorCedula;
        this.emailsPorCedulaClientes = emailsPorCedula;

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
        if (telefonos == null || telefonos.trim().isEmpty())
            return;
        String[] parts = telefonos.split("[,;]+");
        java.util.Set<String> dedupe = new java.util.LinkedHashSet<>();
        for (String p : parts) {
            String t = p == null ? "" : p.trim();
            if (!t.isEmpty())
                dedupe.add(t);
        }
        for (String tel : dedupe) {
            TelefonoCliente telefono = new TelefonoCliente();
            telefono.setCedulaCliente(cedula);
            telefono.setTelefonoCliente(tel);
            telefonoClienteDAO.insert(telefono);
        }
    }

    private void procesarEmailsCliente(Integer cedula, String emails) throws SQLException {
        if (emails == null || emails.trim().isEmpty())
            return;
        String[] parts = emails.split("[,;]+");
        java.util.Set<String> dedupe = new java.util.LinkedHashSet<>();
        for (String p : parts) {
            String m = p == null ? "" : p.trim();
            if (!m.isEmpty())
                dedupe.add(m);
        }
        for (String mail : dedupe) {
            EmailCliente email = new EmailCliente();
            email.setCedulaCliente(cedula);
            email.setCorreoCliente(mail);
            emailClienteDAO.insert(email);
        }
    }

    @FXML
    private void handleReservaCrear() {
        try {
            Reserva reserva = new Reserva();
            reserva.setCedulaCliente(Integer.parseInt(txtReservaCedulaCliente.getText()));

            java.time.LocalDateTime fechaLlegada = parseFecha(txtReservaFechaLlegada.getText());
            reserva.setFechaLlegada(fechaLlegada);
            reserva.setIdHabitacion(Integer.parseInt(txtReservaIdHabitacion.getText()));

            if (txtReservaCarrera == null || txtReservaCarrera.getText().trim().isEmpty()) {
                mostrarError("Debe ingresar la fecha de salida (DD/MM/AA o DD/MM/AAAA).");
                return;
            }
            java.time.LocalDateTime fechaSalida = parseFecha(txtReservaCarrera.getText());

            if (!fechaSalida.isAfter(fechaLlegada)) {
                mostrarError("La fecha de salida debe ser posterior a la fecha de llegada.");
                return;
            }
            java.time.LocalDateTime hoyInicio = java.time.LocalDate.now().atStartOfDay();
            if (fechaSalida.isBefore(hoyInicio)) {
                mostrarError("La fecha de salida debe ser hoy o posterior.");
                return;
            }
            reserva.setFechaSalida(fechaSalida);

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

            Integer nuevaCedula = !txtReservaCedulaCliente.getText().isEmpty()
                    ? Integer.parseInt(txtReservaCedulaCliente.getText())
                    : seleccionada.getCedulaCliente();
            Integer nuevoIdHabitacion = !txtReservaIdHabitacion.getText().isEmpty()
                    ? Integer.parseInt(txtReservaIdHabitacion.getText())
                    : seleccionada.getIdHabitacion();
            java.time.LocalDateTime nuevaFechaLlegada = null;
            if (txtReservaFechaLlegada != null && !txtReservaFechaLlegada.getText().trim().isEmpty()) {
                nuevaFechaLlegada = parseFecha(txtReservaFechaLlegada.getText());
            } else {
                nuevaFechaLlegada = seleccionada.getFechaLlegada();
            }

            java.time.LocalDateTime nuevaFechaSalida = null;
            if (txtReservaCarrera != null && !txtReservaCarrera.getText().trim().isEmpty()) {
                nuevaFechaSalida = parseFecha(txtReservaCarrera.getText());
            } else {
                nuevaFechaSalida = seleccionada.getFechaSalida();
            }

            reserva.setCedulaCliente(nuevaCedula);
            reserva.setIdHabitacion(nuevoIdHabitacion);
            reserva.setFechaLlegada(nuevaFechaLlegada);
            reserva.setFechaSalida(nuevaFechaSalida);

            Integer origCedula = seleccionada.getCedulaCliente();
            Integer origIdHabitacion = seleccionada.getIdHabitacion();
            java.time.LocalDateTime origFechaLlegada = seleccionada.getFechaLlegada();

            java.time.LocalDateTime hoyInicio = java.time.LocalDate.now().atStartOfDay();
            if (reserva.getFechaSalida() == null || reserva.getFechaSalida().isBefore(hoyInicio)) {
                mostrarError("La fecha de salida debe ser hoy o posterior.");
                return;
            }

            if (!reserva.getFechaSalida().isAfter(reserva.getFechaLlegada())) {
                mostrarError("La fecha de salida debe ser posterior a la fecha de llegada.");
                return;
            }

            if (reservaDAO.update(reserva, origCedula, origIdHabitacion, origFechaLlegada)) {
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

        TableColumn<Reserva, Integer> colCedulaCliente = new TableColumn<>("Cedula Cliente");
        colCedulaCliente.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        colCedulaCliente.setPrefWidth(120);

        TableColumn<Reserva, Integer> colIdHabitacion = new TableColumn<>("Id Habitacion");
        colIdHabitacion.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
        colIdHabitacion.setPrefWidth(120);

        TableColumn<Reserva, String> colFechaLlegada = new TableColumn<>("Fecha Llegada");
        colFechaLlegada.setCellValueFactory(cellData -> {
            Reserva r = cellData.getValue();
            java.time.LocalDateTime f = r == null ? null : r.getFechaLlegada();
            return new SimpleStringProperty(formatFecha(f));
        });
        colFechaLlegada.setPrefWidth(180);

        TableColumn<Reserva, String> colFechaSalida = new TableColumn<>("Fecha Salida");
        colFechaSalida.setCellValueFactory(cellData -> {
            Reserva r = cellData.getValue();
            java.time.LocalDateTime f = r == null ? null : r.getFechaSalida();
            return new SimpleStringProperty(formatFecha(f));
        });
        colFechaSalida.setPrefWidth(180);

        tableReservas.getColumns().addAll(colCedulaCliente, colIdHabitacion,
                colFechaLlegada, colFechaSalida);

        if (tableReservasAsignar != null) {
            tableReservasAsignar.getColumns().clear();

            TableColumn<ReservaServicio, Integer> colCedulaClienteA = new TableColumn<>("Cedula Cliente");
            colCedulaClienteA.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
            colCedulaClienteA.setPrefWidth(120);

            TableColumn<ReservaServicio, Integer> colIdHabitacionA = new TableColumn<>("Id Habitacion");
            colIdHabitacionA.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
            colIdHabitacionA.setPrefWidth(120);

            TableColumn<ReservaServicio, Integer> colIdServicioA = new TableColumn<>("Id Servicio");
            colIdServicioA.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
            colIdServicioA.setPrefWidth(100);

            TableColumn<ReservaServicio, String> colFechaLlegadaA = new TableColumn<>("Fecha Llegada");
            colFechaLlegadaA.setCellValueFactory(cellData -> {
                ReservaServicio rs = cellData.getValue();
                java.time.LocalDateTime f = rs == null ? null : rs.getFechaLlegada();
                return new SimpleStringProperty(formatFecha(f));
            });
            colFechaLlegadaA.setPrefWidth(140);

            TableColumn<ReservaServicio, String> colFechaUsoA = new TableColumn<>("Fecha Uso");
            colFechaUsoA.setCellValueFactory(cellData -> {
                ReservaServicio rs = cellData.getValue();
                java.time.LocalDateTime f = rs == null ? null : rs.getFechaUso();
                return new SimpleStringProperty(formatFecha(f));
            });
            colFechaUsoA.setPrefWidth(140);

            tableReservasAsignar.getColumns().addAll(colCedulaClienteA, colIdHabitacionA, colIdServicioA,
                    colFechaLlegadaA, colFechaUsoA);
        }

        System.out.println("Columnas de reservas agregadas: " + tableReservas.getColumns().size());

        List<Reserva> reservas = reservaDAO.findAll();
        System.out.println("Reservas encontradas: " + reservas.size());
        if (!reservas.isEmpty()) {
            Reserva primeraReserva = reservas.get(0);
            System.out.println("Primera reserva - Cliente: " + primeraReserva.getCedulaCliente() +
                    ", Habitacion: " + primeraReserva.getIdHabitacion());
        }
        ObservableList<Reserva> data = FXCollections.observableArrayList(reservas);
        tableReservas.setItems(data);
        if (tableReservasAsignar != null) {
            UserSession session = UserSession.getInstance();
            UserType userType = session.getUserType();

            if (userType == UserType.ADMINISTRACION || userType == UserType.DB_ADMIN
                    || userType == UserType.PERSONAL_SERVICIO) {
                try {
                    List<ReservaServicio> rsList = reservaServicioDAO.findAll();
                    ObservableList<ReservaServicio> rsData = FXCollections.observableArrayList(rsList);
                    tableReservasAsignar.setItems(rsData);
                    tableReservasAsignar.setEditable(false);

                    tableReservasAsignar.setOnMouseClicked(evt -> {
                        ReservaServicio sel = tableReservasAsignar.getSelectionModel().getSelectedItem();
                        reservaServicioSeleccionada = sel;
                        if (sel != null) {
                            txtAsignarCedulaCliente.setText(
                                    sel.getCedulaCliente() != null ? String.valueOf(sel.getCedulaCliente()) : "");
                            txtAsignarIdHabitacion.setText(
                                    sel.getIdHabitacion() != null ? String.valueOf(sel.getIdHabitacion()) : "");
                            txtAsignarIdServicio
                                    .setText(sel.getIdServicio() != null ? String.valueOf(sel.getIdServicio()) : "");
                            if (sel.getFechaLlegada() != null) {
                                txtAsignarFechaLlegada.setText(formatFecha(sel.getFechaLlegada()));
                            } else {
                                txtAsignarFechaLlegada.clear();
                            }
                            if (sel.getFechaUso() != null) {
                                txtAsignarFechaUso.setText(formatFecha(sel.getFechaUso()));
                            } else {
                                txtAsignarFechaUso.clear();
                            }
                        }
                    });
                } catch (Exception e) {
                    System.out.println("No se pudieron cargar reservas-servicios: " + e.getMessage());
                    tableReservasAsignar.setItems(FXCollections.observableArrayList());
                }
            } else {
                tableReservasAsignar.setItems(FXCollections.observableArrayList());
            }
        }
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

            empleado.setCargo(nullIfBlank(txtEmpleadoCargo.getText()));

            String areaVal = (txtEmpleadoArea != null && !txtEmpleadoArea.getText().isEmpty())
                    ? txtEmpleadoArea.getText()
                    : null;
            empleado.setIdArea(areaVal != null && !areaVal.isEmpty() ? Integer.parseInt(areaVal) : null);

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
            empleado.setCedulaEmpleado(cedulaEmpleadoOriginal != null ? cedulaEmpleadoOriginal
                    : Integer.parseInt(txtEmpleadoCedula.getText()));
            empleado.setPrimerNombre(txtEmpleadoPrimerNombre.getText());
            empleado.setSegundoNombre(nullIfBlank(txtEmpleadoSegundoNombre.getText()));
            empleado.setPrimerApellido(txtEmpleadoPrimerApellido.getText());
            empleado.setSegundoApellido(nullIfBlank(txtEmpleadoSegundoApellido.getText()));
            empleado.setCalle(txtEmpleadoCalle.getText());
            empleado.setCarrera(txtEmpleadoCarrera.getText());
            empleado.setNumero(txtEmpleadoNumero.getText());
            empleado.setComplemento(nullIfBlank(txtEmpleadoComplemento.getText()));
            empleado.setCargo(nullIfBlank(txtEmpleadoCargo.getText()));
            String areaValUp = (txtEmpleadoArea != null && !txtEmpleadoArea.getText().isEmpty())
                    ? txtEmpleadoArea.getText()
                    : null;
            empleado.setIdArea(areaValUp != null && !areaValUp.isEmpty() ? Integer.parseInt(areaValUp) : null);

            if (empleadoDAO.update(empleado)) {
                mostrarInfo("Empleado actualizado exitosamente");

                telefonoEmpleadoDAO.deleteByEmpleado(empleado.getCedulaEmpleado());
                emailEmpleadoDAO.deleteByEmpleado(empleado.getCedulaEmpleado());
                procesarTelefonosEmpleado(empleado.getCedulaEmpleado(), txtEmpleadoTelefonos.getText());
                procesarEmailsEmpleado(empleado.getCedulaEmpleado(), txtEmpleadoEmails.getText());

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
        if (tableEmpleados == null)
            return;
        tableEmpleados.getColumns().clear();

        TableColumn<Empleado, Integer> colCedula = new TableColumn<>("Cedula");
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

        TableColumn<Empleado, Integer> colIdArea = new TableColumn<>("Id area");
        colIdArea.setCellValueFactory(new PropertyValueFactory<>("idArea"));
        colIdArea.setPrefWidth(80);

        TableColumn<Empleado, String> colDireccion = new TableColumn<>("Direccion");
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

        java.util.Map<Integer, List<String>> telefonosPorCedula = new java.util.HashMap<>();
        try {
            List<TelefonoEmpleado> todosTels = telefonoEmpleadoDAO.findAll();
            for (TelefonoEmpleado t : todosTels) {
                telefonosPorCedula.computeIfAbsent(t.getCedulaEmpleado(), k -> new ArrayList<>())
                        .add(t.getTelefonoEmpleado());
            }
        } catch (Exception e) {
            System.out.println("Error cargando telefonos empleados: " + e.getMessage());
        }

        java.util.Map<Integer, List<String>> emailsPorCedula = new java.util.HashMap<>();
        try {
            List<EmailEmpleado> todosEmails = emailEmpleadoDAO.findAll();
            for (EmailEmpleado em : todosEmails) {
                emailsPorCedula.computeIfAbsent(em.getCedulaEmpleado(), k -> new ArrayList<>())
                        .add(em.getCorreoEmpleado());
            }
        } catch (Exception e) {
            System.out.println("Error cargando emails empleados: " + e.getMessage());
        }

        this.telefonosPorCedulaEmpleados = telefonosPorCedula;
        this.emailsPorCedulaEmpleados = emailsPorCedula;

        TableColumn<Empleado, String> colTelefonos = new TableColumn<>("Telefonos");
        colTelefonos.setCellValueFactory(cellData -> {
            Empleado emp = cellData.getValue();
            List<String> tels = telefonosPorCedula.getOrDefault(emp.getCedulaEmpleado(), new ArrayList<>());
            String val = String.join(", ", tels);
            return new SimpleStringProperty(val.isEmpty() ? "N/A" : val);
        });
        colTelefonos.setPrefWidth(150);

        TableColumn<Empleado, String> colEmailsEmp = new TableColumn<>("Emails");
        colEmailsEmp.setCellValueFactory(cellData -> {
            Empleado emp = cellData.getValue();
            List<String> ems = emailsPorCedula.getOrDefault(emp.getCedulaEmpleado(), new ArrayList<>());
            String val = String.join(", ", ems);
            return new SimpleStringProperty(val.isEmpty() ? "N/A" : val);
        });
        colEmailsEmp.setPrefWidth(200);

        tableEmpleados.getColumns().addAll(colTelefonos, colEmailsEmp);

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
        txtEmpleadoArea.clear();
        txtEmpleadoCargo.clear();
        txtEmpleadoTelefonos.clear();
        txtEmpleadoEmails.clear();
    }

    private void procesarTelefonosEmpleado(Integer cedula, String telefonos) throws SQLException {
        if (telefonos == null || telefonos.trim().isEmpty())
            return;
        String[] parts = telefonos.split("[,;]+");
        java.util.Set<String> dedupe = new java.util.LinkedHashSet<>();
        for (String p : parts) {
            String t = p == null ? "" : p.trim();
            if (!t.isEmpty())
                dedupe.add(t);
        }
        for (String tel : dedupe) {
            TelefonoEmpleado telefono = new TelefonoEmpleado();
            telefono.setCedulaEmpleado(cedula);
            telefono.setTelefonoEmpleado(tel);
            telefonoEmpleadoDAO.insert(telefono);
        }
    }

    private void procesarEmailsEmpleado(Integer cedula, String emails) throws SQLException {
        if (emails == null || emails.trim().isEmpty())
            return;
        String[] parts = emails.split("[,;]+");
        java.util.Set<String> dedupe = new java.util.LinkedHashSet<>();
        for (String p : parts) {
            String m = p == null ? "" : p.trim();
            if (!m.isEmpty())
                dedupe.add(m);
        }
        for (String mail : dedupe) {
            EmailEmpleado email = new EmailEmpleado();
            email.setCedulaEmpleado(cedula);
            email.setCorreoEmpleado(mail);
            emailEmpleadoDAO.insert(email);
        }
    }

    @FXML
    private void handleAsignarEmpleado() {
        try {
            Atiende atiende = new Atiende();
            atiende.setCedulaCliente(Integer.parseInt(txtAsignarCedulaCliente.getText()));
            atiende.setIdHabitacion(Integer.parseInt(txtAsignarIdHabitacion.getText()));
            atiende.setFechaLlegada(parseFecha(txtAsignarFechaLlegada.getText()));
            atiende.setCedulaEmpleado(Integer.parseInt(txtAsignarCedulaEmpleado.getText()));
            atiende.setIdServicio(Integer.parseInt(txtAsignarIdServicio.getText()));

            if (reservaServicioSeleccionada != null) {

                if (!reservaServicioSeleccionada.getCedulaCliente().equals(atiende.getCedulaCliente()) ||
                        !reservaServicioSeleccionada.getIdHabitacion().equals(atiende.getIdHabitacion()) ||
                        !reservaServicioSeleccionada.getIdServicio().equals(atiende.getIdServicio()) ||
                        !reservaServicioSeleccionada.getFechaLlegada().equals(atiende.getFechaLlegada())) {
                    mostrarError(
                            "La fila seleccionada no coincide con los campos del formulario. Selecciona la fila correcta o limpia el formulario.");
                    return;
                }
                atiende.setFechaUso(reservaServicioSeleccionada.getFechaUso());
            } else {

                atiende.setFechaUso(java.time.LocalDateTime.now());
            }

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

            Atiende seleccionado = tableAsignaciones.getSelectionModel().getSelectedItem();
            Integer cedulaCliente;
            Integer cedulaEmpleado;
            java.time.LocalDateTime fechaLlegada;
            java.time.LocalDateTime fechaUso;
            Integer idHabitacion;
            Integer idServicio;

            if (seleccionado != null) {
                cedulaCliente = seleccionado.getCedulaCliente();
                cedulaEmpleado = seleccionado.getCedulaEmpleado();
                fechaLlegada = seleccionado.getFechaLlegada();
                fechaUso = seleccionado.getFechaUso();
                idHabitacion = seleccionado.getIdHabitacion();
                idServicio = seleccionado.getIdServicio();
            } else {

                cedulaCliente = Integer.parseInt(txtAsignarCedulaCliente.getText());
                cedulaEmpleado = Integer.parseInt(txtAsignarCedulaEmpleado.getText());
                fechaLlegada = parseFecha(txtAsignarFechaLlegada.getText());
                idHabitacion = Integer.parseInt(txtAsignarIdHabitacion.getText());
                idServicio = Integer.parseInt(txtAsignarIdServicio.getText());
                if (reservaServicioSeleccionada != null) {
                    fechaUso = reservaServicioSeleccionada.getFechaUso();
                } else {

                    fechaUso = parseFecha(txtAsignarFechaUso != null ? txtAsignarFechaUso.getText() : null);
                }
            }

            if (fechaUso == null || fechaLlegada == null) {
                mostrarError(
                        "No se pudo determinar las fechas necesarias para desasignar. Selecciona una asignacion o una reserva-servicio valida.");
                return;
            }

            boolean deleted = atiendeDAO.delete(cedulaCliente, cedulaEmpleado, fechaUso, fechaLlegada, idHabitacion,
                    idServicio);
            if (deleted) {
                mostrarInfo("Empleado desasignado exitosamente");
                limpiarCamposAsignar();
                cargarAsignaciones();
            } else {
                mostrarError("No se encontro una asignacion con esos datos. Nada fue eliminado.");
            }
        } catch (Exception e) {
            mostrarError("Error al desasignar empleado: " + e.getMessage());
        }
    }

    private void cargarAsignaciones() throws SQLException {
        if (tableAsignaciones == null)
            return;
        tableAsignaciones.getColumns().clear();

        TableColumn<Atiende, Integer> colCedulaCliente = new TableColumn<>("Cedula Cliente");
        colCedulaCliente.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        colCedulaCliente.setPrefWidth(120);

        TableColumn<Atiende, Integer> colCedulaEmpleado = new TableColumn<>("Cedula Empleado");
        colCedulaEmpleado.setCellValueFactory(new PropertyValueFactory<>("cedulaEmpleado"));
        colCedulaEmpleado.setPrefWidth(140);

        TableColumn<Atiende, Integer> colIdHabitacion = new TableColumn<>("Id Habitacion");
        colIdHabitacion.setCellValueFactory(new PropertyValueFactory<>("idHabitacion"));
        colIdHabitacion.setPrefWidth(120);

        TableColumn<Atiende, Integer> colIdServicio = new TableColumn<>("Id Servicio");
        colIdServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        colIdServicio.setPrefWidth(100);

        java.util.Map<Integer, String> servicioIdToNombre = new java.util.HashMap<>();
        try {
            List<hotel.Modelo.Servicio> todosServicios = servicioDAO.findAll();
            for (hotel.Modelo.Servicio s : todosServicios) {
                servicioIdToNombre.put(s.getIdServicio(), s.getNombreServicio());
            }
        } catch (Exception e) {
            System.out.println("Error cargando servicios: " + e.getMessage());
        }

        java.util.Map<String, java.util.List<Integer>> serviciosPorReserva = new java.util.HashMap<>();
        try {
            List<hotel.Modelo.ReservaServicio> todosRS = reservaServicioDAO.findAll();
            for (hotel.Modelo.ReservaServicio rs : todosRS) {
                String key = rs.getCedulaCliente() + "|" + rs.getIdHabitacion() + "|" + rs.getFechaLlegada();
                serviciosPorReserva.computeIfAbsent(key, k -> new java.util.ArrayList<>()).add(rs.getIdServicio());
            }
        } catch (Exception e) {
            System.out.println("Error cargando reserva_servicio: " + e.getMessage());
        }

        TableColumn<Atiende, String> colServicios = new TableColumn<>("Servicios");
        colServicios.setCellValueFactory(cellData -> {
            Atiende a = cellData.getValue();
            if (a == null)
                return new SimpleStringProperty("N/A");
            Integer idServ = a.getIdServicio();
            String nombre = servicioIdToNombre.getOrDefault(idServ, idServ != null ? String.valueOf(idServ) : "N/A");
            return new SimpleStringProperty(nombre);
        });
        colServicios.setPrefWidth(200);

        TableColumn<Atiende, String> colFechaLlegada = new TableColumn<>("Fecha Llegada");
        colFechaLlegada.setCellValueFactory(cellData -> {
            java.time.LocalDateTime dt = cellData.getValue() != null ? cellData.getValue().getFechaLlegada() : null;
            if (dt == null)
                return new SimpleStringProperty("");
            java.time.format.DateTimeFormatter fmt = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yy");
            return new SimpleStringProperty(dt.toLocalDate().format(fmt));
        });
        colFechaLlegada.setPrefWidth(180);

        TableColumn<Atiende, String> colFechaUso = new TableColumn<>("Fecha Uso");
        colFechaUso.setCellValueFactory(cellData -> {
            java.time.LocalDateTime dt = cellData.getValue() != null ? cellData.getValue().getFechaUso() : null;
            return new SimpleStringProperty(formatFecha(dt));
        });
        colFechaUso.setPrefWidth(180);

        tableAsignaciones.getColumns().addAll(colCedulaCliente, colCedulaEmpleado,
                colIdHabitacion, colIdServicio, colServicios, colFechaLlegada, colFechaUso);

        List<Atiende> asignaciones = atiendeDAO.findAll();
        ObservableList<Atiende> data = FXCollections.observableArrayList(asignaciones);
        tableAsignaciones.setItems(data);

        tableAsignaciones.setEditable(false);
    }

    private void limpiarCamposAsignar() {
        txtAsignarCedulaCliente.clear();
        txtAsignarIdHabitacion.clear();
        txtAsignarFechaLlegada.clear();
        txtAsignarCedulaEmpleado.clear();
        txtAsignarIdServicio.clear();
        reservaServicioSeleccionada = null;
        if (txtAsignarFechaUso != null)
            txtAsignarFechaUso.clear();
    }

    @FXML
    private void handleLiberarHabitacion() {
        try {
            Integer idHabitacion = Integer.parseInt(txtHabitacionId.getText());

            String estado = getEstadoHabitacion(idHabitacion);
            UserSession session = UserSession.getInstance();
            UserType userType = session.getUserType();

            if (userType == UserType.PERSONAL_SERVICIO) {

                if (!"Ocupada".equalsIgnoreCase(estado)) {
                    mostrarError(
                            "Solo puede liberar habitaciones que esten en estado 'Ocupada'. Estado actual: " + estado);
                    return;
                }
            }

            Habitacion habitacion = habitacionDAO.findById(idHabitacion);
            if (habitacion != null) {

                if (userType == UserType.PERSONAL_SERVICIO) {
                    habitacion.setMantenimiento(true);
                } else {
                    habitacion.setMantenimiento(false);
                }
                if (habitacionDAO.update(habitacion)) {
                    mostrarInfo("Habitacion liberada exitosamente");
                    txtHabitacionId.clear();
                    cargarHabitaciones();
                }
            }
        } catch (Exception e) {
            mostrarError("Error al liberar habitacion: " + e.getMessage());
        }
    }

    @FXML
    private void handleTerminarMantenimiento() {
        try {
            Integer idHabitacion = Integer.parseInt(txtHabitacionId.getText());

            String estado = getEstadoHabitacion(idHabitacion);
            if (!"En mantenimiento".equalsIgnoreCase(estado)) {
                mostrarError(
                        "Solo se puede terminar mantenimiento a habitaciones que esten en 'En mantenimiento'. Estado actual: "
                                + estado);
                return;
            }

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
        if (tableHabitaciones == null)
            return;
        tableHabitaciones.getColumns().clear();

        String sql = "SELECT h.idhabitacion AS idhabitacion, " +
                "(c.preciocategoria * GREATEST(DATE_PART('day', r.fechasalida - r.fechallegada), 1)) AS precioestadia, "
                +
                "CASE WHEN h.mantenimiento = TRUE THEN 'En mantenimiento' WHEN r.fechallegada <= CURRENT_DATE AND r.fechasalida >= CURRENT_DATE THEN 'Ocupada' ELSE 'Disponible' END AS estadoactual "
                +
                "FROM habitacion h " +
                "JOIN categoria c ON h.idcategoria = c.idcategoria " +
                "LEFT JOIN reserva r ON h.idhabitacion = r.idhabitacion " +
                "ORDER BY h.idhabitacion";

        tableHabitaciones.getColumns().clear();
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();

        PostgreSQLConnection db = PostgreSQLConnection.getConnector();
        try (Connection conn = db.getConn();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            ResultSetMetaData meta = rs.getMetaData();
            int colCount = meta.getColumnCount();

            for (int i = 1; i <= colCount; i++) {
                final int colIndex = i - 1;
                TableColumn<ObservableList<String>, String> col = new TableColumn<>(meta.getColumnLabel(i));
                col.setCellValueFactory(cellData -> {
                    ObservableList<String> row = cellData.getValue();
                    String value = "";
                    if (row != null && row.size() > colIndex && row.get(colIndex) != null)
                        value = row.get(colIndex);
                    return new SimpleStringProperty(value);
                });
                tableHabitaciones.getColumns().add(col);
            }

            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= colCount; i++) {
                    Object obj = rs.getObject(i);
                    String val = "";
                    if (obj == null) {
                        val = "";
                    } else if (obj instanceof java.sql.Timestamp) {
                        val = formatFecha(((java.sql.Timestamp) obj).toLocalDateTime());
                    } else if (obj instanceof java.sql.Date) {
                        val = formatFecha(((java.sql.Date) obj).toLocalDate().atStartOfDay());
                    } else {
                        String s = rs.getString(i);
                        val = s != null ? s : "";
                    }
                    row.add(val);
                }
                data.add(row);
            }
            tableHabitaciones.setItems(data);
        }
    }

    private String getEstadoHabitacion(Integer idHabitacion) {
        String sql = "SELECT h.mantenimiento, EXISTS(SELECT 1 FROM reserva r WHERE r.idhabitacion = h.idhabitacion AND r.fechallegada <= CURRENT_DATE AND r.fechasalida >= CURRENT_DATE) AS ocupada "
                +
                "FROM habitacion h WHERE h.idhabitacion = ?";
        try {
            PostgreSQLConnection db = PostgreSQLConnection.getConnector();
            try (Connection conn = db.getConn(); PreparedStatement ps = conn.prepareStatement(sql)) {
                ps.setObject(1, idHabitacion);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        boolean mantenimiento = rs.getBoolean("mantenimiento");
                        boolean ocupada = rs.getBoolean("ocupada");
                        if (mantenimiento)
                            return "En mantenimiento";
                        if (ocupada)
                            return "Ocupada";
                        return "Disponible";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error al obtener estado de habitacion: " + e.getMessage());
        }
        return "Desconocido";
    }

    private void cargarServicios() throws SQLException {
        if (tableServicios == null)
            return;
        tableServicios.getColumns().clear();

        TableColumn<Servicio, Integer> colId = new TableColumn<>("Id Servicio");
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
            servicio.setIdServicio(
                    idServicioOriginal != null ? idServicioOriginal : Integer.parseInt(txtServicioId.getText()));
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
            reservaServicio.setFechaLlegada(parseFecha(txtServicioFechaLlegada.getText()));
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
        if (tableServiciosReservaciones == null)
            return;
        tableServiciosReservaciones.getColumns().clear();

        TableColumn<ReservaServicio, Integer> colCedulaCliente = new TableColumn<>("Cedula Cliente");
        colCedulaCliente.setCellValueFactory(new PropertyValueFactory<>("cedulaCliente"));
        colCedulaCliente.setPrefWidth(120);

        TableColumn<ReservaServicio, Integer> colIdHabitacion = new TableColumn<>("Id Habitacion");
        colIdHabitacion.setCellValueFactory(new PropertyValueFactory<>("IdHabitacion"));
        colIdHabitacion.setPrefWidth(120);

        TableColumn<ReservaServicio, Integer> colIdServicio = new TableColumn<>("Id Servicio");
        colIdServicio.setCellValueFactory(new PropertyValueFactory<>("idServicio"));
        colIdServicio.setPrefWidth(100);

        TableColumn<ReservaServicio, String> colFechaLlegada = new TableColumn<>("Fecha Llegada");
        colFechaLlegada.setCellValueFactory(cellData -> {
            ReservaServicio rs = cellData.getValue();
            java.time.LocalDateTime f = rs == null ? null : rs.getFechaLlegada();
            return new SimpleStringProperty(formatFecha(f));
        });
        colFechaLlegada.setPrefWidth(180);

        TableColumn<ReservaServicio, String> colFechaUso = new TableColumn<>("Fecha Uso");
        colFechaUso.setCellValueFactory(cellData -> {
            ReservaServicio rs = cellData.getValue();
            java.time.LocalDateTime f = rs == null ? null : rs.getFechaUso();
            return new SimpleStringProperty(formatFecha(f));
        });
        colFechaUso.setPrefWidth(180);

        tableServiciosReservaciones.getColumns().addAll(colCedulaCliente, colIdHabitacion,
                colIdServicio, colFechaLlegada, colFechaUso);

        List<ReservaServicio> lista = reservaServicioDAO.findAll();
        ObservableList<ReservaServicio> data = FXCollections.observableArrayList(lista);
        tableServiciosReservaciones.setItems(data);

        if (tableServiciosLista != null) {
            tableServiciosLista.getColumns().clear();

            TableColumn<Servicio, Integer> colId = new TableColumn<>("Id Servicio");
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

            tableServiciosLista.getColumns().addAll(colId, colNombre, colDetalle, colPrecio);

            List<Servicio> servicios = servicioDAO.findAll();
            ObservableList<Servicio> serviciosData = FXCollections.observableArrayList(servicios);
            tableServiciosLista.setItems(serviciosData);
        }
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
        if (value == null)
            return null;
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private LocalDateTime parseFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty())
            return null;
        String f = fecha.trim();

        DateTimeFormatter fmt2 = new DateTimeFormatterBuilder()
                .appendPattern("dd/MM/")
                .appendValueReduced(ChronoField.YEAR, 2, 2, 2000)
                .toFormatter();
        DateTimeFormatter fmt4 = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        try {
            LocalDate d = LocalDate.parse(f, fmt2);
            return d.atStartOfDay();
        } catch (Exception e) {
            try {
                LocalDate d = LocalDate.parse(f, fmt4);
                return d.atStartOfDay();
            } catch (Exception e2) {
                try {

                    LocalDate ld = LocalDate.parse(f);
                    return ld.atStartOfDay();
                } catch (Exception e3) {
                    try {
                        LocalDateTime ldt = LocalDateTime.parse(f);
                        return ldt.withHour(0).withMinute(0).withSecond(0).withNano(0);
                    } catch (Exception e4) {
                        throw new IllegalArgumentException(
                                "Formato de fecha invalido (use DD/MM/AA o DD/MM/AAAA): " + fecha);
                    }
                }
            }
        }
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
                && !txtEmpleadoArea.getText().isEmpty();
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
        alert.setTitle("Informacion");
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