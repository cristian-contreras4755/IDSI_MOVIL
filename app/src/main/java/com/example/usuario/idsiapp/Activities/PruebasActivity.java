package com.example.usuario.idsiapp.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.idsiapp.Common.AlertManager;
import com.example.usuario.idsiapp.Common.GeoManager;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.tb_geolocalizacion;
import com.example.usuario.idsiapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;


public class PruebasActivity extends AppCompatActivity {

    private Button btn_ejecutar, btn_run, btn_modelo;
    private AlertManager alertManager;
    private AlertDialog.Builder Alert;
    private SessionManager sessionManager;
    private NetWorkManager netWorkManager;

    // FirebaseDatabase firebaseDatabase;
    // DatabaseReference databaseReference;



    FirebaseFirestore db;

    private GeoManager geoManager;


    private TextView lbl_latitud, lbl_longitud, lbl_estado, lbl_est_cambio;

    private  Boolean Estado_Geolocalizacion_Vendedor;
    private  int Intervalo_actualizacion_posicion;

    private String ruc_global,usuario_global,Empresa_global,cod_vendedor_global,nomvendedor_global,id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas);
       // SolicitarPermiso();
        alertManager = new AlertManager();
        Alert = new AlertDialog.Builder(getApplicationContext());
        geoManager = new GeoManager(getApplicationContext());
        sessionManager= new SessionManager(getApplicationContext());
        netWorkManager= new NetWorkManager(getApplicationContext());


        ruc_global=sessionManager.ObtenerRuc_Session("ruc_global");
        usuario_global=sessionManager.ObtenerUsuario_Session("usuario_global");
        Empresa_global=sessionManager.ObtenerEmpresa_Session("empresa_global");
        cod_vendedor_global=sessionManager.ObtenerCodVendedor_Session("codvendedor_global");
        nomvendedor_global=sessionManager.ObtenerNomVendedor_Session("nomvendedor_global");
        id= ruc_global+cod_vendedor_global;


        btn_ejecutar = (Button) findViewById(R.id.btn_ejecutar);
        db = FirebaseFirestore.getInstance();

        btn_ejecutar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                try {

                    tb_geolocalizacion tb_geolocalizacion= new tb_geolocalizacion();
                    tb_geolocalizacion.setId(id);
                    tb_geolocalizacion.setCod_vendedor(cod_vendedor_global);
                    tb_geolocalizacion.setEstado(true);
                    Date date= new Date();
                    date.getDate();
                    tb_geolocalizacion.setFecha(date);
                    tb_geolocalizacion.setNombres(nomvendedor_global);
                    tb_geolocalizacion.setRuc_empresa(ruc_global);
                    tb_geolocalizacion.setUsuario(usuario_global);
                    geoManager.ObtenerPosiscion2(tb_geolocalizacion);

                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),"Error Al Enviar Posicion"+ex.getMessage(),Toast.LENGTH_LONG).show();
                }
                /*

                DocumentReference Lista= db.collection("tb_geolocalizacion").document("20160000001VND0006");
                Lista.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                Toast.makeText(getApplicationContext(),"DocumentSnapshot data:"+document.getData(),Toast.LENGTH_LONG).show();
                            } else {

                                Map<String, Object> data = new HashMap<>();
                                Date date= new Date();
                                date.getDate();

                                GeoPoint geoPoint= new GeoPoint(0,0);

                                data.put("cod_vendedor", "VND0006");
                                data.put("estado", true);
                                data.put("fecha", date);
                                data.put("medio_intenet", "WIFI");
                                data.put("nombres", "X");
                                data.put("p_bateria", "100%");
                                data.put("posicion", geoPoint);
                                data.put("ruc_empresa", "20160000001");
                                data.put("usuario", "usu06");
                                CollectionReference tb_geolocalizacion = db.collection("tb_geolocalizacion");
                                tb_geolocalizacion.document("20160000001VND0006").set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(getApplicationContext(),"Dato Registrado",Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(getApplicationContext(),"Error Al registrar Usuario",Toast.LENGTH_LONG).show();
                                    }
                                });

                               // Toast.makeText(getApplicationContext(),"No such document",Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(),"Exception"+task.getException(),Toast.LENGTH_LONG).show();
                        }

                    }
                });



                */

            }
        });



       /*
        btn_run.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                 //   SolicitarPermiso();
                 //   geoManager.ObtenerPosiscion("20160000001VND0001");
                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(),""+ex.getMessage(),Toast.LENGTH_LONG).show();
                }


            }
        });

        */


    }




    private void obtenerCoordenadas() {


        try{

            LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
            final  boolean GpsActivo=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
/*

            LocationListener locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    lbl_latitud.setText(""+location.getLatitude());
                    lbl_longitud.setText(""+location.getLatitude());

                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                    Toast.makeText(getApplicationContext(),"ACTIVO",Toast.LENGTH_LONG).show();
                }

                @Override
                public void onProviderDisabled(String provider) {
                    Toast.makeText(getApplicationContext(),"DESACTIVO",Toast.LENGTH_LONG).show();

                }
            };

*/


            if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }


            // Location location=lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            //        lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
          //  lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

            Location location=lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

            lbl_latitud .setText(""+location.getLatitude());
            lbl_longitud.setText(""+location.getLongitude());


        }catch(Exception e){

            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();

        }



        final Handler handler = new Handler();

        Timer timer=new Timer();
        TimerTask Tarea_ActulizarPosicicion= new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        try {

                            if (Estado_Geolocalizacion_Vendedor){
                              ///  geoManager.ObtenerPosiscion();
                            }


                        }catch (Exception e){
                            Toast.makeText(getApplicationContext(),"Error al enviar posicion",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        };

        timer.schedule(Tarea_ActulizarPosicicion,0,6000);


    }





    public void  SolicitarPermiso(){

         int permisoGPS =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION);
         int permisoGPS2 =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION);
         int permiso_Internet =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_NETWORK_STATE);


         if (permisoGPS!= PackageManager.PERMISSION_GRANTED ||permisoGPS2  != PackageManager.PERMISSION_GRANTED){

                   requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},100);
         }

    }



    public void  SolicitarPermisoPrueba(){

        int permisoGPS =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION);
        int permisoGPS2 =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION);
        // int permiso_Internet =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_NETWORK_STATE);


        if (permisoGPS!= PackageManager.PERMISSION_GRANTED ||
                permisoGPS2!= PackageManager.PERMISSION_GRANTED  /*||
                 permiso_Internet != PackageManager.PERMISSION_GRANTED */){

            AlertDialog.Builder Alert= new AlertDialog.Builder(getApplicationContext());
            Alert.setIcon(R.mipmap.ic_launcher_logo);
            Alert.setTitle("Permisos de GPS");
            Alert.setMessage("Estimado usuario,por favor active el GPS del telefono.");
            Alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},100);
                }
            });
            AlertDialog alertDialog=Alert.create();
            alertDialog.show();
        }
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 100: {

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getApplicationContext(),"permiso activado",Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),"permiso denegado",Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }




}