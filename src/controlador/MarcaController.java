/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Marca;
import modelo.MarcaEntity;
import vista.subViews.MarcaView;

/**
 * Controlador encargado de administrar acciones dentro de la vista de Marcas en
 * el ABM.
 *
 * @author mazal
 */
public class MarcaController implements ActionListener {

    // Vista principal que el controlador administra.
    private MarcaView view;

    // Tabla dentro de la vista.
    private JTable table;

    // Modelo de la tabla dentro de la vista.
    private DefaultTableModel model;

    private MarcaEntity repo = new MarcaEntity();

    public MarcaController(MarcaView view) {
        this.view = view;
        this.table = view.brandTable;
        this.model = (DefaultTableModel) this.table.getModel();

        this.initListeners();
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.view.brandList) {
            this.listarMarcas();

            this.view.brandDelete.setEnabled(true);
            this.view.brandSave.setEnabled(true);

            return;
        }

        if (e.getSource() == this.view.brandSave) {
            this.actualizarFilas();
            return;
        }

        if (e.getSource() == this.view.brandInsert) {
            this.insertarNuevaMarca();
            return;
        }

        if (e.getSource() == this.view.brandDelete) {
            int result = JOptionPane.showConfirmDialog(null,
                    "¿Esta seguro de borrar la fila?",
                    "Confirme el borrado",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE);

            if (result != 0) {
                return;
            }

            this.borrarFila();
            return;
        }
    }

    private void initListeners() {
        // Agregar escucha a los botones de listar, guardar cambios y insertar.
        this.view.brandList.addActionListener(this);
        this.view.brandSave.addActionListener(this);
        this.view.brandInsert.addActionListener(this);
        this.view.brandDelete.addActionListener(this);
    }

    /**
     * Borra las entradas de la tabla y rellena con nuevas marcas.
     *
     * @param marcas Marcas a agregar
     */
    private void rellenarTabla(List<Marca> marcas) {
        model.setRowCount(0);

        for (Marca marca : marcas) {
            model.addRow(new Object[]{marca.getId(), marca.getNombre(), marca.getOrigen()});
        }

        this.view.brandDelete.setEnabled(true);
        this.view.brandSave.setEnabled(true);
    }

    /**
     * Lista en la tabla todas las marcas del dominio.
     */
    private void listarMarcas() {
        // Traer todas las marcas.
        List<Marca> marcas = this.repo.buscarTodos();

        // Asignarlas a la tabla de la vista.
        this.rellenarTabla(marcas);
    }

    /**
     * Actualizar todos los cambios provistos en la tabla.
     */
    private void actualizarFilas() {
        List<Marca> marcas = new ArrayList<>(), originales = this.repo.buscarTodos();

        for (int i = 0; i < model.getRowCount(); i++) {
            int id = Integer.parseInt(model.getValueAt(i, 0).toString());
            String nombre = model.getValueAt(i, 1).toString(), origen = model.getValueAt(i, 2).toString();

            if (ValidacionesHelper.validarStringLongitudSinNumeros(nombre) && ValidacionesHelper.validarStringLongitudSinNumeros(origen)) {
                Marca fila = new Marca(id, nombre, origen);
                marcas.add(fila);
            } else {
                Marca original = originales.get(i);
                marcas.add(original);
                JOptionPane.showMessageDialog(null, String.format("La fila %s no se actualizará debido a que es inválida.", original.getNombre()));
            }
        }

        for (Marca marca : marcas) {
            this.repo.actualizar(marca.getId(), marca.getNombre(), marca.getOrigen());
        }

        this.rellenarTabla(marcas);
    }

    /**
     * Inserta una nueva marca en la base de datos.
     */
    private void insertarNuevaMarca() {
        String nombre = this.view.brandName.getText(), origen = this.view.brandOrigin.getText();

        // Si no son validos los valores, no dejarlo
        if (!ValidacionesHelper.validarStringLongitudSinNumeros(nombre) || !ValidacionesHelper.validarStringLongitudSinNumeros(origen)) {
            JOptionPane.showMessageDialog(null, "Tienes errores en los campos, corrigelos y luego intenta insertar.");
            return;
        }

        Marca marca = new Marca(nombre, origen);

        this.repo.agregar(marca);
        this.listarMarcas();

        // Limpiar campos de texto.
        this.view.limpiarCampos();
    }

    /**
     * Borra una fila de la base de datos.
     */
    private void borrarFila() {
        int rowIndex = this.table.getSelectedRow();

        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(null, "No seleccionaste ninguna fila para borrar.");
            return;
        }

        // Traer ID de la fila.
        int id = (int) this.table.getValueAt(rowIndex, 0);

        // Borrar la fila de la tabla y de la base
        this.repo.borrar(id);
        this.model.removeRow(rowIndex);
    }
}
