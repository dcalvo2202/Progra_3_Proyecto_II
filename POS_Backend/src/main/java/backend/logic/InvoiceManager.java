package backend.logic;

import backend.database.DatabaseConnection;
import shared.Cliente;
import shared.Factura;
import shared.Linea;
import shared.Producto;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class InvoiceManager {

    // Método para obtener todas las facturas como una cadena
    public String getAllInvoicesAsString() {
        List<Factura> facturas = getAllInvoices();
        StringBuilder result = new StringBuilder();

        for (Factura factura : facturas) {
            result.append("Factura: ").append(factura.getNumeroFactura()).append(", Cliente: ")
                    .append(factura.getCliente().getNombre()).append(", Total: ")
                    .append(factura.getImporteFactura()).append("\n");
        }
        return result.toString();
    }

    // Método para obtener todas las facturas desde la base de datos
    public List<Factura> getAllInvoices() {
        List<Factura> facturas = new ArrayList<>();
        String sql = "SELECT * FROM facturas";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Cliente cliente = getClientById(rs.getString("cliente_id"));
                List<Linea> lineas = getLineasByFacturaId(rs.getInt("id"));
                Factura factura = new Factura(rs.getString("id"), cliente, null, lineas, rs.getDate("fecha").toLocalDate());
                facturas.add(factura);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return facturas;
    }

    // Método auxiliar para obtener un cliente por ID desde la base de datos
    private Cliente getClientById(String clientId) {
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

    // Método auxiliar para obtener las líneas de productos de una factura por su ID
    private List<Linea> getLineasByFacturaId(int facturaId) {
        List<Linea> lineas = new ArrayList<>();
        String sql = "SELECT * FROM lineas WHERE factura_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, facturaId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Producto producto = getProductById(rs.getString("producto_id"));
                Linea linea = new Linea(
                        producto,
                        rs.getInt("cantidad"),
                        rs.getDouble("descuento"),
                        rs.getString("id")
                );
                lineas.add(linea);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lineas;
    }

    // Método auxiliar para obtener un producto por ID desde la base de datos
    private Producto getProductById(String productId) {
        String sql = "SELECT * FROM productos WHERE id = ?";
        Producto producto = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                producto = new Producto(
                        rs.getString("id"),
                        rs.getString("descripcion"),
                        rs.getString("unidadDeMedida"),
                        rs.getDouble("precioUnitario"),
                        rs.getInt("existencias"),
                        null  // Puedes obtener la categoría si la necesitas
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return producto;
    }

    // Método para crear una factura en la base de datos
    public boolean createInvoice(String clientId, List<Linea> lineas, double total) {
        String sqlFactura = "INSERT INTO facturas (cliente_id, total, fecha) VALUES (?, ?, ?)";
        String sqlLinea = "INSERT INTO lineas (factura_id, producto_id, cantidad, descuento) VALUES (?, ?, ?, ?)";
        boolean success = false;

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);  // Inicia una transacción

            // Inserta la factura
            try (PreparedStatement pstmtFactura = conn.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
                pstmtFactura.setString(1, clientId);
                pstmtFactura.setDouble(2, total);
                pstmtFactura.setDate(3, Date.valueOf(LocalDate.now()));
                pstmtFactura.executeUpdate();

                // Obtiene el ID generado de la factura
                ResultSet generatedKeys = pstmtFactura.getGeneratedKeys();
                if (generatedKeys.next()) {
                    int facturaId = generatedKeys.getInt(1);

                    // Inserta cada línea de producto asociada a la factura
                    try (PreparedStatement pstmtLinea = conn.prepareStatement(sqlLinea)) {
                        for (Linea linea : lineas) {
                            pstmtLinea.setInt(1, facturaId);
                            pstmtLinea.setString(2, linea.getProducto().getCodigo());
                            pstmtLinea.setInt(3, linea.getCantidad());
                            pstmtLinea.setDouble(4, linea.getDescuento());
                            pstmtLinea.addBatch();
                        }
                        pstmtLinea.executeBatch();
                    }
                    conn.commit();  // Confirma la transacción
                    success = true;
                }
            } catch (SQLException e) {
                conn.rollback();  // Revertir la transacción en caso de error
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    // Método para actualizar el total de una factura
    public boolean updateInvoiceTotal(int invoiceId, double newTotal) {
        String sql = "UPDATE facturas SET total = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDouble(1, newTotal);
            pstmt.setInt(2, invoiceId);
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
