package com.example.usuario.idsiapp.Models;

import java.io.Serializable;

public class Empresa implements Serializable {

    private String ruc_empresa;
    private String razon_social;
    private String cod_moneda;

    public Empresa(String ruc_empresa, String razon_social, String cod_moneda) {
        this.ruc_empresa = ruc_empresa;
        this.razon_social = razon_social;
        this.cod_moneda = cod_moneda;
    }

    public Empresa() {
    }


    public String getRuc_empresa() {
        return ruc_empresa;
    }

    public void setRuc_empresa(String ruc_empresa) {
        this.ruc_empresa = ruc_empresa;
    }

    public String getRazon_social() {
        return razon_social;
    }

    public void setRazon_social(String razon_social) {
        this.razon_social = razon_social;
    }

    public String getCod_moneda() {
        return cod_moneda;
    }

    public void setCod_moneda(String cod_moneda) {
        this.cod_moneda = cod_moneda;
    }

}
