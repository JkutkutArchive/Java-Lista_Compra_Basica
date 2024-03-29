package dam.view;

import dam.control.Controller;
import dam.model.ListaCompra;
import dam.model.Producto;
import dam.model.UnidadesProducto;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;

public class View extends JFrame {
    private JPanel menu;
    private JPanel panelControl;
    private JLabel lvlProducto;
    private JLabel lvlCantidad;
    private JLabel lvlUnidad;

    private JTextField txtFProducto;
    private JSpinner spCantidad;
    private JComboBox cmbUnidad;
    private JButton btnAdd;
    private JButton btnDel;
    private JList listProductos;
    private JLabel lblError;

    private ListaCompra listaProductos;

    public View() {
        setTitle("Lista de la compra");
        setContentPane(menu);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();

        listaProductos = new ListaCompra();
    }

    private void initComponents() {
        // Units
        cmbUnidad.setModel(new DefaultComboBoxModel(UnidadesProducto.getUnidades()));
    }

    public void setControlador(Controller controller) {
        btnAdd.addActionListener(controller);
        btnDel.addActionListener(controller);
    }

    // GETTERS
    public JButton getBtnAdd() {
        return btnAdd;
    }

    public JButton getBtnDel() {
        return btnDel;
    }

    public JTextField getTxtFProducto() {
        return txtFProducto;
    }

    public JSpinner getSpCantidad() {
        return spCantidad;
    }

    public JComboBox getCmbUnidad() {
        return cmbUnidad;
    }

    // SETTERS

    /**
     * Muestra un mensaje de error en la la interfaz.
     *
     * @param error Mensaje de error.
     */
    public void setError(String error) {
        if (error == null || error.isEmpty())
            lblError.setText("");
        else
            lblError.setText("Error: " + error);
    }

    /**
     * Elimina la label de error.
     */
    public void clearError() {
        setError("");
    }

    /**
     * Resetea el formulario.
     */
    private void resetForm() {
        getTxtFProducto().setText("");
        getSpCantidad().setValue(0);
        // getCmbUnidad().setSelectedIndex(0);
    }

    /**
     * Añade un producto a la lista de productos si no existe ya.
     */
    public void addProduct() {
        String name = getTxtFProducto().getText();
        if (name.isEmpty()) {
            setError("Debe ingresar un nombre");
            return ;
        }
        int cant = (int) getSpCantidad().getValue();
        if (cant <= 0 || cant > 500) {
            setError("Debe ingresar una cantidad válida (entre 1 y 500)");
            return ;
        }
        String unidad = getCmbUnidad().getSelectedItem().toString();
        Producto p = new Producto(name, cant, unidad);
        if (listaProductos.contains(p)) {
            setError("El producto ya existe");
            return ;
        }
        listaProductos.addProducto(p);
        updateList();
        clearError();
        resetForm();
    }

    /**
     * Elimina un producto de la lista de productos.
     */
    public void removeProduct() {
        if (listProductos.getSelectedIndex() == -1) {
            setError("Debe seleccionar un producto para poder eliminarlo.");
            return;
        }
        listaProductos.removeProducto(listProductos.getSelectedIndex());
        updateList();
        clearError();
    }

    private void updateList() {
        listProductos.setListData(listaProductos.getProductos());
    }
}
