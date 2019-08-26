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

import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.R;

import java.util.ArrayList;
import java.util.List;

public class AdaptadorListaClienteTarget extends BaseAdapter  implements Filterable {

    Context Contexto;
    List<Cliente> ListaCliente,ListaClienteTemp;
    FiltroAvanzadocliente FAC;


    public AdaptadorListaClienteTarget(Context contexto, List<Cliente> listaCliente) {
        Contexto = contexto;
        ListaCliente = listaCliente;
        ListaClienteTemp=listaCliente;

    }



    @Override
    public int getCount() {

        int Cantidad=  ListaCliente.size();

        return Cantidad;
    }

    @Override
    public Object getItem(int position) {
        return ListaCliente.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {


        final  Cliente objcli=(Cliente)getItem(position);

        View Vista=convertView;
        LayoutInflater LI= LayoutInflater.from(Contexto);
        Vista=LI.inflate(R.layout.elemento_item_listaclientetarget,null);


        TextView NomCliente=(TextView)Vista.findViewById(R.id.lbl_nomCliTarget);
        TextView CodCliente=(TextView)Vista.findViewById(R.id.lbl_codigoClienteTarget);

       // LinearLayout ll_item=(LinearLayout)Vista.findViewById(R.id.ll_Item);

        NomCliente.setText(ListaCliente.get(position).getNombre_cliente());
        CodCliente.setText(ListaCliente.get(position).getNro_documento());
        return Vista;
    }




    @Override
    public Filter getFilter() {

        if (FAC == null) {

            FAC = new FiltroAvanzadocliente();
        }
        return FAC;
    }


    class   FiltroAvanzadocliente extends  Filter{


        @Override
        protected FilterResults performFiltering(CharSequence constraint) {


            FilterResults Fr= new FilterResults();

            if (constraint!=null  && constraint.length()>0){

                constraint=constraint.toString().toUpperCase();
                ArrayList<Cliente> filtro=new ArrayList<Cliente>();


                for (int i = 0;i<ListaClienteTemp.size();i++){

                    if (ListaClienteTemp.get(i).getNombre_cliente().toUpperCase().contains(constraint) ||ListaClienteTemp.get(i).getNro_documento().toUpperCase().contains(constraint)){

                        Cliente cliente= new Cliente("",ListaClienteTemp.get(i).getCod_cliente(),ListaClienteTemp.get(i).getCod_tipo_doc(),ListaClienteTemp.get(i).getNombre_corto_Tipo_doc(),ListaClienteTemp.get(i).getNro_documento(),ListaClienteTemp.get(i).getNombre_cliente(),ListaClienteTemp.get(i).getCod_pais(),ListaClienteTemp.get(i).getCod_postal(),"","","","","","","","",true,"","","","","","","","","","");

                        filtro.add(cliente);
                    }


                }
                Fr.count=filtro.size();
                Fr.values=filtro;

            }else {


                Fr.count=ListaClienteTemp.size();
                Fr.values=ListaClienteTemp;
            }


            return Fr;

        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

           ListaCliente =(ArrayList<Cliente>)results.values;
            notifyDataSetChanged();
        }
    }




}
