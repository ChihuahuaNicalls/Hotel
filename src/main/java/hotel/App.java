package hotel;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
    private static Stage primaryStage;

    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        scene = new Scene(loadFXML("hotel/login"), 320, 400);
        stage.setScene(scene);
        stage.setTitle("Sistema de Gestión Hotelera");
        stage.setResizable(false);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
        
        javafx.application.Platform.runLater(() -> {
            if (fxml.contains("menu")) {
                primaryStage.setWidth(1200);
                primaryStage.setHeight(700);
            } else if (fxml.contains("login")) {
                primaryStage.setWidth(320);
                primaryStage.setHeight(400);
            }
            primaryStage.centerOnScreen();
        });
    }

    private static Parent loadFXML(String fxml) throws IOException {
        // Si la ruta ya incluye el paquete, usar directamente; si no, buscar en resources
        String resourcePath = fxml.endsWith(".fxml") ? fxml : fxml + ".fxml";
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/" + resourcePath));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}