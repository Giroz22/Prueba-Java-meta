package controller;

import entity.Compra;
import model.CompraModel;

public class CompraController extends BaseController<Compra>{
    public CompraController() {
        super(new CompraModel());
    }

    @Override
    public Compra requestData(int id) {
        return null;
    }

    @Override
    public Compra requestData(int id, Compra obj) {
        return null;
    }
}
