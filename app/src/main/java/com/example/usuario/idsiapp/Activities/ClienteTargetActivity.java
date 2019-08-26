package com.example.usuario.idsiapp.Activities;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.ClienteTarget;
import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ClienteTargetActivity extends AppCompatActivity {
    private RequestQueue RQue;
    private TextView lbl_ruc;

    private TextView lbl_tipoDoc,lbl_nrodoc,lbl_razonsocial,lbl_nombre,lbl_direccion,lbl_pais,lbl_ubigeo,lbl_telefono1,lbl_telefono2,lbl_correo;


    private TextView Tsnt_tip_cntrb;
    private TextView Tsnt_fec_inscrip;
    private TextView Tsnt_ini_act;
    private TextView Tsnt_fec_baja;
    private TextView Tnt_est_cntrb;
    private TextView Tnt_cond_cntrb;
    private TextView Tnt_emi_comp;
    private TextView Tnt_sis_contab;
    private TextView Tnt_lst_acts_econo;
    private TextView Tnt_lst_comp_pago;
    private TextView Tnt_act_com_ext;
    private TextView Tnt_lst_sis_emi_elec;
    private TextView Tnt_emi_elec;
    private TextView Tnt_lst_comps_elec;
    private TextView Tnt_fec_ins_ple;
    private TextView Tnt_nro_trab;
    private TextView Tnt_nro_pres_srv;
    private TextView Tnt_lst_rprs_legs;
    private TextView Telf_busc;





    private SessionManager sessionManager;
    private String cod_cliente,ruc_empresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cliente_target);
        RQue=Volley.newRequestQueue(getApplicationContext());
        sessionManager= new SessionManager(getApplicationContext());
        cod_cliente=getIntent().getStringExtra("cod_cliente");
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");


      //  lbl_ruc=(TextView)findViewById(R.id.lbl_ruc);


        lbl_tipoDoc=(TextView)findViewById(R.id.lbl_tipoDoc);
        lbl_nrodoc=(TextView)findViewById(R.id.lbl_nrodoc);
        lbl_razonsocial=(TextView)findViewById(R.id.lbl_razonsocial);
        lbl_nombre=(TextView)findViewById(R.id.lbl_nombre);
        lbl_direccion=(TextView)findViewById(R.id.lbl_direccion);
        lbl_pais=(TextView)findViewById(R.id.lbl_pais);
        lbl_ubigeo=(TextView)findViewById(R.id.lbl_ubigeo);
        lbl_telefono1=(TextView)findViewById(R.id.lbl_telefono1);
        lbl_telefono2=(TextView)findViewById(R.id.lbl_telefono2);
        lbl_correo=(TextView)findViewById(R.id.lbl_correo);




         Tsnt_tip_cntrb=(TextView)findViewById(R.id.lbl_snt_tip_cntrb);
         Tsnt_fec_inscrip=(TextView)findViewById(R.id.lbl_snt_fec_inscrip);
         Tsnt_ini_act=(TextView)findViewById(R.id.lbl_snt_ini_act);
         Tsnt_fec_baja=(TextView)findViewById(R.id.lbl_snt_fec_baja);
         Tnt_est_cntrb=(TextView)findViewById(R.id.lbl_snt_est_cntrb);
         Tnt_cond_cntrb=(TextView)findViewById(R.id.lbl_snt_cond_cntrb);
         Tnt_emi_comp=(TextView)findViewById(R.id.lbl_snt_emi_comp);
         Tnt_sis_contab=(TextView)findViewById(R.id.lbl_snt_sis_contab);
         Tnt_lst_acts_econo=(TextView)findViewById(R.id.lbl_snt_lst_acts_econo);
         Tnt_lst_comp_pago=(TextView)findViewById(R.id.lbl_snt_lst_comp_pago);
         Tnt_act_com_ext=(TextView)findViewById(R.id.lbl_snt_act_com_ext);
         Tnt_lst_sis_emi_elec=(TextView)findViewById(R.id.lbl_snt_lst_sis_emi_elec);
         Tnt_emi_elec=(TextView)findViewById(R.id.lbl_snt_emi_elec);
         Tnt_lst_comps_elec=(TextView)findViewById(R.id.lbl_snt_lst_comps_elec);
         Tnt_fec_ins_ple=(TextView)findViewById(R.id.lbl_snt_fec_ins_ple);
         Tnt_nro_trab=(TextView)findViewById(R.id.lbl_snt_nro_trab);
         Tnt_nro_pres_srv=(TextView)findViewById(R.id.lbl_snt_nro_pres_srv);
         Tnt_lst_rprs_legs=(TextView)findViewById(R.id.lbl_snt_lst_rprs_legs1);
         Telf_busc=(TextView)findViewById(R.id.lbl_telf_busc);



         ConsultarServicio(ruc_empresa,cod_cliente);

    }



    public  void ConsultarServicio(String ruc_empresa,String cod_cliente) {
      //  AlertEspera("Espere","Cargando...");
        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                "http://idsierp.dyndns.org:5000/api/Cliente/ObtenerClientePorCodigo?ruc_empresa="+ruc_empresa+"&cod_cliente="+cod_cliente,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            ClienteTarget clienteTarget= new ClienteTarget();


                           // clienteTarget.setRuc_empresa(response.getString("ruc_empresa"));
                            clienteTarget.setTipo_documento(response.getString("tipo_documento"));
                            clienteTarget.setNro_documento(response.getString("nro_documento"));
                            clienteTarget.setRazon_social(response.getString("razon_social"));
                            clienteTarget.setNombre(response.getString("nombre"));
                            clienteTarget.setDireccion(response.getString("direccion"));
                            clienteTarget.setPais(response.getString("pais"));
                            clienteTarget.setUbigeo(response.getString("ubigeo"));


                            if (response.getString("telefono1")==null){

                                clienteTarget.setTelefono1("-");
                            }else {
                                clienteTarget.setTelefono1(response.getString("telefono1"));

                            }

                            if ((response.getString("telefono2")==null)){

                                clienteTarget.setTelefono2("-");
                            }else {

                                clienteTarget.setTelefono2(response.getString("telefono2"));
                            }

                            if(response.getString("correo")==null){
                                clienteTarget.setCorreo("-");
                                AlertaAdvertencia("debug", response.getString("telefono2") );

                            }else {
                                clienteTarget.setCorreo(response.getString("correo"));
                            }






                            clienteTarget.setSnt_tip_cntrb(response.getString("snt_tip_cntrb"));
                            clienteTarget.setSnt_fec_inscrip(response.getString("snt_fec_inscrip"));
                            clienteTarget.setSnt_ini_act(response.getString("snt_ini_act"));
                            clienteTarget.setSnt_fec_baja(response.getString("snt_fec_baja"));
                            clienteTarget.setSnt_emi_elec(response.getString("snt_emi_elec"));
                            clienteTarget.setSnt_est_cntrb(response.getString("snt_est_cntrb"));
                            clienteTarget.setSnt_cond_cntrb(response.getString("snt_cond_cntrb"));
                            clienteTarget.setSnt_sis_contab(response.getString("snt_sis_contab"));
                            clienteTarget.setSnt_emi_comp(response.getString("snt_emi_comp"));
                            clienteTarget.setSnt_lst_comp_pago(response.getString("snt_lst_comp_pago"));
                            clienteTarget.setSnt_lst_comps_elec(response.getString("snt_lst_comps_elec"));
                            clienteTarget.setSnt_fec_ins_ple(response.getString("snt_fec_ins_ple"));







                            clienteTarget.setSnt_lst_acts_econo(response.getString("snt_lst_acts_econo"));
                            clienteTarget.setTelf_busc(response.getString("telf_busc"));
                            clienteTarget.setSnt_lst_sis_emi_elec(response.getString("snt_lst_sis_emi_elec"));
                            clienteTarget.setSnt_act_com_ext(response.getString("snt_act_com_ext"));
                            clienteTarget.setSnt_lst_rprs_legs(response.getString("snt_lst_rprs_legs"));
                            clienteTarget.setSnt_nro_trab(response.getString("snt_nro_trab"));
                            clienteTarget.setSnt_nro_pres_srv(response.getString("snt_nro_pres_srv"));




                           // AlertaAdvertencia("json",clienteTarget.getSnt_lst_rprs_legs());
                            enviar(clienteTarget);
                          //  ListarCliente(listaCliente);
                           // progressDialog.dismiss();

                        }catch (Exception ex) {

                            ex.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

              //  progressDialog.dismiss();
                Toast.makeText(getApplicationContext(), "error" +error.getMessage(), Toast.LENGTH_SHORT).show();
            }


        });

        RQue.add(Requeste);


    }



    public  void enviar( ClienteTarget clienteTarget){

      //  lbl_ruc.setText(clienteTarget.getRuc_empresa().toString());
        lbl_tipoDoc.setText(clienteTarget.getTipo_documento().toString());
        lbl_nrodoc.setText(clienteTarget.getNro_documento().toString());
        lbl_razonsocial.setText(clienteTarget.getRazon_social().toString());
        lbl_nombre.setText(clienteTarget.getNombre().toString());
        lbl_direccion.setText(clienteTarget.getDireccion().toString());
        lbl_pais.setText(clienteTarget.getPais().toString());
        lbl_ubigeo.setText(clienteTarget.getUbigeo().toString());
        lbl_telefono1.setText(clienteTarget.getTelefono1().toString());
        lbl_telefono2.setText(clienteTarget.getTelefono2().toString());
        lbl_correo.setText(clienteTarget.getCorreo().toString());



        Tsnt_tip_cntrb.setText(clienteTarget.getSnt_tip_cntrb().toString());
        Tsnt_fec_inscrip.setText(clienteTarget.getSnt_fec_inscrip().toString());
        Tsnt_ini_act.setText(clienteTarget.getSnt_ini_act().toString());
        Tsnt_fec_baja.setText(clienteTarget.getSnt_fec_baja().toString());
        Tnt_est_cntrb.setText(clienteTarget.getSnt_est_cntrb().toString());
        Tnt_cond_cntrb.setText(clienteTarget.getSnt_cond_cntrb().toString());
        Tnt_emi_comp.setText(clienteTarget.getSnt_emi_comp().toString());
        Tnt_sis_contab.setText(clienteTarget.getSnt_sis_contab().toString());
        Tnt_lst_acts_econo.setText(clienteTarget.getSnt_lst_acts_econo().toString());
        Tnt_lst_comp_pago.setText(clienteTarget.getSnt_lst_comp_pago().toString());
        Tnt_act_com_ext.setText(clienteTarget.getSnt_act_com_ext().toString());
        Tnt_lst_sis_emi_elec.setText(clienteTarget.getSnt_lst_sis_emi_elec().toString());
        Tnt_emi_elec.setText(clienteTarget.getSnt_emi_elec().toString());
        Tnt_lst_comps_elec.setText(clienteTarget.getSnt_lst_comps_elec().toString());
        Tnt_fec_ins_ple.setText(clienteTarget.getSnt_fec_ins_ple().toString());
        Tnt_nro_trab.setText(clienteTarget.getSnt_nro_trab().toString());
        Tnt_nro_pres_srv.setText(clienteTarget.getSnt_nro_pres_srv().toString());
        Tnt_lst_rprs_legs.setText(clienteTarget.getSnt_lst_rprs_legs().toString());
        Telf_busc.setText(clienteTarget.getTelf_busc().toString());

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

}
