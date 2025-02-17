package serviciosocial;

import DataBaseMethods.ConnectionDB;
import DataBaseMethods.ProductsEntry;
import Functions.ProductKey;
import Functions.UserStartSession;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.StageStyle;


import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;


public class ProductosController implements Initializable {


    @FXML
    private ScrollPane productScrollPane;

    @FXML
    private GridPane productGridPane1;

    @FXML
    private TextField itemNameField;

    @FXML
    private ListView<String> selectedItemsList;

    @FXML
    private Label totalPriceLabel;

    @FXML
    private Button cancelButton;

    @FXML
    private Button pagarButton;
    
    @FXML
    private TextField BarcodeInsert;
    
    @FXML
    private Button CancelAll;
    
    @FXML
    private Label MoneyLabel;
    
    @FXML
    private Label ChangeLabel;
    
    private boolean Activo = true;
    private double totalPrice = 0.0;
    private Map<ProductKey, Integer> cartItems = new HashMap<>();
    private String currentUser = UserStartSession.getInstance().getLoggedInUser();
        
    @Override
    public void initialize(URL url, ResourceBundle rb) {

      productScrollPane.setFitToWidth(true);
    loadProductsFromDatabase();
    cancelButton.setOnAction(event -> handleCancelAction());
    pagarButton.setOnAction(event -> handlePayAction());
    
    // Escuchar la tecla Enter en lugar de buscar en cada escritura
    itemNameField.setOnKeyPressed(event -> {
        if (event.getCode().toString().equals("ENTER")) {
            searchProducts(itemNameField.getText());
        }
    });
    
    BarcodeInsert.setOnAction(event -> handleBarcodeInsert());
    CancelAll.setOnAction(event -> handleCancelAllAction());
    }
    

    private void loadProductsFromDatabase() {
        try {
            Connection conn = ConnectionDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT nombre, precio, barcode,cantidad FROM productos";
            ResultSet rs = stmt.executeQuery(sql);

            int row = 0;
            int col = 0;

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String barcode = rs.getString("barcode");
                int Existencias = rs.getInt("cantidad");
                
                //Titulo producto
                Label nameLabel = new Label(nombre);
                nameLabel.setStyle("-fx-text-fill: #4d575e; -fx-font-size: 20px; -fx-font-weight: bold;");

                
                //Precio
                Label priceLabel = new Label("$ " + String.format("%.2f", precio));
                priceLabel.setStyle("-fx-text-fill: #2be21c; -fx-font-size: 16px; -fx-font-weight: bold;");
                
                //Cantidad del producto
                Label QuantityLabel = new Label("Existencias: " + String.format("%d", Existencias));
                QuantityLabel.setStyle("-fx-text-fill: #ff6161; -fx-font-size: 16px; -fx-font-weight: bold;");
                
                
                //Generacion de bloque
                VBox vbox = new VBox(nameLabel, priceLabel, QuantityLabel);
                vbox.setSpacing(10);
                vbox.setStyle("-fx-border-color: #ffb630; -fx-border-radius:10 ;   -fx-border-width: 2px; -fx-background-color: #e9e9e9; -fx-padding: 10; -fx-background-radius: 10;");

                Button button = new Button();
                button.setGraphic(vbox);
                button.setPrefSize(180, 180);
                button.setStyle("-fx-background-color: transparent;");

                button.setOnAction(event -> handleButtonAction(nombre, precio, barcode));

                productGridPane1.add(button, col, row);

                col++;
                if (col == 6) {
                    col = 0;
                    row++;
                }
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    //FUNCIÓN que agrega productos al carrito
    private void handleButtonAction(String nombre, double precio, String barcode) {
        System.out.println("El estado esta: " + Activo);
       if(Activo == false){
           clearInterface();
           Activo = true;
       }
        
        TextInputDialog dialog = new TextInputDialog("1");
        dialog.setTitle("Agregar Productos");
        dialog.setHeaderText(null);
        dialog.initStyle(StageStyle.UNDECORATED);
        dialog.setContentText("Ingrese la cantidad de productos a agregar:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            int quantity = Integer.parseInt(result.get());
            addItemToCart(nombre, barcode, precio, quantity);
        }
    }

    private void addItemToCart(String nombre, String barcode, double precio, int quantity) {
        ProductKey key = new ProductKey(nombre, barcode);
        cartItems.put(key, cartItems.getOrDefault(key, 0) + quantity);
        totalPrice += precio * quantity;
        totalPriceLabel.setText("$" + String.format("%.2f", totalPrice));
        updateCart();
    }

    private void handleCancelAction() {
        String selectedItem = selectedItemsList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setTitle("Cancelar Productos");
            dialog.setHeaderText(null);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.setContentText("Ingrese la cantidad de productos a cancelar:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                int quantity = Integer.parseInt(result.get());
                removeItemFromCart(selectedItem, quantity);
            }
        }
    }

    private void removeItemFromCart(String selectedItem, int quantity) {
        String[] parts = selectedItem.split(" x");
        if (parts.length < 2) return; // Protección contra formato incorrecto

        String nameAndBarcode = parts[0];
        int currentQuantity = Integer.parseInt(parts[1].split(" - ")[0]);

        String[] nameBarcodeParts = nameAndBarcode.split(" \\(");
        if (nameBarcodeParts.length < 2) return; // Protección contra formato incorrecto

        String name = nameBarcodeParts[0];
        String barcode = nameBarcodeParts[1].replace(")", "");
        ProductKey key = new ProductKey(name, barcode);

        int currentQuantityInCart = cartItems.getOrDefault(key, 0);

        if (currentQuantityInCart >= quantity) {
            int newQuantity = currentQuantityInCart - quantity;
            if (newQuantity == 0) {
                cartItems.remove(key);
            } else {
                cartItems.put(key, newQuantity);
            }
            double itemPrice = getPrice(name, barcode);
            totalPrice -= itemPrice * quantity;
            totalPriceLabel.setText("$" + String.format("%.2f", totalPrice));
            updateCart();
        } else {
            Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "No hay suficiente cantidad de " + name + " para eliminar.");
           
        }
    }

