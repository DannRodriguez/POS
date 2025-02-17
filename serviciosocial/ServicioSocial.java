/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package serviciosocial;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.stage.StageStyle;


/**
 *
 * @author Daniel
 */
public class ServicioSocial extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        
//         Image icon = new Image(getClass().getResourceAsStream("/imgs/ITACA.png"));
//        stage.getIcons().add(icon);
        
        
        Parent root = FXMLLoader.load(getClass().getResource("SalesRecord.fxml"));
        
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
         stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        stage.setResizable(false);
       
        stage.setTitle("Registro de Ingresos");
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
