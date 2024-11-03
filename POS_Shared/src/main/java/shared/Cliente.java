package shared;

public class Cliente {
    private String id;
    private String nombre;
    private String telefono;
    private String email;
    private double descuento;

    public Cliente() {
        this("","","","",0);
    } // se ocupa para JAXB

    public Cliente(String id, String nombre, String telefono, String email, double descuento) {
        this.id = id;
        this.nombre = nombre;
        this.telefono = telefono;
        this.email = email;
        this.descuento = descuento;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public double getDescuento() { return descuento; }
    public void setDescuento(double descuento) { this.descuento = descuento; }

    @Override
    public String toString() {
        return nombre;
    }
}
