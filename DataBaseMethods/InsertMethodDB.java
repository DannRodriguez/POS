package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Functions.VerifyUserStatus;
import javafx.scene.control.Alert;

public class InsertMethodDB {
  
    
    public static boolean registerUser(String nombreUsuario, String contrasena, String adminKey) {
        String checkAdminStatusQuery = "SELECT AdminStatus FROM Administradores WHERE admin_key = ?";
        String checkAdminKeyQuery = "SELECT COUNT(*) FROM Administradores WHERE admin_key = ?";
        String insertUserQuery = "INSERT INTO Usuarios (nombre_usuario, contrasena, admin_key, fecha, hora) VALUES (?, ?, ?, CURDATE(), CURTIME())";

        try (Connection connection = ConnectionDB.getConnection()) {
            // Verificar si el administrador está activo
            
            VerifyUserStatus verifyUser = new VerifyUserStatus();
            verifyUser.verificarYDesactivarUsuarios();

            // Verificar si la admin_key es válida
            try (PreparedStatement checkAdminKeyStmt = connection.prepareStatement(checkAdminKeyQuery)) {
                checkAdminKeyStmt.setString(1, adminKey);
                ResultSet adminKeyResultSet = checkAdminKeyStmt.executeQuery();
                
                if (adminKeyResultSet.next() && adminKeyResultSet.getInt(1) > 0) {
                    // La admin_key es válida, verificar el estado del administrador
                    try (PreparedStatement checkAdminStatusStmt = connection.prepareStatement(checkAdminStatusQuery)) {
                        checkAdminStatusStmt.setString(1, adminKey);
                        ResultSet adminStatusResultSet = checkAdminStatusStmt.executeQuery();

                        if (adminStatusResultSet.next() && adminStatusResultSet.getBoolean("AdminStatus")) {
                            // El administrador está activo, proceder con el registro del usuario
                            try (PreparedStatement insertStmt = connection.prepareStatement(insertUserQuery)) {
                                insertStmt.setString(1, nombreUsuario);
                                insertStmt.setString(2, contrasena);
                                insertStmt.setString(3, adminKey);
                                insertStmt.executeUpdate();
                                return true;
                            }
                        } else {
                            // El administrador no está activo
                            
                            Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "El usuario administrador se encuentra inactivo, favor de llamar a soporte.");
                            System.out.println("Error: El administrador no está activo.");
                            return false;
                        }
                    }
                } else {
                    // La admin_key no es válida
                    System.out.println("Error: Clave de administrador no válida.");
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
