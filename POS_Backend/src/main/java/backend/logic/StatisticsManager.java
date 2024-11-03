package backend.logic;

import backend.database.DatabaseConnection;
import shared.VentasMes;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

public class StatisticsManager {

    // Método para obtener las estadísticas de ventas por mes
    public List<VentasMes> getMonthlySalesStatistics() {
        List<VentasMes> ventasMesList = new ArrayList<>();
        String sql = "SELECT YEAR(fecha) AS year, MONTH(fecha) AS month, SUM(total) AS totalVentas, categoria " +
                "FROM facturas JOIN productos ON facturas.producto_id = productos.id " +
                "GROUP BY YEAR(fecha), MONTH(fecha), categoria";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int year = rs.getInt("year");
                int month = rs.getInt("month");
                double totalVentas = rs.getDouble("totalVentas");
                String categoria = rs.getString("categoria");

                VentasMes ventasMes = new VentasMes(YearMonth.of(year, month), totalVentas, categoria);
                ventasMesList.add(ventasMes);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ventasMesList;
    }

    // Método para obtener las estadísticas como una cadena (para pruebas rápidas)
    public String getStatisticsAsString() {
        List<VentasMes> ventasMesList = getMonthlySalesStatistics();
        StringBuilder result = new StringBuilder("Estadísticas de ventas por mes:\n");

        for (VentasMes ventasMes : ventasMesList) {
            result.append("Mes: ").append(ventasMes.getMes())
                    .append(", Categoría: ").append(ventasMes.getCategoria())
                    .append(", Ventas Totales: ").append(ventasMes.getTotalVentas())
                    .append("\n");
        }
        return result.toString();
    }
}
