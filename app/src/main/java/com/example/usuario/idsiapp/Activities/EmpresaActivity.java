package com.example.usuario.idsiapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Common.AlertManager;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.Empresa;
import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class EmpresaActivity extends AppCompatActivity {

    private ListView lv_lista_Empresas;
    private ArrayList<Empresa>ListaEmpresa;
    private ArrayList<String>ListaEmpresaLianeal;
    private String UsuarioGlobal;
    private ArrayAdapter<String>ListaAdaptadorEmpresa;
    private SessionManager sessionManager;
    private RequestQueue RQue;
    private NetWorkManager netWorkManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa);
        sessionManager= new SessionManager(getApplicationContext());
        netWorkManager= new NetWorkManager(getApplicationContext());
        RQue=Volley.newRequestQueue(getApplicationContext());

        SolicitarPermiso();

        lv_lista_Empresas=(ListView)findViewById(R.id.lv_lista_Empresas);
        UsuarioGlobal=getIntent().getStringExtra("usuario_global");
        ListaEmpresa= (ArrayList<Empresa>) getIntent().getSerializableExtra("lista_empresa_global");

        if (ListaEmpresa.size()>0 && ListaEmpresa!=null ){

            ArrayList<String> Empresas= new ArrayList<>();
            for (Empresa Item:ListaEmpresa) {
                Empresas.add(Item.getRuc_empresa()+"-"+Item.getRazon_social());
            }


            ArrayAdapter<String> AdapterEmpresa= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,Empresas);;
            lv_lista_Empresas.setAdapter(AdapterEmpresa);
        }



        lv_lista_Empresas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try{
                    String itemtext=(String)lv_lista_Empresas.getItemAtPosition(position);
                    int indice_guion=itemtext.indexOf("-");
                    String rucE=itemtext.substring(0,indice_guion);

                    int cantidadCaracteres=itemtext.length();

                   String Empresa=itemtext.substring((indice_guion+1),cantidadCaracteres);


                    if (rucE.length()>0 && UsuarioGlobal.length()>0 ){

                        if (netWorkManager.Is_Online()){
                            ValidarUsuarioVendedor( rucE,Empresa,UsuarioGlobal,"");
                        }else {
                            AlertaAdvertencia_Atras("Selección de Empresa","No se pudo completar la operacíon, verifique su conexión de internet");
                        }
                    }
                }catch (Exception ex){
                    AlertaAdvertencia("Error","Error:"+ex.getMessage());
                }

            }
        });
    }



    public void AlertaAdvertencia(String Titulo,String Mensaje){

        AlertDialog.Builder Alert= new AlertDialog.Builder(this);
        Alert.setIcon(R.mipmap.ic_launcher_logo);
        Alert.setTitle(Titulo);
        Alert.setMessage(Mensaje);
        Alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                //  Toast.makeText(getApplicationContext(),"Hola",Toast.LENGTH_LONG).show();
            }
        });
        AlertDialog alertDialog=Alert.create();
        alertDialog.show();
    }


    public void   ValidarUsuarioVendedor(final String Ruc,  final String Empresa,final String Usuario, final String Cod_vendedor){

        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.POST,
                "http://idsierp.dyndns.org:5000/api/Seguridad/ValidaUsuarioVendedorMovil?ruc="+Ruc+"&usuario="+Usuario,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            String codigo_vendedor=response.getString("cod_vendedor");
                            String nombre_vendedor=response.getString("nom_vendedor");
                            IniciarSession( Ruc, Empresa,Usuario,codigo_vendedor,nombre_vendedor);
                            ConsultarConfGeolocalizacion( Ruc,codigo_vendedor);

                        }catch (Exception ex) {
                            AlertaAdvertencia("Error",""+ex.getMessage());
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{

                    NetworkResponse networkResponse = error.networkResponse;

                    if (networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_BAD_REQUEST) {

                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject data = new JSONObject(responseBody);
                        AlertaAdvertencia("Error",data.get("mensajeError").toString());

                    }else if(networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_INTERNAL_ERROR) {

                        AlertaAdvertencia("Error","Error Interno, intenleno nuevamente dentro de unos minutos");

                    }else {
                        AlertaAdvertencia("Error","Error Interno, intenleno nuevamente dentro de unos minutos");
                    }

                }catch (Exception ex){

                }
            }

        });

        RQue.add(Requeste);
    }



    public void IniciarSession(String Ruc,String Empresa, String Usuario, String Cod_vendedor,String nom_vendedor) {
        sessionManager.Login( Ruc, Empresa, Usuario , Cod_vendedor,nom_vendedor);
    }


    public void AlertaAdvertencia_Atras(String Titulo,String Mensaje){

        AlertDialog.Builder Alert= new AlertDialog.Builder(this);
        Alert.setIcon(R.mipmap.ic_launcher_logo);
        Alert.setTitle(Titulo);
        Alert.setMessage(Mensaje);
        Alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            /*

                Intent I= new Intent(getApplicationContext(),Login.class);
                startActivity(I);
                finish();
             */
            }
        });
        AlertDialog alertDialog=Alert.create();
        alertDialog.show();
    }





    public void  SolicitarPermiso(){

        int permisoGPS =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION);
        int permisoGPS2 =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION);
        int permiso_Internet =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_NETWORK_STATE);
        int permiso_telefono =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.READ_PHONE_STATE);


        if (permisoGPS!= PackageManager.PERMISSION_GRANTED ||permisoGPS2  != PackageManager.PERMISSION_GRANTED || permiso_telefono != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.READ_PHONE_STATE},100);
        }

        if (permiso_telefono != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},101);
        }


    }




    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),"permiso GPS activado",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"permiso GPS denegado",Toast.LENGTH_LONG).show();
                }
                return;
            }
            case 101: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),"permiso de telefono activado",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"permiso de telefonoo denegado",Toast.LENGTH_LONG).show();
                }
                return;
            }


        }
    }




    public void   ConsultarConfGeolocalizacion(String Ruc,String Cod_vededor){

        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.POST,
                "http://idsierp.dyndns.org:5000/api/Seguridad/ConsultarConfGeolocalizacion?ruc_empresa="+Ruc+"&cod_vendedor="+Cod_vededor,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            int intevalo_consulta_segundos=response.getInt("intevalo_consulta_seg");
                            Boolean ib_geolocalizacion=response.getBoolean("ib_geolocalizacion");

                          Integer milisegundos=   intevalo_consulta_segundos*1000;
                            Intent I= new Intent(getApplicationContext(),PrincipalActivity.class);
                            I.putExtra("intevalo_consulta_seg",milisegundos);
                            I.putExtra("ib_geolocalizacion",ib_geolocalizacion);
                            startActivity(I);

                        }catch (Exception ex) {
                            Toast.makeText(getApplicationContext(),"No pudimos obtener la configuración de geolocalizacion ",Toast.LENGTH_LONG).show();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                try{

                    NetworkResponse networkResponse = error.networkResponse;

                    if (networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_BAD_REQUEST) {

                        String responseBody = new String(error.networkResponse.data, "utf-8");
                        JSONObject data = new JSONObject(responseBody);
                        AlertaAdvertencia("Error",data.get("mensajeError").toString());

                    }else if(networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_INTERNAL_ERROR) {

                        AlertaAdvertencia("Error","Error Interno, intenleno nuevamente dentro de unos minutos");

                    }else {
                        AlertaAdvertencia("Error","Error Interno, intenleno nuevamente dentro de unos minutos");
                    }

                }catch (Exception ex){

                }
            }

        });

        RQue.add(Requeste);
    }

}



