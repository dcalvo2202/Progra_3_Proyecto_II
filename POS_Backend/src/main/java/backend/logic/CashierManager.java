package backend.logic;

import backend.database.DatabaseConnection;
import shared.Cajero;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CashierManager {

    // Método para obtener todos los cajeros como una cadena
    public String getAllCashiersAsString() {
        List<Cajero> cajeros = getAllCashiers();
        StringBuilder result = new StringBuilder();

        for (Cajero cajero : cajeros) {
            result.append("Cajero: ").append(cajero.getId()).append(", Nombre: ")
                    .append(cajero.getNombre()).append("\n");
        }
        return result.toString();
    }

    // Método para obtener todos los cajeros desde la base de datos
    public List<Cajero> getAllCashiers() {
        List<Cajero> cajeros = new ArrayList<>();
        String sql = "SELECT * FROM cajeros";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cajero cajero = new Cajero(rs.getString("id"), rs.getString("nombre"));
                cajeros.add(cajero);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cajeros;
    }

    // Método para actualizar la información de un cajero
    public boolean updateCashier(String id, String name, String shift) {
        String sql = "UPDATE cajeros SET nombre = ?, turno = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, name);
            pstmt.setString(2, shift);
            pstmt.setString(3, id);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
