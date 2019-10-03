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
    private String simb_moneda;
    private String idestado_op;
    private String estado_op;
    private String fecha_entrega;
    private String fecha_vncto ;
    private String cod_cotizacion;
    private String valor_neto;
    private String base_imponible;
    private String igv;
    private String total;

    private  ArrayList <PedidoDet > detalle ;

    public Pedido() {
    }

    public Pedido(String cod_pedido, String nro_pedido, String vendedor, String cliente, String fecha, String obs, String moneda, String simb_moneda, String idestado_op, String estado_op, String fecha_entrega, String fecha_vncto, String cod_cotizacion, String valor_neto, String base_imponible, String igv, String total, ArrayList<PedidoDet> detalle) {
        this.cod_pedido = cod_pedido;
        this.nro_pedido = nro_pedido;
        this.vendedor = vendedor;
        this.cliente = cliente;
        this.fecha = fecha;
        this.obs = obs;
        this.moneda = moneda;
        this.simb_moneda = simb_moneda;
        this.idestado_op = idestado_op;
        this.estado_op = estado_op;
        this.fecha_entrega = fecha_entrega;
        this.fecha_vncto = fecha_vncto;
        this.cod_cotizacion = cod_cotizacion;
        this.valor_neto = valor_neto;
        this.base_imponible = base_imponible;
        this.igv = igv;
        this.total = total;
        this.detalle = detalle;
    }

    public String getSimb_moneda() {
        return simb_moneda;
    }

    public void setSimb_moneda(String simb_moneda) {
        this.simb_moneda = simb_moneda;
    }

    public String getIdestado_op() {
        return idestado_op;
    }

    public void setIdestado_op(String idestado_op) {
        this.idestado_op = idestado_op;
    }

    public String getEstado_op() {
        return estado_op;
    }

    public void setEstado_op(String estado_op) {
        this.estado_op = estado_op;
    }

    public String getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(String fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getFecha_vncto() {
        return fecha_vncto;
    }

    public void setFecha_vncto(String fecha_vncto) {
        //this.fecha_vncto = fecha_vncto;
        this.fecha_vncto = fecha_vncto=="null"?"***":fecha_vncto;
    }

    public String getCod_cotizacion() {
        return cod_cotizacion;
    }

    public void setCod_cotizacion(String cod_cotizacion) {
        //this.cod_cotizacion = cod_cotizacion;

        this.cod_cotizacion = cod_cotizacion=="null"?"No tiene cotización":cod_cotizacion;
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
       // this.obs = obs;
        this.obs = obs=="null"?"***":obs;
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





