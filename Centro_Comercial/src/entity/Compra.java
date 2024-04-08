package entity;

import java.sql.Date;

public class Compra {
    //Atributos
    private int id;
    private int id_cliente;
    private int id_producto;
    private Date fecha_compra;
    private int cantidad;
    //Constructores

    public Compra() {
    }

    public Compra(int id, int id_cliente, int id_producto, Date fecha_compra, int cantidad) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_producto = id_producto;
        this.fecha_compra = fecha_compra;
        this.cantidad = cantidad;
    }
    //Getters y Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }

    public Date getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(Date fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    //ToString

    @Override
    public String toString() {
        return "Compra{" +
                "id=" + id +
                ", id_cliente=" + id_cliente +
                ", id_producto=" + id_producto +
                ", fecha_compra=" + fecha_compra +
                ", cantidad=" + cantidad +
                '}';
    }
}
