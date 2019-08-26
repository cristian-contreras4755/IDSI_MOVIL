package com.example.usuario.idsiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.usuario.idsiapp.Models.ProductoStock;
import com.example.usuario.idsiapp.R;
import java.util.ArrayList;
import java.util.List;

public class AdaptadorListaProdStck_lv  extends BaseAdapter implements Filterable {

    Context Contexto;
    List<ProductoStock> ListaprodStock,ListaprodStockTemp;
    FiltroAvanzadoLstInvProd FA;

    public AdaptadorListaProdStck_lv(Context contexto, List<ProductoStock> listaprodStock) {
        Contexto = contexto;
        ListaprodStock = listaprodStock;
        ListaprodStockTemp = listaprodStock;
    }


    @Override
    public int getCount() {
        int Cantidad=  ListaprodStock.size();
        return Cantidad;
    }

    @Override
    public Object getItem(int position) {

        return ListaprodStock.get(position);
    }




    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ProductoStock objProductoStock=(ProductoStock)getItem(position);


        View Vista=convertView;
        LayoutInflater LI= LayoutInflater.from(Contexto);
        Vista=LI.inflate(R.layout.elemento_item_lista_stock_producto,null);


        TextView   lbl_codprod=(TextView)Vista.findViewById(R.id.lbl_Itemcodprod);
        TextView  lbl_nomprod=(TextView)Vista.findViewById(R.id.lbl_Itemnomprod);
        TextView lbl_marca=(TextView)Vista.findViewById(R.id.lbl_Itemmarca);
        TextView lbl_stock=(TextView)Vista.findViewById(R.id.lbl_Itemstock);
        TextView lbl_codIntr=(TextView)Vista.findViewById(R.id.lbl_Itemcodinter);

      //  LinearLayout ll_item=(LinearLayout)Vista.findViewById(R.id.ll_item_linea);

        lbl_codprod.setText(ListaprodStock.get(position).getCod_producto());
        lbl_nomprod.setText(ListaprodStock.get(position).getNombre_prod());
        lbl_marca.setText(ListaprodStock.get(position).getNom_marca());
        lbl_stock.setText(ListaprodStock.get(position).getCantidad());
        lbl_codIntr.setText(ListaprodStock.get(position).getCod_interno());
        return  Vista;
    }



    // Implementacion de filtro
    @Override
    public Filter getFilter() {

        if (FA==null){

            FA= new FiltroAvanzadoLstInvProd();
        }

        return FA;
    }


    class FiltroAvanzadoLstInvProd extends Filter{

        FilterResults Fr= new FilterResults();
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {



            FilterResults Fr= new FilterResults();

            if (constraint!=null  && constraint.length()>0){

                constraint=constraint.toString().toUpperCase();
                ArrayList<ProductoStock> filtro=new ArrayList<ProductoStock>();


                for (int i = 0;i<ListaprodStockTemp.size();i++){

                    if (ListaprodStockTemp.get(i).getNombre_prod().toUpperCase().contains(constraint) ||ListaprodStockTemp.get(i).getCod_interno().toUpperCase().contains(constraint)||ListaprodStockTemp.get(i).getCod_producto().toUpperCase().contains(constraint)){
                        ProductoStock productoStock= new ProductoStock(ListaprodStockTemp.get(i).getCod_producto(),ListaprodStockTemp.get(i).getCod_interno(),ListaprodStockTemp.get(i).getNombre_prod(),"","","","","","","","","","","","");
                        filtro.add(productoStock);
                    }
                }
                Fr.count=filtro.size();
                Fr.values=filtro;

            }else {


                Fr.count=ListaprodStockTemp.size();
                Fr.values=ListaprodStockTemp;
            }


            return Fr;

        }


        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

                ListaprodStock =(ArrayList<ProductoStock>)results.values;
                notifyDataSetChanged();
        }

    }

}
