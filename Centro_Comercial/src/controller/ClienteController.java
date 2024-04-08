package controller;

import entity.Cliente;
import model.ClienteModel;

public class ClienteController extends BaseController<Cliente>{
    public ClienteController() {
        super(new ClienteModel());
    }

    @Override
    public Cliente requestData(int id) {
        return null;
    }

    @Override
    public Cliente requestData(int id, Cliente obj) {
        return null;
    }
}
