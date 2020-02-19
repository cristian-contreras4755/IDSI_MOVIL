package com.example.usuario.idsiapp.Common;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.BoringLayout;
import android.util.Log;
import android.widget.Toast;

import com.google.errorprone.annotations.Var;


public class NetWorkManager {
    private Context context;
    public NetWorkManager() {
    }

    public NetWorkManager(Context context) {
        this.context = context;
    }

    public  boolean Is_Online(){
        boolean EstadoInternet= false;
        try {

            ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo!=null && networkInfo.isConnected() && networkInfo.isAvailable()){

                EstadoInternet= true;

            } else {
                EstadoInternet=false;
            }
        }catch (Exception ex){
            ex.getMessage();
            EstadoInternet=false;
        }

    return  EstadoInternet ;
    }

    public String GetUrlBaseServices(){
       // return "http://idsierp.dyndns.org:5000/api" ;
        return "http://3.84.91.212/ServiciosGen/api" ;
    }




/*
    public  boolean   is_OnlineIntenet(){
        boolean Estado=false;
        try{
            Process process=Runtime.getRuntime().exec("ping www.google.es");
            int valor= process.waitFor();
             if (valor==0){
                 Estado=true;
             }else {

                 Estado=false;
             }
        }catch (Exception ex){

            Estado=false;
        }
        return Estado;
    }

*/


}
