package modelo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AutoDAO {
    Conexion conectar = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Auto> listar() {
        List<Auto> datos = new ArrayList<>();
        String sql = "SELECT * FROM automovil"; // Cambiado a "auto" en lugar de "persona"
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) { // Corregido a rs.next()
                Auto a = new Auto();
                a.setId(rs.getInt(1)); // Suponiendo que la columna 1 es el ID
                a.setMarca(rs.getString(2));
                a.setModelo(rs.getString(3));
                a.setPatente(rs.getString(4));
                datos.add(a);
            }
            con.close(); // Cierra la conexión después de usarla
        } catch (Exception e) {
            e.printStackTrace();
        }
        return datos;
    }
    public int agregar(Auto a){
        String sql="insert into automovil (Marca,Modelo,Patente) values(?,?,?)";
        try{
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,a.getMarca());
            ps.setString(2,a.getModelo());
            ps.setString(3,a.getPatente());
            ps.executeUpdate();
        }catch (Exception e){
            
        }
        return 1;
    }
    public int Actualizar(Auto a){
        int r=0;
        String sql="update automovil set Marca=?, Modelo=?, Patente=? where id=?";
        try{
            con=conectar.getConnection();
            ps=con.prepareStatement(sql);
            ps.setString(1,a.getMarca());
            ps.setString(2,a.getModelo());
            ps.setString(3,a.getPatente());
            ps.setInt(4,a.getId());
            r=ps.executeUpdate();
            if (r==1){
                return 1;
            }else{
                return 0;
            }
        }catch (Exception e){
            
        }
        return 1;
        
    }
   public void delete(int id) {
    String sql = "delete from automovil where id=?";
    try {
        con = conectar.getConnection();
        ps = con.prepareStatement(sql);
        ps.setInt(1, id);
        ps.executeUpdate();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
   

}
