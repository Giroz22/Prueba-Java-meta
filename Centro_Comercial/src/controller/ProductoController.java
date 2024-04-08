package controller;

import entity.Producto;
import entity.Tienda;
import model.ProductoModel;

import javax.swing.*;
import java.util.List;

public class ProductoController extends BaseController<Producto>{

    final static ProductoModel objProductoModel = new ProductoModel();
    private final List<Tienda> listTiendas;

    public ProductoController() {
        super(ProductoController.objProductoModel);
        this.listTiendas = TiendaController.objTiendaModel.findAll();
    }

    @Override
    public Producto requestData(int id) {
        Producto objNewProducto =  null;

        try{
            //Se piden y validan los datos
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto: ");
            double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio del producto: "));
            if(precio < 0) throw new Exception("El precio debe ser mayor a 0");

            Tienda objTienda = (Tienda) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una tienda:",
                    "Opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listTiendas.toArray(),
                    listTiendas.getFirst()
            );

            int stock = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el stock del producto: "));
            if(stock < 0) throw new Exception("El stock debe ser mayor a 0");

            objNewProducto = new Producto(id, nombre, precio, objTienda.getId(), stock);

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error!! tipo de dato debe ser numérico","Error",JOptionPane.ERROR);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos:\n" + e.getMessage(),"Error",JOptionPane.ERROR);
            System.out.println(e.getMessage());
        }

        return objNewProducto;
    }

    @Override
    public Producto requestData(int id, Producto objOldProducto) {
        Producto objNewProducto =  null;

        try{
            //Se piden y validan los datos
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre del producto: ", objOldProducto.getNombre());
            double precio = Double.parseDouble(JOptionPane.showInputDialog(null, "Ingrese el precio del producto: ", objOldProducto.getPrecio()));
            if(precio < 0) throw new Exception("El precio debe ser mayor a 0");

            Tienda objTienda = (Tienda) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una tienda:",
                    "Opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listTiendas.toArray(),
                    listTiendas.stream().filter(tienda -> tienda.getId() == objOldProducto.getId_tienda())
            );

            int stock = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingrese el stock del producto: ", objOldProducto.getStock()));
            if(stock < 0) throw new Exception("El stock debe ser mayor a 0");

            objNewProducto = new Producto(id, nombre, precio, objTienda.getId(), stock);

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error!! tipo de dato debe ser numérico","Error",JOptionPane.ERROR);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos:\n" + e.getMessage(),"Error",JOptionPane.ERROR);
            System.out.println(e.getMessage());
        }

        return objNewProducto;
    }
}
