package com.example.usuario.idsiapp.Models;

import java.util.ArrayList;
import java.util.Date;

public class PedidoModel {



    private String ruc_empresa;
    private String nro_orden_pedido;
    private Date fecha_emision;
    private String cod_forma_pago;
    private String cod_vendedor;
    private String cod_area;
    private String cod_cte;
    private String dir_entrega;
    private String cod_cliente;
    private Date fecha_entrega;
    private String observacion;
    private double Valor_total;
    private double total_Dscto_p;
    private double total_Dscto_i;
    private double Valor_Neto;
    private double inf;
    private double dscto_fnz_inf_p;
    private double dscto_fnz_inf_i;
    private double inf_neto;
    private double dscto_Fnz_inafecto_p;
    private double dscto_Fnz_inafecto_i;
    private double bim;
    private double bin_neto;
    private double igv;
    private double total;
    private String cod_moneda;
    private double cambio_moneda;
    private String usuario_crea;
    private String cod_cotizacion;
    private int tipo_autorizacion;
    private String cod_centro_costo;
    private String cod_centro_costo_sub;
    private String cod_centro_costo_sub_sub;
    private Date fecha_vencimiento;
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
    private ArrayList< PedidoDetModel > detalle = new ArrayList <PedidoDetModel> ();

//constructores

    public PedidoModel() {
    }

    public PedidoModel(String ruc_empresa, String nro_orden_pedido, Date fecha_emision, String cod_forma_pago, String cod_vendedor, String cod_area, String cod_cte, String dir_entrega, String cod_cliente, Date fecha_entrega, String observacion, double valor_total, double total_Dscto_p, double total_Dscto_i, double valor_Neto, double inf, double dscto_fnz_inf_p, double dscto_fnz_inf_i, double inf_neto, double dscto_Fnz_inafecto_p, double dscto_Fnz_inafecto_i, double bim, double bin_neto, double igv, double total, String cod_moneda, double cambio_moneda, String usuario_crea, String cod_cotizacion, int tipo_autorizacion, String cod_centro_costo, String cod_centro_costo_sub, String cod_centro_costo_sub_sub, Date fecha_vencimiento, String campo_adicional_01, String campo_adicional_02, String campo_adicional_03, String campo_adicional_04, String campo_adicional_05, String campo_adicional_06, String campo_adicional_07, String campo_adicional_08, String campo_adicional_09, String campo_adicional_10, ArrayList<PedidoDetModel> detalle) {
        this.ruc_empresa = ruc_empresa;
        this.nro_orden_pedido = nro_orden_pedido;
        this.fecha_emision = fecha_emision;
        this.cod_forma_pago = cod_forma_pago;
        this.cod_vendedor = cod_vendedor;
        this.cod_area = cod_area;
        this.cod_cte = cod_cte;
        this.dir_entrega = dir_entrega;
        this.cod_cliente = cod_cliente;
        this.fecha_entrega = fecha_entrega;
        this.observacion = observacion;
        Valor_total = valor_total;
        this.total_Dscto_p = total_Dscto_p;
        this.total_Dscto_i = total_Dscto_i;
        Valor_Neto = valor_Neto;
        this.inf = inf;
        this.dscto_fnz_inf_p = dscto_fnz_inf_p;
        this.dscto_fnz_inf_i = dscto_fnz_inf_i;
        this.inf_neto = inf_neto;
        this.dscto_Fnz_inafecto_p = dscto_Fnz_inafecto_p;
        this.dscto_Fnz_inafecto_i = dscto_Fnz_inafecto_i;
        this.bim = bim;
        this.bin_neto = bin_neto;
        this.igv = igv;
        this.total = total;
        this.cod_moneda = cod_moneda;
        this.cambio_moneda = cambio_moneda;
        this.usuario_crea = usuario_crea;
        this.cod_cotizacion = cod_cotizacion;
        this.tipo_autorizacion = tipo_autorizacion;
        this.cod_centro_costo = cod_centro_costo;
        this.cod_centro_costo_sub = cod_centro_costo_sub;
        this.cod_centro_costo_sub_sub = cod_centro_costo_sub_sub;
        this.fecha_vencimiento = fecha_vencimiento;
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
        this.detalle = detalle;
    }

    public String getRuc_empresa() {
        return ruc_empresa;
    }

    public void setRuc_empresa(String ruc_empresa) {
        this.ruc_empresa = ruc_empresa;
    }

    public String getNro_orden_pedido() {
        return nro_orden_pedido;
    }

    public void setNro_orden_pedido(String nro_orden_pedido) {
        this.nro_orden_pedido = nro_orden_pedido;
    }

    public Date getFecha_emision() {
        return fecha_emision;
    }

    public void setFecha_emision(Date fecha_emision) {
        this.fecha_emision = fecha_emision;
    }

    public String getCod_forma_pago() {
        return cod_forma_pago;
    }

