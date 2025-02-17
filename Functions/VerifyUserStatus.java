/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Functions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.fxml.FXML;

/**
 *
 * @author kjfer
 */
public class VerifyUserStatus {
    
     @FXML
    public void verificarYDesactivarUsuarios() {
        try (Connection conn = DataBaseMethods.ConnectionDB.getConnection()) {
            String sql = "SELECT admin_key,AdminStatus,Creation_Date FROM administradores";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    int id = rs.getInt("admin_key");
                    
                   
                    boolean activo = rs.getBoolean("AdminStatus");
                    LocalDate fechaActivacion = rs.getDate("Creation_Date").toLocalDate();
                    // Verificar si la fecha de activación ha pasado más de un día
                    if (activo && fechaActivacion.plusDays(30).isBefore(LocalDate.now())) {
                        // Desactivar el usuario en la base de datos
                        desactivarUsuario(conn, id);
                        System.out.println("Usuario desactivado: " + id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
     public void desactivarUsuario(Connection conn, int id) throws SQLException {
        String sql = "UPDATE administradores SET AdminStatus = FALSE WHERE admin_key = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
    
    
    
}