    private void updateCart() {
        selectedItemsList.getItems().clear();
        for (Map.Entry<ProductKey, Integer> entry : cartItems.entrySet()) {
            ProductKey key = entry.getKey();
            int quantity = entry.getValue();
            selectedItemsList.getItems().add(key + " x" + quantity + " - $" + String.format("%.2f", getPrice(key.getName(), key.getBarcode()) * quantity));
        }
    }

    private double getPrice(String name, String barcode) {
        double price = 0.0;
        try {
            Connection conn = ConnectionDB.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT precio FROM productos WHERE nombre='" + name + "' AND barcode='" + barcode + "'";
            ResultSet rs = stmt.executeQuery(sql);
            if (rs.next()) {
                price = rs.getDouble("precio");
            }
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return price;
    }

    
    //FUNCION de procesado del pago
 private void handlePayAction() {
    if (cartItems.isEmpty()) {
        Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "No hay productos en el carrito para pagar.");
        return;
    }

    // Solicitar ingreso de efectivo
    Optional<Double> efectivoOptional = solicitarEfectivo();

    if (!efectivoOptional.isPresent()) {
        // Si no se ingresó un valor válido, se cancela el proceso de pago
        return;
    }

    double efectivoIngresado = efectivoOptional.get();

    // Validar que el efectivo sea suficiente
    if (efectivoIngresado < totalPrice) {
        Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "El efectivo ingresado no es suficiente para cubrir el costo total.");
        return;
    }

    // Datos del usuario
    String user = currentUser;

    try {
        // Procesar cada producto en el carrito
        Connection conn = ConnectionDB.getConnection();
        conn.setAutoCommit(false); // Iniciar transacción

        for (Map.Entry<ProductKey, Integer> entry : cartItems.entrySet()) {
            ProductKey key = entry.getKey();
            int quantity = entry.getValue();
            double price = getPrice(key.getName(), key.getBarcode());

            // Insertar cada producto vendido
            ProductsEntry.ProductRegistrer(key.getName(), price * quantity, user, key.getBarcode());

            // Actualizar las existencias del producto en la base de datos
            String updateStockSql = "UPDATE productos SET cantidad = cantidad - ? WHERE barcode = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateStockSql)) {
                updateStmt.setInt(1, quantity);
                updateStmt.setString(2, key.getBarcode());
                updateStmt.executeUpdate();
            }
        }

        conn.commit(); // Confirmar transacción
        conn.close(); // Cerrar la conexión

        // Actualizar la interfaz después de la venta
        updateCart();
        Functions.FunctionsOfClasses.showAlertGood(Alert.AlertType.INFORMATION, "", "Pago realizado con éxito. Cambio: $" + String.format("%.2f", (efectivoIngresado - totalPrice)));
        
