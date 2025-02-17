/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Functions;

import javafx.scene.control.Alert;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import java.io.IOException;
import java.util.Optional;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.StageStyle;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.util.Duration;

public class FunctionsOfClasses {
    
    
    public static void showAlertGood(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
         String imagePath = "/imgs/success.png";
        Image iconImage = new Image(imagePath);

        // Crear un ImageView para el icono
        ImageView iconImageView = new ImageView(iconImage);
        iconImageView.setFitWidth(48); // Ajustar el ancho del icono
        iconImageView.setFitHeight(48); // Ajustar la altura del icono

        // Establecer el icono al lado del título
        alert.setGraphic(iconImageView);
        
        // Crear un DialogPane personalizado para la alerta
        alert.initStyle(StageStyle.UNDECORATED); // Quitar la barra de título
       
        
        alert.showAndWait();
    }
    
      public static void showAlertFail(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        
        
        String imagePath = "/imgs/eliminar.png";
        Image iconImage = new Image(imagePath);

        // Crear un ImageView para el icono
        ImageView iconImageView = new ImageView(iconImage);
        iconImageView.setFitWidth(48); // Ajustar el ancho del icono
        iconImageView.setFitHeight(48); // Ajustar la altura del icono

        // Establecer el icono al lado del título
        alert.setGraphic(iconImageView);
        
           alert.initStyle(StageStyle.UNDECORATED); // Quitar la barra de título
           
        alert.showAndWait();
    }
    
        public static void showAlertCloseSession(Alert.AlertType alertType, String title, String message,ActionEvent event) {
    // Crear botones personalizados con texto específico
    ButtonType buttonTypeYes = new ButtonType("Sí, salir", ButtonBar.ButtonData.OK_DONE);
    ButtonType buttonTypeNo = new ButtonType("No, quedarse", ButtonBar.ButtonData.CANCEL_CLOSE);

    // Crear la alerta con los botones personalizados
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, buttonTypeYes, buttonTypeNo);
    alert.setTitle(title);
    alert.setHeaderText(null);
    
    String imagePath = "/imgs/CloseSessionDoor.png";
    Image iconImage = new Image(imagePath);

    // Crear un ImageView para el icono
    ImageView iconImageView = new ImageView(iconImage);
    iconImageView.setFitWidth(48); // Ajustar el ancho del icono
    iconImageView.setFitHeight(48); // Ajustar la altura del icono

    // Establecer el icono al lado del título
    alert.setGraphic(iconImageView);
    
    // Quitar la barra de título
    alert.initStyle(StageStyle.UNDECORATED);
    
    // Mostrar la alerta y esperar la respuesta del usuario
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == buttonTypeYes) {
        switchToScene(event, "/serviciosocial/FXMLLogin.fxml");
       // System.exit(0);
    }
//    } else {
//        System.out.println("Usuario eligió quedarse.");
//    }
}  
        
             public static void showAlertExit(Alert.AlertType alertType, String title, String message) {
    // Crear botones personalizados con texto específico
    ButtonType buttonTypeYes = new ButtonType("Sí, salir", ButtonBar.ButtonData.OK_DONE);
    ButtonType buttonTypeNo = new ButtonType("No, quedarse", ButtonBar.ButtonData.CANCEL_CLOSE);

    // Crear la alerta con los botones personalizados
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION, message, buttonTypeYes, buttonTypeNo);
    alert.setTitle(title);
    alert.setHeaderText(null);
    
    String imagePath = "/imgs/eliminar.png";
    Image iconImage = new Image(imagePath);

    // Crear un ImageView para el icono
    ImageView iconImageView = new ImageView(iconImage);
    iconImageView.setFitWidth(48); // Ajustar el ancho del icono
    iconImageView.setFitHeight(48); // Ajustar la altura del icono

    // Establecer el icono al lado del título
    alert.setGraphic(iconImageView);
    
    // Quitar la barra de título
    alert.initStyle(StageStyle.UNDECORATED);
    
    // Mostrar la alerta y esperar la respuesta del usuario
    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == buttonTypeYes) {
        System.exit(0);
       // System.exit(0);
    }
