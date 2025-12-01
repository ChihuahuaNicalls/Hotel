module hotel {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    exports hotel.DB;
    exports hotel.DAO;
    exports hotel.Modelo;

    opens hotel to javafx.fxml;
    exports hotel;
}