        MoneyLabel.setText("$" + String.format("%.2f", efectivoIngresado));
        ChangeLabel.setText("$" + String.format("%.2f", efectivoIngresado - totalPrice));
        Activo = false;
        pagarButton.setDisable(true);
        cancelButton.setDisable(true);
        CancelAll.setDisable(true);

    } catch (Exception e) {
        e.printStackTrace();
        Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "Error al procesar el pago.");
    }
}




 
   
   
   
   
    private void searchProducts(String query) {
    productGridPane1.getChildren().clear();
       
    try {
        Connection conn = ConnectionDB.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT nombre, precio, barcode, cantidad FROM productos WHERE nombre LIKE '%" + query + "%' OR barcode LIKE '%" + query + "%'";
        ResultSet rs = stmt.executeQuery(sql);

        int row = 0;
        int col = 0;

        while (rs.next()) {
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");
            String barcode = rs.getString("barcode");
            int cantidad = rs.getInt("cantidad");
            
            Label nameLabel = new Label(nombre);
            nameLabel.setStyle("-fx-text-fill: #4d575e; -fx-font-size: 20px; -fx-font-weight: bold;");

            
            Label priceLabel = new Label("$" + String.format("%.2f", precio));
            priceLabel.setStyle("-fx-text-fill: #2be21c; -fx-font-size: 16px; -fx-font-weight: bold;");
        
            
            Label QuantityLabel = new Label("Cantidad: " + String.format("%d", cantidad));
            QuantityLabel.setStyle("-fx-text-fill: #119fff; -fx-font-size: 16px; -fx-font-weight: bold;");
            
            VBox vbox = new VBox(nameLabel, priceLabel, QuantityLabel);
            vbox.setSpacing(10);
             vbox.setStyle("-fx-border-color: #ff6161; -fx-border-radius:10 ;   -fx-border-width: 2px; -fx-background-color: #e9e9e9; -fx-padding: 10; -fx-background-radius: 10;");

            Button button = new Button();
            button.setGraphic(vbox);
            button.setPrefSize(180, 180);
            button.setStyle("-fx-background-color: transparent;");

            button.setOnAction(event -> handleButtonAction(nombre, precio, barcode));

            productGridPane1.add(button, col, row);

            col++;
            if (col == 6) {
                col = 0;
                row++;
            }
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
       
        // Aquí podrías añadir algún mensaje de error para el usuario
    }
}
    
    //FUNCIÓN agregar al carrito mediante la pistola de codigo de barras
    private void handleBarcodeInsert() {
    String barcode = BarcodeInsert.getText().trim();
    if (barcode.isEmpty()) {
        return;
    }

    try {
        Connection conn = ConnectionDB.getConnection();
        Statement stmt = conn.createStatement();
        String sql = "SELECT nombre, precio FROM productos WHERE barcode='" + barcode + "'";
        ResultSet rs = stmt.executeQuery(sql);

        if (rs.next()) {
            String nombre = rs.getString("nombre");
            double precio = rs.getDouble("precio");

            // Mostrar el diálogo para ingresar la cantidad
            TextInputDialog dialog = new TextInputDialog("1");
            dialog.setHeaderText(null);
            dialog.initStyle(StageStyle.UNDECORATED);
            dialog.setContentText("Ingrese la cantidad de " + nombre + " a añadir:");

            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                try {
                    int quantity = Integer.parseInt(result.get());
                    if (quantity > 0) {
                        addItemToCart(nombre, barcode, precio, quantity);
                    } else {
                        Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "La cantidad debe ser mayor que cero.");
                    }
                } catch (NumberFormatException e) {
                    Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "Ingrese un número válido.");
                }
            }
        } else {
            Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "Producto no encontrado para el código de barras: " + barcode);
        }

        rs.close();
        stmt.close();
        conn.close();
    } catch (Exception e) {
        e.printStackTrace();
        // Manejar la excepción de manera adecuada aquí
    }

    // Limpiar el campo de texto después de procesar el código de barras
    BarcodeInsert.clear();
}
    
    //FUNCIÓN cancelar todo el pedido de el carrito
private void handleCancelAllAction() {
    // Verifica si el ListView está vacío antes de proceder
    if (selectedItemsList.getItems().isEmpty()) {
        // Si está vacío, no hace nada y simplemente retorna
        return;
    }

    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.initStyle(StageStyle.UNDECORATED);
    alert.setTitle("Confirmación de Cancelación");
    alert.setHeaderText(null);
    alert.setContentText("¿Estás seguro de que deseas cancelar todos los productos en el carrito?");

    Optional<ButtonType> result = alert.showAndWait();
    if (result.isPresent() && result.get() == ButtonType.OK) {
        // Limpiar el carrito y actualizar la interfaz
        cartItems.clear();
        totalPrice = 0.0;
        totalPriceLabel.setText("$0.00");
        updateCart();
    }
}

//FUNCIÓN para generar cambio
private Optional<Double> solicitarEfectivo() {
    TextInputDialog dialog = new TextInputDialog();
    dialog.initStyle(StageStyle.UNDECORATED);
    dialog.setHeaderText("Ingrese la cantidad de efectivo:");
    dialog.setContentText("$:");

    Optional<String> result = dialog.showAndWait();

    if (result.isPresent()) {
        String input = result.get();
        try {
            double efectivo = Double.parseDouble(input);
            return Optional.of(efectivo);
        } catch (NumberFormatException e) {
            // Si el valor ingresado no es numérico, muestra una alerta de error
            Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "", "El valor ingresado no es un número válido.");
            return Optional.empty();
        }
    } else {
        return Optional.empty();
    }
}

private void clearInterface() {
    cartItems.clear();  // Limpiar el carrito de compras
    selectedItemsList.getItems().clear();  // Limpiar el ListView
    totalPriceLabel.setText("$0.00");  // Reiniciar la etiqueta de precio total
    MoneyLabel.setText("$0.00");  // Limpiar la etiqueta de dinero ingresado
    ChangeLabel.setText("$0.00");  // Limpiar la etiqueta de cambio
    totalPrice = 0.0;  // Reiniciar el precio total
    pagarButton.setDisable(false);
    cancelButton.setDisable(false);
    CancelAll.setDisable(false);
}



}