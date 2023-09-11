package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de Persona.
 *
 * Todas las acciones contra la base de datos para Persona estan aqui.
 * @author mazal
 */
public class PersonaEntity {

    private Connection conn = new Conexion().getConnection();

    public List<Persona> buscarTodos() {
        // Declarar query.
        String query = "SELECT * FROM persona ORDER BY id;";

        try {
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);

            // Executed
            ResultSet rs = prepared.executeQuery();

            List<Persona> result = new ArrayList<>();
            while (rs.next()) {
                // Crear entidad.
                Persona persona = new Persona(rs.getInt("id"), rs.getString("nombre"), rs.getString("apellido"), rs.getInt("dni"), rs.getInt("rol"));
                result.add(persona);
            }

            return result;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<Persona>();
        }
    }

    public void agregar(Persona persona) {
        try {
            // Build query.
            String query = "INSERT INTO persona (nombre, apellido, dni, rol) VALUES (?, ?, ?, ?);";

            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setString(1, persona.getNombre());
            prepared.setString(2, persona.getApellido());
            prepared.setInt(3, persona.getDni());
            prepared.setInt(4, persona.getRol());

            // Executed
            prepared.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void actualizar(int id, String nombre, String apellido, int dni, int rol) {
        try {
            // Build query.
            String query = "UPDATE persona SET nombre = ?, apellido = ?, dni = ?, rol = ? WHERE id = ?;";

            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setString(1, nombre);
            prepared.setString(2, apellido);
            prepared.setInt(3, dni);
            prepared.setInt(4, rol);
            prepared.setInt(5, id);

            // Executed
            prepared.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void borrar(int id) {
        try {
            // Build query.
            String query = "DELETE FROM persona WHERE id = ?;";

            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setInt(1, id);

            // Executed
            prepared.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
