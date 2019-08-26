package com.example.usuario.idsiapp.Models;

public class Producto {


    private String cod_producto;
    private String nombre_producto1;
    private String nombre_producto2;
    private String descripcion;
    private String nombre_producto_corto ;
    private Double descuento ;
    private String cod_unidad_medida;
    private String nombre_unidad_medida;
    private String nombre_corto_unidad_medida;
    private Double precio_vta;
    private Double valor_vta;


    public Producto(String cod_producto, String nombre_producto1, String nombre_producto2, String descripcion, String nombre_producto_corto, Double descuento, String cod_unidad_medida, String nombre_unidad_medida, String nombre_corto_unidad_medida, Double precio_vta, Double valor_vta) {
        this.cod_producto = cod_producto;
        this.nombre_producto1 = nombre_producto1;
        this.nombre_producto2 = nombre_producto2;
        this.descripcion = descripcion;
        this.nombre_producto_corto = nombre_producto_corto;
        this.descuento = descuento;
        this.cod_unidad_medida = cod_unidad_medida;
        this.nombre_unidad_medida = nombre_unidad_medida;
        this.nombre_corto_unidad_medida = nombre_corto_unidad_medida;
        this.precio_vta = precio_vta;
        this.valor_vta = valor_vta;
    }

    public Producto() {
    }




    public Double getPrecio_vta() {
        return precio_vta;
    }

    public void setPrecio_vta(Double precio_vta) {
        this.precio_vta = precio_vta;
    }

    public Double getValor_vta() {
        return valor_vta;
    }

    public void setValor_vta(Double valor_vta) {
        this.valor_vta = valor_vta;
    }


    public String getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(String cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getNombre_producto1() {
        return nombre_producto1;
    }

    public void setNombre_producto1(String nombre_producto1) {
        this.nombre_producto1 = nombre_producto1;
    }

    public String getNombre_producto2() {
        return nombre_producto2;
    }

    public void setNombre_producto2(String nombre_producto2) {
        this.nombre_producto2 = nombre_producto2;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre_producto_corto() {
        return nombre_producto_corto;
    }

    public void setNombre_producto_corto(String nombre_producto_corto) {
        this.nombre_producto_corto = nombre_producto_corto;
    }

    public Double getDescuento() {
        return descuento;
    }

    public void setDescuento(Double descuento) {
        this.descuento = descuento;
    }

    public String getCod_unidad_medida() {
        return cod_unidad_medida;
    }

    public void setCod_unidad_medida(String cod_unidad_medida) {
        this.cod_unidad_medida = cod_unidad_medida;
    }

    public String getNombre_unidad_medida() {
        return nombre_unidad_medida;
    }

    public void setNombre_unidad_medida(String nombre_unidad_medida) {
        this.nombre_unidad_medida = nombre_unidad_medida;
    }

    public String getNombre_corto_unidad_medida() {
        return nombre_corto_unidad_medida;
    }

    public void setNombre_corto_unidad_medida(String nombre_corto_unidad_medida) {
        this.nombre_corto_unidad_medida = nombre_corto_unidad_medida;
    }




}
