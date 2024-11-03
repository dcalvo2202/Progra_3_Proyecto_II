package backend.logic;

import backend.database.DatabaseConnection;
import shared.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientManager {
    // Método para agregar un nuevo cliente
    public boolean addClient(Cliente cliente) {
        String sql = "INSERT INTO clientes (id, nombre, telefono, email, descuento) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getId());
            pstmt.setString(2, cliente.getNombre());
            pstmt.setString(3, cliente.getTelefono());
            pstmt.setString(4, cliente.getEmail());
            pstmt.setDouble(5, cliente.getDescuento());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un cliente
    public boolean updateClient(Cliente cliente) {
        String sql = "UPDATE clientes SET nombre = ?, telefono = ?, email = ?, descuento = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, cliente.getNombre());
            pstmt.setString(2, cliente.getTelefono());
            pstmt.setString(3, cliente.getEmail());
            pstmt.setDouble(4, cliente.getDescuento());
            pstmt.setString(5, cliente.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un cliente por ID
    public Cliente getClientById(String clientId) {
        String sql = "SELECT * FROM clientes WHERE id = ?";
        Cliente cliente = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, clientId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                cliente = new Cliente(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getDouble("descuento")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cliente;
    }

    // Método para obtener todos los clientes
    public List<Cliente> getAllClients() {
        List<Cliente> clientes = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = new Cliente(
                        rs.getString("id"),
                        rs.getString("nombre"),
                        rs.getString("telefono"),
                        rs.getString("email"),
                        rs.getDouble("descuento")
                );
                clientes.add(cliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clientes;
    }
}
