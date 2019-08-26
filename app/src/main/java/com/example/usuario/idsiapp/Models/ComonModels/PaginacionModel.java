package com.example.usuario.idsiapp.Models.ComonModels;

public class PaginacionModel {

    private  int page_nro;
    private  int page_size;
    private  int page_count;
    private  int total;

    public PaginacionModel() {

    }

    public PaginacionModel(int page_nro, int page_size, int page_count, int total) {
        this.page_nro = page_nro;
        this.page_size = page_size;
        this.page_count = page_count;
        this.total = total;
    }

    public int getPage_nro() {
        return page_nro;
    }

    public void setPage_nro(int page_nro) {
        this.page_nro = page_nro;
    }

    public int getPage_size() {
        return page_size;
    }

    public void setPage_size(int page_size) {
        this.page_size = page_size;
    }

    public int getPage_count() {
        return page_count;
    }

    public void setPage_count(int page_count) {
        this.page_count = page_count;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
