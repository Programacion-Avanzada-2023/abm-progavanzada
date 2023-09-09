package modelo;

public class Marca {

    private int id;
    private String nombre;
    private String origen;

    public Marca(int id, String nombre, String origen) {
        this.id = id;
        this.nombre = nombre;
        this.origen = origen;
    }

    public int getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getOrigen() {
        return origen;
    }

    public void setOrigen(String origen) {
        this.origen = origen;
    }
}
