package com.example.usuario.idsiapp.Common;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.example.usuario.idsiapp.Activities.Login;
import com.example.usuario.idsiapp.Activities.PrincipalActivity;
import com.example.usuario.idsiapp.Activities.RegistrarPedidoActivity;

public class SessionManager {

    private Context context;

    public SessionManager() {
    }

    public SessionManager(Context context) {
        this.context = context;
    }

    public void Login(String ruc_global,String empresa_global, String usuario_global,String cod_vendedor,String nom_vendedor){
        SharedPreferences sharedP=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        SharedPreferences.Editor  Editor=sharedP.edit();
        Editor.putString("ruc_global",ruc_global);
        Editor.putString("empresa_global",empresa_global);
        Editor.putString("usuario_global",usuario_global);
        Editor.putString("codvendedor_global",cod_vendedor);
        Editor.putString("nomvendedor_global",nom_vendedor);
        Editor.apply();

       // Toast.makeText(context,"session correcta",Toast.LENGTH_LONG).show();
    }



    public void Agregar_datos_session(int tiempo_envio_loc_seg,Boolean Estado_loc){
        SharedPreferences sharedP=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        SharedPreferences.Editor  Editor=sharedP.edit();
                        int Milisegundos_seg=(tiempo_envio_loc_seg*1000);
                        Editor.putInt("tiempo_envio_localizacion_global",Milisegundos_seg);
                        Editor.putBoolean("estado_localizacion_global",Estado_loc);
                        Editor.apply();
    }




    public  String ObtenerRuc_Session(String LLave){
        SharedPreferences shared=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        return shared.getString(LLave,"");
    }


    public  String ObtenerNomVendedor_Session(String LLave){
        SharedPreferences shared=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        return shared.getString(LLave,"");
    }


    public  String ObtenerEmpresa_Session(String LLave){
        SharedPreferences shared=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        return shared.getString(LLave,"");
    }

    public  String ObtenerUsuario_Session(String LLave){
        SharedPreferences shared=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        return shared.getString(LLave,"");
    }

    public  String ObtenerCodVendedor_Session(String LLave){
        SharedPreferences shared=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        return shared.getString(LLave,"");
    }


    public  int Obtenerminuto_envio_localizacion_Session(String LLave){
        SharedPreferences shared=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        return shared.getInt(LLave,0);
    }

    public  Boolean Obtenerestado_localizacion_Session(String LLave){
        SharedPreferences shared=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        return shared.getBoolean(LLave,false);
    }


    public void LogOut_tryError(){
        SharedPreferences sharedP=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        SharedPreferences.Editor Editor=sharedP.edit();
        Editor.clear();
        Editor.commit();
        Intent I = new Intent(context,Login.class);
        I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(I);
    }

    public void LogOut(){
        SharedPreferences sharedP=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        SharedPreferences.Editor Editor=sharedP.edit();
        Editor.clear();
        Editor.commit();
        //cerrar  todas las actividades
        Intent I = new Intent(context,Login.class);
        I.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(I);
    }

    public void LogOut_force(){
        SharedPreferences sharedP=context.getSharedPreferences("session_idsi",Context.MODE_PRIVATE);
        SharedPreferences.Editor Editor=sharedP.edit();
        Editor.clear();
        Editor.commit();
    }

}
