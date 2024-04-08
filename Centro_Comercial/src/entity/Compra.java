package entity;

import java.sql.Timestamp;
import java.time.LocalDate;

public class Compra {
    //Atributos
    private int id;
    private int id_cliente;
    private int id_producto;
    private LocalDate fecha_compra;
    private int cantidad;
    //Constructores

    public Compra() {
    }

    public Compra(int id, int id_cliente, int id_producto, LocalDate fecha_compra, int cantidad) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_producto = id_producto;
        this.fecha_compra = fecha_compra;
        this.cantidad = cantidad;
    }

    public Compra(int id, int id_cliente, int id_producto, int cantidad) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_producto = id_producto;
        this.fecha_compra = null;
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

    public LocalDate getFecha_compra() {
        return fecha_compra;
    }

    public void setFecha_compra(LocalDate fecha_compra) {
        this.fecha_compra = fecha_compra;
    }

    public void setFecha_compra(Timestamp fecha_compra) {
        this.fecha_compra = fecha_compra.toLocalDateTime().toLocalDate();
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
