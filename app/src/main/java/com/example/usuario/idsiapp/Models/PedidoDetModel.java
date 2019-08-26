package com.example.usuario.idsiapp.Models;

import java.util.Date;

public class PedidoDetModel {


    private int item;
    private String cod_producto;
    private String cod_servicio;
    private String descripcion;
    private int cod_unidad_medida;
    private double precio_unitario;
    private double cantidad;
    private double valor;
    private double DsctoP;
    private double DsctoI;
    private double bim;
    private double igv;
    private double total;
    private String observacion;
    private double valor_venta_unitario;
    private double TotalVtaSD;
    private double PrecioUnitSD;
    private double PendEnt;
    private String cod_almacen;
    private Date fecha_modificacion;
    private String usuario_modifica;
    private String campo_adicional_01;
    private String campo_adicional_02;
    private String campo_adicional_03;
    private String campo_adicional_04;
    private String campo_adicional_05;
    private String campo_adicional_06;
    private String campo_adicional_07;
    private String campo_adicional_08;
    private String campo_adicional_09;
    private String campo_adicional_10;
    private String cod_centro_costo;
    private String cod_centro_costo_sub;
    private String cod_centro_costo_sub_sub;

    public PedidoDetModel() {
    }

    public PedidoDetModel(int item, String cod_producto, String cod_servicio, String descripcion, int cod_unidad_medida, double precio_unitario, double cantidad, double valor, double dsctoP, double dsctoI, double bim, double igv, double total, String observacion, double valor_venta_unitario, double totalVtaSD, double precioUnitSD, double pendEnt, String cod_almacen, Date fecha_modificacion, String usuario_modifica, String campo_adicional_01, String campo_adicional_02, String campo_adicional_03, String campo_adicional_04, String campo_adicional_05, String campo_adicional_06, String campo_adicional_07, String campo_adicional_08, String campo_adicional_09, String campo_adicional_10, String cod_centro_costo, String cod_centro_costo_sub, String cod_centro_costo_sub_sub) {
        this.item = item;
        this.cod_producto = cod_producto;
        this.cod_servicio = cod_servicio;
        this.descripcion = descripcion;
        this.cod_unidad_medida = cod_unidad_medida;
        this.precio_unitario = precio_unitario;
        this.cantidad = cantidad;
        this.valor = valor;
        DsctoP = dsctoP;
        DsctoI = dsctoI;
        this.bim = bim;
        this.igv = igv;
        this.total = total;
        this.observacion = observacion;
        this.valor_venta_unitario = valor_venta_unitario;
        TotalVtaSD = totalVtaSD;
        PrecioUnitSD = precioUnitSD;
        PendEnt = pendEnt;
        this.cod_almacen = cod_almacen;
        this.fecha_modificacion = fecha_modificacion;
        this.usuario_modifica = usuario_modifica;
        this.campo_adicional_01 = campo_adicional_01;
        this.campo_adicional_02 = campo_adicional_02;
        this.campo_adicional_03 = campo_adicional_03;
        this.campo_adicional_04 = campo_adicional_04;
        this.campo_adicional_05 = campo_adicional_05;
        this.campo_adicional_06 = campo_adicional_06;
        this.campo_adicional_07 = campo_adicional_07;
        this.campo_adicional_08 = campo_adicional_08;
        this.campo_adicional_09 = campo_adicional_09;
        this.campo_adicional_10 = campo_adicional_10;
        this.cod_centro_costo = cod_centro_costo;
        this.cod_centro_costo_sub = cod_centro_costo_sub;
        this.cod_centro_costo_sub_sub = cod_centro_costo_sub_sub;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public String getCod_producto() {
        return cod_producto;
    }

    public void setCod_producto(String cod_producto) {
        this.cod_producto = cod_producto;
    }

    public String getCod_servicio() {
        return cod_servicio;
    }

    public void setCod_servicio(String cod_servicio) {
        this.cod_servicio = cod_servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCod_unidad_medida() {
        return cod_unidad_medida;
    }

    public void setCod_unidad_medida(int cod_unidad_medida) {
        this.cod_unidad_medida = cod_unidad_medida;
    }

    public double getPrecio_unitario() {
        return precio_unitario;
    }

    public void setPrecio_unitario(double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }

    public double getCantidad() {
        return cantidad;
    }

    public void setCantidad(double cantidad) {
        this.cantidad = cantidad;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public double getDsctoP() {
        return DsctoP;
    }

    public void setDsctoP(double dsctoP) {
        DsctoP = dsctoP;
    }

    public double getDsctoI() {
        return DsctoI;
    }

    public void setDsctoI(double dsctoI) {
        DsctoI = dsctoI;
    }

    public double getBim() {
        return bim;
    }

    public void setBim(double bim) {
        this.bim = bim;
    }

    public double getIgv() {
        return igv;
    }

    public void setIgv(double igv) {
        this.igv = igv;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getValor_venta_unitario() {
        return valor_venta_unitario;
    }

    public void setValor_venta_unitario(double valor_venta_unitario) {
        this.valor_venta_unitario = valor_venta_unitario;
    }

    public double getTotalVtaSD() {
        return TotalVtaSD;
    }

    public void setTotalVtaSD(double totalVtaSD) {
        TotalVtaSD = totalVtaSD;
    }

    public double getPrecioUnitSD() {
        return PrecioUnitSD;
    }

    public void setPrecioUnitSD(double precioUnitSD) {
        PrecioUnitSD = precioUnitSD;
    }

    public double getPendEnt() {
        return PendEnt;
    }

    public void setPendEnt(double pendEnt) {
        PendEnt = pendEnt;
    }

    public String getCod_almacen() {
        return cod_almacen;
    }

    public void setCod_almacen(String cod_almacen) {
        this.cod_almacen = cod_almacen;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public String getUsuario_modifica() {
        return usuario_modifica;
    }

    public void setUsuario_modifica(String usuario_modifica) {
        this.usuario_modifica = usuario_modifica;
    }

    public String getCampo_adicional_01() {
        return campo_adicional_01;
    }

    public void setCampo_adicional_01(String campo_adicional_01) {
        this.campo_adicional_01 = campo_adicional_01;
    }

    public String getCampo_adicional_02() {
        return campo_adicional_02;
    }

    public void setCampo_adicional_02(String campo_adicional_02) {
        this.campo_adicional_02 = campo_adicional_02;
    }

    public String getCampo_adicional_03() {
        return campo_adicional_03;
    }

    public void setCampo_adicional_03(String campo_adicional_03) {
        this.campo_adicional_03 = campo_adicional_03;
    }

    public String getCampo_adicional_04() {
        return campo_adicional_04;
    }

    public void setCampo_adicional_04(String campo_adicional_04) {
        this.campo_adicional_04 = campo_adicional_04;
    }

    public String getCampo_adicional_05() {
        return campo_adicional_05;
    }

    public void setCampo_adicional_05(String campo_adicional_05) {
        this.campo_adicional_05 = campo_adicional_05;
    }

    public String getCampo_adicional_06() {
        return campo_adicional_06;
    }

    public void setCampo_adicional_06(String campo_adicional_06) {
        this.campo_adicional_06 = campo_adicional_06;
    }

    public String getCampo_adicional_07() {
        return campo_adicional_07;
    }

    public void setCampo_adicional_07(String campo_adicional_07) {
        this.campo_adicional_07 = campo_adicional_07;
    }

    public String getCampo_adicional_08() {
        return campo_adicional_08;
    }

    public void setCampo_adicional_08(String campo_adicional_08) {
        this.campo_adicional_08 = campo_adicional_08;
    }

    public String getCampo_adicional_09() {
        return campo_adicional_09;
    }

    public void setCampo_adicional_09(String campo_adicional_09) {
        this.campo_adicional_09 = campo_adicional_09;
    }

    public String getCampo_adicional_10() {
        return campo_adicional_10;
    }

    public void setCampo_adicional_10(String campo_adicional_10) {
        this.campo_adicional_10 = campo_adicional_10;
    }

    public String getCod_centro_costo() {
        return cod_centro_costo;
    }

    public void setCod_centro_costo(String cod_centro_costo) {
        this.cod_centro_costo = cod_centro_costo;
    }

    public String getCod_centro_costo_sub() {
        return cod_centro_costo_sub;
    }

    public void setCod_centro_costo_sub(String cod_centro_costo_sub) {
        this.cod_centro_costo_sub = cod_centro_costo_sub;
    }

    public String getCod_centro_costo_sub_sub() {
        return cod_centro_costo_sub_sub;
    }

    public void setCod_centro_costo_sub_sub(String cod_centro_costo_sub_sub) {
        this.cod_centro_costo_sub_sub = cod_centro_costo_sub_sub;
    }

}
