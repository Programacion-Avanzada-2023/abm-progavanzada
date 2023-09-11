/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import modelo.Marca;
import modelo.MarcaEntity;
import modelo.Modelo;
import modelo.ModeloEntity;
import vista.subViews.ModeloView;

/**
 * Controlador encargado de administrar acciones dentro de la vista de Modelos
 * en el ABM.
 *
 * @author mazal
 */
public class ModeloController implements ActionListener {

    // Vista principal que el controlador administra.
    private ModeloView view;

    // Tabla dentro de la vista.
    private JTable table;

    // Modelo de la tabla dentro de la vista.
    private DefaultTableModel model;

    private MarcaEntity repoMarca = new MarcaEntity();
    private ModeloEntity repo = new ModeloEntity();

    // Listado de marcas disponibles, actualizado en cada re-render.
    private List<Marca> marcas;

    public ModeloController(ModeloView view) {
        this.view = view;
        this.table = view.modelTable;
        this.model = (DefaultTableModel) this.table.getModel();

        this.initListeners();
        this.rellenarComboBoxes();
    }

    private void initListeners() {
        // Agregar escucha a los botones de listar, guardar cambios y insertar.
        this.view.modelList.addActionListener(this);
        this.view.modelSave.addActionListener(this);
        this.view.modelInsert.addActionListener(this);
        this.view.modelDelete.addActionListener(this);
    }

    /**
     * Rellena todos los JComboBox presentes en la vista con los datos pertinentes.
     */
    public void rellenarComboBoxes() {
        marcas = this.repoMarca.buscarTodos();

        // Transformar a vectores.
        Vector items = new Vector() {
        };
        for (Marca marca : marcas) {
            items.add(marca.getNombre());
        }

        // Cambiar el modelo del ComboBox en la lista de campos.
        DefaultComboBoxModel model = (DefaultComboBoxModel) this.view.brandsComboBox.getModel();
        model.removeAllElements();
        model.addAll(items);

        // Seleccionar por defecto el primero para evitar seleccion vacia
        model.setSelectedItem(marcas.get(0).getNombre());

        // Rellenar los combobox de la columna de "Marca" en la tabla.
        JComboBox comboMarca = new JComboBox();
        DefaultComboBoxModel marcaModel = (DefaultComboBoxModel) comboMarca.getModel();
        marcaModel.removeAllElements();
        marcaModel.addAll(items);
        comboMarca.setModel(marcaModel);

        // Obtener columna
        TableColumn columna = table.getColumnModel().getColumn(2);
        columna.setCellEditor(new DefaultCellEditor(comboMarca));
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.view.modelList) {
            this.listarModelos();

            this.view.modelDelete.setEnabled(true);
            this.view.modelSave.setEnabled(true);

            return;
        }

        if (e.getSource() == this.view.modelSave) {
            this.actualizarFilas();
            return;
        }

        if (e.getSource() == this.view.modelInsert) {
            this.insertarNuevoModelo();
            return;
        }

        if (e.getSource() == this.view.modelDelete) {
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

        this.view.modelDelete.setEnabled(true);
        this.view.modelSave.setEnabled(true);
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

    private Marca buscarMarcaPorNombre(String nombre) {
        Marca marcaDeModelo = null;
        for (Marca marca : this.marcas) {
            if (marca.getNombre().equals(nombre)) {
                marcaDeModelo = marca;
                break;
            }
        }

        return marcaDeModelo;
    }

    /**
     * Actualizar todos los cambios provistos en la tabla.
     */
    private void actualizarFilas() {
        List<Modelo> modelos = new ArrayList<>(), originales = this.repo.buscarTodos();

        for (int i = 0; i < model.getRowCount(); i++) {
            int id = Integer.parseInt(model.getValueAt(i, 0).toString());
            String nombre = model.getValueAt(i, 1).toString(), marcaString = model.getValueAt(i, 2).toString();
            int year = Integer.parseInt(model.getValueAt(i, 3).toString());

            if (ValidacionesHelper.validarStringLongitudSinNumeros(nombre)) {
                // Buscar marca del modelo
                Marca marcaDeModelo = buscarMarcaPorNombre(marcaString);

                Modelo modelo = new Modelo(id, marcaDeModelo, nombre, year);
                modelos.add(modelo);
            } else {
                Modelo original = originales.get(i);
                modelos.add(original);
                JOptionPane.showMessageDialog(null, String.format("La fila %s no se actualizará debido a que es inválida.", original.getNombre()));
            }
        }

        for (Modelo modelo : modelos) {
            this.repo.actualizar(modelo.getId(), modelo.getNombre(), modelo.getMarca().getId(), modelo.getYear());
        }

        this.rellenarTabla(modelos);
    }

    /**
     * Inserta un nuevo modelo en la base de datos.
     */
    private void insertarNuevoModelo() {
        String nombre = this.view.modelName.getText(), year = this.view.modelYear.getText(), marca = this.view.brandsComboBox.getSelectedItem().toString();

        // Si no son validos los valores, no dejarlo
        if (!ValidacionesHelper.validarStringLongitudSinNumeros(nombre) || !ValidacionesHelper.validarSoloNumerosFormatoAnio(year)) {
            JOptionPane.showMessageDialog(null, "Tienes errores en los campos, corrigelos y luego intenta insertar.");
            return;
        }

        // Buscar marca del modelo.
        Marca marcaDeModelo = buscarMarcaPorNombre(marca);

        Modelo model = new Modelo(marcaDeModelo, nombre, Integer.parseInt(year));

        this.repo.agregar(model);
        this.listarModelos();

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
