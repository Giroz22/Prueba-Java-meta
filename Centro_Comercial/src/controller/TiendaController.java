package controller;

import entity.Tienda;
import model.TiendaModel;

public class TiendaController extends BaseController<Tienda>{

    public TiendaController() {
        super(new TiendaModel());
    }

    @Override
    public Tienda requestData(int id) {
        return null;
    }

    @Override
    public Tienda requestData(int id, Tienda obj) {
        return null;
    }
}
