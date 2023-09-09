package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ModeloEntity {
    private Connection conn = new Conexion().getConnection();

    public List<Modelo> buscarTodos() {
        // Declarar query.
        String query = "SELECT md.id, md.nombre, md.year, m.nombre AS marca, m.origen AS origen FROM modelo md INNER JOIN marca m ON md.marca = m.id;";
        
        try {
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            
            // Executed
            ResultSet rs = prepared.executeQuery();
            
            List<Modelo> result = new ArrayList<>();
            while (rs.next()) {
                // Crear entidad.
                Marca marca = new Marca(rs.getString("marca"), rs.getString("origen"));
                
                Modelo modelo = new Modelo(rs.getInt("id"), marca, rs.getString("nombre"), rs.getInt("year"));
                result.add(modelo);
            }
            
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<Modelo>();
        }
    }
    
    public void agregar(Modelo modelo) {
        try {
            // Build query.
            String query = "INSERT INTO modelo (nombre, marca, year) VALUES (?, ?, ?);";
            
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setString(1, modelo.getNombre());
            prepared.setInt(2, modelo.getMarca().getId());
            prepared.setInt(3, modelo.getYear());
            
            // Executed
            prepared.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void actualizar(int id, String nombre, int marca, int year) {
        try {
            // Build query.
            String query = "UPDATE modelo SET nombre = ?, marca = ?, year = ? WHERE id = ?;";
            
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setString(1, nombre);
            prepared.setInt(2, marca);
            prepared.setInt(3, year);
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
            String query = "DELETE FROM modelo WHERE id = ?;";
            
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
