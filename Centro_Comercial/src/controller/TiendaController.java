package controller;

import entity.Tienda;
import model.ProductoModel;
import model.TiendaModel;

import javax.swing.*;

public class TiendaController extends BaseController<Tienda>{

    final static TiendaModel objTiendaModel = new TiendaModel();


    public TiendaController() {
        super(objTiendaModel);
    }

    @Override
    public Tienda requestData(int id) {
        Tienda objNewTienda=null;

        try{
            //Se obtienen y validan los datos
            String nombre = JOptionPane.showInputDialog(null,  "Ingrese el nombre de la tienda:");
            
            int numTienda = Integer.parseInt(JOptionPane.showInputDialog(null,  "Ingrese el número de la tienda:"));
            if(numTienda < 100) throw new Exception("El número de la tienda debe ser mayor a 100");
            
            String ubicacion = "Local " + numTienda;

            //Se asignan los datos obtenidos
            objNewTienda = new Tienda(id, nombre, ubicacion);
            
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error!! tipo de dato debe ser numérico","Error",JOptionPane.ERROR);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos:\n" + e.getMessage(),"Error",JOptionPane.ERROR);
            System.out.println(e.getMessage());
        }

        return objNewTienda;
    }

    @Override
    public Tienda requestData(int id, Tienda objOldTienda) {
        Tienda objNewTienda=null;

        try{
            //Se obtienen y validan los datos
            String nombre = JOptionPane.showInputDialog(null,  "Ingrese el nombre de la tienda:", objOldTienda.getNombre());

            int numTienda = Integer.parseInt(JOptionPane.showInputDialog(null,  "Ingrese el número de la tienda:", objOldTienda.getUbicacion().split(" ")[1]));
            if(numTienda < 100) throw new Exception("El número de la tienda debe ser mayor a 100");

            String ubicacion = "Local " + numTienda;

            //Se asignan los datos obtenidos
            objNewTienda = new Tienda(id, nombre, ubicacion);

        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "Error!! tipo de dato debe ser numérico","Error",JOptionPane.ERROR);
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al pedir los datos:\n" + e.getMessage(),"Error",JOptionPane.ERROR);
            System.out.println(e.getMessage());
        }

        return objNewTienda;
    }
}