    public void setCod_forma_pago(String cod_forma_pago) {
        this.cod_forma_pago = cod_forma_pago;
    }

    public String getCod_vendedor() {
        return cod_vendedor;
    }

    public void setCod_vendedor(String cod_vendedor) {
        this.cod_vendedor = cod_vendedor;
    }

    public String getCod_area() {
        return cod_area;
    }

    public void setCod_area(String cod_area) {
        this.cod_area = cod_area;
    }

    public String getCod_cte() {
        return cod_cte;
    }

    public void setCod_cte(String cod_cte) {
        this.cod_cte = cod_cte;
    }

    public String getDir_entrega() {
        return dir_entrega;
    }

    public void setDir_entrega(String dir_entrega) {
        this.dir_entrega = dir_entrega;
    }

    public String getCod_cliente() {
        return cod_cliente;
    }

    public void setCod_cliente(String cod_cliente) {
        this.cod_cliente = cod_cliente;
    }

    public Date getFecha_entrega() {
        return fecha_entrega;
    }

    public void setFecha_entrega(Date fecha_entrega) {
        this.fecha_entrega = fecha_entrega;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public double getValor_total() {
        return Valor_total;
    }

    public void setValor_total(double valor_total) {
        Valor_total = valor_total;
    }

    public double getTotal_Dscto_p() {
        return total_Dscto_p;
    }

    public void setTotal_Dscto_p(double total_Dscto_p) {
        this.total_Dscto_p = total_Dscto_p;
    }

    public double getTotal_Dscto_i() {
        return total_Dscto_i;
    }

    public void setTotal_Dscto_i(double total_Dscto_i) {
        this.total_Dscto_i = total_Dscto_i;
    }

    public double getValor_Neto() {
        return Valor_Neto;
    }

    public void setValor_Neto(double valor_Neto) {
        Valor_Neto = valor_Neto;
    }

    public double getInf() {
        return inf;
    }

    public void setInf(double inf) {
        this.inf = inf;
    }

    public double getDscto_fnz_inf_p() {
        return dscto_fnz_inf_p;
    }

    public void setDscto_fnz_inf_p(double dscto_fnz_inf_p) {
        this.dscto_fnz_inf_p = dscto_fnz_inf_p;
    }

    public double getDscto_fnz_inf_i() {
        return dscto_fnz_inf_i;
    }

    public void setDscto_fnz_inf_i(double dscto_fnz_inf_i) {
        this.dscto_fnz_inf_i = dscto_fnz_inf_i;
    }

    public double getInf_neto() {
        return inf_neto;
    }

    public void setInf_neto(double inf_neto) {
        this.inf_neto = inf_neto;
    }

    public double getDscto_Fnz_inafecto_p() {
        return dscto_Fnz_inafecto_p;
    }

    public void setDscto_Fnz_inafecto_p(double dscto_Fnz_inafecto_p) {
        this.dscto_Fnz_inafecto_p = dscto_Fnz_inafecto_p;
    }

    public double getDscto_Fnz_inafecto_i() {
        return dscto_Fnz_inafecto_i;
    }

    public void setDscto_Fnz_inafecto_i(double dscto_Fnz_inafecto_i) {
        this.dscto_Fnz_inafecto_i = dscto_Fnz_inafecto_i;
    }

    public double getBim() {
        return bim;
    }

    public void setBim(double bim) {
        this.bim = bim;
    }

    public double getBin_neto() {
        return bin_neto;
    }

    public void setBin_neto(double bin_neto) {
        this.bin_neto = bin_neto;
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

    public String getCod_moneda() {
        return cod_moneda;
    }

    public void setCod_moneda(String cod_moneda) {
        this.cod_moneda = cod_moneda;
    }

    public double getCambio_moneda() {
        return cambio_moneda;
    }

    public void setCambio_moneda(double cambio_moneda) {
        this.cambio_moneda = cambio_moneda;
    }

    public String getUsuario_crea() {
        return usuario_crea;
    }

    public void setUsuario_crea(String usuario_crea) {
        this.usuario_crea = usuario_crea;
    }

    public String getCod_cotizacion() {
        return cod_cotizacion;
    }

    public void setCod_cotizacion(String cod_cotizacion) {
        this.cod_cotizacion = cod_cotizacion;
    }

    public int getTipo_autorizacion() {
        return tipo_autorizacion;
    }

    public void setTipo_autorizacion(int tipo_autorizacion) {
        this.tipo_autorizacion = tipo_autorizacion;
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

    public Date getFecha_vencimiento() {
        return fecha_vencimiento;
    }

    public void setFecha_vencimiento(Date fecha_vencimiento) {
        this.fecha_vencimiento = fecha_vencimiento;
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

    public ArrayList<PedidoDetModel> getDetalle() {
        return detalle;
    }

    public void setDetalle(ArrayList<PedidoDetModel> detalle) {
        this.detalle = detalle;
    }









}
