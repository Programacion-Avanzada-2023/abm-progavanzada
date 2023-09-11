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
import javax.swing.table.TableColumnModel;
import modelo.Automovil;
import modelo.AutomovilEntity;
import modelo.Modelo;
import modelo.ModeloEntity;
import modelo.Persona;
import modelo.PersonaEntity;
import vista.subViews.VehiculoView;

/**
 * Controlador encargado de administrar acciones dentro de la vista de Vehiculos
 * en el ABM.
 *
 * @author mazal
 */
public class VehiculoController implements ActionListener {

    // Vista principal que el controlador administra.
    private VehiculoView view;

    // Tabla dentro de la vista.
    private JTable table;

    // Modelo de la tabla dentro de la vista.
    private DefaultTableModel model;

    // Repositorio de Modelos.
    private ModeloEntity repoModelo = new ModeloEntity();

    // Repositorio de Personas.
    private PersonaEntity repoPersona = new PersonaEntity();

    // Repositorio de Vehiculos.
    private AutomovilEntity repo = new AutomovilEntity();

    // Listado de modelos disponibles, actualizado en cada re-render.
    private List<Modelo> modelos;

    // Listado de personas disponibles, actualizado en cada re-render.
    private List<Persona> personas;

    public VehiculoController(VehiculoView view) {
        this.view = view;
        this.table = view.vehicleTable;
        this.model = (DefaultTableModel) this.table.getModel();

        this.initListeners();
        this.rellenarComboBoxes();
    }

    private void initListeners() {
        // Agregar escucha a los botones de listar, guardar cambios y insertar.
        this.view.vehicleList.addActionListener(this);
        this.view.vehicleSave.addActionListener(this);
        this.view.vehicleInsert.addActionListener(this);
        this.view.vehicleDelete.addActionListener(this);
    }

    /**
     * Rellena todos los JComboBox presentes en la vista con los datos pertinentes.
     */
    public void rellenarComboBoxes() {
        // Buscar todos los modelos y personas disponibles en el contexto de la app.
        modelos = this.repoModelo.buscarTodos();
        personas = this.repoPersona.buscarTodos();

        // Transformar a vectores.
        Vector modeloItems = new Vector() {
        }, personaItems = new Vector() {
        };
        for (Modelo modelo : modelos) {
            modeloItems.add(modelo.getModeloyMarca());
        }
        for (Persona persona : personas) {
            personaItems.add(persona.getNombreyApellido());
        }

        // Cambiar el modelo del ComboBox en la lista de campos, agregando los items de cada uno.
        DefaultComboBoxModel modelsModel = (DefaultComboBoxModel) this.view.modelsComboBox.getModel(),
                personaModel = (DefaultComboBoxModel) this.view.clientsComboBox.getModel();
        modelsModel.removeAllElements();
        personaModel.removeAllElements();
        modelsModel.addAll(modeloItems);
        personaModel.addAll(personaItems);

        // Seleccionar por defecto el primero para evitar seleccion vacia
        modelsModel.setSelectedItem(modelos.get(0).getModeloyMarca());
        personaModel.setSelectedItem(personas.get(0).getNombreyApellido());

        // Rellenar los combobox de la columna de "Modelo" en la tabla.
        JComboBox comboModelo = new JComboBox(), comboPersona = new JComboBox();
        DefaultComboBoxModel comboModeloModel = (DefaultComboBoxModel) comboModelo.getModel(),
                comboPersonaModel = (DefaultComboBoxModel) comboPersona.getModel();

        comboModeloModel.removeAllElements();
        comboModeloModel.addAll(modeloItems);
        comboModelo.setModel(comboModeloModel);

        comboPersonaModel.removeAllElements();
        comboPersonaModel.addAll(personaItems);
        comboPersona.setModel(comboPersonaModel);

        // Obtener columnas de interes.
        TableColumnModel modeloTabla = table.getColumnModel();
        TableColumn columnaModelo = modeloTabla.getColumn(1), columnaCliente = modeloTabla.getColumn(3);
        columnaModelo.setCellEditor(new DefaultCellEditor(comboModelo));
        columnaCliente.setCellEditor(new DefaultCellEditor(comboPersona));
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.view.vehicleList) {
            this.listarVehiculos();
            return;
        }

