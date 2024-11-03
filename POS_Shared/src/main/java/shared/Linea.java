package shared;

public class Linea {
    String id;
    Producto producto;
    int cantidad;
    double descuento;

    public Linea() {
        this.producto = new Producto();
        this.cantidad = 0;
        this.descuento = 0.0;
        this.id = "";
    }

    public Linea(Producto producto, int cantidad, double descuento, String id) {
        this.producto = producto;
        this.descuento = descuento;
        this.cantidad = cantidad;
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double precioNeto() {
        return (producto.getPrecioUnitario() * cantidad) - descuento;
    }

    @Override
    public String toString() {
        return id;
    }
}
