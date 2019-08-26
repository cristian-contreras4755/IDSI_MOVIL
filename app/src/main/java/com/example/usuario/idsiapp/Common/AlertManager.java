package com.example.usuario.idsiapp.Common;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.example.usuario.idsiapp.R;

public class AlertManager {

    //private Context context;
    private ProgressDialog progressDialog;

        public AlertManager() { }


    public void Alerta(String Titulo, String Mensaje,Context context,AlertDialog.Builder Alert) {
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



/*
    public void AlertEspera(String Titulo, String Mensaje,Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setIcon(R.mipmap.ic_launcher);
        progressDialog.setTitle(Titulo);
        progressDialog.setMessage(Mensaje);
        progressDialog.show();
    }
    */

    public void AlertEsperaClose(){
        progressDialog.dismiss();
    }

}
