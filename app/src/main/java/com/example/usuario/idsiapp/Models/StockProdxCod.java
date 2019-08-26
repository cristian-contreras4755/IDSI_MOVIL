package com.example.usuario.idsiapp.Models;

import java.util.ArrayList;

public class StockProdxCod {

   private Byte[] img;
   private ArrayList<ProdStockAlm> ListaAlmacen;

    public StockProdxCod() {
    }

    public StockProdxCod(Byte[] img, ArrayList<ProdStockAlm> listaAlmacen) {
        this.img = img;
        ListaAlmacen = listaAlmacen;
    }

    public Byte[] getImg() {
        return img;
    }

    public void setImg(Byte[] img) {
        this.img = img;
    }

    public ArrayList<ProdStockAlm> getListaAlmacen() {
        return ListaAlmacen;
    }

    public void setListaAlmacen(ArrayList<ProdStockAlm> listaAlmacen) {
        ListaAlmacen = listaAlmacen;
    }
}
