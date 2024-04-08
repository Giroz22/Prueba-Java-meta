package controller;

import entity.Cliente;
import entity.Compra;
import entity.Producto;
import entity.Tienda;
import model.CompraModel;

import javax.swing.*;
import java.util.List;

public class CompraController extends BaseController<Compra>{

    static final CompraModel objCompraModel = new CompraModel();
    private final List<Cliente> listClientes;
    private final List<Producto> listProductos;

    public CompraController() {
        super(CompraController.objCompraModel);
        this.listClientes = ClienteController.objClienteModel.findAll();
        this.listProductos = ProductoController.objProductoModel.findAll();
    }

    @Override
    public Compra requestData(int id) {
        Compra objCompra = null;

        try{
            //Se piden y validan los datos
            Cliente objCliente = (Cliente) JOptionPane.showInputDialog(
                    null,
                    "Seleccione cliente al que se le realizara la compra",
                    "Opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listClientes.toArray(),
                    listClientes.getFirst()
            );

            Producto objProducto = (Producto) JOptionPane.showInputDialog(
                    null,
                    "Seleccione una opción:",
                    "Seleccione el producto que desea comprar:",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listProductos.toArray(),
                    listProductos.getFirst()
            );

            int cantidad = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la cantidad de "+ objProducto.getNombre() +" que desea comprar:\nDisponible: " + objProducto.getStock()));
            if (cantidad < 0) throw new RuntimeException("La cantidad debe ser positiva");

            //Se Asignan los datos al objeto
            objCompra = new Compra(id,objCliente.getId(),objProducto.getId(),cantidad);

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error!! tipo de dato debe ser numérico","Error",JOptionPane.ERROR);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos:\n" + e.getMessage(),"Error",JOptionPane.ERROR);
            System.out.println(e.getMessage());
        }

