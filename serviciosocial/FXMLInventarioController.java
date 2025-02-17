/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package serviciosocial;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import Functions.FunctionsOfClasses;



       

public class FXMLInventarioController implements Initializable {
    
    @FXML 
    private Button AddItemBtn;

    @FXML 
    private Button UpdateItemsBtn;
    
       @FXML 
    private Button ViewItemsBtn;
      
    FunctionsOfClasses functions = new FunctionsOfClasses();
    
    @FXML
    private void HandleSwitchScene(){
        functions.openSecondaryWindow(UpdateItemsBtn, "/serviciosocial/UpdateProducts.fxml");
    }
   
     @FXML
    private void HandleSwitchSceneToInventory(){
        functions.openSecondaryWindow(ViewItemsBtn, "/serviciosocial/ProductsInventory.fxml");
    }
       
         
           private void applyBounceAnimation(Button button) {
        Timeline bounceTimeline = Functions.FunctionsOfClasses.createBounceAnimation(button);

        // Configurar el evento para ejecutar la animación cuando se pasa el ratón sobre el botón
        button.setOnMouseEntered(event -> bounceTimeline.playFromStart());
    }
           
           
        @Override
        public void initialize(URL url, ResourceBundle rb) {
           
            applyBounceAnimation(AddItemBtn);
             AddItemBtn.setOnAction((event) -> {
            functions.openSecondaryWindow(AddItemBtn, "/serviciosocial/AddProducts.fxml");
            });
            applyBounceAnimation(UpdateItemsBtn);
           
            applyBounceAnimation(ViewItemsBtn);
        
        }    

}
