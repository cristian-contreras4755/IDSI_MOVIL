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
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");
        ruc_empresa="20160000001";
        UrlBase=netWorkManager.GetUrlBaseServices();
        cod_pedido=getIntent().getStringExtra("cod_pedido");
        RQue= Volley.newRequestQueue(getApplicationContext());

    }



    public void ConsultaStock() {

        if (ruc_empresa.isEmpty() ||  ruc_empresa==null){
            Toast.makeText(getApplicationContext(),"No puedimos encontrar el ruc de la empresa",Toast.LENGTH_LONG).show();
            return;
        }
/*
        if (!netWorkManager.Is_Online()){
            AlertaError("Validación de conexion de internet","Asegurese de  de  tener cuna conexion a internet.");
            return;
        }
*/

        UrlServices= UrlBase+"/Inventario/ObtenerStockProdxcod?ruc="+ruc_empresa+"&cd_prod="+"cod_prod";
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
                            pedidoObj.setCod_pedido(Jobj.get("cod_pedido").toString());
                            pedidoObj.setNro_pedido(Jobj.get("nro_pedido").toString());
                            pedidoObj.setVendedor(Jobj.get("vendedor").toString());
                            pedidoObj.setCliente(Jobj.get("cliente").toString());
                            pedidoObj.setFecha(Jobj.get("fecha").toString());
                            pedidoObj.setObs(Jobj.get("obs").toString());
                            pedidoObj.setMoneda(Jobj.get("moneda").toString());
                            pedidoObj.setValor_neto(Jobj.get("valor_neto").toString());
                            pedidoObj.setBase_imponible(Jobj.get("base_imponible").toString());
                            pedidoObj.setIgv(Jobj.get("igv").toString());
                            pedidoObj.setTotal(Jobj.get("total").toString());


                            ArrayList <PedidoDet > detalle_Temp= new ArrayList<>();
                            JSONArray detalle=Jobj.getJSONArray("detalle");
                            for ( int i=0;i<detalle.length();i++ ){
                                PedidoDet pedidoDet= new PedidoDet();
                                JSONObject  Objecto=detalle.getJSONObject(i);
                                pedidoDet.setCod_prod(Objecto.getString("cod_prod"));
                                pedidoDet.setCod_corp(Objecto.getString("cod_corp"));
                                pedidoDet.setDescripcion(Objecto.getString("descripcion"));
                                pedidoDet.setUnidad_medida(Objecto.getString("unidad_medida"));
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


                           // GenerarTabla(listaProdStockAlm);//comentado por mi

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
