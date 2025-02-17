

package serviciosocial;

import DataBaseMethods.InsertMethodDB;
import DataBaseMethods.VerifyIfUserExists;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import Functions.VerifyUserStatus;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import Functions.FunctionsOfClasses;



public class Registro1Controller implements Initializable {
    
  
    
   @FXML
    private TextField nombreUsuarioField;

    @FXML
    private PasswordField contraseñaField;
    
    @FXML
    private PasswordField PasswordConfirm;

    @FXML
    private PasswordField adminKeyField;

    @FXML
    private Button CodeRegister;
    
  
    
 @FXML
    private void handleRegister() {
        
        VerifyIfUserExists Verify = new VerifyIfUserExists();
        String nombreUsuario = nombreUsuarioField.getText();
        String contraseña = contraseñaField.getText();
        String adminKey = adminKeyField.getText();
        String password = PasswordConfirm.getText();
        
     if (nombreUsuarioField.getText().isEmpty()) {
    Functions.FunctionsOfClasses.ToolTipActive(nombreUsuarioField, "Rellenar campo obligatoriamente", 200);
} else if (!Verify.usuarioExiste(nombreUsuario)) {
    if (contraseñaField.getText().isEmpty()) {
        Functions.FunctionsOfClasses.ToolTipActive(contraseñaField, "Rellenar campo obligatoriamente", 200);
    } else if (PasswordConfirm.getText().isEmpty()) {
        Functions.FunctionsOfClasses.ToolTipActive(PasswordConfirm, "Rellenar campo obligatoriamente", 200);
    } else if (!contraseña.equals(password)) {
        Functions.FunctionsOfClasses.ToolTipActive(contraseñaField, "Contraseñas no coincidentes", 170);
        Functions.FunctionsOfClasses.ToolTipActive(PasswordConfirm, "Contraseñas no coincidentes", 170);
    } else {
        boolean success = InsertMethodDB.registerUser(nombreUsuario, contraseña, adminKey);
        if (success) {
            Functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "Registro Exitoso", "Usuario registrado correctamente.");
            FunctionsOfClasses funct = new FunctionsOfClasses();
            funct.Switch(adminKeyField);
        } else {
            Functions.FunctionsOfClasses.ToolTipActive(adminKeyField, "ID_Key invalida", 100);
        }
    }
} else {
    Functions.FunctionsOfClasses.ToolTipActive(nombreUsuarioField, "Usuario existente", 110);
}
    }
    
    
@FXML
    private void handleRegistrarUser(ActionEvent event) {
        String fxmlFile = "/serviciosocial/FXMLLogin.fxml"; // Reemplaza con la ruta de tu archivo FXML
        Functions.FunctionsOfClasses.switchToScene(event, fxmlFile);
    }
    
@FXML
private void openSecondaryWindow() {
    try {
        // Cargar el archivo FXML para la ventana secundaria
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviciosocial/Codes.fxml"));
        Parent root = loader.load();

        // Crear una nueva ventana
        Stage newStage = new Stage();
        //newStage.setTitle("Ventana Secundaria"); // Opcional: establece el título de la nueva ventana
        newStage.setScene(new Scene(root)); // No especifica un tamaño, usa el tamaño definido en el FXML
        newStage.initStyle(StageStyle.UNDECORATED);
        // Establecer la ventana principal como propietaria
        Stage primaryStage = (Stage) nombreUsuarioField.getScene().getWindow(); // Obtén el Stage principal a partir de uno de los controles
        newStage.initModality(Modality.APPLICATION_MODAL); // Configura la ventana como modal
        newStage.initOwner(primaryStage); // Establece la ventana principal como la ventana propietaria

        // Mostrar la nueva ventana
        newStage.show();
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    

    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         VerifyUserStatus verifyUser = new VerifyUserStatus();
         verifyUser.verificarYDesactivarUsuarios();
         
         
             nombreUsuarioField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (event.getCharacter().equals(" ")) {
                event.consume(); // Bloquea la tecla de espacio
                Functions.FunctionsOfClasses.ToolTipActive(nombreUsuarioField, "Espacio no permitido", 135);
                // Crear un Tooltip para mostrar el mensaje
               
            }
        });
         
    }  
}

