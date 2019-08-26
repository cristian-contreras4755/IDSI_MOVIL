package com.example.usuario.idsiapp.Models;

public class ProductoStockSimple {

    private String cod_producto;
    private String cod_comercial;
    private String nombre_prod;
    private String stock;

    public ProductoStockSimple() {
    }

    public ProductoStockSimple(String cod_producto, String cod_comercial, String nombre_prod, String stock) {
        this.cod_producto = cod_producto;
        this.cod_comercial = cod_comercial;
        this.nombre_prod = nombre_prod;
        this.stock = stock;
    }

    public String getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(String cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getCod_comercial() {
        return cod_comercial;
    }

    public void setCod_comercial(String cod_comercial) {


        this.cod_comercial = cod_comercial=="null"?"***":cod_comercial;
    }

    public String getNombre_prod() {
        return nombre_prod;
    }

    public void setNombre_prod(String nombre_prod) {
        this.nombre_prod = nombre_prod;
    }

    public String getStock() {
        return stock;
    }

    public void setStock(String stock) {
        this.stock = stock;
    }
}
