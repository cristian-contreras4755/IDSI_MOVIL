package com.example.usuario.idsiapp.Models;
import com.google.firebase.firestore.GeoPoint;
import java.util.Date;

public class tb_geolocalizacion {
    private String id;
    private String cod_vendedor;
    private boolean estado;
    private Date fecha;
    private String tip_conexion;
    private String porcentaje_bateria;
    private String nombres;
    private GeoPoint posicion;
    private String ruc_empresa;
    private String usuario;
    private String telf_operadora;
    private String telf_nro;
    private String telf_imei;
    private String telf_marca;
    private String telf_version_so;

    public tb_geolocalizacion() {
    }

    public tb_geolocalizacion(String id, String cod_vendedor, boolean estado, Date fecha, String tip_conexion, String porcentaje_bateria, String nombres, GeoPoint posicion, String ruc_empresa, String usuario, String telf_operadora, String telf_nro, String telf_imei, String telf_marca, String telf_version_so) {
        this.id = id;
        this.cod_vendedor = cod_vendedor;
        this.estado = estado;
        this.fecha = fecha;
        this.tip_conexion = tip_conexion;
        this.porcentaje_bateria = porcentaje_bateria;
        this.nombres = nombres;
        this.posicion = posicion;
        this.ruc_empresa = ruc_empresa;
        this.usuario = usuario;
        this.telf_operadora = telf_operadora;
        this.telf_nro = telf_nro;
        this.telf_imei = telf_imei;
        this.telf_marca = telf_marca;
        this.telf_version_so = telf_version_so;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCod_vendedor() {
        return cod_vendedor;
    }

    public void setCod_vendedor(String cod_vendedor) {
        this.cod_vendedor = cod_vendedor;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTip_conexion() {
        return tip_conexion;
    }

    public void setTip_conexion(String tip_conexion) {
        this.tip_conexion = tip_conexion;
    }

    public String getPorcentaje_bateria() {
        return porcentaje_bateria;
    }

    public void setPorcentaje_bateria(String porcentaje_bateria) {
        this.porcentaje_bateria = porcentaje_bateria;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public GeoPoint getPosicion() {
        return posicion;
    }

    public void setPosicion(GeoPoint posicion) {
        this.posicion = posicion;
    }

    public String getRuc_empresa() {
        return ruc_empresa;
    }

    public void setRuc_empresa(String ruc_empresa) {
        this.ruc_empresa = ruc_empresa;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getTelf_operadora() {
        return telf_operadora;
    }

    public void setTelf_operadora(String telf_operadora) {
        this.telf_operadora = telf_operadora;
    }

    public String getTelf_nro() {
        return telf_nro;
    }

    public void setTelf_nro(String telf_nro) {
        this.telf_nro = telf_nro;
    }

    public String getTelf_imei() {
        return telf_imei;
    }

    public void setTelf_imei(String telf_imei) {
        this.telf_imei = telf_imei;
    }

    public String getTelf_marca() {
        return telf_marca;
    }

    public void setTelf_marca(String telf_marca) {
        this.telf_marca = telf_marca;
    }

    public String getTelf_version_so() {
        return telf_version_so;
    }

    public void setTelf_version_so(String telf_version_so) {
        this.telf_version_so = telf_version_so;
    }
}







