/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class VerifyIfUserExists {
   
    public  boolean usuarioExiste(String nombreUsuario) {
        String query = "SELECT COUNT(*) FROM usuarios WHERE nombre_usuario = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            
            statement.setString(1, nombreUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int count = resultSet.getInt(1);
                return count > 0; // Si count > 0, significa que el usuario ya existe
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Por defecto, asumimos que el usuario no existe o hubo un error
    }
    
}
