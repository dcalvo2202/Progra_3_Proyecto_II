package backend.logic;

import backend.database.DatabaseConnection;
import shared.Categoria;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {

    // Método para agregar una nueva categoría
    public boolean addCategory(Categoria categoria) {
        String sql = "INSERT INTO categorias (id, nombre) VALUES (?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getId());
            pstmt.setString(2, categoria.getNombre());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar una categoría existente
    public boolean updateCategory(Categoria categoria) {
        String sql = "UPDATE categorias SET nombre = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoria.getNombre());
            pstmt.setString(2, categoria.getId());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener una categoría por su ID
    public Categoria getCategoryById(String categoryId) throws SQLException {
        String sql = "SELECT * FROM categorias WHERE id = ?";
        Categoria categoria = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, categoryId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                categoria = new Categoria(
                        rs.getString("id"),
                        rs.getString("nombre")
                );
            }
        }
        return categoria;
    }

    public List<Categoria> getAllCategories() {
        List<Categoria> categorias = new ArrayList<>();
        String sql = "SELECT * FROM categorias";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Categoria categoria = new Categoria(
                        rs.getString("id"),
                        rs.getString("nombre")
                );
                categorias.add(categoria);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categorias;
    }
}