        return objCompra;
    }

    @Override
    public Compra requestData(int id, Compra objoldCompra) {
        Compra objNewCompra = null;

        try{
            //Se piden y validan los datos
            Cliente objCliente = (Cliente) JOptionPane.showInputDialog(
                    null,
                    "Seleccione cliente al que se le realizara la compra",
                    "Opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listClientes.toArray(),
                    listClientes.stream().filter(cliente-> cliente.getId() == objoldCompra.getId_cliente()).toArray()[0]
            );

            Producto objProducto = (Producto) JOptionPane.showInputDialog(
                    null,
                    "Seleccione el producto que desea comprar:",
                    "Opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listProductos.toArray(),
                    listProductos.stream().filter(producto-> producto.getId() == objoldCompra.getId_producto()).toArray()[0]
            );

            int cantidad = Integer.parseInt(JOptionPane.showInputDialog(null,"Ingrese la cantidad de "+ objProducto.getNombre() +" que desea comprar:\nDisponible: " + objProducto.getStock(),objoldCompra.getCantidad()));
            if (cantidad < 0) throw new RuntimeException("La cantidad debe ser positiva");

            //Se Asignan los datos al objeto
            objNewCompra = new Compra(id, objCliente.getId(), objProducto.getId(),cantidad);

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error!! tipo de dato debe ser numérico","Error",JOptionPane.ERROR);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos","Error", JOptionPane.ERROR);
            System.out.println(e.getMessage());
        }

        return objNewCompra;
    }

    @Override
    public void create() {
        Compra objSave = this.requestData(0);
        if(objSave == null) return;

        //Se realiza la compra
        if(!realizarCompra(objSave.getCantidad(), objSave.getId_producto())) return;

        //Guardamos la compra
        Compra objCompraDB = CompraController.objCompraModel.save(objSave);
        if(objCompraDB == null) return;

        Producto objProducto = ProductoController.objProductoModel.findById(objCompraDB.getId_producto());
        Tienda objTienda = TiendaController.objTiendaModel.findById(objProducto.getId_tienda());
        Cliente objCliente = ClienteController.objClienteModel.findById(objCompraDB.getId_cliente());

        generarFactura(objCompraDB ,objProducto, objTienda, objCliente);
    }

    @Override
    public void update() {
        try {
            // Obtenemos la info tanto del objeto nuevo como del antiguo
            Compra objOld = this.selectObject();
            if (objOld == null) return;

            //Pedimos la información
            Compra objUpdated = this.requestData(objOld.getId(), objOld);
            if (objUpdated == null) return;

            //Validamos si los objetos tiene la misma información
            if(objOld.equals(objUpdated)){
                JOptionPane.showMessageDialog(null, "La información ingresada es igual a la existente");
                return;
            }

            //Mostramos el mensaje de confirmación
            int isSure = JOptionPane.showConfirmDialog(null, "Esta seguro de actualizar?\n"
                    + "Old:\n" + objOld.toString() + "\n"
                    + "New:\n" + objUpdated.toString());

            if (isSure == 0) {

                //Se realiza la compra
                int newCantidad = objUpdated.getCantidad() - objOld.getCantidad();
                if(!realizarCompra(newCantidad, objUpdated.getId_producto())) return;

                //Actualizamos el objeto
                Compra objCompraDB = CompraController.objCompraModel.update(objUpdated);

                if(objCompraDB == null) return;

                JOptionPane.showMessageDialog(null, "Se actualizo correctamente!!");

                Producto objProducto = ProductoController.objProductoModel.findById(objCompraDB.getId_producto());
                Tienda objTienda = TiendaController.objTiendaModel.findById(objProducto.getId_tienda());
                Cliente objCliente = ClienteController.objClienteModel.findById(objCompraDB.getId_cliente());

                generarFactura(objCompraDB ,objProducto, objTienda, objCliente);

            } else {
                JOptionPane.showMessageDialog(null, "Actualización cancelada");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al actualizar " + e.getMessage());
        }
    }

    @Override
    public String getAll(List<Compra> listCompras) {
        String strListObj =  "";

        for( Compra objCompra: listCompras){
            Cliente objCliente = ClienteController.objClienteModel.findById(objCompra.getId_cliente());
            Producto objProducto = ProductoController.objProductoModel.findById(objCompra.getId_producto());

            strListObj += objCompra.toString(objCliente,objProducto);
        }

        return strListObj;
    }

    private boolean realizarCompra(int cantidad, int idProducto){
        //Traemos el producto actual
        Producto objProducto = ProductoController.objProductoModel.findById(idProducto);

        int stockObj = objProducto.getStock();

        //Validamos si hay suficiente stock
        if (stockObj < cantidad){
            JOptionPane.showMessageDialog(null, "No hay suficiente stock del producto " + objProducto.getNombre() );
            return false;
        };

        //Restamos el stock
        objProducto.setStock(stockObj - cantidad);

        //Actualizamos la información
        ProductoController.objProductoModel.update(objProducto);
        JOptionPane.showMessageDialog(null,"Compra realizada correctamente!!");
        return true;
    }

    private void generarFactura(Compra objCompra,Producto objProducto, Tienda objTienda, Cliente objCliente){
        int cantidad = objCompra.getCantidad();
        double precioBase = objProducto.getPrecio();
        double precioTotal = precioBase * cantidad;
        double precioTotalIVA = precioTotal + (precioTotal * 0.19);

        JOptionPane.showMessageDialog(null,
                "===== Factura de Compra =====\n" +
                        "Producto: " + objProducto.getNombre() + "\n" +
                        "Precio: " + objProducto.getPrecio() + "\n" +
                        "Cantidad: " + objCompra.getCantidad() +
                        "\n\n" +
                        "Tienda: " +  objTienda.getNombre() + "\n" +
                        "Ubicación: " + objTienda.getUbicacion() +
                        "\n\n" +
                        "Cliente: " + objCliente.getNombre() + " " + objCliente.getApellido() + "\n"+
                        "Email: " + objCliente.getEmail() +
                        "\n\n" +
                        "Precio base: " + objProducto.getPrecio() + "\n" +
                        "Precio total: " + precioTotal + "\n"  +
                        "Precio total IVA: " + precioTotalIVA
                );
    }
}
