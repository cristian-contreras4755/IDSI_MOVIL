package com.example.usuario.idsiapp.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Adapters.AdaptadorListaCliente;
import com.example.usuario.idsiapp.Common.AlertManager;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.Producto;
import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ListaClienteActivity extends AppCompatActivity  implements TextWatcher{


    private RequestQueue RQue;
    private AdaptadorListaCliente adaptadorListaCliente;
    private ListView lv_cliente;
    private String  ruc_empresa;
    private TextView txt_buscar_cliente;
   // private ProgressBar PBCliente;

   // private AlertManager alertManager;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private NetWorkManager netWorkManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_cliente);
        sessionManager= new SessionManager(getApplicationContext());
        netWorkManager= new NetWorkManager(getApplicationContext());

       // alertManager= new AlertManager(getApplicationContext(),progressDialog);
        txt_buscar_cliente=(TextView) findViewById(R.id.txt_buscar_cliente);
        txt_buscar_cliente.setEnabled(false);

        RQue=Volley.newRequestQueue(getApplicationContext());
        lv_cliente=(ListView)findViewById(R.id.lv_lista_cliente);
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");
        //ruc_empresa=getIntent().getStringExtra("ruc_empresa");  anterir se obtiene  ruc por  intent



        if (netWorkManager.Is_Online()){
            ConsultarServicio(ruc_empresa);
        }else {
            AlertaAdvertencia_Atras("Lista Cliente","No se pudo completar la operacíon, verifique su conexión de internet");
        }


        txt_buscar_cliente.addTextChangedListener(this);

        lv_cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                try{

                    final Cliente objclie=(Cliente) lv_cliente.getItemAtPosition(position);

                    Intent data = new Intent();
                    data.putExtra("cod_cliente",objclie.getCod_cliente());
                    data.putExtra("nombre_cliente",objclie.getNombre_cliente());
                    setResult(2,data);
                    finish();

                }catch (Exception Ex){Ex.getMessage();}

            }
        });


    }



    public  void ConsultarServicio(String ruc_empresa) {
        AlertEspera("Espere","Cargando...");
        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                "http://idsierp.dyndns.org:5000/api/Cliente/ObtenerListaClientes?ruc_empresa="+ruc_empresa,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{


                            ArrayList<Cliente> listaCliente= new ArrayList<>();

                            JSONArray clienteArray=response.getJSONArray("cliente");

                            for ( int i=0;i<clienteArray.length();i++ ){

                                Cliente cliente= new Cliente();
                                JSONObject  Objecto=clienteArray.getJSONObject(i);

                                cliente.setRuc_empresa(Objecto.getString("ruc_empresa"));
                                cliente.setCod_cliente(Objecto.getString("cod_cliente"));
                                cliente.setCod_tipo_doc(Objecto.getString("cod_tipo_doc"));
                                cliente.setNombre_corto_Tipo_doc(Objecto.getString("nombre_corto_Tipo_doc"));
                                cliente.setNro_documento(Objecto.getString("nro_documento"));
                                cliente.setNombre_cliente(Objecto.getString("nombre_cliente"));
                                cliente.setCod_pais(Objecto.getString("cod_pais"));
                                cliente.setCod_postal(Objecto.getString("cod_postal"));
                                cliente.setUbigeo(Objecto.getString("ubigeo"));
                                cliente.setDireccion(Objecto.getString("direccion"));
                                cliente.setTelefono1(Objecto.getString("telefono1"));
                                cliente.setTelefono2(Objecto.getString("telefono2"));
                                cliente.setFax(Objecto.getString("fax"));
                                cliente.setPagina_web(Objecto.getString("pagina_web"));
                                cliente.setObservacion(Objecto.getString("observacion"));
                                cliente.setRuc_empresa(Objecto.getString("estado"));
                                cliente.setCampo_adicional_01(Objecto.getString("campo_adicional_01"));
                                cliente.setCampo_adicional_02(Objecto.getString("campo_adicional_02"));
                                cliente.setCampo_adicional_03(Objecto.getString("campo_adicional_03"));
                                cliente.setCampo_adicional_04(Objecto.getString("campo_adicional_04"));
                                cliente.setCampo_adicional_05(Objecto.getString("campo_adicional_05"));
                                cliente.setCampo_adicional_06(Objecto.getString("campo_adicional_06"));
                                cliente.setCampo_adicional_07(Objecto.getString("campo_adicional_07"));
                                cliente.setCampo_adicional_08(Objecto.getString("campo_adicional_08"));
                                cliente.setCampo_adicional_09(Objecto.getString("campo_adicional_09"));
                                cliente.setCampo_adicional_10(Objecto.getString("campo_adicional_10"));

                                listaCliente.add(cliente);

                            }

                            ListarCliente(listaCliente);
                            progressDialog.dismiss();

                        }catch (Exception ex) {

                            AlertaError("Lista Cliente","por favor, vuelga a cargar la lista de clientes");
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                AlertaError("Lista Cliente","por favor, vuelga a cargar la lista de clientes");
            }


        });

        RQue.add(Requeste);


    }


    public  void ListarCliente(ArrayList<Cliente> listaCliente){

        adaptadorListaCliente=new AdaptadorListaCliente(getApplicationContext(),listaCliente);
        lv_cliente.setAdapter(adaptadorListaCliente);

        if (listaCliente.size()>0){

            txt_buscar_cliente.setEnabled(true);
        }

    }




    //filtros

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {



    this.adaptadorListaCliente.getFilter().filter(s);
    }

    @Override
    public void afterTextChanged(Editable s) {

    }



    //alert
    public void AlertEspera(String Titulo, String Mensaje){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher_logo);
        progressDialog.setTitle(Titulo);
        progressDialog.setCancelable(false);
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
