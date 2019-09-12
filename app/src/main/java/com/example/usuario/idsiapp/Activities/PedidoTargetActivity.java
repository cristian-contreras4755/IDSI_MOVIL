package com.example.usuario.idsiapp.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TableLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_target);
        sessionManager= new SessionManager(this);
        netWorkManager= new NetWorkManager(this);
       /// ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");

        UrlBase=netWorkManager.GetUrlBaseServices();
       // cod_pedido=getIntent().getStringExtra("cod_pedido");

        ruc_empresa="20160000001";
        cod_pedido="OP00000002";

        RQue= Volley.newRequestQueue(getApplicationContext());

        ConsultaStock();
        ConsultaStock();

    }



    public void ConsultaStock() {

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




/*



        "pedido": {

                    "txt_Tarped_cod_pedido": "OP00000002",
                    "txt_Tarped_nro_pedido": "OP00000002",
                    "txt_Tarped_vendedor": "Hidrogo Scipión, Pablo",
                    "txt_Tarped_cliente": "INVERSIONES TECNOLOGICAS JRG S.A.C",
                    "txt_Tarped_fecha": "15/01/2016",
                    "txt_Tarped_obs": "No se aceptan cambios despues de 1 semana",
                    "txt_Tarped_moneda": "Soles",
                    "txt_Tarped_simb_moneda": "S/",
                    "txt_Tarped_idestado_op": "01",
                    "txt_Tarped_estado_op": "Pendiente Entrega",
                    "txt_Tarped_fecha_entrega": "20/05/2016",
                    "txt_Tarped_fecha_vncto": null,
                    "txt_Tarped_cod_cotizacion": "CT00000003",
                    "txt_Tarped_valor_neto": "2542.3729000",
                    "txt_Tarped_base_imponible": "2542.3729000",
                    "txt_Tarped_igv": "457.6271220",
                    "txt_Tarped_total": "3000.0000220",


                    "detalle": [
            {
                "cod_prod": "PD00019",
                    "cod_corp": "LPHP03131",
                    "descripcion": "Laptop HP 440 G1",
                    "unidad_medida": "UNIDAD (BIENES)",
                    "unidad_medida_corto": "UND",
                    "valor_unitario": "2542.372900000",
                    "precio_unitario": "3000.0029000",
                    "cantidad": "1.000000000",
                    "pendiente_entrada": "1.000",
                    "valor": "2542.372900000",
                    "dscto_porcentual": null,
                    "dscto_importe": null,
                    "base_imponible": "2542.372900000",
                    "igv": "457.630000000",
                    "incluye_igv": "1",
                    "total": "3000.002900000",
                    "obs": null,
                    "cod_almacen": "A01"
            }
        ]
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

                           // GenerarTabla(listaProdStockAlm);
                            // comentado por mi

                        }catch (Exception ex) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"1error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("error:",ex.getMessage().toString());

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
