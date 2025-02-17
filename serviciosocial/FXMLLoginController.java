package serviciosocial;

import DataBaseMethods.LoginMethod;

import Functions.VerifyUserStatus;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class FXMLLoginController implements Initializable {
    
  @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;
    
    private LoginMethod loginService;

    public FXMLLoginController() {
        this.loginService = new LoginMethod();
    }
   

    @FXML 
    private void handleButtonLogin(ActionEvent event) {
          String username = usernameField.getText();
        String password = passwordField.getText();

        if (loginService.validateLogin(username, password)) {
            // Acceso concedido, redirigir a la siguiente escena o ventana
            Functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Inicio exitoso", "Bienvenido " + username + "!");
             String fxmlFile = "/serviciosocial/BarMenuUI.fxml"; // Reemplaza con la ruta de tu archivo FXML
        Functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
        } else {
            // Acceso denegado, mostrar alerta
           Functions.FunctionsOfClasses.ToolTipActive(usernameField, "Usuario o contraseña incorrectos", 200);
            Functions.FunctionsOfClasses.ToolTipActive(passwordField, "Usuario o contraseña incorrectos", 200);
            usernameField.clear();
            passwordField.clear();
           // Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Login Failed", "Usuario o contraseña invalido");
        }
    }
           
    
    
@FXML
    private void handleRegistrarUsuarioAction(ActionEvent event) {
 String fxmlFile = "/serviciosocial/Registro1.fxml"; // Reemplaza con la ruta de tu archivo FXML
        Functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
    
 @FXML
 private void handleCloseSession(ActionEvent event){
     
     Functions.FunctionsOfClasses.showAlertExit(Alert.AlertType.ERROR, "Salir", "¿Desea cerrar el programa?");
 }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         VerifyUserStatus verifyUser = new VerifyUserStatus();
         verifyUser.verificarYDesactivarUsuarios();
       
         
    }  
}



