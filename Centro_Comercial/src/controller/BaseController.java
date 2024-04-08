package controller;

import model.BaseModel;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.lang.reflect.Method;
import java.util.function.IntFunction;

public abstract class  BaseController<T> {

    private final BaseModel<T> modelBase;

    public BaseController(BaseModel<T> modelBase) {
        this.modelBase = modelBase;
    }

    /***
     * Solicita los datos de una entidad y devuelve un objeto con estos
     * @param id ID del objeto
     * @return Objeto con toda la información ingresada por el usuario
     */
    public abstract T requestData(int id);
    /***
     * Rellena los campos, solicita los nuevos datos de una entidad y devuelve un objeto con estos
     * @param id ID del objeto
     * @param obj Objeto con la información que será actualizada para llenar los campos
     * @return Objeto con toda la información ingresada por el usuario
     */
    public abstract T requestData(int id, T obj);


    /***
     * Obtiene y lista toda la información de una entidad
     */
    public void getAll(){
        List<T> listObj = modelBase.findAll();
        if(listObj.isEmpty()){
            JOptionPane.showMessageDialog(null, "No se encontró información");
            return;
        };

        String strListObj = getAll(listObj);
        JOptionPane.showMessageDialog(null, "Datos encontrados:\n"+ strListObj);
    }

    /***
     * Obtiene y devuelve una cadena con toda la información de una entidad
     * @param listObj Lista con los objetos
     * @return cadena con toda la información de una entidad
     */
    public String getAll(List<T> listObj){
        String strListObj =  "";

        for( T obj: listObj){
            strListObj += obj.toString() + "\n";
        }

        return strListObj;
    }

    /***
     *  Pide el id de una entidad para buscar su información
     */
    public void getById(){
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Ingresa el id que desea buscar:"));

            T obj = modelBase.findById(id);

            if (obj == null) return;

            JOptionPane.showMessageDialog(null, "Info: \n" + obj.toString());
        }catch (NumberFormatException e){
            JOptionPane.showMessageDialog(null, "ID invalida");
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al buscar por id");
        }
    }

    /***
     * Pide la información de un nuevo objeto y la almacena en la DB
     */
    public void create(){
        T objSave = this.requestData(0);
        if(objSave == null) return;

        T objDB = this.modelBase.save(objSave);
        if(objDB == null) return;

        //Mostramos
        JOptionPane.showMessageDialog(null, "Se agregó correctamente!!\n" + objDB.toString());
    }

    /***
     * Pide la nueva información de un nuevo objeto y la almacena en la DB
     */
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
                //Actualizamos el objeto
                T obj = this.modelBase.update(objUpdated);
                if (obj != null) {
                    JOptionPane.showMessageDialog(null, "Se actualizo correctamente!!");
                }
            } else {
                JOptionPane.showMessageDialog(null, "Actualización cancelada");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al actualizar " + e.getMessage());
        }
    }

    /***
     * Eliminamos la información de un objeto
     */
    public void delete(){
        try {
            //Obtenemos el objeto que se eliminara
            T obj = this.selectObject();
            if (obj == null) return; //Validamos

            //Mostramos el mensaje de confirmación
            int isSure = JOptionPane.showConfirmDialog(null, "Esta seguro de eliminar?\n"
                    + obj.toString());

            if (isSure == 0) {

                // Invocamos el método getId() del objeto para obtener el ID
                Method getIdMethod = obj.getClass().getMethod("getId");
                int idObj = (int) getIdMethod.invoke(obj);

                boolean isDeleted = this.modelBase.delete(idObj);
                if (!isDeleted) return; // Validamos si se eliminó el objeto

                JOptionPane.showMessageDialog(null, "Se elimino correctamente");
            } else {
                JOptionPane.showMessageDialog(null, "Se cancelo la eliminación");
            }
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, "Error al eliminar" + e.getMessage() );
        }
    }

    /***
     * Obtenemos todos los objetos y los mostramos para que el usuario seleccione cuál eliminará
     * @return Objeto seleccionado por el usuario
     */
    public T selectObject(){
        T objSelected = null;

        try{
            Object[] listObj = this.modelBase.findAll().toArray();

            Object objUser = JOptionPane.showInputDialog(
                    null,
                    "Seleccione una opción:",
                    "Opciones",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    listObj,
                    listObj[0]
            );

            objSelected = ((T) objUser);
        }catch (Exception e) {
            System.err.println("Error al seleccionar" + e.getMessage());
        }

        return objSelected;
    }

}
