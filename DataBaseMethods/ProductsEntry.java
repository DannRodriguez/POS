package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductsEntry {

    public static void ProductRegistrer(String nombreProducto, double monto, String nombreVendedor, String codigoBarras) {
        String query = "INSERT INTO soldproducts (ProductName, Price, SellerName, barcode, fecha) VALUES (?, ?, ?, ?, CURDATE())";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            
            stmt.setString(1, nombreProducto);
            stmt.setDouble(2, monto);
            stmt.setString(3, nombreVendedor);
            stmt.setString(4, codigoBarras);

            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
