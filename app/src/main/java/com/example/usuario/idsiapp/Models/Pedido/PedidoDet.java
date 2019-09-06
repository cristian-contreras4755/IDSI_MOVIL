package com.example.usuario.idsiapp.Models.Pedido;

public class PedidoDet {

    private String cod_prod;
    private String cod_corp;
    private String descripcion;
    private String unidad_medida;
    private String unidad_medida_corto;
    private String valor_unitario;
    private String precio_unitario;
    private String cantidad;
    private String pendiente_entrada;
    private String valor;
    private String dscto_porcentual ;
    private String dscto_importe ;






    private String base_imponible;
    private String igv;
    private String incluye_igv;
    private String total;
    private String obs ;
    private String cod_almacen;

    public PedidoDet() {
    }

    public PedidoDet(String cod_prod, String cod_corp, String descripcion, String unidad_medida, String unidad_medida_corto, String valor_unitario, String precio_unitario, String cantidad, String pendiente_entrada, String valor, String dscto_porcentual, String dscto_importe, String base_imponible, String igv, String incluye_igv, String total, String obs, String cod_almacen) {
        this.cod_prod = cod_prod;
        this.cod_corp = cod_corp;
        this.descripcion = descripcion;
        this.unidad_medida = unidad_medida;
        this.unidad_medida_corto = unidad_medida_corto;
        this.valor_unitario = valor_unitario;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
        this.pendiente_entrada = pendiente_entrada;
        this.valor = valor;
        this.dscto_porcentual = dscto_porcentual;
        this.dscto_importe = dscto_importe;
        this.base_imponible = base_imponible;
        this.igv = igv;
        this.incluye_igv = incluye_igv;
        this.total = total;
        this.obs = obs;
        this.cod_almacen = cod_almacen;
    }

    public String getUnidad_medida_corto() {
        return unidad_medida_corto;
    }

    public void setUnidad_medida_corto(String unidad_medida_corto) {
        this.unidad_medida_corto = unidad_medida_corto;
    }


// Getter Methods

    public String getCod_prod() {
        return cod_prod;
    }

    public String getCod_corp() {
        return cod_corp;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getUnidad_medida() {
        return unidad_medida;
    }

    public String getValor_unitario() {
        return valor_unitario;
    }

    public String getPrecio_unitario() {
        return precio_unitario;
    }

    public String getCantidad() {
        return cantidad;
    }

    public String getPendiente_entrada() {
        return pendiente_entrada;
    }

    public String getValor() {
        return valor;
    }

    public String getDscto_porcentual() {
        return dscto_porcentual;
    }

    public String getDscto_importe() {
        return dscto_importe;
    }

    public String getBase_imponible() {
        return base_imponible;
    }

    public String getIgv() {
        return igv;
    }

    public String getIncluye_igv() {
        return incluye_igv;
    }

    public String getTotal() {
        return total;
    }

    public String getObs() {
        return obs;
    }

    public String getCod_almacen() {
        return cod_almacen;
    }

    // Setter Methods

    public void setCod_prod(String cod_prod) {
        this.cod_prod = cod_prod;
    }

    public void setCod_corp(String cod_corp) {
        this.cod_corp = cod_corp;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setUnidad_medida(String unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    public void setValor_unitario(String valor_unitario) {
        this.valor_unitario = valor_unitario;
    }

    public void setPrecio_unitario(String precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public void setPendiente_entrada(String pendiente_entrada) {
        this.pendiente_entrada = pendiente_entrada;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setDscto_porcentual(String dscto_porcentual) {
        this.dscto_porcentual = dscto_porcentual;
    }

    public void setDscto_importe(String dscto_importe) {
        this.dscto_importe = dscto_importe;
    }

    public void setBase_imponible(String base_imponible) {
        this.base_imponible = base_imponible;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public void setIncluye_igv(String incluye_igv) {
        this.incluye_igv = incluye_igv;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public void setCod_almacen(String cod_almacen) {
        this.cod_almacen = cod_almacen;
    }

}
