package com.example.usuario.idsiapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.usuario.idsiapp.Activities.ListaClienteFragment;
import com.example.usuario.idsiapp.Activities.PrincipalActivity;
import com.example.usuario.idsiapp.Activities.RegistrarPedidoFragment;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;


public class AdaptadorListaCliente  extends BaseAdapter implements Filterable {



    Context Contexto;
    List<Cliente> ListaCliente,ListaClienteTemp;
    FiltroAvanzado FA;

    public AdaptadorListaCliente(Context contexto, List<Cliente> listaCliente) {
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
        Vista=LI.inflate(R.layout.elemento_item_listacliente,null);


        TextView NomCliente=(TextView)Vista.findViewById(R.id.lbl_nomCli);
        TextView TipDocCliente=(TextView)Vista.findViewById(R.id.lbl_tipoDoc);
        TextView NroDocCliente=(TextView)Vista.findViewById(R.id.lbl_nrodocumento);
        TextView CodCliente=(TextView)Vista.findViewById(R.id.lbl_codigoCliente);

        LinearLayout ll_item=(LinearLayout)Vista.findViewById(R.id.ll_Item);



        NomCliente.setText(ListaCliente.get(position).getNombre_cliente());
        TipDocCliente.setText(ListaCliente.get(position).getNombre_corto_Tipo_doc());
        NroDocCliente.setText(ListaCliente.get(position).getNro_documento());
        CodCliente.setText(ListaCliente.get(position).getCod_cliente());



        /*

        Vista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(Contexto,objcli.getCod_cliente(), Toast.LENGTH_SHORT).show();

                Bundle  bundleEnvioRegistraPedido= new Bundle();
                bundleEnvioRegistraPedido.putString("nombre_cliente",objcli.getNombre_cliente());
                bundleEnvioRegistraPedido.putString("cod_cliente",objcli.getCod_cliente());


                RegistrarPedidoFragment registrarPedidoFragment= new RegistrarPedidoFragment();
                registrarPedidoFragment.setArguments(bundleEnvioRegistraPedido);


              //  FragmentManager manager = ge;;
               // manager.beginTransaction().replace(R.id.content_principal, registrarPedidoFragment).commit();
                //((PrincipalActivity)Contexto).onBackPressed();



            }
        });

        */




        return Vista;
    }



    //filtro cod 11-03-2018
    @Override
    public Filter getFilter() {


        if (FA==null){

            FA= new FiltroAvanzado();
        }

        return FA;
    }



    class  FiltroAvanzado extends Filter{

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
