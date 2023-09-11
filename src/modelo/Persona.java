package modelo;

public class Persona {

    private int id;
    private String nombre;
    private String apellido;
    private int dni;
    // 1 = Tecnico, 0 = Cliente
    private int rol;

    public Persona(int id, String nombre, String apellido, int dni, int rol) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.rol = rol;
    }

    public Persona(String nombre, String apellido, int dni, int rol) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.rol = rol;
    }
    
    public String getNombreyApellido() {
        return this.apellido + ", " + this.nombre;
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public int getDni() {
        return dni;
    }

    public void setDni(int dni) {
        this.dni = dni;
    }

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

}
