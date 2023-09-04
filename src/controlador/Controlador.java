package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Auto;
import modelo.AutoDAO;
import vista.Vista;

public class Controlador implements ActionListener {
    AutoDAO dao = new AutoDAO();
    Auto a = new Auto();
    Vista vista;
    DefaultTableModel modelo = new DefaultTableModel();

    public Controlador(Vista vista) {
        this.vista = vista;
        this.vista.btnListar.addActionListener(this);
        modelo.addColumn("ID");
        modelo.addColumn("Marca");
        modelo.addColumn("Modelo");
        modelo.addColumn("Patente");
        this.vista.tabla.setModel(modelo);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnOk.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vista.btnListar) {
            limpiarTabla();
            listar(vista.tabla);
            actualizar();
        }
        if (e.getSource()== vista.btnGuardar){
            agregar();
            limpiarTabla();
            listar(vista.tabla);
        }
        if(e.getSource()==vista.btnEditar){
            int fila=vista.tabla.getSelectedRow();
            if (fila==-1){
                 JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila");
            }else{
                int id = Integer.parseInt((String) vista.tabla.getValueAt(fila, 0).toString());
                String marca = (String) vista.tabla.getValueAt(fila, 1);
                String modelo = (String) vista.tabla.getValueAt(fila, 2);
                String patente = (String) vista.tabla.getValueAt(fila, 3);
                vista.txtID.setText(""+id);
                vista.txtMarca.setText(marca);
                vista.txtModelo.setText(modelo);
                vista.txtPatente.setText(patente);
            }
        }
        if(e.getSource()==vista.btnOk){
            actualizar();
            limpiarTabla();
            listar(vista.tabla);
        }
        if (e.getSource() == vista.btnEliminar) {
            int fila = vista.tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(vista, "Debe seleccionar un vehiculo");
             } else {
                int id = Integer.parseInt(vista.tabla.getValueAt(fila, 0).toString());
                dao.delete(id);
                JOptionPane.showMessageDialog(vista, "Auto eliminado");
                limpiarTabla();
                listar(vista.tabla);
    }
}


    }
    
    
    public void actualizar(){
        int id=Integer.parseInt(vista.txtID.getText());
        String marca=vista.txtMarca.getText();
        String modelo=vista.txtModelo.getText();
        String patente=vista.txtPatente.getText();
        a.setId(id);
        a.setMarca(marca);
        a.setModelo(modelo);
        a.setPatente(patente);
        int r=dao.Actualizar(a);
        if(r==1){
            JOptionPane.showMessageDialog(vista,"auto actualizado correctamente");
        }else{
            JOptionPane.showMessageDialog(vista,"error al actualizar");
        }
    }

    public void listar(JTable tabla) {
        modelo.setRowCount(0); // Limpiar la tabla antes de cargar nuevos datos
        List<Auto> lista = dao.listar();
        Object[] object = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0] = lista.get(i).getId();
            object[1] = lista.get(i).getMarca();
            object[2] = lista.get(i).getModelo();
            object[3] = lista.get(i).getPatente();
            modelo.addRow(object);
        }
    }

    private void agregar() {
        
        String marca=vista.txtMarca.getText();
        String modelo=vista.txtModelo.getText();
        String patente=vista.txtPatente.getText();
        a.setMarca(marca);
        a.setModelo(modelo);
        a.setPatente(patente);
        int r=dao.agregar(a);
        if(r==1){
            JOptionPane.showMessageDialog(vista,"auto agregado con exito");
        }else{
             JOptionPane.showMessageDialog(vista,"Error");
        }
    
    }
    public void delete() {
        int fila = vista.tabla.getSelectedRow();

        if (fila == -1) {
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un vehiculo");
        } else {
            int id = Integer.parseInt(vista.tabla.getValueAt(fila, 0).toString());
            dao.delete(id);
            JOptionPane.showMessageDialog(vista, "Auto eliminado");
            limpiarTabla();
            listar(vista.tabla);
        }
    }
    
     void limpiarTabla() {
        for (int i = 0; i < vista.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i - 1;
        }  
     }
}
