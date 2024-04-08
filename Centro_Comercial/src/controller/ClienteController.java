package controller;

import entity.Cliente;
import model.ClienteModel;

import javax.swing.*;

public class ClienteController extends BaseController<Cliente>{

    static final ClienteModel objClienteModel = new ClienteModel();

    public ClienteController() {
        super(objClienteModel);
    }

    @Override
    public Cliente requestData(int id) {
        Cliente objCliente = null;
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del cliente");
            String apellido = JOptionPane.showInputDialog(null, "Ingrese el apellido del cliente");
            String email = JOptionPane.showInputDialog(null, "Ingrese el email del cliente");

            objCliente = new Cliente(id,nombre,apellido,email);
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos");
            System.out.println(e.getMessage());
        }

        return objCliente;
    }

    @Override
    public Cliente requestData(int id, Cliente oldObj) {
        Cliente objCliente = null;
        try {
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el  del cliente", oldObj.getNombre());
            String apellido = JOptionPane.showInputDialog(null, "Ingrese el  del cliente", oldObj.getApellido());
            String email = JOptionPane.showInputDialog(null, "Ingrese el  del cliente", oldObj.getEmail());

            objCliente = new Cliente(id,nombre,apellido,email);

        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos");
            System.out.println(e.getMessage());
        }

        return objCliente;
    }
}
