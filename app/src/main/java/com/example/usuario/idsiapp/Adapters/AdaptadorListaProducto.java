package com.example.usuario.idsiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.Producto;
import com.example.usuario.idsiapp.R;

import java.util.ArrayList;

public class AdaptadorListaProducto extends BaseAdapter implements Filterable {


    ArrayList<Producto> ListaProducto,ListaProductoTemp;
    Context Contexto;
    FiltroAvanzadoProductos FAP;


    public AdaptadorListaProducto(ArrayList<Producto> listaProducto, Context contexto) {
        ListaProducto = listaProducto;
        ListaProductoTemp= listaProducto;
        Contexto = contexto;
    }


    @Override
    public int getCount() {
        return ListaProducto.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaProducto.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        // final  Producto objprod=(Producto)getItem(position);

        View Vista=convertView;
        LayoutInflater LI= LayoutInflater.from(Contexto);
        Vista=LI.inflate(R.layout.elemento_item_listaproducto,null);

        TextView NomProd,valorVta,Dscto,Descripion,UnidMed,CodProd;

        NomProd=(TextView)Vista.findViewById(R.id.lbl_nomprod);
        valorVta=(TextView)Vista.findViewById(R.id.lbl_valorvta);
        Dscto=(TextView)Vista.findViewById(R.id.lbl_descto);
        Descripion=(TextView)Vista.findViewById(R.id.lbl_descripcion);
        UnidMed=(TextView)Vista.findViewById(R.id.lbl_unidMed);
        CodProd=(TextView)Vista.findViewById(R.id.lbl_codprod);


        NomProd.setText(ListaProducto.get(position).getNombre_producto1());
        //se cambio a valor de  venta
        valorVta.setText(ListaProducto.get(position).getValor_vta().toString());
        Dscto.setText(ListaProducto.get(position).getDescuento().toString());
        Descripion.setText(ListaProducto.get(position).getDescripcion());
        UnidMed.setText(ListaProducto.get(position).getNombre_unidad_medida());
        CodProd.setText(ListaProducto.get(position).getCod_producto());
        return  Vista;
    }





    //filtro de productos
    @Override
    public Filter getFilter() {
        if (FAP==null){

            FAP = new FiltroAvanzadoProductos();
        }

        return FAP;
    }


    class  FiltroAvanzadoProductos extends  Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            FilterResults Fr= new FilterResults();

            if (constraint!=null  && constraint.length()>0){

                constraint=constraint.toString().toUpperCase();
                ArrayList<Producto> filtro=new ArrayList<Producto>();


                for (int i = 0;i<ListaProductoTemp.size();i++){

                    if (ListaProductoTemp.get(i).getNombre_producto1().toUpperCase().contains(constraint)){

                        Producto producto= new Producto(ListaProductoTemp.get(i).getCod_producto(),ListaProductoTemp.get(i).getNombre_producto1(),ListaProductoTemp.get(i).getNombre_producto2(),ListaProductoTemp.get(i).getDescripcion(),ListaProductoTemp.get(i).getNombre_producto_corto(),ListaProductoTemp.get(i).getDescuento(),ListaProductoTemp.get(i).getCod_unidad_medida(),ListaProductoTemp.get(i).getNombre_unidad_medida(),ListaProductoTemp.get(i).getNombre_corto_unidad_medida(),ListaProductoTemp.get(i).getPrecio_vta(),ListaProductoTemp.get(i).getValor_vta());

                        filtro.add(producto);
                    }


                }
                Fr.count=filtro.size();
                Fr.values=filtro;

            }else {

                Fr.count=ListaProductoTemp.size();
                Fr.values=ListaProductoTemp;
            }

            return Fr;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ListaProducto=(ArrayList<Producto>)results.values;
            notifyDataSetChanged();
        }


    }



}
