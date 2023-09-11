package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AutomovilEntity {

    private Connection conn = new Conexion().getConnection();

    public List<Automovil> buscarTodos() {
        // Declarar query.
        String query = "SELECT "
                + "a.id, a.patente,"
                + "md.id AS modeloId, md.nombre AS modelo, md.year,"
                + "m.id AS marcaId, m.nombre AS marca, m.origen AS origen,"
                + "p.id AS personaId, p.nombre AS personaNombre, p.apellido AS personaApellido, p.dni, p.rol "
                + "FROM automovil a "
                + "INNER JOIN modelo md ON md.id = a.modelo "
                + "INNER JOIN marca m ON m.id = md.marca "
                + "INNER JOIN persona p ON p.id = a.cliente "
                + "ORDER BY a.id;";

        try {
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);

            // Executed
            ResultSet rs = prepared.executeQuery();

            List<Automovil> result = new ArrayList<>();
            while (rs.next()) {
                // Crear entidades.
                Marca marca = new Marca(rs.getInt("marcaId"), rs.getString("marca"), rs.getString("origen"));
                Modelo modelo = new Modelo(rs.getInt("modeloId"), marca, rs.getString("modelo"), rs.getInt("year"));
                Persona cliente = new Persona(rs.getInt("personaId"), rs.getString("personaNombre"), rs.getString("personaApellido"), rs.getInt("dni"), rs.getInt("rol"));

                Automovil auto = new Automovil(rs.getInt("id"), modelo, rs.getString("patente"), cliente);
                result.add(auto);
            }

            return result;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<Automovil>();
        }
    }

    public void agregar(Automovil automovil) {
        try {
            // Build query.
            String query = "INSERT INTO automovil (modelo, patente, cliente) VALUES (?, ?, ?);";

            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setInt(1, automovil.getModelo().getId());
            prepared.setString(2, automovil.getPatente());
            prepared.setInt(3, automovil.getCliente().getId());

            // Executed
            prepared.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void actualizar(int id, int modelo, String patente, int cliente) {
        try {
            // Build query.
            String query = "UPDATE automovil SET modelo = ?, patente = ?, cliente = ? WHERE id = ?;";

            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setInt(1, modelo);
            prepared.setString(2, patente);
            prepared.setInt(3, cliente);
            prepared.setInt(4, id);

            // Executed
            prepared.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void borrar(int id) {
        try {
            // Build query.
            String query = "DELETE FROM automovil WHERE id = ?;";

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
