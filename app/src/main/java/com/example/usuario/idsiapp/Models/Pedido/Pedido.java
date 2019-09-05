package com.example.usuario.idsiapp.Models.Pedido;

import java.util.ArrayList;

public class Pedido {

    private String cod_pedido;
    private String nro_pedido;
    private String vendedor;
    private String cliente;
    private String fecha;
    private String obs;
    private String moneda;
    private String valor_neto;
    private String base_imponible;
    private String igv;
    private String total;
    private  ArrayList <PedidoDet > detalle ;

    public Pedido() {
    }

    public Pedido(String cod_pedido, String nro_pedido, String vendedor, String cliente, String fecha, String obs, String moneda, String valor_neto, String base_imponible, String igv, String total, ArrayList<PedidoDet> detalle) {
        this.cod_pedido = cod_pedido;
        this.nro_pedido = nro_pedido;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.fecha = fecha;
        this.obs = obs;
        this.moneda = moneda;
        this.valor_neto = valor_neto;
        this.base_imponible = base_imponible;
        this.igv = igv;
        this.total = total;
        this.detalle = detalle;
    }

    public String getCod_pedido() {
        return cod_pedido;
    }

    public void setCod_pedido(String cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public String getNro_pedido() {
        return nro_pedido;
    }

    public void setNro_pedido(String nro_pedido) {
        this.nro_pedido = nro_pedido;
    }

    public String getVendedor() {
        return vendedor;
    }

    public void setVendedor(String vendedor) {
        this.vendedor = vendedor;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getValor_neto() {
        return valor_neto;
    }

    public void setValor_neto(String valor_neto) {
        this.valor_neto = valor_neto;
    }

    public String getBase_imponible() {
        return base_imponible;
    }

    public void setBase_imponible(String base_imponible) {
        this.base_imponible = base_imponible;
    }

    public String getIgv() {
        return igv;
    }

    public void setIgv(String igv) {
        this.igv = igv;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public ArrayList<PedidoDet> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<PedidoDet> detalle) {
        this.detalle = detalle;
    }
}





