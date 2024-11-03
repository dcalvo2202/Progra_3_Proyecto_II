package shared;

public class Producto {
    private String codigo;
    private String descripcion;
    private String unidadDeMedida;
    private double precioUnitario;
    private int existencias;
    private Categoria categoria;

    public Producto () {
        this("", "", "", 0.0, 0, null);
    }

    public Producto(String codigo, String descripcion, String unidadDeMedida, double precioUnitario, int existencias, Categoria categoria) {
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.unidadDeMedida = unidadDeMedida;
        this.precioUnitario = precioUnitario;
        this.existencias = existencias;
        this.categoria = categoria;
    }

    public String getCodigo() { return codigo; }
    public void setCodigo(String codigo) { this.codigo = codigo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getUnidadDeMedida() { return unidadDeMedida; }
    public void setUnidadDeMedida(String unidadDeMedida) { this.unidadDeMedida = unidadDeMedida; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public int getExistencias() { return existencias; }
    public void setExistencias(int existencias) { this.existencias = existencias; }

    public Categoria getCategoria() { return categoria; }
    public void setCategoria(Categoria categoria) { this.categoria = categoria; }

    @Override
    public String toString() {
        return descripcion;
    }
}
