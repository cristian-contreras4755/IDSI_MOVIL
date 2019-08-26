package com.example.usuario.idsiapp.Adapters;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.ProductoStock;
import com.example.usuario.idsiapp.R;

import java.util.ArrayList;
import java.util.List;


public class AdaptadorListaInvStock extends
        RecyclerView.Adapter<AdaptadorListaInvStock.ViewHolderStockProd> {

    private ArrayList<ProductoStock> ListaproductoStocks;

    public AdaptadorListaInvStock(ArrayList<ProductoStock> listaproductoStocks) {
       this.ListaproductoStocks = listaproductoStocks;
    }



    @NonNull
    @Override
    public ViewHolderStockProd onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
       View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.elemento_item_lista_stock_producto, null, false);
        return  new ViewHolderStockProd(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderStockProd viewHolderStockProd, int i) {
        viewHolderStockProd.lbl_codprod.setText(ListaproductoStocks.get(i).getCod_producto());
        viewHolderStockProd.lbl_nomprod.setText(ListaproductoStocks.get(i).getNombre_prod());
        viewHolderStockProd.lbl_marca.setText(ListaproductoStocks.get(i).getNom_marca());
        viewHolderStockProd.lbl_stock.setText(ListaproductoStocks.get(i).getCantidad().toString());
    }

    @Override
    public int getItemCount() {
        return ListaproductoStocks.size();
    }


    public class ViewHolderStockProd extends RecyclerView.ViewHolder{

        TextView lbl_codprod,lbl_nomprod,lbl_marca,lbl_stock;

        public ViewHolderStockProd(@NonNull View itemView) {
            super(itemView);

            lbl_codprod=(TextView)itemView.findViewById(R.id.lbl_Itemcodprod);
            lbl_nomprod=(TextView)itemView.findViewById(R.id.lbl_Itemnomprod);
            lbl_marca=(TextView)itemView.findViewById(R.id.lbl_Itemmarca);
            lbl_stock=(TextView)itemView.findViewById(R.id.lbl_Itemstock);
        }
    }


}
