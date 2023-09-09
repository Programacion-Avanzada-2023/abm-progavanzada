package modelo;

public class Automovil {

    private int id;
    private Marca marca;
    private Modelo modelo;
    private String patente;
    private Persona cliente;

    public Automovil(int id, Marca marca, Modelo modelo, String patente, Persona cliente) {
        this.id = id;
        this.marca = marca;
        this.modelo = modelo;
        this.patente = patente;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public Modelo getModelo() {
        return modelo;
    }

    public void setModelo(Modelo modelo) {
        this.modelo = modelo;
    }

    public String getPatente() {
        return patente;
    }

    public void setPatente(String patente) {
        this.patente = patente;
    }

    public Persona getCliente() {
        return cliente;
    }

    public void setCliente(Persona cliente) {
        this.cliente = cliente;
    }

}
