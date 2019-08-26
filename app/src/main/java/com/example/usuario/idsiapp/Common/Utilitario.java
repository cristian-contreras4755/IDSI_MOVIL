package com.example.usuario.idsiapp.Common;

public class Utilitario {

    public  Double FormateoDecimales(double Nro, int NroDecimales ){
        return Math.round(Nro*Math.pow(10,NroDecimales))/Math.pow(10,NroDecimales);
    }

}
