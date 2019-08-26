package com.example.usuario.idsiapp.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Adapters.AdaptadorListaProducto;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.Producto;
import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaProductoActivity extends AppCompatActivity  implements TextWatcher {



    private  String ruc_empresa;
    private ArrayList<Producto> ListaProducto;
    private ListView lv_lista_producto;
    private RequestQueue RQue;
    private AdaptadorListaProducto adaptadorListaProducto;

    private EditText txt_buscar_producto;


      private ProgressDialog progressDialog;
      private SessionManager sessionManager;
      private NetWorkManager netWorkManager;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_producto);
        sessionManager= new SessionManager(getApplicationContext());
        netWorkManager= new NetWorkManager(getApplicationContext());



        txt_buscar_producto=(EditText)findViewById(R.id.txt_buscar_producto);
        txt_buscar_producto.setEnabled(false);

        RQue=Volley.newRequestQueue(getApplicationContext());
        lv_lista_producto=(ListView)findViewById(R.id.lv_lista_producto);

        ruc_empresa= sessionManager.ObtenerRuc_Session("ruc_global"); // recepcion de ruc por session
       // ruc_empresa=getIntent().getStringExtra("ruc_empresa"); recepcion de ruc por intent


        if (netWorkManager.Is_Online()){
            ConsultarServicio( ruc_empresa);
        }else {
            AlertaAdvertencia_Atras("Lista Producto","No se pudo completar la operacíon, verifique su conexión de internet");
        }




        txt_buscar_producto.addTextChangedListener(this);



        lv_lista_producto.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                try{

                    final Producto objproducto=(Producto)lv_lista_producto.getItemAtPosition(position);

                    Intent data = new Intent();
                    data.putExtra("cod_producto",objproducto.getCod_producto());
                    data.putExtra("nombre_producto",objproducto.getNombre_producto1());
                    data.putExtra("valorventa",objproducto.getValor_vta());
                    setResult(RESULT_OK,data);
                    finish();

                }catch (Exception Ex){Ex.getMessage();}


            }
        });


    }


    public  void ConsultarServicio(String ruc_empresa) {
        AlertEspera("Espere","Cargando...");
        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                "http://idsierp.dyndns.org:5000/api/Producto/ObtenerListaProductos?ruc_Empresa="+ruc_empresa,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{


                            ArrayList<Producto> listaProducto= new ArrayList<>();
                             JSONArray productoArray=response.getJSONArray("productos");
                            // txt_buscar_producto.setText(productoArray.toString());

                            for ( int i=0;i<productoArray.length();i++ ){

                                Producto producto= new Producto ();
                                JSONObject  Objecto=productoArray.getJSONObject(i);

                                producto.setCod_producto(Objecto.getString("cod_producto"));
                                producto.setNombre_producto1(Objecto.getString("nombre_producto1"));
                                producto.setNombre_producto2("");
                                producto.setNombre_producto_corto("");

                                producto.setValor_vta(Double.parseDouble(Objecto.getString("valor_vta")));


                                if ((Objecto.getString("descripcion"))==null || (Objecto.getString("descripcion")).equals("") || (Objecto.getString("descripcion")).equals("null") ){

                                    producto.setDescripcion("--");
                                }else {
                                    producto.setDescripcion(Objecto.getString("descripcion"));
                                }



                                producto.setDescuento(0.00);
                                producto.setCod_unidad_medida(Objecto.getString("cod_unidad_medida"));
                                producto.setNombre_unidad_medida(Objecto.getString("nombre_unidad_medida"));
                                producto.setNombre_corto_unidad_medida("");



                                /*
                                *  producto.setCod_producto(Objecto.getString("cod_producto"));
                                producto.setNombre_producto1(Objecto.getString("nombre_producto1"));
                                producto.setNombre_producto2(Objecto.getString("nombre_producto2"));
                                producto.setDescripcion(Objecto.getString("descripcion"));
                                producto.setNombre_producto_corto(Objecto.getString("nombre_producto_corto"));
                                producto.setValor_vta(Double.parseDouble( Objecto.getString("valor_vta")));
                                producto.setPrecio_vta(Double.parseDouble( Objecto.getString("precio_vta")));
                                producto.setDescuento(Double.parseDouble(Objecto.getString("descuento")));
                                producto.setCod_unidad_medida(Objecto.getString("cod_unidad_medida"));
                                producto.setNombre_unidad_medida(Objecto.getString("nombre_unidad_medida"));
                                producto.setNombre_corto_unidad_medida(Objecto.getString("nombre_corto_unidad_medida"));*/
                                /*

                                if ((Objecto.getString("nombre_producto2"))==null ||(Objecto.getString("nombre_producto2")).equals("")){

                                    producto.setNombre_producto2("");
                                }else {
                                    producto.setNombre_producto2(Objecto.getString("nombre_producto2"));
                                }



                                if ((Objecto.getString("descripcion"))==null || (Objecto.getString("descripcion")).equals("") ){

                                    producto.setDescripcion("");
                                }else {
                                    producto.setDescripcion(Objecto.getString("descripcion"));
                                }


                                if ((Objecto.getString("nombre_producto_corto"))==null ||(Objecto.getString("nombre_producto_corto")).equals("")){

                                    producto.setNombre_producto_corto("");
                                }else {
                                    producto.setNombre_producto_corto(Objecto.getString("nombre_producto_corto"));
                                }

                                producto.setValor_vta(Double.parseDouble( Objecto.getString("valor_vta")));
                                producto.setPrecio_vta(Double.parseDouble( Objecto.getString("precio_vta")));


                                if ((Objecto.getString("descuento"))==null){

                                    producto.setDescuento(0.0);
                                }else {
                                    producto.setDescuento(Double.parseDouble(Objecto.getString("descuento")));//caida por nulo
                                }





                                producto.setCod_unidad_medida(Objecto.getString("cod_unidad_medida"));
                                producto.setNombre_unidad_medida(Objecto.getString("nombre_unidad_medida"));
                                producto.setNombre_corto_unidad_medida(Objecto.getString("nombre_corto_unidad_medida"));
*/

                                listaProducto.add(producto);
                            }
                           // txt_buscar_producto.setText(listaProducto.toString());

                            ListarProducto(listaProducto);
                            progressDialog.dismiss();

                        }catch (Exception ex) {
                            progressDialog.dismiss();
                            ex.printStackTrace();
                            AlertaError("Lista productos","por favor vuelva a cargar la lista de productos");
                            //Toast.makeText(getApplicationContext(), "error Al deserializar producto" +ex.getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                AlertaError("Lista productos","por favor vuelva a cargar la lista de productos");
               // Toast.makeText(getApplicationContext(), "error al conultar el servicio de productos" +error.getMessage(), Toast.LENGTH_LONG).show();

            }


        });

        RQue.add(Requeste);
    }

    public  void ListarProducto(ArrayList<Producto> listaProducto){

        adaptadorListaProducto=new AdaptadorListaProducto(listaProducto,getApplicationContext());
        lv_lista_producto.setAdapter(adaptadorListaProducto);

        if (listaProducto.size()>0){

            txt_buscar_producto.setEnabled(true);
        }
    }





    //filtros 11-03-2019


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        this.adaptadorListaProducto.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    //alert
    public void AlertEspera(String Titulo, String Mensaje){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher_logo);
        progressDialog.setTitle(Titulo);
        progressDialog.setMessage(Mensaje);
        progressDialog.show();
    }





    public void AlertaAdvertencia(String Titulo,String Mensaje){

        AlertDialog.Builder Alert= new AlertDialog.Builder(this);
        Alert.setIcon(R.mipmap.ic_launcher_logo);
        Alert.setTitle(Titulo);
        Alert.setMessage(Mensaje);
        Alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });
        AlertDialog alertDialog=Alert.create();
        alertDialog.show();
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




    public void AlertaAdvertencia_Atras(String Titulo,String Mensaje){

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
