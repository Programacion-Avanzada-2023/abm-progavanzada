/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import modelo.Modelo;
import modelo.ModeloEntity;
import vista.subViews.ModeloView;

/**
 *
 * @author mazal
 */
public class ModeloController implements ActionListener {

    private ModeloView view;
    private JTable table;
    private DefaultTableModel model;

    private ModeloEntity repo = new ModeloEntity();

    public ModeloController(ModeloView view) {
        this.view = view;
        this.table = view.modelTable;
        this.model = (DefaultTableModel) this.table.getModel();

        this.initListeners();
    }

    private void initListeners() {
        // Agregar escucha a los botones de listar, guardar cambios y insertar.
        this.view.modelList.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.view.modelList) {
            this.listarModelos();

            this.view.modelDelete.setEnabled(true);
            this.view.modelSave.setEnabled(true);

            return;
        }
    }

    /**
     * Borra las entradas de la tabla y rellena con nuevos modelos.
     *
     * @param modelos Modelos a agregar
     */
    private void rellenarTabla(List<Modelo> modelos) {
        model.setRowCount(0);

        for (Modelo modelo : modelos) {
            model.addRow(new Object[]{modelo.getId(), modelo.getNombre(), modelo.getMarca().getNombre(), modelo.getYear()});
        }
    }

    /**
     * Lista en la tabla todos los modelos del dominio.
     */
    private void listarModelos() {
        // Traer todas las marcas.
        List<Modelo> modelos = this.repo.buscarTodos();

        // Asignarlas a la tabla de la vista.
        this.rellenarTabla(modelos);
    }
}
