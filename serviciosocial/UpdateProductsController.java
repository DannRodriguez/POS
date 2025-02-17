/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package serviciosocial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author kjfer
 */
public class UpdateProductsController implements Initializable {

 @FXML
 private ImageView CloseWindow;
    
@FXML
private ImageView ImgUpdate;
    
@FXML 
private TextField TextfieldCode;

@FXML 
private TextField TextfieldQuantity;

@FXML 
private TextField TextfieldProductName;

@FXML 
private TextField TextfieldFinalQuantity;

    
private void clear(){
    TextfieldCode.clear();
    TextfieldQuantity.clear();
    TextfieldProductName.clear();
    TextfieldFinalQuantity.clear();;
    
}

  @FXML 
    private void HandleCloseStage(){
        Stage stage = (Stage) CloseWindow.getScene().getWindow();
        stage.close();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
       ImgUpdate.setOnMouseEntered(event -> {
            ImgUpdate.setImage(new Image(getClass().getResource("/imgs/UpdateMini2.png").toExternalForm()));
        });

        ImgUpdate.setOnMouseExited(event -> {
            ImgUpdate.setImage(new Image(getClass().getResource("/imgs/UpdateMiniImg.png").toExternalForm()));
        });
        
        CloseWindow.setOnMouseEntered((event) -> {
          CloseWindow.setImage(new Image(getClass().getResource("/imgs/CloseWindow1.png").toExternalForm()));
        });
        
        CloseWindow.setOnMouseExited(event -> {
            CloseWindow.setImage(new Image(getClass().getResource("/imgs/CloseWindow.png").toExternalForm()));
        });
        
        ImgUpdate.setOnMouseClicked((event) -> {
        clear();
        });
        
        CloseWindow.setOnMouseClicked((event) -> {
        HandleCloseStage();
        });
    }    
    
}
