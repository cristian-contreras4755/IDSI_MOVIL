package com.example.usuario.idsiapp.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.Pedido.Pedido;
import com.example.usuario.idsiapp.Models.Pedido.PedidoDet;
import com.example.usuario.idsiapp.Models.ProdStockAlm;
import com.example.usuario.idsiapp.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;

public class PedidoTargetActivity extends AppCompatActivity {

    private SessionManager sessionManager;
    private NetWorkManager netWorkManager;
    private String ruc_empresa,cod_pedido;
    private RequestQueue RQue;
    private ArrayList<ProdStockAlm> listaProdStockAlm;
    private Pedido  pedidoObj;

    private ProgressDialog progressDialog;
    private String UrlBase,UrlServices;
    private  TableLayout  Tbl_pedido_detalle;


    private TextView lbl_Tarped_cod_pedido;
    private TextView lbl_Tarped_nro_pedido;
    private TextView lbl_Tarped_cliente;
    private TextView lbl_Tarped_fecha;
    private TextView lbl_Tarped_obs;
    private TextView lbl_Tarped_estado_op;
    private TextView lbl_Tarped_fecha_entrega;
    private TextView lbl_Tarped_fecha_vncto;
    private TextView lbl_Tarped_cod_cotizacion;
    private TextView lbl_Tarped_base_imponible;
    private TextView lbl_Tarped_igv;
    private TextView lbl_Tarped_total;











    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_target);
        sessionManager= new SessionManager(this);
        netWorkManager= new NetWorkManager(this);
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");

        UrlBase=netWorkManager.GetUrlBaseServices();
        cod_pedido=getIntent().getStringExtra("cod_pedido");

        Tbl_pedido_detalle=(TableLayout)findViewById(R.id.Tbl_pedido_detalle) ;

        lbl_Tarped_cod_pedido=(TextView)findViewById(R.id.lbl_Tarped_cod_pedido);
        lbl_Tarped_nro_pedido=(TextView)findViewById(R.id.lbl_Tarped_nro_pedido);
        lbl_Tarped_cliente=(TextView)findViewById(R.id.lbl_Tarped_cliente);
        lbl_Tarped_fecha=(TextView)findViewById(R.id.lbl_Tarped_fecha);
        lbl_Tarped_obs=(TextView)findViewById(R.id.lbl_Tarped_obs);
        lbl_Tarped_estado_op=(TextView)findViewById(R.id.lbl_Tarped_estado_op);
        lbl_Tarped_fecha_entrega=(TextView)findViewById(R.id.lbl_Tarped_fecha_entrega);
        lbl_Tarped_fecha_vncto=(TextView)findViewById(R.id.lbl_Tarped_fecha_vncto);
        lbl_Tarped_cod_cotizacion=(TextView)findViewById(R.id.lbl_Tarped_cod_cotizacion);
        lbl_Tarped_base_imponible=(TextView)findViewById(R.id.lbl_Tarped_base_imponible);
        lbl_Tarped_igv=(TextView)findViewById(R.id.lbl_Tarped_igv);
        lbl_Tarped_total=(TextView)findViewById(R.id.lbl_Tarped_total);

      //  ruc_empresa="20160000001";
        //cod_pedido="OP00000002";

        RQue= Volley.newRequestQueue(getApplicationContext());
        ConsultaPedido();
    }



    public void ConsultaPedido() {

        if (ruc_empresa.isEmpty() ||  ruc_empresa==null){
            Toast.makeText(getApplicationContext(),"No puedimos encontrar el ruc de la empresa",Toast.LENGTH_LONG).show();
            return;
        }
/*
        if (!netWorkManager.Is_Online()){
            AlertaError("Validación de conexion de internet","Asegurese de  de  tener una conexion a internet.");
            return;
        }
*/



        UrlServices= UrlBase+"/Pedido/ObtenerPedido?ruc_empresa="+ruc_empresa+"&cod_pedido="+cod_pedido;
        AlertEspera("Espere","Cargando..");
        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                UrlServices,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            pedidoObj= new Pedido();
                            JSONObject Jobj= new JSONObject(response.toString());



                            pedidoObj.setCod_pedido(Jobj.getJSONObject("pedido").getString("cod_pedido"));
                            pedidoObj.setNro_pedido(Jobj.getJSONObject("pedido").getString("nro_pedido"));
                            pedidoObj.setVendedor(Jobj.getJSONObject("pedido").getString("vendedor"));
                            pedidoObj.setCliente(Jobj.getJSONObject("pedido").getString("cliente"));
                            pedidoObj.setFecha(Jobj.getJSONObject("pedido").getString("fecha"));
                            pedidoObj.setObs(Jobj.getJSONObject("pedido").getString("obs"));
                            pedidoObj.setMoneda(Jobj.getJSONObject("pedido").getString("moneda"));
                            pedidoObj.setSimb_moneda(Jobj.getJSONObject("pedido").getString("simb_moneda"));
                            pedidoObj.setIdestado_op(Jobj.getJSONObject("pedido").getString("idestado_op"));
                            pedidoObj.setEstado_op(Jobj.getJSONObject("pedido").getString("estado_op"));
                            pedidoObj.setFecha_entrega(Jobj.getJSONObject("pedido").getString("fecha_entrega"));
                            pedidoObj.setFecha_vncto(Jobj.getJSONObject("pedido").getString("fecha_vncto"));
                            pedidoObj.setCod_cotizacion(Jobj.getJSONObject("pedido").getString("cod_cotizacion"));
                            pedidoObj.setValor_neto(Jobj.getJSONObject("pedido").getString("valor_neto"));
                            pedidoObj.setBase_imponible(Jobj.getJSONObject("pedido").getString("base_imponible"));
                            pedidoObj.setIgv(Jobj.getJSONObject("pedido").getString("igv"));
                            pedidoObj.setTotal(Jobj.getJSONObject("pedido").getString("total"));


                            ArrayList <PedidoDet > detalle_Temp= new ArrayList<>();
                            JSONArray detalle=Jobj.getJSONObject("pedido").getJSONArray("detalle");

                            for ( int i=0;i<detalle.length();i++ ){
                                PedidoDet pedidoDet= new PedidoDet();
                                JSONObject  Objecto=detalle.getJSONObject(i);
                                pedidoDet.setCod_prod(Objecto.getString("cod_prod"));
                                pedidoDet.setCod_corp(Objecto.getString("cod_corp"));
                                pedidoDet.setDescripcion(Objecto.getString("descripcion"));
                                pedidoDet.setUnidad_medida(Objecto.getString("unidad_medida"));
                                pedidoDet.setUnidad_medida_corto(Objecto.getString("unidad_medida_corto"));
                                pedidoDet.setValor_unitario(Objecto.getString("valor_unitario"));
                                pedidoDet.setPrecio_unitario(Objecto.getString("precio_unitario"));
                                pedidoDet.setCantidad(Objecto.getString("cantidad"));
                                pedidoDet.setPendiente_entrada(Objecto.getString("pendiente_entrada"));
                                pedidoDet.setValor(Objecto.getString("valor"));
                                pedidoDet.setDscto_porcentual(Objecto.getString("dscto_porcentual"));
                                pedidoDet.setDscto_importe(Objecto.getString("dscto_importe"));
                                pedidoDet.setBase_imponible(Objecto.getString("base_imponible"));
                                pedidoDet.setIgv(Objecto.getString("igv"));
                                pedidoDet.setIncluye_igv(Objecto.getString("incluye_igv"));
                                pedidoDet.setTotal(Objecto.getString("total"));
                                pedidoDet.setObs(Objecto.getString("obs"));
                                pedidoDet.setCod_almacen(Objecto.getString("cod_almacen"));
                                detalle_Temp.add(pedidoDet);
                            }
                            pedidoObj.setDetalle(detalle_Temp);
                            GenerarTabla(pedidoObj);


                        }catch (Exception ex) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"1error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("error:",ex.getMessage().toString());


                        }


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                AlertaError("Listar stock productos.","por favor,  intentelo  dentro de unos minutos");
            }

        });
        RQue.add(Requeste);
    }




    public void GenerarTabla(Pedido pedido){

        try {


            TableRow tbrowTitulos = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("Item");
            tv0.setTextColor(Color.WHITE);
            tv0.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv0);


            TextView tv1 = new TextView(this);
            tv1.setText("Código");
            tv1.setTextColor(Color.WHITE);
            tv1.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv1);


            TextView tv2 = new TextView(this);
            tv2.setText("Nro.Pedido");
            tv2.setTextColor(Color.WHITE);
            tv2.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv2);



            TextView tv3 = new TextView(this);
            tv3.setText("Producto");
            tv3.setTextColor(Color.WHITE);
            tv3.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv3);


            TextView tv4 = new TextView(this);
            tv4.setText("U. Medida");
            tv4.setTextColor(Color.WHITE);
            tv4.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv4);


            TextView tv5 = new TextView(this);
            tv5.setText("Cantidad");
            tv5.setTextColor(Color.WHITE);
            tv5.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv5);

            TextView tv6 = new TextView(this);
            tv6.setText("Valor unitario");
            tv6.setTextColor(Color.WHITE);
            tv6.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv6);

            TextView tv7 = new TextView(this);
            tv7.setText("Precio Unitario");
            tv7.setTextColor(Color.WHITE);
            tv7.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv7);


            TextView tv8 = new TextView(this);
            tv8.setText("Descuento");
            tv8.setTextColor(Color.WHITE);
            tv8.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv8);



            TextView tv9 = new TextView(this);
            tv9.setText("Igv");
            tv9.setTextColor(Color.WHITE);
            tv9.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv9);


            TextView tv10 = new TextView(this);
            tv10.setText("Total");
            tv10.setTextColor(Color.WHITE);
            tv10.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv10);


            tbrowTitulos.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.FILL_PARENT));

            Tbl_pedido_detalle.addView(tbrowTitulos);



            for (int i=0;i<pedido.getDetalle().size();i++){

                View tableRow = LayoutInflater.from(this).inflate(R.layout.elemento_item_listadetpedtarget,null,false);

                TextView txt_item=(TextView)tableRow.findViewById(R.id.txt_item);
                TextView txt_cod_prod=(TextView)tableRow.findViewById(R.id.txt_cod_prod);
                TextView txt_Tarped_cod_corp=(TextView)tableRow.findViewById(R.id.txt_Tarped_cod_corp);
                TextView txt_Tarped_descripcion=(TextView)tableRow.findViewById(R.id.txt_Tarped_descripcion);
                TextView txt_Tarped_unidad_medida_corto=(TextView)tableRow.findViewById(R.id.txt_Tarped_unidad_medida_corto);
                TextView txt_Tarped_cantidad=(TextView)tableRow.findViewById(R.id.txt_Tarped_cantidad);
                TextView txt_Tarped_valor_unitario=(TextView)tableRow.findViewById(R.id.txt_Tarped_valor_unitario);
                TextView txt_Tarped_precio_unitario=(TextView)tableRow.findViewById(R.id.txt_Tarped_precio_unitario);
                TextView txt_Tarped_dscto=(TextView)tableRow.findViewById(R.id.txt_Tarped_dscto);
                TextView txt_Tarped_igv=(TextView)tableRow.findViewById(R.id.txt_Tarped_igv);
                TextView txt_Tarped_total=(TextView)tableRow.findViewById(R.id.txt_Tarped_total);


                txt_item.setText("1");
                txt_cod_prod.setText(pedido.getDetalle().get(i).getCod_prod());
                txt_Tarped_cod_corp.setText(pedido.getDetalle().get(i).getCod_corp());
                txt_Tarped_descripcion.setText(pedido.getDetalle().get(i).getDescripcion());
                txt_Tarped_unidad_medida_corto.setText(pedido.getDetalle().get(i).getUnidad_medida_corto());
                txt_Tarped_cantidad.setText(pedido.getDetalle().get(i).getCantidad());
                txt_Tarped_valor_unitario .setText(pedido.getDetalle().get(i).getValor_unitario());
                txt_Tarped_precio_unitario.setText(pedido.getDetalle().get(i).getPrecio_unitario());
                txt_Tarped_dscto.setText(pedido.getDetalle().get(i).getDscto_importe());
                txt_Tarped_igv.setText(pedido.getDetalle().get(i).getIgv());
                txt_Tarped_total.setText(pedido.getDetalle().get(i).getTotal());

                Tbl_pedido_detalle.addView(tableRow);
            }

            lbl_Tarped_cod_pedido.setText(pedido.getCod_pedido());
            lbl_Tarped_nro_pedido.setText(pedido.getNro_pedido());
            lbl_Tarped_cliente.setText(pedido.getCliente());
            lbl_Tarped_fecha.setText(pedido.getFecha());
            lbl_Tarped_obs.setText(pedido.getObs());
            lbl_Tarped_estado_op.setText(pedido.getEstado_op());
            lbl_Tarped_fecha_entrega.setText(pedido.getFecha_entrega());
            lbl_Tarped_fecha_vncto.setText(pedido.getFecha_vncto());
            lbl_Tarped_cod_cotizacion.setText(pedido.getCod_cotizacion());
            lbl_Tarped_base_imponible.setText(pedido.getBase_imponible());
            lbl_Tarped_igv.setText(pedido.getIgv());
            lbl_Tarped_total.setText(pedido.getTotal());


            progressDialog.dismiss();

        }catch (Exception ex){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }





    public void AlertEspera(String Titulo, String Mensaje){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher_logo);
        progressDialog.setTitle(Titulo);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Mensaje);
        progressDialog.show();
    }
    public void AlertaError(String Titulo,String Mensaje){

        AlertDialog.Builder Alert= new AlertDialog.Builder(this);
        Alert.setIcon(R.mipmap.ic_launcher_logo);
        Alert.setTitle(Titulo);
        Alert.setMessage(Mensaje);
        Alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                onBackPressed();
            }
        });
        AlertDialog alertDialog=Alert.create();
        alertDialog.show();
    }

}
