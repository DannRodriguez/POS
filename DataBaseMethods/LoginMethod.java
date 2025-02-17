package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import Functions.UserStartSession;

public class LoginMethod {

    public boolean validateLogin(String username, String password) {
        String queryAdmin = "SELECT AdminStatus FROM administradores LIMIT 1";
        String queryUser = "SELECT * FROM usuarios WHERE nombre_usuario = ? AND contrasena = ?";

        try (Connection connection = ConnectionDB.getConnection();
             PreparedStatement stmtAdmin = connection.prepareStatement(queryAdmin);
             PreparedStatement stmtUser = connection.prepareStatement(queryUser)) {

            // Verificar estado del administrador antes de permitir el inicio de sesión
            ResultSet rsAdmin = stmtAdmin.executeQuery();
            if (rsAdmin.next()) {
                boolean adminActivo = rsAdmin.getBoolean("AdminStatus");
                if (!adminActivo) {
                    Functions.FunctionsOfClasses.showAlertFail(Alert.AlertType.ERROR, "Error", "Error: Administrador no activo. No se puede iniciar sesión.");
                    return false;
                }
            } else {
                System.out.println("Error: No se encontró el administrador.");
                return false;
            }

            // Validar el inicio de sesión del usuario normal
            stmtUser.setString(1, username);
            stmtUser.setString(2, password);
            ResultSet rsUser = stmtUser.executeQuery();

            if (rsUser.next()) {
                // Si la validación es exitosa, establece la sesión
                UserStartSession.getInstance().setLoggedInUser(username);
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
