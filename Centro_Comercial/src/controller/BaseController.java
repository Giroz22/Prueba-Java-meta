package controller;

import model.BaseModel;

import javax.swing.*;
import java.util.List;
import java.lang.reflect.Method;

public abstract class  BaseController<T> {

    private final BaseModel<T> modelBase;

    public BaseController(BaseModel<T> modelBase) {
        this.modelBase = modelBase;
    }

    //Pide los datos de una entidad y devuelve un objeto con estos
    public abstract T requestData(int id);
    public abstract T requestData(int id, T obj);

    //Obtiene y lista toda la información de una entidad
    public void getAll(){
        List<T> listObj = modelBase.findAll();
        if(listObj.isEmpty()) return;

        String strListObj = getAll(listObj);
        JOptionPane.showMessageDialog(null, "List All:\n"+ strListObj);
    }

    //Obtiene y devuelve una cadena con toda la información de una entidad
    public String getAll(List<T> listObj){
        String strListObj =  "";

        for( T obj: listObj){
            strListObj += obj.toString() + "\n";
        }

        return strListObj;
    }

    // Pide el id de una entidad para buscar su información
    public void getById(){
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Write id to find: "));

            T obj = modelBase.findById(id);

            if (obj == null) return;

            JOptionPane.showMessageDialog(null, "Info: \n" + obj.toString());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "ID invalid");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error getId");
        }
    }

    //Pide la información de un nuevo objeto y la almacena en la DB
    public void create(){
        T objSave = this.requestData(0);
        if(objSave == null) return;

        T objDB = this.modelBase.save(objSave);
        if(objDB == null) return;

        //Mostramos
        JOptionPane.showMessageDialog(null, "Add successful\n" + objDB.toString());
    }
    //Pide la nueva información de un nuevo objeto y la almacena en la DB
    public void update(){
        try {
            // Obtenemos la info tanto del objeto nuevo como del antiguo
            T objOld = this.selectObject();
            if (objOld == null) return;

            // Invocamos el método getId() del objeto para obtener el ID
            Method getIdMethod = objOld.getClass().getMethod("getId");
            int idObj = (int) getIdMethod.invoke(objOld);

            //Pedimos la información
            T objUpdated = this.requestData(idObj, objOld);
            if (objUpdated == null) return;

            //Mostramos el mensaje de confirmación
            int isSure = JOptionPane.showConfirmDialog(null, "Are you sure of update?\n"
                    + "Old:\n" + objOld.toString() + "\n"
                    + "New:\n" + objUpdated.toString());

            if (isSure == 0) {
                //Actualizamos el objeto
                T obj = this.modelBase.update(objUpdated);
                if (obj != null) {
                    JOptionPane.showMessageDialog(null, "Successfully updated");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Update canceled");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error update " + e.getMessage());
        }
    }

    //Eliminamos la informacion de un objeto
    public void delete(){
        try {
            //Obtenemos el objeto que se eliminara
            T obj = this.selectObject();
            if (obj == null) return; //Validamos

            //Mostramos el mensaje de confirmación
            int isSure = JOptionPane.showConfirmDialog(null, "Are you sure of delete?\n"
                    + obj.toString());

            if (isSure == 0) {

                // Invocamos el método getId() del objeto para obtener el ID
                Method getIdMethod = obj.getClass().getMethod("getId");
                int idObj = (int) getIdMethod.invoke(obj);

                boolean isDeleted = this.modelBase.delete(idObj);
                if (!isDeleted) return; // Validamos si se eliminó el objeto

                JOptionPane.showMessageDialog(null, "Successfully deleted");
            } else {
                JOptionPane.showMessageDialog(null, "Update canceled");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error occurred while deleting" + e.getMessage() );
        }
    }

    //Obtenemos todos los objetos y los mostramos para que el usuario seleccione cuál eliminará
    public T selectObject(){
        T objSelected = null;

        try{
            T[] listObj = (T[]) this.modelBase.findAll().toArray();
            objSelected = (T) JOptionPane.showInputDialog(
                    null,
                    "Select an option:",
                    "Options",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listObj,
                    listObj[0]
            );
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error select an object");
        }

        return objSelected;
    }

}
