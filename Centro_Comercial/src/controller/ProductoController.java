package controller;

import entity.Producto;
import model.ProductoModel;

public class ProductoController extends BaseController<Producto>{
    public ProductoController() {
        super(new ProductoModel());
    }

    @Override
    public Producto requestData(int id) {
        return null;
    }

    @Override
    public Producto requestData(int id, Producto obj) {
        return null;
    }
}
