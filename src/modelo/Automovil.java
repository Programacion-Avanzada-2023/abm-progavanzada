package modelo;

public class Automovil {

    private int id;
    private Modelo modelo;
    private String patente;
    private Persona cliente;

    public Automovil(int id, Modelo modelo, String patente, Persona cliente) {
        this.id = id;
        this.modelo = modelo;
        this.patente = patente;
        this.cliente = cliente;
    }
    
     public Automovil(Modelo modelo, String patente, Persona cliente) {
        this.modelo = modelo;
        this.patente = patente;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
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
