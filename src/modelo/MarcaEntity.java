package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositorio de Marca.
 *
 * Todas las acciones contra la base de datos para Marca estan aqui.
 * @author mazal
 */
public class MarcaEntity {
    private Connection conn = new Conexion().getConnection();

    public List<Marca> buscarTodos() {
        // Declarar query.
        String query = "SELECT * FROM marca ORDER BY id;";
        
        try {
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            
            // Executed
            ResultSet rs = prepared.executeQuery();
            
            List<Marca> result = new ArrayList<>();
            while (rs.next()) {
                // Crear entidad.
                Marca marca = new Marca(rs.getInt("id"), rs.getString("nombre"), rs.getString("origen"));
                result.add(marca);
            }
            
            return result;
        } catch (Exception e) {
            System.out.println(e);
            return new ArrayList<Marca>();
        }
    }
    
    public void agregar(Marca marca) {
        try {
            // Build query.
            String query = "INSERT INTO marca (nombre, origen) VALUES (?, ?);";
            
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setString(1, marca.getNombre());
            prepared.setString(2, marca.getOrigen());
            
            // Executed
            prepared.execute();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void actualizar(int id, String nombre, String origen) {
        try {
            // Build query.
            String query = "UPDATE marca SET nombre = ?, origen = ? WHERE id = ?;";
            
            // Make the query.
            PreparedStatement prepared = conn.prepareStatement(query);
            prepared.setString(1, nombre);
            prepared.setString(2, origen);
            prepared.setInt(3, id);
            
            // Executed
            prepared.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
    public void borrar(int id) {
        try {
            // Build query.
            String query = "DELETE FROM marca WHERE id = ?;";
            
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
