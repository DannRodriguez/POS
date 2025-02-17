/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package serviciosocial;

import Functions.UserStartSession;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/**
 * FXML Controller class
 *
 * @author kjfer
 */
public class BarMenuUIController implements Initializable {

    @FXML
    private Button ventaProductosButton;
    @FXML
    private Button historialVentasButton;
    @FXML
    private Button productosButton;
    
    @FXML
    private StackPane contentArea;
    
    @FXML
    private Label Username;
    
    private String CurrentUser = UserStartSession.getInstance().getLoggedInUser();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // Configura los eventos de los botones
        setupButtonEvents();
        Username.setText(CurrentUser + ".");
        Username.setStyle("-fx-font-size: 100px; -fx-font-family: \"Britannic Bold\";  -fx-text-fill: #fe6a17;" );
             
        
    }
    
    @FXML
    private void handleCloseSesion(ActionEvent event) {
        Functions.FunctionsOfClasses.showAlertCloseSession(Alert.AlertType.ERROR, "", "¿Desea cerrar sesión?",event);
    }
    
    private void setupButtonEvents() {
        // Asigna el mismo manejador a todos los botones
        ventaProductosButton.setOnAction(this::handleButtonAction);
        historialVentasButton.setOnAction(this::handleButtonAction);
        productosButton.setOnAction(this::handleButtonAction);
    }
    
    private void handleButtonAction(ActionEvent event) {
        // Determina cuál botón fue presionado y carga el contenido correspondiente
        Button pressedButton = (Button) event.getSource();
        
        // Cambia el contenido según el botón presionado
        String fxmlFile = "";
        if (pressedButton == ventaProductosButton) {
            fxmlFile = "Productos.fxml";
        } else if (pressedButton == historialVentasButton) {
            fxmlFile = "SalesRecord.fxml";
        } else if (pressedButton == productosButton) {
            fxmlFile = "FXMLInventario.fxml";
        }

        loadContent(fxmlFile);
        handleButtonEffect(pressedButton);
    }
    
  private void loadContent(String fxmlFile) {
    FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
    Parent newContent;
    try {
        newContent = loader.load();
    } catch (IOException e) {
        e.printStackTrace();
        // Muestra un mensaje de error al usuario si es necesario
        return;
    }
    contentArea.getChildren().setAll(newContent);
}

    
    private void handleButtonEffect(Button pressedButton) {
        clearPressedEffects();
        applyPressedEffect(pressedButton);
    }

    private void clearPressedEffects() {
        ventaProductosButton.setStyle("");
        historialVentasButton.setStyle("");
        productosButton.setStyle("");
        // Puedes añadir más botones aquí si es necesario
    }

    private void applyPressedEffect(Button button) {
        button.setStyle("-fx-background-color: #c8752f; -fx-border-color: #e28639; -fx-text-fill: #efefef; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.25), 10, 0, 0, 5);");
    }
}
