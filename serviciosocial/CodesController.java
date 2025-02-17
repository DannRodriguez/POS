package serviciosocial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import Functions.FunctionsOfClasses;
import DataBaseMethods.SetAdminActive;
import javafx.scene.control.Button;
import javafx.stage.Stage;
public class CodesController implements Initializable {

    @FXML 
    private TextField CodeField;
    
    @FXML
    private Button BtnRegisterCode;
    
    @FXML
    private Button CloseBtn;
    
     @FXML 
    private void HandleCloseStage(){
        Stage stage = (Stage) CloseBtn.getScene().getWindow();
        stage.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       FunctionsOfClasses.applyTextFormatter(CodeField);
       BtnRegisterCode.setOnAction((event) -> {
           SetAdminActive.validateAndUpdateAdminStatus(CodeField.getText(),CodeField);
       });
    
      CloseBtn.setOnAction((event) -> {
      HandleCloseStage();
      });
    }    
    
   
    
   
}
