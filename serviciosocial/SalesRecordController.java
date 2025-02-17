/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package serviciosocial;

import java.net.URL;
import java.util.ResourceBundle;
import Functions.FunctionsOfClasses;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author kjfer
 */
public class SalesRecordController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private Button BtnEndDay;
   
     @FXML
    private Button BtnGenReport;
 
    
    
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        FunctionsOfClasses.ButtonEffect(BtnEndDay);
         FunctionsOfClasses.ButtonEffect(BtnGenReport);
    }
}