        if (e.getSource() == this.view.vehicleSave) {
            this.actualizarFilas();
            return;
        }

        if (e.getSource() == this.view.vehicleInsert) {
            this.insertarNuevoVehiculo();
            return;
        }

        if (e.getSource() == this.view.vehicleDelete) {
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
     * Borra las entradas de la tabla y rellena con nuevos vehiculos.
     *
     * @param automoviles Los automoviles a agregar
     */
    private void rellenarTabla(List<Automovil> automoviles) {
        model.setRowCount(0);

        for (Automovil automovil : automoviles) {
            model.addRow(new Object[]{automovil.getId(), automovil.getModelo().getModeloyMarca(), automovil.getPatente(), automovil.getCliente().getNombreyApellido()});
        }

        this.view.vehicleDelete.setEnabled(true);
        this.view.vehicleSave.setEnabled(true);
    }

    /**
     * Lista en la tabla todos los modelos del dominio.
     */
    private void listarVehiculos() {
        // Traer todas las marcas.
        List<Automovil> vehiculos = this.repo.buscarTodos();

        // Asignarlas a la tabla de la vista.
        this.rellenarTabla(vehiculos);
    }

    /**
     * Busca un modelo por su string formatteado de nombre + marca.
     * @param modeloYMarca
     * @return 
     */
    private Modelo buscarModeloPorNombreYMarca(String modeloYMarca) {
        for (Modelo modelo : modelos) {
            if (modelo.getModeloyMarca().equals(modeloYMarca)) {
                return modelo;
            }
        }
        return null;
    }

    /**
     * Busca a una persona por su nombre y apellido formatteados.
     * @param nombreYApellido
     * @return 
     */
    private Persona buscarPersonaPorNombreYApellido(String nombreYApellido) {
        for (Persona cliente : personas) {
            if (cliente.getNombreyApellido().equals(nombreYApellido)) {
                return cliente;
            }
        }
        return null;
    }

    /**
     * Actualizar todos los cambios provistos en la tabla.
     */
    private void actualizarFilas() {
        List<Automovil> automoviles = new ArrayList<>(), originales = this.repo.buscarTodos();

        for (int i = 0; i < model.getRowCount(); i++) {
            int id = Integer.parseInt(model.getValueAt(i, 0).toString());
            String modelo = model.getValueAt(i, 1).toString(), patente = model.getValueAt(i, 2).toString(),
                    cliente = model.getValueAt(i, 3).toString();

            if (ValidacionesHelper.validarFormatoPatente(patente)) {
                // Buscar modelo.
                Modelo modeloDeVehiculo = this.buscarModeloPorNombreYMarca(modelo);
                Persona clienteDeVehiculo = this.buscarPersonaPorNombreYApellido(cliente);

                Automovil automovil = new Automovil(id, modeloDeVehiculo, patente, clienteDeVehiculo);

                automoviles.add(automovil);
            } else {
                Automovil original = originales.get(i);
                automoviles.add(original);
                JOptionPane.showMessageDialog(null, String.format("La fila %s no se actualizará debido a que es inválida.", original.getPatente()));
            }
        }

        for (Automovil automovil : automoviles) {
            this.repo.actualizar(automovil.getId(), automovil.getModelo().getId(), automovil.getPatente(), automovil.getCliente().getId());
        }

        this.rellenarTabla(automoviles);
    }

    /**
     * Inserta un nuevo vehiculo en la base de datos.
     */
    private void insertarNuevoVehiculo() {
        String modelo = this.view.modelsComboBox.getSelectedItem().toString(),
                patente = this.view.vehicleDomain.getText(), cliente = this.view.clientsComboBox.getSelectedItem().toString();

        // Si no son validos los valores, no dejarlo
        if (!ValidacionesHelper.validarFormatoPatente(patente)) {
            JOptionPane.showMessageDialog(null, "Tienes errores en los campos, corrigelos y luego intenta insertar.");
            return;
        }

        // Buscar modelo y cliente.
        Modelo modeloDeVehiculo = this.buscarModeloPorNombreYMarca(modelo);
        Persona clienteDeVehiculo = this.buscarPersonaPorNombreYApellido(cliente);

        Automovil vehiculo = new Automovil(modeloDeVehiculo, patente, clienteDeVehiculo);

        this.repo.agregar(vehiculo);
        this.listarVehiculos();

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
