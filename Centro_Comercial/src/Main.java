import controller.*;
import entity.Producto;
import model.BaseModel;
import model.TiendaModel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        Main main = new Main();
        String opc = "";

        do{
            //limpiamos el objController
            BaseController<?> objController = null;

            opc = JOptionPane.showInputDialog(null, """
                    Select a option:
                    1. Producto 
                    2. Cliente 
                    3. Compra 
                    4. Tienda            
                    5. Salir
                    """);

            //En base a la opción seleccionada el objController toma un valor u otro
            switch (opc){
                case "1":
                    objController = new ProductoController();
                    break;
                case "2":
                    objController = new ClienteController();
                    break;
                case "3":
                    objController = new CompraController();
                    break;
                case "4":
                    objController = new TiendaController();
                    break;
                case "5":
                    continue;
                default:
                    JOptionPane.showMessageDialog(null, "Opción invalida");
                    continue;
            }

            main.menuCrud(objController);

        }while (!opc.equals("5"));
    }

    public void menuCrud(BaseController<?> objController){
        String opc="";
        do{
            opc = JOptionPane.showInputDialog(null, """
                    Select a option:
                    1. Listar todos
                    2. Buscar por ID
                    3. Crear
                    4. Actualizar
                    5. Eliminar
                    6. Volver
                    """);

            switch (opc){
                case "1":
                    objController.getAll();
                    break;
                case "2":
                    objController.getById();
                    break;
                case "3":
                    objController.create();
                    break;
                case "4":
                    objController.update();
                    break;
                case "5":
                    objController.delete();
                    break;
                case "6":
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Option invalid");
            }
        }while (!opc.equals("6"));
    }
}
