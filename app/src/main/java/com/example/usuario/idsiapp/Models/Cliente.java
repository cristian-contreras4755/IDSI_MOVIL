package com.example.usuario.idsiapp.Models;

public class Cliente {

    private String ruc_empresa;
    private String cod_cliente;
    private String cod_tipo_doc;
    private String nombre_corto_Tipo_doc;
    private String nro_documento;
    private String nombre_cliente;
    private String cod_pais ;
    private String cod_postal;
    private String ubigeo ;
    private String direccion ;
    private String telefono1 ;
    private String telefono2 ;
    private String fax ;
    private String correo ;
    private String pagina_web ;
    private String observacion ;
    private boolean estado;
    private String campo_adicional_01;
    private String campo_adicional_02 ;
    private String campo_adicional_03 ;
    private String campo_adicional_04 ;
    private String campo_adicional_05 ;
    private String campo_adicional_06 ;
    private String campo_adicional_07 ;
    private String campo_adicional_08 ;
    private String campo_adicional_09 ;
    private String campo_adicional_10 ;


    public Cliente(String ruc_empresa, String cod_cliente, String cod_tipo_doc, String nombre_corto_Tipo_doc, String nro_documento, String nombre_cliente, String cod_pais, String cod_postal, String ubigeo, String direccion, String telefono1, String telefono2, String fax, String correo, String pagina_web, String observacion, boolean estado, String campo_adicional_01, String campo_adicional_02, String campo_adicional_03, String campo_adicional_04, String campo_adicional_05, String campo_adicional_06, String campo_adicional_07, String campo_adicional_08, String campo_adicional_09, String campo_adicional_10) {

        this.ruc_empresa = ruc_empresa;
        this.cod_cliente = cod_cliente;
        this.cod_tipo_doc = cod_tipo_doc;
        this.nombre_corto_Tipo_doc = nombre_corto_Tipo_doc;
        this.nro_documento = nro_documento;
        this.nombre_cliente = nombre_cliente;
        this.cod_pais = cod_pais;
        this.cod_postal = cod_postal;
        this.ubigeo = ubigeo;
        this.direccion = direccion;
        this.telefono1 = telefono1;
        this.telefono2 = telefono2;
        this.fax = fax;
        this.correo = correo;
        this.pagina_web = pagina_web;
        this.observacion = observacion;
        this.estado = estado;
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
    }

    public Cliente() {
    }




    public String getRuc_empresa() {
        return ruc_empresa;
    }

    public void setRuc_empresa(String ruc_empresa) {
        this.ruc_empresa = ruc_empresa;
    }

    public String getCod_cliente() {
        return cod_cliente;
    }

    public void setCod_cliente(String cod_cliente) {
        this.cod_cliente = cod_cliente;
    }

    public String getCod_tipo_doc() {
        return cod_tipo_doc;
    }

    public void setCod_tipo_doc(String cod_tipo_doc) {
        this.cod_tipo_doc = cod_tipo_doc;
    }

    public String getNombre_corto_Tipo_doc() {
        return nombre_corto_Tipo_doc;
    }

    public void setNombre_corto_Tipo_doc(String nombre_corto_Tipo_doc) {
        this.nombre_corto_Tipo_doc = nombre_corto_Tipo_doc;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    public String getCod_pais() {
        return cod_pais;
    }

    public void setCod_pais(String cod_pais) {
        this.cod_pais = cod_pais;
    }

    public String getCod_postal() {
        return cod_postal;
    }

    public void setCod_postal(String cod_postal) {
        this.cod_postal = cod_postal;
    }

    public String getUbigeo() {
        return ubigeo;
    }

    public void setUbigeo(String ubigeo) {
        this.ubigeo = ubigeo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono1() {
        return telefono1;
    }

    public void setTelefono1(String telefono1) {
        this.telefono1 = telefono1;
    }

    public String getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(String telefono2) {
        this.telefono2 = telefono2;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPagina_web() {
        return pagina_web;
    }

    public void setPagina_web(String pagina_web) {
        this.pagina_web = pagina_web;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
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








}