//    } else {
//        System.out.println("Usuario eligió quedarse.");
//    }
}  
      
    
    
      public static void switchToScene(ActionEvent event, String fxmlFile) {
        try {
            // Cargar la nueva escena desde el archivo FXML
            FXMLLoader loader = new FXMLLoader(FunctionsOfClasses.class.getResource(fxmlFile));
            Parent root = loader.load();
            
            // Obtener la etapa actual (Stage)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            
            // Configurar la nueva escena
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.centerOnScreen();
            
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
      
          //FUNCIÓN que realiza el acomodo del texto en el frame de licencia
        public static void applyTextFormatter(TextField textField) {
        textField.setTextFormatter(new TextFormatter<>(change -> {
            // Obtener el texto actual y convertir a mayúsculas
            String text = change.getControlNewText().toUpperCase();

            // Eliminar cualquier caracter que no sea letra o número
            text = text.replaceAll("[^A-Z0-9]", "");

            // Limitar a 16 caracteres
            if (text.length() > 16) {
                text = text.substring(0, 16);
            }

            // Dividir en grupos de 4 caracteres y agregar guiones
            StringBuilder formattedText = new StringBuilder();
            for (int i = 0; i < text.length(); i++) {
                if (i > 0 && i % 4 == 0) {
                    formattedText.append("-");
                }
                formattedText.append(text.charAt(i));
            }
            
            // Obtener la nueva posición del cursor
            int newCursorPosition = change.getAnchor() + formattedText.length() - change.getControlNewText().length();

            // Establecer el texto formateado
            change.setText(formattedText.toString());

            // Ajustar la posición del cursor
            change.selectRange(newCursorPosition, newCursorPosition);

            // Reemplazar todo el texto
            change.setRange(0, change.getControlText().length());

            return change; // Aplicar el cambio
        }));
    }
        
       public static Timeline createBounceAnimation(Button AddItemBtn) {
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, 
                new javafx.animation.KeyValue(AddItemBtn.scaleXProperty(), 1),
                new javafx.animation.KeyValue(AddItemBtn.scaleYProperty(), 1)),
            new KeyFrame(Duration.millis(100), 
                new javafx.animation.KeyValue(AddItemBtn.scaleXProperty(), 1.3),
                new javafx.animation.KeyValue(AddItemBtn.scaleYProperty(), 1.3)),
            new KeyFrame(Duration.millis(200), 
                new javafx.animation.KeyValue(AddItemBtn.scaleXProperty(), 0.8),
                new javafx.animation.KeyValue(AddItemBtn.scaleYProperty(), 0.8)),
            new KeyFrame(Duration.millis(300), 
                new javafx.animation.KeyValue(AddItemBtn.scaleXProperty(), 1.1),
                new javafx.animation.KeyValue(AddItemBtn.scaleYProperty(), 1.1)),
            new KeyFrame(Duration.millis(400), 
                new javafx.animation.KeyValue(AddItemBtn.scaleXProperty(), 1.0),
                new javafx.animation.KeyValue(AddItemBtn.scaleYProperty(), 1.0))
        );
        timeline.setCycleCount(1);
        return timeline;
    }
       
       public static void ToolTipActive(TextField InsertTextField, String EntryMessage, int range){
             // Crear un Tooltip para mostrar el mensaje
                Tooltip tooltip = new Tooltip(EntryMessage);
                tooltip.setAutoHide(true);
                tooltip.show(InsertTextField, 
                             InsertTextField.getScene().getWindow().getX() + InsertTextField.getLayoutX()-range, 
                             InsertTextField.getScene().getWindow().getY() + InsertTextField.getLayoutY() );

                // Usar un Timeline para controlar la duración del Tooltip
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), e -> tooltip.hide()));
                timeline.setCycleCount(1);
                timeline.play();
       }
       
       
       public  void Switch(TextField textofield){
             try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/serviciosocial/FXMLLogin.fxml")); // Reemplaza con tu FXML
        Parent root = loader.load();
        
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.initStyle(StageStyle.UNDECORATED);
        stage.show();
        
        // Cerrar la ventana actual
        Stage currentStage = (Stage) textofield.getScene().getWindow();
        currentStage.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
       }
       
       
       
 public  void openSecondaryWindow(Button nombreUsuarioField , String path) {
    try {
        // Cargar el archivo FXML para la ventana secundaria
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
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
 
 public static void ButtonEffect(Button button) {
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(0.5), button);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), button);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Usar un arreglo para la referencia del Timeline
        final Timeline[] timeline = new Timeline[1];

        // Crear el Timeline para alternar entre desvanecimientos
        timeline[0] = new Timeline(
            new KeyFrame(Duration.seconds(0.5), event -> fadeOut.play()),
            new KeyFrame(Duration.seconds(1), event -> fadeIn.play())
        );
        timeline[0].setCycleCount(Timeline.INDEFINITE);

        // Manejar el evento de mouse enter
        button.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
            if (!timeline[0].getStatus().equals(Timeline.Status.RUNNING)) {
                timeline[0].play(); // Iniciar la animación de parpadeo
            }
        });

        // Manejar el evento de mouse exit
        button.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
            if (timeline[0].getStatus().equals(Timeline.Status.RUNNING)) {
                timeline[0].stop(); // Detener la animación de parpadeo
                button.setOpacity(1.0); // Asegurarse de que el botón sea visible al salir
            }
        });
    }
 
 
       
       
}
        
        
        
                
      
    
      
      
      
    
    
      

