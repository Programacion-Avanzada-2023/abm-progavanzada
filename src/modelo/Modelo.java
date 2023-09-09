package modelo;

public class Modelo {

    private int id;
    private Marca marca;
    private String nombre;
    private int year;

    public Modelo(int id, Marca marca, String nombre, int year) {
        this.id = id;
        this.marca = marca;
        this.nombre = nombre;
        this.year = year;
    }

    public int getId() {
        return this.id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

}
