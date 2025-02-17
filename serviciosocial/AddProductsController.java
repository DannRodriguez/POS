/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package serviciosocial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kjfer
 */
public class AddProductsController implements Initializable {

    @FXML 
    private ImageView CloseWindow;
    
      @FXML 
    private void HandleCloseStage(){
        Stage stage = (Stage) CloseWindow.getScene().getWindow();
        stage.close();
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        CloseWindow.setOnMouseEntered((event) -> {
          CloseWindow.setImage(new Image(getClass().getResource("/imgs/CloseWindow1.png").toExternalForm()));
        });
        
        CloseWindow.setOnMouseExited(event -> {
            CloseWindow.setImage(new Image(getClass().getResource("/imgs/CloseWindow.png").toExternalForm()));
        });
        
        
        CloseWindow.setOnMouseClicked((event) -> {
        HandleCloseStage();
        });
    }    
    
}
