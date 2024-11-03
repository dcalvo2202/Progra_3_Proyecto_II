package shared;

import java.time.YearMonth;

public class VentasMes {
    private YearMonth mes;
    private double totalVentas;
    private String categoria;

    public VentasMes(YearMonth mes, double totalVentas, String categoria) {
        this.mes = mes;
        this.totalVentas = totalVentas;
        this.categoria = categoria;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public YearMonth getMes() {
        return mes;
    }

    public double getTotalVentas() {
        return totalVentas;
    }

    @Override
    public String toString() {
        return "Categoria: " + categoria + "Mes: " + mes + " - Total Ventas: " + totalVentas;
    }
}
