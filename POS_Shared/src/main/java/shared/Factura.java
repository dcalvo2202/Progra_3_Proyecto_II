package shared;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Factura {
    private String numeroFactura;
    private Cliente cliente;
    private Cajero cajero;
    private List<Linea> listaProductos;
    private LocalDate fecha;

    public Factura() {
        this.numeroFactura = "";
        this.cliente = null;
        this.cajero = null;
        this.listaProductos = new ArrayList<>();
        this.fecha = LocalDate.now();
    }

    public Factura(String numeroFactura, Cliente cliente, Cajero cajero, List<Linea> lineas, LocalDate fecha) {

        this.numeroFactura = numeroFactura;
        this.cliente = cliente;
        this.cajero = cajero;
        this.listaProductos = lineas;
        this.fecha = fecha;

    }

    public String getNumeroFactura() { return numeroFactura; }
    public void setNumeroFactura(String numeroFactura) { this.numeroFactura = numeroFactura; }

    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    public Cajero getCajero() { return cajero; }
    public void setCajero(Cajero cajero) { this.cajero = cajero; }

    public List<Linea> getListaProductos() { return listaProductos; }
    public void setListaProductos(List<Linea> listaProductos) { this.listaProductos = listaProductos; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public void agregarProducto(Linea linea) {
        this.listaProductos.add(linea);
    }

    public void eliminarProducto(Linea linea) {
        this.listaProductos.remove(linea);
    }

    public double getSubTotal() {
        double total = 0;
        for (Linea linea : listaProductos) {
            total = total + (linea.getProducto().getPrecioUnitario() * linea.getCantidad());
        }
        return total;
    }

    public double getDescuentoTotal() {
        double total = 0;
        for (Linea linea : listaProductos) {
            total = total + linea.getDescuento();
        }
        return total;
    }

    public double getImporteFactura() {
        double importe = 0;
        for (Linea l : listaProductos) {
            importe = (getSubTotal()) - l.getDescuento();
        }
        return importe;
    }

    public String getNombreCliente() {
        return cliente.getNombre();
    }

    public List<String> getCategorias() {
        List<String> categorias = new ArrayList<>();
        for (Linea l : listaProductos) {
            categorias.add(l.getProducto().getCategoria().getNombre());
        }
        return categorias;
    }

    public List<Producto> getProductos() {
        List<Producto> productos = new ArrayList<>();
        for (Linea l : listaProductos) {
            productos.add(l.getProducto());
        }
        return productos;
    }

    @Override
    public String toString() {
        return this.numeroFactura;
    }
}
