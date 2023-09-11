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
import modelo.Persona;
import modelo.PersonaEntity;
import vista.subViews.PersonaView;

/**
 * Controlador encargado de administrar acciones dentro de la vista de Persona
 * (Cliente y Tecnicos) en el ABM.
 *
 * @author mazal
 */
public class PersonaController implements ActionListener {

    // Vista principal que el controlador administra.
    private PersonaView view;

    // Tabla dentro de la vista.
    private JTable table;

    // Modelo de la tabla dentro de la vista.
    private DefaultTableModel model;

    // Repositorio de Personas.
    private PersonaEntity repo = new PersonaEntity();

    // Listado de marcas disponibles, actualizado en cada re-render.
    private List<Marca> marcas;

    public PersonaController(PersonaView view) {
        this.view = view;
        this.table = view.personaTable;
        this.model = (DefaultTableModel) this.table.getModel();

        this.initListeners();
        this.rellenarComboBoxes();
    }

    /**
     * Rellena todos los JComboBox presentes en la vista con los datos pertinentes.
     */
    public void rellenarComboBoxes() {
        Vector roles = new Vector() {
        };
        roles.add("Cliente");
        roles.add("Tecnico");

        // Rellenar los combobox de la columna de "Rol" en la tabla.
        JComboBox comboRol = new JComboBox();
        DefaultComboBoxModel rolModel = (DefaultComboBoxModel) comboRol.getModel();
        rolModel.removeAllElements();
        rolModel.addAll(roles);
        comboRol.setModel(rolModel);

        // Obtener columna
        TableColumn columna = table.getColumnModel().getColumn(4);
        columna.setCellEditor(new DefaultCellEditor(comboRol));
    }

    private void initListeners() {
        // Agregar escucha a los botones de listar, guardar cambios y insertar.
        this.view.personaList.addActionListener(this);
        this.view.personaSave.addActionListener(this);
        this.view.personaInsert.addActionListener(this);
        this.view.personaDelete.addActionListener(this);
    }

    @Override
    public void actionPerformed(final ActionEvent e) {
        if (e.getSource() == this.view.personaList) {
            this.listarPersonas();

            return;
        }

        if (e.getSource() == this.view.personaSave) {
            this.actualizarFilas();
            return;
        }

        if (e.getSource() == this.view.personaInsert) {
            this.insertarNuevaPersona();
            return;
        }

        if (e.getSource() == this.view.personaDelete) {
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
     * Borra las entradas de la tabla y rellena con nuevas personas.
     *
     * @param personas Personas a agregar
     */
    private void rellenarTabla(List<Persona> personas) {
        model.setRowCount(0);

        for (Persona persona : personas) {
            model.addRow(new Object[]{persona.getId(), persona.getNombre(), persona.getApellido(), persona.getDni(), persona.getRol() == 1 ? "Tecnico" : "Cliente"});
        }

        this.view.personaDelete.setEnabled(true);
        this.view.personaSave.setEnabled(true);
    }

    /**
     * Lista en la tabla todas las personas del dominio.
     */
    private void listarPersonas() {
        // Traer todas las marcas.
        List<Persona> personas = this.repo.buscarTodos();

        // Asignarlas a la tabla de la vista.
        this.rellenarTabla(personas);
    }

    /**
     * Actualizar todos los cambios provistos en la tabla.
     */
    private void actualizarFilas() {
        List<Persona> personas = new ArrayList<>(), originales = this.repo.buscarTodos();

        for (int i = 0; i < model.getRowCount(); i++) {
            int id = Integer.parseInt(model.getValueAt(i, 0).toString());
            String nombre = model.getValueAt(i, 1).toString(), apellido = model.getValueAt(i, 2).toString(),
                    dni = model.getValueAt(i, 3).toString(), rol = model.getValueAt(i, 4).toString();

            if (ValidacionesHelper.validarStringLongitudSinNumeros(nombre) && ValidacionesHelper.validarStringLongitudSinNumeros(apellido)
                    && ValidacionesHelper.validarDni(dni)) {
                int rolInt = rol == "Cliente" ? 0 : 1;

                Persona persona = new Persona(id, nombre, apellido, Integer.parseInt(dni), rolInt);
                personas.add(persona);
            } else {
                Persona original = originales.get(i);
                personas.add(original);
                JOptionPane.showMessageDialog(null, String.format("La fila %s no se actualizará debido a que es inválida.", original.getNombre()));
            }
        }

        for (Persona persona : personas) {
            this.repo.actualizar(persona.getId(), persona.getNombre(), persona.getApellido(), persona.getDni(), persona.getRol());
        }

        this.rellenarTabla(personas);
    }

    /**
     * Inserta una nueva persona en la base de datos.
     */
    private void insertarNuevaPersona() {
        String nombre = this.view.personName.getText(), apellido = this.view.personSurname.getText(),
                dni = this.view.personDni.getText(), rol = this.view.personRole.getSelectedItem().toString();

        // Si no son validos los valores, no dejarlo
        if (!ValidacionesHelper.validarStringLongitudSinNumeros(nombre)
                || !ValidacionesHelper.validarStringLongitudSinNumeros(apellido)
                || !ValidacionesHelper.validarDni(dni)) {
            JOptionPane.showMessageDialog(null, "Tienes errores en los campos, corrigelos y luego intenta insertar.");
            return;
        }

        int rolInt = rol == "Cliente" ? 0 : 1;
        Persona persona = new Persona(nombre, apellido, Integer.parseInt(dni), rolInt);

        this.repo.agregar(persona);
        this.listarPersonas();

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
