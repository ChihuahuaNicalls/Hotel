package hotel;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController {
    
    @FXML
    private TextField txtUsuario;
    
    @FXML
    private PasswordField txtPassword;
    
    @FXML
    private Button btnIngresar;

    @FXML
    private void handleIngresar() {
        String username = txtUsuario.getText().trim();
        String password = txtPassword.getText();

        if (username.isEmpty() || password.isEmpty()) {
            mostrarAlerta("Campos vacios", "Por favor ingrese usuario y contraseña", Alert.AlertType.WARNING);
            return;
        }

        if (AuthenticationService.authenticate(username, password)) {
            UserType userType = AuthenticationService.getUserType(username);
            
            if (userType != null) {
                UserSession.getInstance().login(username, password, userType);
                
                mostrarAlerta("Inicio de sesion exitoso", 
                            "Bienvenido " + username + "\nTipo de usuario: " + userType.getDisplayName(), 
                            Alert.AlertType.INFORMATION);
                
                try {
                    App.setRoot("hotel/menu");
                } catch (IOException e) {
                    mostrarAlerta("Error", "No se pudo cargar el menu: " + e.getMessage(), Alert.AlertType.ERROR);
                }
            } else {
                mostrarAlerta("Error de autenticacion", "Usuario no reconocido en el sistema", Alert.AlertType.ERROR);
            }
        } else {
            mostrarAlerta("Credenciales invalidas", "Usuario o contraseña incorrectos", Alert.AlertType.ERROR);
            txtPassword.clear();
        }
    }

    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }
}
