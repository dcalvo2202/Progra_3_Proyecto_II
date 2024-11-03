package backend.logic;

import backend.database.DatabaseConnection;
import shared.Producto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductManager {
    // Método para agregar un nuevo producto
    public boolean addProduct(Producto producto) {
        String sql = "INSERT INTO productos (codigo, descripcion, unidadDeMedida, precioUnitario, existencias, categoria) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getCodigo());
            pstmt.setString(2, producto.getDescripcion());
            pstmt.setString(3, producto.getUnidadDeMedida());
            pstmt.setDouble(4, producto.getPrecioUnitario());
            pstmt.setInt(5, producto.getExistencias());
            pstmt.setString(6, producto.getCategoria().getId()); // Suponiendo que categoría tiene un id
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para actualizar un producto existente
    public boolean updateProduct(Producto producto) {
        String sql = "UPDATE productos SET descripcion = ?, unidadDeMedida = ?, precioUnitario = ?, existencias = ?, categoria = ? " +
                "WHERE codigo = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, producto.getDescripcion());
            pstmt.setString(2, producto.getUnidadDeMedida());
            pstmt.setDouble(3, producto.getPrecioUnitario());
            pstmt.setInt(4, producto.getExistencias());
            pstmt.setString(5, producto.getCategoria().getId());
            pstmt.setString(6, producto.getCodigo());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Método para obtener un producto por su ID (código)
    public Producto getProductById(String productId) {
        String sql = "SELECT * FROM productos WHERE codigo = ?";
        Producto producto = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                producto = new Producto(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getString("unidadDeMedida"),
                        rs.getDouble("precioUnitario"),
                        rs.getInt("existencias"),
                        null // Asumimos que se asignará una categoría después si es necesario
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    // Método para obtener todos los productos
    public List<Producto> getAllProducts() {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM productos";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getString("codigo"),
                        rs.getString("descripcion"),
                        rs.getString("unidadDeMedida"),
                        rs.getDouble("precioUnitario"),
                        rs.getInt("existencias"),
                        null
                );
                productos.add(producto);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productos;
    }
}
