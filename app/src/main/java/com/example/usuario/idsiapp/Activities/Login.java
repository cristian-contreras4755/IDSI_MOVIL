package com.example.usuario.idsiapp.Activities;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.Empresa;
import com.example.usuario.idsiapp.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;



public class Login extends AppCompatActivity {

    private RequestQueue RQue;
    private Button btn_login;
    private EditText txt_usuarios,txt_password;
    //private  ArrayList<Empresa> ListaEmpresa;
    private  ProgressDialog progressDialog ;
    private NetWorkManager netWorkManager;

    private String UrlBase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        netWorkManager= new NetWorkManager(getApplicationContext());
        UrlBase=netWorkManager.GetUrlBaseServices();


        RQue=Volley.newRequestQueue(getApplicationContext());
        btn_login=(Button)findViewById(R.id.btn_login);

         txt_usuarios=(EditText)findViewById(R.id.txt_usuarios);
         txt_password=(EditText)findViewById(R.id.txt_password);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    if (ValidarControles()){


                        if (netWorkManager.Is_Online()){

                            ConsultarServicioLogin(txt_usuarios.getText().toString(),txt_password.getText().toString());
                        }else {
                            AlertaAdvertencia_Atras("INICIO DE SESSION","No se pudo ingresar a la aplicación, verifique su conexión de internet");
                        }

                    }


                }catch (Exception ex){

                }

            }
        });

    }





    public  void ConsultarServicioLogin( String  Usuario,  String  Constrasena) {
        AlertaEspera("Espere","Cargando.....");

        JSONObject JsonLogin= new JSONObject();
        try{
            JsonLogin.put("usuario",Usuario);
            JsonLogin.put("password",Constrasena);

        }catch (Exception ex){

        }


        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.POST,
                UrlBase+"/Seguridad/Loginmovil",
                JsonLogin,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            ArrayList<Empresa> ListaEmpresa= new ArrayList<Empresa>();
                            JSONArray clienteArray=response.getJSONArray("empresa");

                            for ( int i=0;i<clienteArray.length();i++ ){

                                Empresa empresa= new Empresa();
                                JSONObject  Objecto=clienteArray.getJSONObject(i);

                                empresa.setRuc_empresa(Objecto.getString("ruc_empresa"));
                                empresa.setRazon_social(Objecto.getString("razon_social"));
                                empresa.setCod_moneda(Objecto.getString("cod_moneda"));
                                ListaEmpresa.add(empresa);


                                //validar session









                            }
                            EnviarDatos(ListaEmpresa);

                        }catch (Exception ex) {

                            ex.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    try{

                        NetworkResponse networkResponse = error.networkResponse;

                        if (networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_BAD_REQUEST) {
                            progressDialog.dismiss();
                            String responseBody = new String(error.networkResponse.data, "utf-8");
                            JSONObject data = new JSONObject(responseBody);
                            AlertaAdvertencia("INICIO DE SESSION",data.get("mensajeError").toString());


                        }else if(networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_INTERNAL_ERROR) {

                            AlertaAdvertencia("INICIO DE SESSION","Status code:"+networkResponse.statusCode+", intenleno nuevamente dentro de unos minutos");
                            progressDialog.dismiss();
                        }else {
                            AlertaAdvertencia("INICIO DE SESSION","Status code:"+networkResponse.statusCode+", intenleno nuevamente dentro de unos minutos");
                            progressDialog.dismiss();
                        }

                    }catch (Exception ex){

                     //   AlertaAdvertencia("INICIO DE SESSION","Error Interno, intenleno nuevamente dentro de unos minutos: "+ex.getMessage()+"-"+error.getMessage()+error.networkResponse+"-"+error.getCause()+"-"+UrlBase+"/Seguridad/Loginmovil");
                        AlertaAdvertencia("INICIO DE SESSION","Error Interno, intenleno nuevamente dentro de unos minutos: "+ex.getMessage());

                        progressDialog.dismiss();
                    }

                }

        });

        RQue.add(Requeste);

    }

    public  boolean ValidarControles(){

        boolean Estado=false;
        if (txt_usuarios.getText().toString().equals("")){
            AlertaAdvertencia("INICIO DE SESSION","Por favor Ingrese Usuario");
            Estado=false;
        }else if (txt_password.getText().toString().equals("")){
            AlertaAdvertencia("INICIO DE SESSION","Por favor Ingrese password");
            Estado=false;
        }else {
            Estado=true;
        }
        return Estado;
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

    public void AlertaEspera(String Titulo,String Mensaje){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher_logo);
        progressDialog.setTitle(Titulo);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Mensaje);
        progressDialog.show();
    }


    public void EnviarDatos(ArrayList<Empresa> ListaEmpresas){

        try{
            Intent I= new Intent(getApplicationContext(),EmpresaActivity.class);
            I.putExtra("usuario_global",txt_usuarios.getText().toString());
            I.putExtra("lista_empresa_global",ListaEmpresas);



            startActivity(I);
            progressDialog.dismiss();
            finish();
        }catch (Exception Ex){
            AlertaAdvertencia("INICIO DE SESSION","Intentelo nuevamente dentro de unos minutos"+Ex.getMessage());
        }

    }




    public void AlertaAdvertencia_Atras(String Titulo,String Mensaje){

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








}
