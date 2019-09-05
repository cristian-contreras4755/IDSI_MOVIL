package com.example.usuario.idsiapp.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.example.usuario.idsiapp.Models.ComonModels.PaginacionModel;
import com.example.usuario.idsiapp.Models.Pedido.Pedido;
import com.example.usuario.idsiapp.Models.Pedido.PedidoSimple;
import com.example.usuario.idsiapp.Models.ProductoStockSimple;

import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaPedidoActivity extends AppCompatActivity {


    private SessionManager sessionManager;
    private NetWorkManager netWorkManager;
    private String ruc_empresa;
    private String codvendedor;
    private RequestQueue RQue;
    private ProgressDialog progressDialog;
    private PaginacionModel paginacionModel;

    private ArrayList<PedidoSimple> ListaPedido;

    private String UrlServices;
    private String TextoBuscar;
    private String UrlBase;
    private ImageButton btn_ant,btn_next,btn_buscar_pedido;
    private  EditText  txt_buscar_pedido;
    private TextView lbl_paginacion_pedido;
    private Boolean buscar=false;
    private TableLayout TbL_pedido;

    //--------------------------
    private  int nro_pagina=1;
    private  int tamanio_pagina=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_pedido);
        sessionManager= new SessionManager(getApplicationContext());
        netWorkManager= new NetWorkManager(getApplicationContext());
        RQue= Volley.newRequestQueue(getApplicationContext());
        TbL_pedido=(TableLayout) findViewById(R.id.Tbl_Pedido);

        ruc_empresa= sessionManager.ObtenerRuc_Session("ruc_global");
        codvendedor= sessionManager.ObtenerCodVendedor_Session("codvendedor_global");
        // ruc_empresa=getIntent().getStringExtra("ruc_empresa"); recepcion de ruc por intent
        Toast.makeText(getApplicationContext(),"codvendedor :"+codvendedor,Toast.LENGTH_LONG).show();


        btn_ant=(ImageButton) findViewById(R.id.btn_ant_pedido);
        btn_next=(ImageButton) findViewById(R.id.btn_next_pedido);
        btn_buscar_pedido=(ImageButton) findViewById(R.id.btn_buscar_pedido);
        txt_buscar_pedido=(EditText) findViewById(R.id.txt_buscar_cliente_pedido);
        lbl_paginacion_pedido=(TextView) findViewById(R.id.lbl_paginacion_pedido);

    }








    public void ConsultaServicioPedido() {

        if (ruc_empresa.isEmpty() ||  ruc_empresa==null){
            Toast.makeText(getApplicationContext(),"No puedimos encontrar el ruc de la empresa",Toast.LENGTH_LONG).show();
            return;
        }

        //http://idsierp.dyndns.org:5000/api/Pedido/ObtenerPedidosVendedor?RucE=20160000001&FecD=10-01-2019&FecH=10-01-2019&Cd_Vdr=VND0002&pageNro=1&pageSize=20
//http://idsierp.dyndns.org:5000/api/Pedido/ObtenerPedidosVendedor?RucE=20160000001&FecD=10-01-2019&FecH=10-01-2019&Cd_Vdr=VND0002&TextSearch=barra&pageNro=1&pageSize=20
        if (buscar && TextoBuscar.length()!=0){
            UrlServices=UrlBase+"/Inventario/ObtenerStockProductos_simple?RucE="+ruc_empresa+"&FecD=01-01-2010&FecH=01-01-2010&TextSearch="+TextoBuscar+"&pageNro="+nro_pagina+"&pageSize="+tamanio_pagina;
        }else {
            UrlServices= UrlBase+"/Inventario/ObtenerStockProductos_simple?RucE="+ruc_empresa+"&FecD=01-01-2010&FecH=01-01-2010&pageNro="+nro_pagina+"&pageSize="+tamanio_pagina;
        }


        AlertEspera("Espere","Cargando..");
        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                UrlServices,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            paginacionModel= new PaginacionModel();
                            ListaPedido= new ArrayList<>();

                            JSONObject Paginacion=response.getJSONObject("paginacion");
                            paginacionModel.setPage_nro(Paginacion.getInt("page_nro"));
                            paginacionModel.setPage_size(Paginacion.getInt("page_size"));
                            paginacionModel.setPage_count(Paginacion.getInt("page_count"));
                            paginacionModel.setTotal(Paginacion.getInt("total"));


                            JSONArray datos=response.getJSONArray("pedidos");
                            for ( int i=0;i<datos.length();i++ ){
                                PedidoSimple pedidoSimple= new PedidoSimple();
                                JSONObject  Objecto=datos.getJSONObject(i);
                                pedidoSimple.setCod_pedido(Objecto.getString("cod_pedido"));
                                pedidoSimple.setNro_pedido(Objecto.getString("nro_pedido"));
                                pedidoSimple.setCliente(Objecto.getString("cliente"));
                                pedidoSimple.setFecha_emision(Objecto.getString("fecha_emision"));
                                pedidoSimple.setTotal(Objecto.getString("total"));
                                pedidoSimple.setEstado(Objecto.getString("estado"));
                                ListaPedido.add(pedidoSimple);

                            }


                            poblar(ListaPedido);
                            Set_Paginacion();

                        }catch (Exception ex) {

                            Toast.makeText(getApplicationContext(),"error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
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



    public void poblar(ArrayList<PedidoSimple> listaPedido){
        try {

            TableRow tbrowTitulos = new TableRow(this);

            TextView tv0 = new TextView(this);
            tv0.setText("Código");
            tv0.setTextColor(Color.WHITE);
            tv0.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv0);


            TextView tv1 = new TextView(this);
            tv1.setText("Nro. pedido");
            tv1.setTextColor(Color.WHITE);
            tv1.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv1);


            TextView tv2 = new TextView(this);
            tv2.setText("Cliente");
            tv2.setTextColor(Color.WHITE);
            tv2.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv2);




            TextView tv3 = new TextView(this);
            tv3.setText("Fecha");
            tv3.setTextColor(Color.WHITE);
            tv3.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv3);



            TextView tv4 = new TextView(this);
            tv4.setText("total");
            tv4.setTextColor(Color.WHITE);
            tv4.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv4);

            TextView tv5 = new TextView(this);
            tv5.setText("Estado");
            tv5.setTextColor(Color.WHITE);
            tv5.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv5);

            TextView tv16 = new TextView(this);
            tv16.setText("Ver");
            tv16.setTextColor(Color.WHITE);
            tv16.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv16);


            tbrowTitulos.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.FILL_PARENT));

            //TbL_Cabecera.addView(tbrowTitulos);

            TbL_pedido.addView(tbrowTitulos);


            for (int i=0;i<listaPedido.size();i++){


                View tableRow = LayoutInflater.from(this).inflate(R.layout.elemento_item_listastockprod,null,false);


                TextView txt_ped_cod_pedido=(TextView)tableRow.findViewById(R.id.txt_ped_cod_pedido);
                TextView txt_ped_nro_pedido=(TextView)tableRow.findViewById(R.id.txt_ped_nro_pedido);
                TextView txt_ped_cliente=(TextView)tableRow.findViewById(R.id.txt_ped_cliente);
                TextView txt_ped_fecha_emision=(TextView)tableRow.findViewById(R.id.txt_ped_fecha_emision);
                TextView txt_ped_total=(TextView)tableRow.findViewById(R.id.txt_ped_total);
                TextView txt_ped_estado=(TextView)tableRow.findViewById(R.id.txt_ped_estado);
                ImageButton btn_render_Pedidotarget=(ImageButton)tableRow.findViewById(R.id.btn_render_Pedidotarget);



                txt_ped_cod_pedido.setText(listaPedido.get(i).getCod_pedido());
                txt_ped_nro_pedido.setText(listaPedido.get(i).getNro_pedido());
                txt_ped_cliente.setText(listaPedido.get(i).getCliente());
                txt_ped_fecha_emision.setText(listaPedido.get(i).getFecha_emision());
                txt_ped_total.setText(listaPedido.get(i).getTotal());
                txt_ped_estado.setText(listaPedido.get(i).getEstado());

                btn_render_Pedidotarget.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        TableRow row =(TableRow)v.getParent();
                        TextView txt_codped =(TextView)row.getChildAt(0);

                        String cod_ped=(String) txt_codped.getText();


                        if (cod_ped!=null || cod_ped!="" ){
                            /*
                            Intent data = new Intent(getApplicationContext(),ProductoStockTargetActivity.class);
                            data.putExtra("cod_prod",cod_ped);
                            startActivity(data);
                            */

                        }else  {

                            return;
                        }

                    }
                });

                TbL_pedido.addView(tableRow);
            }
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

    public  void  Set_Paginacion(){
        String pag="";
        //validamos  paginacion
        if (paginacionModel.getTotal()<= paginacionModel.getPage_size()){

            pag="Pag. "+paginacionModel.getPage_nro()+"/"+paginacionModel.getPage_count()+" - Reg. "+paginacionModel.getTotal()+"/"+paginacionModel.getTotal()+"";

        }else  {

            pag="Pag. "+paginacionModel.getPage_nro()+"/"+paginacionModel.getPage_count()+" - Reg. "+paginacionModel.getPage_size()+"/"+paginacionModel.getTotal()+"";
        }

        lbl_paginacion_pedido.setText(pag);
    }





}
