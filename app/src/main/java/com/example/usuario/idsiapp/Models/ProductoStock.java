package com.example.usuario.idsiapp.Models;

public class ProductoStock {

    private String cod_producto;
    private String cod_interno;
    private String nombre_prod;
    private String desc_prod;
    private String nom_corto_prod;
    private String cod_marca;
    private String nom_marca;
    private String nom_corto_marca;
    private String cod_clase;
    private String nom_clase;
    private String nom_corto_clase;
    private String cod_subclase;
    private String nom_subclase;
    private String nom_corto_subclase;
    private String cantidad;

    public ProductoStock(String cod_producto, String cod_interno, String nombre_prod, String desc_prod, String nom_corto_prod, String cod_marca, String nom_marca, String nom_corto_marca, String cod_clase, String nom_clase, String nom_corto_clase, String cod_subclase, String nom_subclase, String nom_corto_subclase, String cantidad) {
        this.cod_producto = cod_producto;
        this.cod_interno = cod_interno;
        this.nombre_prod = nombre_prod;
        this.desc_prod = desc_prod;
        this.nom_corto_prod = nom_corto_prod;
        this.cod_marca = cod_marca;
        this.nom_marca = nom_marca;
        this.nom_corto_marca = nom_corto_marca;
        this.cod_clase = cod_clase;
        this.nom_clase = nom_clase;
        this.nom_corto_clase = nom_corto_clase;
        this.cod_subclase = cod_subclase;
        this.nom_subclase = nom_subclase;
        this.nom_corto_subclase = nom_corto_subclase;
        this.cantidad = cantidad;

    }

    public ProductoStock() {
    }


    public String getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(String cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getCod_interno() {

        return cod_interno;
    }

    public void setCod_interno(String cod_interno) {

        this.cod_interno =cod_interno=="null"?"-":cod_interno;
    }

    public String getNombre_prod() {
        return nombre_prod;
    }

    public void setNombre_prod(String nombre_prod) {
        this.nombre_prod = nombre_prod;
    }

    public String getDesc_prod() {
        return desc_prod;
    }

    public void setDesc_prod(String desc_prod) {
        this.desc_prod =  desc_prod=="null"?"-":desc_prod;
    }

    public String getNom_corto_prod() {
        return nom_corto_prod;
    }

    public void setNom_corto_prod(String nom_corto_prod) {
        this.nom_corto_prod = nom_corto_prod;
    }

    public String getCod_marca() {
        return cod_marca;
    }

    public void setCod_marca(String cod_marca) {
        this.cod_marca = cod_marca=="null"?"-":cod_marca;
    }

    public String getNom_marca() {
        return nom_marca;
    }

    public void setNom_marca(String nom_marca) {

        this.nom_marca = nom_marca=="null"?"-":nom_marca;
    }

    public String getNom_corto_marca() {
        return nom_corto_marca;
    }

    public void setNom_corto_marca(String nom_corto_marca) {
        this.nom_corto_marca = nom_corto_marca;
    }

    public String getCod_clase() {
        return cod_clase;
    }

    public void setCod_clase(String cod_clase) {
        this.cod_clase = cod_clase=="null"?"-":cod_clase;
    }

    public String getNom_clase() {
        return nom_clase;
    }

    public void setNom_clase(String nom_clase) {
        this.nom_clase = nom_clase=="null"?"-":nom_clase;
    }

    public String getNom_corto_clase() {
        return nom_corto_clase;
    }

    public void setNom_corto_clase(String nom_corto_clase) {
        this.nom_corto_clase = nom_corto_clase=="null"?"-":nom_corto_clase;
    }

    public String getCod_subclase() {
        return cod_subclase;
    }

    public void setCod_subclase(String cod_subclase) {
        this.cod_subclase = cod_subclase=="null"?"-":cod_subclase;
    }

    public String getNom_subclase() {
        return nom_subclase;
    }

    public void setNom_subclase(String nom_subclase) {
        this.nom_subclase = nom_subclase=="null"?"-":nom_subclase;
    }

    public String getNom_corto_subclase() {
        return nom_corto_subclase;
    }

    public void setNom_corto_subclase(String nom_corto_subclase) {
        this.nom_corto_subclase = nom_corto_subclase=="null"?"-":nom_corto_subclase;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

}
