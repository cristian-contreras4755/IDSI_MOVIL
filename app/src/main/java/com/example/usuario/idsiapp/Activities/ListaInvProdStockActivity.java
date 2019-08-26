package com.example.usuario.idsiapp.Activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Adapters.AdaptadorListaProdStck_lv;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.ComonModels.PaginacionModel;
import com.example.usuario.idsiapp.Models.ProductoStock;
import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaInvProdStockActivity extends AppCompatActivity implements TextWatcher {

    private ListView lv_lst_prod_stock;
    private ArrayList<ProductoStock> listaStockProd;
    private AdaptadorListaProdStck_lv adaptadorListaProdStck_lv;
    private PaginacionModel paginacionModel;
    private SessionManager sessionManager;
    private NetWorkManager netWorkManager;
    private RequestQueue RQue;
    private ProgressDialog progressDialog;
    private String  ruc_empresa;
    private String UrlBase;
    private String UrlServices;
    private EditText txt_lst_buscar_prodstock;
    private ImageButton btn_pag_antlv,btn_pag_nextlv,btn_lst_buscar_prodstock;
    private TextView lbl_dato_paginacion;
    /**************************************/
    private int nro_pagina=1;
    private int tamanio_pagina=20;

    /***************************************/
    private int nro_pagina_default=1;
    private int tamanio_pagina_default=20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_inv_prod_stock);

        lv_lst_prod_stock=(ListView)findViewById(R.id.lv_lst_prod_stock);
        txt_lst_buscar_prodstock=(EditText) findViewById(R.id.txt_lst_buscar_prodstock);
        lbl_dato_paginacion=(TextView) findViewById(R.id.lbl_dato_paginacion);

        //*botones//
        btn_pag_antlv=(ImageButton) findViewById(R.id.btn_pag_antlv);
        btn_pag_nextlv=(ImageButton) findViewById(R.id.btn_pag_nextlv);
        btn_lst_buscar_prodstock=(ImageButton) findViewById(R.id.btn_lst_buscar_prodstock);

        //botones
        btn_pag_antlv.setEnabled(true);
        btn_pag_nextlv.setEnabled(true);


        sessionManager= new SessionManager(this);
        netWorkManager= new NetWorkManager(this);
      //  ruc_empresa="20160000001";//sessionManager.ObtenerRuc_Session("ruc_global");
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");

        UrlBase=netWorkManager.GetUrlBaseServices();
        RQue= Volley.newRequestQueue(getApplicationContext());

        txt_lst_buscar_prodstock.addTextChangedListener(this);
        ConsultaStock();
        lv_lst_prod_stock.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final ProductoStock objProductoStock=(ProductoStock)lv_lst_prod_stock.getItemAtPosition(position);

                if (objProductoStock.getCod_producto()!=null ){
                    Intent data = new Intent(getApplicationContext(),ProductoStockTargetActivity.class);
                    data.putExtra("cod_prod",objProductoStock.getCod_producto());
                    startActivity(data);
                }else {

                    AlertaError("Obtener codigo producto.","No se pudo obtener el codigo de producto");
                }
            }
        });



        btn_pag_antlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"anterior",Toast.LENGTH_SHORT).show();

                if (paginacionModel.getPage_nro()>1){
                    nro_pagina=paginacionModel.getPage_nro()-1;
                    tamanio_pagina=paginacionModel.getPage_size();

                    ConsultaStock();
                    SetPaginacion();
                    btn_pag_antlv.setEnabled(true);
                    btn_pag_nextlv.setEnabled(true);
                }else {

                    btn_pag_antlv.setEnabled(false);
                    btn_pag_nextlv.setEnabled(true);
                }

            }
        });

        btn_pag_nextlv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if ( paginacionModel.getPage_nro()<paginacionModel.getPage_count()){

                    nro_pagina=paginacionModel.getPage_nro()+1;
                    tamanio_pagina=paginacionModel.getPage_size();
                    //lv_lst_prod_stock.removeAllViews();
                    ConsultaStock();
                    SetPaginacion();

                    btn_pag_antlv.setEnabled(true);
                    btn_pag_nextlv.setEnabled(true);

                }else {

                    btn_pag_antlv.setEnabled(true);
                    btn_pag_nextlv.setEnabled(false);
                }



            }
        });

        btn_lst_buscar_prodstock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nro_pagina=1;
                tamanio_pagina=10;
                ConsultaStock();
                SetPaginacion();
            }
        });

    }


    public void ConsultaStock() {

        if (ruc_empresa.isEmpty() ||  ruc_empresa==null){
            Toast.makeText(getApplicationContext(),"No puedimos encontrar el ruc de la empresa",Toast.LENGTH_LONG).show();
            return;
        }

//http://idsierp.dyndns.org:5000/api/Inventario/ObtenerStockProductos2?RucE=20117779379&FecD=01-01-2019&FecH=01-01-2019&pageNro=1&pageSize=100

        if (txt_lst_buscar_prodstock.length()!=0){
            UrlServices=UrlBase+"/Inventario/ObtenerStockProductos2?RucE="+ruc_empresa+"&FecD=01-01-2010&FecH=01-01-2010&TextSearch="+txt_lst_buscar_prodstock.getText()+"&pageNro="+nro_pagina+"&pageSize="+tamanio_pagina;
        }else {
            UrlServices= UrlBase+"/Inventario/ObtenerStockProductos2?RucE="+ruc_empresa+"&FecD=01-01-2010&FecH=01-01-2010&pageNro="+nro_pagina+"&pageSize="+tamanio_pagina;
        }


        //txt_lst_buscar_prodstock.setText(UrlServices);

        AlertEspera("Espere","Cargando..");
        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                UrlServices,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            paginacionModel= new PaginacionModel();
                            listaStockProd= new ArrayList<>();

                            JSONObject Paginacion=response.getJSONObject("paginacion");
                            paginacionModel.setPage_nro(Paginacion.getInt("page_nro"));
                            paginacionModel.setPage_size(Paginacion.getInt("page_size"));
                            paginacionModel.setPage_count(Paginacion.getInt("page_count"));
                            paginacionModel.setTotal(Paginacion.getInt("total"));


                            //Toast.makeText(getApplicationContext(),"No prueba de  boton"+paginacionModel.getPage_size()+"-"+paginacionModel.getPage_count()+"-"+paginacionModel.getTotal(),Toast.LENGTH_LONG).show();


                            JSONArray datos=response.getJSONArray("inv_prod_stock");
                            for ( int i=0;i<datos.length();i++ ){
                                ProductoStock productoStock= new ProductoStock();
                                JSONObject  Objecto=datos.getJSONObject(i);
                                productoStock.setCod_producto(Objecto.getString("cod_producto"));
                                productoStock.setCod_interno(Objecto.getString("cod_interno"));
                                productoStock.setNombre_prod(Objecto.getString("nombre_prod"));
                                productoStock.setNom_corto_prod(Objecto.getString("nom_corto_prod"));
                                productoStock.setDesc_prod(Objecto.getString("desc_prod"));
                                productoStock.setNom_corto_marca(Objecto.getString("nom_corto_prod"));
                                productoStock.setCod_marca(Objecto.getString("cod_marca"));
                                productoStock.setNom_marca(Objecto.getString("nom_marca"));
                                productoStock.setNom_corto_marca(Objecto.getString("nom_corto_marca"));
                                productoStock.setCod_clase(Objecto.getString("cod_clase"));
                                productoStock.setNom_clase(Objecto.getString("nom_clase"));
                                productoStock.setNom_corto_clase(Objecto.getString("nom_corto_clase"));
                                productoStock.setCod_subclase(Objecto.getString("cod_subclase"));
                                productoStock.setNom_subclase(Objecto.getString("nom_subclase"));
                                productoStock.setNom_corto_subclase(Objecto.getString("nom_corto_subclase"));
                                productoStock.setCantidad(Objecto.getString("cantidad"));

                                listaStockProd.add(productoStock);
                            }
                            ListarStockProd(listaStockProd);
                            SetPaginacion();

                        }catch (Exception ex) {
                           // paginacionModel=null;
                            Toast.makeText(getApplicationContext(),"hola error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
                        }



                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                paginacionModel=null;
                AlertaError("Listar stock productos.",UrlServices+"por favor,  intentelo  dentro de unos minutos"+error.networkResponse+"-"+error.getMessage()+"-"+error.getLocalizedMessage()+"-"+error.getStackTrace());
            }


        });
        RQue.add(Requeste);
    }





    public  void ListarStockProd(ArrayList<ProductoStock>  ListaproductoStocks){

        adaptadorListaProdStck_lv=new AdaptadorListaProdStck_lv(getApplicationContext(),ListaproductoStocks);
        lv_lst_prod_stock.setAdapter(adaptadorListaProdStck_lv);
        progressDialog.dismiss();

        if (ListaproductoStocks.size()>0){
            txt_lst_buscar_prodstock.setEnabled(true);
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

    public void SetPaginacion(){

        String pag="Pag. "+paginacionModel.getPage_nro()+"/"+paginacionModel.getPage_count()+" - Reg. "+paginacionModel.getPage_size()+"/"+paginacionModel.getTotal()+"";
        lbl_dato_paginacion.setText(pag);

    }


    public  void Bloquearbotones(){


    }




    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if (s!=null || s!="" ){

            this.adaptadorListaProdStck_lv.getFilter().filter(s);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
