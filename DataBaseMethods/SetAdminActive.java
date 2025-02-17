package DataBaseMethods;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.TextField;

public class SetAdminActive {

    public static void validateAndUpdateAdminStatus(String codigo, TextField InsertText) {
        if(InsertText.getText().isEmpty()){
            Functions.FunctionsOfClasses.ToolTipActive(InsertText, "Rellenar necesariamente", 0);
        }else{
        try (Connection conn = ConnectionDB.getConnection()) {
            // Paso 1: Validar si el código existe
            String checkQuery = "SELECT COUNT(*) FROM codes WHERE RootCodes = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkQuery)) {
                checkStmt.setString(1, codigo);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) { // Mover el cursor al primer registro
                        int count = rs.getInt(1); // Obtener el valor de la primera columna

                        if (count > 0) {
                            // Paso 2: Borrar el código de la base de datos
                            String deleteQuery = "DELETE FROM codes WHERE RootCodes = ?";
                            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                                deleteStmt.setString(1, codigo);
                                deleteStmt.executeUpdate();
                            }

                            // Paso 3: Actualizar el adminstatus de todos los administradores
                            String updateQuery = "UPDATE administradores SET AdminStatus = 1, Creation_Date = CURDATE()";
                            try (PreparedStatement updateStmt = conn.prepareStatement(updateQuery)) {
                                updateStmt.executeUpdate();
                            }

                            Functions.FunctionsOfClasses.ToolTipActive(InsertText, "Código registrado exitosamente", 0);
                            InsertText.clear();
                        } else {
                          Functions.FunctionsOfClasses.ToolTipActive(InsertText, "Código no encontrado", 0);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    }
}
