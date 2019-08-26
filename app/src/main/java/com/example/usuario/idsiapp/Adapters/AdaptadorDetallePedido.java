package com.example.usuario.idsiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.idsiapp.Activities.RegistrarPedidoActivity;
import com.example.usuario.idsiapp.Models.PedidoDetModel;
import com.example.usuario.idsiapp.R;

import java.util.ArrayList;

public class AdaptadorDetallePedido extends BaseAdapter {

    Context Contexto;
    ArrayList<PedidoDetModel> ListaDetallePedido;

    public AdaptadorDetallePedido(Context contexto, ArrayList<PedidoDetModel> listaDetallePedido) {
        Contexto = contexto;
        ListaDetallePedido = listaDetallePedido;
    }





    @Override
    public int getCount() {
        return ListaDetallePedido.size();
    }

    @Override
    public Object getItem(int position) {
        return ListaDetallePedido.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {


        final PedidoDetModel objPedDet=(PedidoDetModel)getItem(position);

        View Vista=convertView;
        LayoutInflater LI= LayoutInflater.from(Contexto);
        Vista=LI.inflate(R.layout.elemento_item_listadetallepedido,null);

        TextView NomProducto,Cantidad,PrecioUnitario,TotalItem;
     //   Button btn_elim_iten_det;

        NomProducto=(TextView) Vista.findViewById(R.id.lbl_nombreProducto);
        Cantidad=(TextView)Vista.findViewById(R.id.lbl_cantidadProducto);
        PrecioUnitario=(TextView)Vista.findViewById(R.id.lbl_precioproducto);
        TotalItem=(TextView)Vista.findViewById(R.id.lbl_Totalitem);

      //  btn_elim_iten_det=(Button) Vista.findViewById(R.id.btn_elim_iten_det);


        NomProducto.setText(ListaDetallePedido.get(position).getDescripcion());
        Cantidad.setText(String.valueOf(ListaDetallePedido.get(position).getCantidad()));
        PrecioUnitario.setText(String.valueOf(ListaDetallePedido.get(position).getPrecio_unitario()));
        TotalItem.setText(String.valueOf(ListaDetallePedido.get(position).getTotal()));


/*
        btn_elim_iten_det.setTag(position);

        btn_elim_iten_det.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

             //   Toast.makeText(Contexto,objPedDet.getDescripcion()+","+position, Toast.LENGTH_SHORT).show();


           // RegistrarPedidoActivity r= new RegistrarPedidoActivity();
               // r.RefrescarDetallePedido();

              //  ListaDetallePedido.get(position);
               // ListaDetallePedido.remove(position);

            }
        });

*/


        return Vista;
    }


}
