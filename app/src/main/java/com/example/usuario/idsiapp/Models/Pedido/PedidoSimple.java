package com.example.usuario.idsiapp.Models.Pedido;

public class PedidoSimple {

    private String cod_pedido;
    private String nro_pedido;
    private String cliente;
    private String fecha_emision;
    private String total;
    private String estado;


    public PedidoSimple() {
    }

    public PedidoSimple(String cod_pedido, String nro_pedido, String cliente, String fecha_emision, String total, String estado) {
        this.cod_pedido = cod_pedido;
        this.nro_pedido = nro_pedido;
        this.cliente = cliente;
        this.fecha_emision = fecha_emision;
        this.total = total;
        this.estado = estado;
    }

    public String getCod_pedido() {
        return cod_pedido;
    }

    public String getNro_pedido() {
        return nro_pedido;
    }

    public String getCliente() {
        return cliente;
    }

    public String getFecha_emision() {
        return fecha_emision;
    }

    public String getTotal() {
        return total;
    }

    public String getEstado() {
        return estado;
    }

    // Setter Methods

    public void setCod_pedido(String cod_pedido) {
        this.cod_pedido = cod_pedido;
    }

    public void setNro_pedido(String nro_pedido) {
        this.nro_pedido = nro_pedido;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setFecha_emision(String fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }




}
