package shared;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.time.LocalDate;
import java.util.List;

public class GraficoVentas {
    public JPanel crearGrafico(List<VentasMes> ventasList, List<LocalDate> f) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (VentasMes venta : ventasList) {
            dataset.addValue(venta.getTotalVentas(), venta.getCategoria(), venta.getMes().toString());
        }
        JFreeChart chart = ChartFactory.createLineChart(
                "Ventas por mes",
                "Mes",
                "Ventas",
                dataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 300));

        JPanel panelGrafico = new JPanel();
        panelGrafico.add(chartPanel);

        return panelGrafico;
    }
}
