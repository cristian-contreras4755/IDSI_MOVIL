package com.example.usuario.idsiapp.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Adapters.AdaptadorListaInvStock;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.ComonModels.PaginacionModel;
import com.example.usuario.idsiapp.Models.ProductoStockSimple;
import com.example.usuario.idsiapp.R;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListaInvStockActivity extends AppCompatActivity {

    private PaginacionModel paginacionModel;
    private  AdaptadorListaInvStock adaptadorListaInvStock;
    private ArrayList<ProductoStockSimple> listaStockProd;
    private ArrayList<ProductoStockSimple> listaStockProd_Temp;
    private ArrayList<ProductoStockSimple> listaStockProd_BusquedaTemp;
    private SessionManager sessionManager;
    private NetWorkManager netWorkManager;
    private RequestQueue RQue;
    private ProgressDialog progressDialog;
    private String  ruc_empresa;
    private ImageButton btn_buscar_lst_producto_Stock,btn_ant,btn_next;
    private FloatingActionButton fab_menu ;

    private TextView lbl_text_paginacion;
    private EditText txt_buscar_lst_prod_stock;
    private String UrlBase;
    private TableLayout TbL_inv_listaProductosStock,TbL_Cabecera;
    private String UrlServices;
    private String TextoBuscar;
    private Boolean buscar=false;

    //--------------------------------------------//

    private  int nro_pagina=1;
    private  int tamanio_pagina=20;


   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_inv_stock);
         sessionManager= new SessionManager(this);
         netWorkManager= new NetWorkManager(this);
      // ruc_empresa="20117779379";
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");
        UrlBase=netWorkManager.GetUrlBaseServices();
        RQue= Volley.newRequestQueue(getApplicationContext());
       TbL_inv_listaProductosStock=(TableLayout) findViewById(R.id.TbL_inv_listaProductosStock);
       TbL_Cabecera=(TableLayout) findViewById(R.id.TbL_Cabecera);

       btn_buscar_lst_producto_Stock=(ImageButton) findViewById(R.id.btn_buscar_lst_producto_Stock);

       lbl_text_paginacion=(TextView) findViewById(R.id.lbl_text_paginacion);

       btn_ant=(ImageButton) findViewById(R.id.btn_ant);
       btn_next=(ImageButton) findViewById(R.id.btn_next);
       fab_menu=(FloatingActionButton)findViewById(R.id.fab_menu);

       txt_buscar_lst_prod_stock=(EditText) findViewById(R.id.txt_buscar_lst_prod_stock);

       ConsultaStock();
       btn_buscar_lst_producto_Stock.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               /*
               if (!(txt_buscar_lst_prod_stock.getText().length()>0)){
                   return;
               }
*/
               TbL_inv_listaProductosStock.removeAllViews();
               TextoBuscar=txt_buscar_lst_prod_stock.getText().toString();
               buscar=true;
               nro_pagina=1;
               tamanio_pagina=20;
               ConsultaStock();

            /*
               if(listaStockProd!=null){
                   Filtrar( listaStockProd,  txt_buscar_lst_prod_stock.getText().toString()); Filtrar( listaStockProd,  txt_buscar_lst_prod_stock.getText().toString());
               }
        */

           }
       });



               btn_ant.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                       if (paginacionModel!=null ){


                           if (paginacionModel.getPage_nro()>1){

                               nro_pagina=paginacionModel.getPage_nro()-1;
                               if (nro_pagina!=0){

                                   TbL_inv_listaProductosStock.removeAllViews();
                                   ConsultaStock();
                                   btn_next.setEnabled(true);


                               }else {

                                   btn_ant.setEnabled(false);
                                   btn_next.setEnabled(true);
                               }

                           }else{
                               btn_ant.setEnabled(false);
                           }
                       }




                   }
               });

               btn_next.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                       if (paginacionModel!=null ){


                           if (paginacionModel.getPage_count()>=nro_pagina){

                               nro_pagina=paginacionModel.getPage_nro()+1;

                               TbL_inv_listaProductosStock.removeAllViews();
                               ConsultaStock();
                           }else{
                               btn_next.setEnabled(false);
                               btn_ant.setEnabled(true);
                           }
                       }
                   }
               });




       fab_menu.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {

              AlertDialog.Builder Ab =new AlertDialog.Builder(ListaInvStockActivity.this);
              View vista=getLayoutInflater().inflate(R.layout.dialog_pagination,null);
             final   Spinner spiner1=(Spinner)vista.findViewById(R.id.spiner_cant_pag_dialog);


               Ab.setIcon(R.mipmap.ic_launcher_logo);
               Ab.setTitle("Configuración");
               Ab.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {

                       tamanio_pagina=Integer.parseInt(spiner1.getSelectedItem().toString());
                      // Toast.makeText(getApplicationContext(),""+spiner1.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
                   }
               });


               String[] array = {"20", "50","100"};
               ArrayAdapter<String> adapterSpinner;
               adapterSpinner = new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_spinner_item, array);
               spiner1.setAdapter(adapterSpinner);
               spiner1.setSelection(1);


               Ab.setView(vista);
               AlertDialog dialog=Ab.create();
               Ab.create();
               Ab.show();
           }
       });





       txt_buscar_lst_prod_stock.addTextChangedListener(new TextWatcher() {
           @Override
           public void beforeTextChanged(CharSequence s, int start, int count, int after) {


           }

           @Override
           public void onTextChanged(CharSequence s, int start, int before, int count) {

           }

           @Override
           public void afterTextChanged(Editable s) {

               if(listaStockProd!=null){
                   Filtrar( listaStockProd,  txt_buscar_lst_prod_stock.getText().toString()); Filtrar( listaStockProd,  txt_buscar_lst_prod_stock.getText().toString());
               }


             /*
               if (txt_buscar_lst_prod_stock.length()==0){
                   TbL_inv_listaProductosStock.removeAllViews();
                   poblar(listaStockProd);
               }
              */

           }
       });




    }
    @Override
    public Resources.Theme getTheme() {
        return super.getTheme();
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
                            listaStockProd= new ArrayList<>();

                            JSONObject Paginacion=response.getJSONObject("paginacion");
                            paginacionModel.setPage_nro(Paginacion.getInt("page_nro"));
                            paginacionModel.setPage_size(Paginacion.getInt("page_size"));
                            paginacionModel.setPage_count(Paginacion.getInt("page_count"));
                            paginacionModel.setTotal(Paginacion.getInt("total"));


                            //Toast.makeText(getApplicationContext(),"No prueba de  boton"+paginacionModel.getPage_size()+"-"+paginacionModel.getPage_count()+"-"+paginacionModel.getTotal(),Toast.LENGTH_LONG).show();


                            JSONArray datos=response.getJSONArray("inv_prod_stock");
                            for ( int i=0;i<datos.length();i++ ){
                                ProductoStockSimple productoStock= new ProductoStockSimple();
                                JSONObject  Objecto=datos.getJSONObject(i);
                                productoStock.setCod_producto(Objecto.getString("cod_producto"));
                                productoStock.setCod_comercial(Objecto.getString("cod_comercial"));
                                productoStock.setNombre_prod(Objecto.getString("nombre_prod"));
                                /*
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
                                */
                                productoStock.setStock(Objecto.getString("cantidad"));
                                listaStockProd.add(productoStock);

                            }

                            poblar( listaStockProd);
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


        });/*{
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros=new HashMap<String,String>();
                parametros.put("RucE",ruc_empresa);
                parametros.put("FecD","01-01-2010");//dato ficticios el servicio  responde  y el proc de la bd  responde con fechas en duro
                parametros.put("FecH","01-01-2010");//dato ficticios el servicio  responde  y el proc de la bd  responde con fechas en duro
                parametros.put("pageNro", "1");
                parametros.put("pageSize","10");
               // parametros.put("Cod_Alm","");
               // parametros.put("TextSearch",txt_buscar_lst_prod_stock.getText().toString());
                return parametros;
            }
        };
*/
        RQue.add(Requeste);
    }

    public void poblar(ArrayList<ProductoStockSimple> listaStockProd){
       try {

           TableRow tbrowTitulos = new TableRow(this);

           TextView tv0 = new TextView(this);
           tv0.setText("Código");

           tv0.setTextColor(Color.WHITE);
           tv0.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv0);


           TextView tv1 = new TextView(this);
           tv1.setText("Cód. Comercial");
           tv1.setTextColor(Color.WHITE);
           tv1.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv1);


           TextView tv2 = new TextView(this);
           tv2.setText("Producto");
           tv2.setTextColor(Color.WHITE);
           tv2.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv2);


           /*
           TextView tv3 = new TextView(this);
           tv3.setText("descripcion");
           tv3.setTextColor(Color.WHITE);
           tv3.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv3);
*/

           /*
           TextView tv4 = new TextView(this);
           tv4.setText("nom_corto_prod");
           tv4.setTextColor(Color.WHITE);
           tv4.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv4);


           TextView tv5 = new TextView(this);
           tv5.setText("cod_marca");
           tv5.setTextColor(Color.WHITE);
           tv5.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv5);

           TextView tv6 = new TextView(this);
           tv6.setText("nom_marca");
           tv6.setPadding(1,1,5,1);
           tv6.setTextColor(Color.WHITE);
           tv6.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv6);

           TextView tv7 = new TextView(this);
           tv7.setText("nom_corto_marca");
           tv7.setTextColor(Color.WHITE);
           tv7.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv7);

           TextView tv8 = new TextView(this);
           tv8.setText("cod_clase");
           tv8.setTextColor(Color.WHITE);
           tv8.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv8);

           TextView tv10 = new TextView(this);
           tv10.setText("nom_clase");
           tv10.setTextColor(Color.WHITE);
           tv10.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv10);

           TextView tv11 = new TextView(this);
           tv11.setText("nom_corto_clase");
           tv11.setTextColor(Color.WHITE);
           tv11.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv11);

           TextView tv12 = new TextView(this);
           tv12.setText("cod_subclase");
           tv12.setTextColor(Color.WHITE);
           tv12.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv12);

           TextView tv13 = new TextView(this);
           tv13.setText("nom_subclase");
           tv13.setTextColor(Color.WHITE);
           tv13.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv13);

           TextView tv14 = new TextView(this);
           tv14.setText("nom_corto_subclase");
           tv14.setTextColor(Color.WHITE);
           tv14.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv14);
           */

           TextView tv15 = new TextView(this);
           tv15.setText("Stock");
           tv15.setTextColor(Color.WHITE);
           tv15.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv15);

           TextView tv16 = new TextView(this);
           tv16.setText("Ver");
           tv16.setTextColor(Color.WHITE);
           tv16.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
           tbrowTitulos.addView(tv16);


           tbrowTitulos.setLayoutParams(new TableLayout.LayoutParams(
                   TableLayout.LayoutParams.WRAP_CONTENT,
                   TableLayout.LayoutParams.FILL_PARENT));

         //TbL_Cabecera.addView(tbrowTitulos);

           TbL_inv_listaProductosStock.addView(tbrowTitulos);


               for (int i=0;i<listaStockProd.size();i++){


                   View tableRow = LayoutInflater.from(this).inflate(R.layout.elemento_item_listastockprod,null,false);
                   TextView txt_codigo=(TextView)tableRow.findViewById(R.id.txt_codigo);
                   TextView txt_cod_interno=(TextView)tableRow.findViewById(R.id.txt_cod_interno);
                   TextView txt_nombre=(TextView)tableRow.findViewById(R.id.txt_nombre);

                   ImageButton btn_render_targetprodstock=(ImageButton)tableRow.findViewById(R.id.btn_render_targetprodstock);




                   /*
                   TextView txt_descripcion=(TextView)tableRow.findViewById(R.id.txt_descripcion);
                   TextView txt_nom_corto=(TextView)tableRow.findViewById(R.id.txt_nom_corto);
                   TextView txt_cod_marca=(TextView)tableRow.findViewById(R.id.txt_cod_marca);
                   TextView txt_nom_marca=(TextView)tableRow.findViewById(R.id.txt_nom_marca);
                   TextView txt_nom_corto_marca=(TextView)tableRow.findViewById(R.id.txt_nom_corto_marca);
                   TextView txt_cod_clase=(TextView)tableRow.findViewById(R.id.txt_cod_clase);
                   TextView txt_nom_clase=(TextView)tableRow.findViewById(R.id.txt_nom_clase);
                   TextView txt_nom_corto_clase=(TextView)tableRow.findViewById(R.id.txt_nom_corto_clase);
                   TextView txt_cod_subclase=(TextView)tableRow.findViewById(R.id.txt_cod_subclase);
                   TextView txt_nom_subclase=(TextView)tableRow.findViewById(R.id.txt_nom_subclase);
                   TextView txt_nom_corto_subclase=(TextView)tableRow.findViewById(R.id.txt_nom_corto_subclase);
                   */

                   TextView txt_cantidad=(TextView)tableRow.findViewById(R.id.txt_cantidad);
                   txt_codigo.setText(listaStockProd.get(i).getCod_producto());
                   txt_cod_interno.setText(listaStockProd.get(i).getCod_comercial());
                   txt_nombre.setText(listaStockProd.get(i).getNombre_prod());

                   /*
                   txt_descripcion.setText(listaStockProd.get(i).getDesc_prod());
                   txt_nom_corto.setText(listaStockProd.get(i).getNom_corto_prod());
                   txt_cod_marca.setText(listaStockProd.get(i).getCod_marca());
                   txt_nom_marca.setText(listaStockProd.get(i).getNom_marca());
                   txt_nom_corto_marca.setText(listaStockProd.get(i).getNom_corto_marca());
                   txt_cod_clase.setText(listaStockProd.get(i).getCod_clase());
                   txt_nom_clase.setText(listaStockProd.get(i).getNom_clase());
                   txt_nom_corto_clase.setText(listaStockProd.get(i).getNom_corto_clase());
                   txt_cod_subclase.setText(listaStockProd.get(i).getCod_subclase());
                   txt_nom_subclase.setText(listaStockProd.get(i).getNom_subclase());
                   txt_nom_corto_subclase.setText(listaStockProd.get(i).getNom_corto_subclase());
                   */
                   txt_cantidad.setText(listaStockProd.get(i).getStock());

                   btn_render_targetprodstock.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {


                           TableRow row =(TableRow)v.getParent();
                           TextView txt_codprod =(TextView)row.getChildAt(0);
                           String cod_prod=(String) txt_codprod.getText();


                           if (cod_prod!=null || cod_prod!="" ){

                               Intent data = new Intent(getApplicationContext(),ProductoStockTargetActivity.class);
                               data.putExtra("cod_prod",cod_prod);
                               startActivity(data);
                           }else  {

                               return;
                           }

                       }
                   });

                   TbL_inv_listaProductosStock.addView(tableRow);

               }
           progressDialog.dismiss();
       }catch (Exception ex){
           progressDialog.dismiss();
           Toast.makeText(getApplicationContext(),"error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
       }

    }

    public void Filtrar(ArrayList<ProductoStockSimple> listaStockProd,String  textoFiltro){

       try {
         listaStockProd_Temp =(ArrayList<ProductoStockSimple>)listaStockProd.clone();

           for (int i=0;i<listaStockProd.size();i++){

               if (!(listaStockProd.get(i).getNombre_prod().toLowerCase()).contains(textoFiltro.toLowerCase())){
                   listaStockProd_Temp.remove(listaStockProd.get(i));
               }
           }

           TbL_inv_listaProductosStock.removeAllViews();
           if (listaStockProd_Temp.size()>0){
               poblar(listaStockProd_Temp);
           }else {

               Toast.makeText(getApplicationContext(),"No no ha productos con : "+textoFiltro,Toast.LENGTH_LONG).show();
           }
       }catch (Exception ex){

           Toast.makeText(getApplicationContext(),"Error al filtrar"+ex.getMessage(),Toast.LENGTH_LONG).show();
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

       String pag="Pag. "+paginacionModel.getPage_nro()+"/"+paginacionModel.getPage_count()+" - Reg. "+paginacionModel.getPage_size()+"/"+paginacionModel.getTotal()+"";
        lbl_text_paginacion.setText(pag);
    }


/*
    public  void SetSpinerPaginacion(){
        String[] array = {"20", "50","100"};
        ArrayAdapter<String> adapterSpinner;
        adapterSpinner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, array);
        spiner_cant_pag.setAdapter(adapterSpinner);
        spiner_cant_pag.setSelection(1);
    }
*/






}
