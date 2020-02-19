package com.example.usuario.idsiapp.Common;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.example.usuario.idsiapp.Models.tb_geolocalizacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class GeoManager extends Activity {
    private Context context;
    public double Lon;
    public double Lat;
    FirebaseFirestore db;
    private String Estado;

    private String telf_operadora,telf_nro,telf_imei,telf_marca,telf_version_so;


//variables de prueba
    public double Lon_prueba;
    public double Lat_prueba;



    public GeoManager(Context context) {
        this.context = context;
        db = FirebaseFirestore.getInstance();
    }


    public String ObtenerNielBateria() {
        BatteryManager bm = (BatteryManager) context.getSystemService(BATTERY_SERVICE);
        int batLevel = bm.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
        return batLevel + "%";
    }

    private void ObtenerDatosTelefono() {
        try {
            final TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(context.TELEPHONY_SERVICE);
            //if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                //Hacemos la validación de métodos, ya que el método getDeviceId() ya no se admite para android Oreo en adelante, debemos usar el método getImei()
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    return ;
                }
                telf_operadora=telephonyManager.getNetworkOperatorName();
                telf_nro=telephonyManager.getLine1Number();
                telf_imei=telephonyManager.getDeviceId();
                telf_marca=telephonyManager.getSimOperator();
                telf_version_so=telephonyManager.getDeviceSoftwareVersion();
         //   }



        }catch (Exception ex){
            Toast.makeText(context,"Nose pudo obtener  el IMMEI  del telefono,verifique que permisos este activo.",Toast.LENGTH_LONG).show();
        }
    }

    public String ObtenerTipoConexion( ){

        String MedioConexion="";
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {

            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                 MedioConexion="WIFI";

               // Log.d("MIAPP", " Nombre red Wi-Fi: " + networkInfo.getExtraInfo());
            }else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE){
                    MedioConexion="Datos Movil";
            }

        } else {
             MedioConexion="INDEFINIDO";
        }
        return    MedioConexion;
    }


    public void ObtenerPosiscion(tb_geolocalizacion tb_geolocalizacion){
   try{
       LocationManager lm = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);
       final  boolean GpsActivo=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

       if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
       {
           return  ;
       }


       Location location=lm.getLastKnownLocation( LocationManager.GPS_PROVIDER);

       if (location!=null){

           ObtenerDatosTelefono();

           double laa=location.getLatitude();
           double loo=location.getLongitude();
           GeoPoint geoPoint= new GeoPoint(loo,laa);
           tb_geolocalizacion.setPosicion(geoPoint);
           tb_geolocalizacion.setTip_conexion(ObtenerTipoConexion());
           tb_geolocalizacion.setPorcentaje_bateria(ObtenerNielBateria());
           tb_geolocalizacion.setTelf_operadora(telf_operadora);
           tb_geolocalizacion.setTelf_nro(telf_nro);
           tb_geolocalizacion.setTelf_imei(telf_imei);
           tb_geolocalizacion.setTelf_marca(telf_marca);
           tb_geolocalizacion.setTelf_version_so(telf_version_so);

           /*
           tb_geolocalizacion.setTelf_operadora("");
           tb_geolocalizacion.setTelf_nro("");
           tb_geolocalizacion.setTelf_imei("");
           tb_geolocalizacion.setTelf_marca("");
           tb_geolocalizacion.setTelf_version_so("");
       */
           Crea_Edita_posiscion(tb_geolocalizacion);
       }else {

           Toast.makeText(context,"No se pudo obtener  tu localización, verifique su conexion de internet y GPS",Toast.LENGTH_LONG).show();
       }

   }catch (Exception e){
       Toast.makeText(context,"No se pudo obtener tu ubicación,verifique su conexion de internet y GPS"+e.getMessage(),Toast.LENGTH_LONG).show();
   }
    }


    public void ObtenerPosiscion2(tb_geolocalizacion tb_geolocalizacion){
        try{

            LocationManager lm = (LocationManager)context.getSystemService(context.LOCATION_SERVICE);

             boolean GpsTelActivo=lm.isProviderEnabled(LocationManager.GPS_PROVIDER);

            // boolean GpsNetActivo=lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED)
            {
                return  ;
            }


/*
            if (!GpsNetActivo){

                Toast.makeText(context,"verifique su conexion de internet",Toast.LENGTH_LONG).show();
                return;
            }
            */

            if (!GpsTelActivo){

                Toast.makeText(context," verifique si su  GPS esta activo.",Toast.LENGTH_LONG).show();
                return;
            }

/*
            if (!GpsTelActivo  && !GpsNetActivo ){

                Toast.makeText(context,"verifique su conexion de internet y GPS",Toast.LENGTH_LONG).show();
                return;
            }
            */

            /*
                    if (GpsNetActivo ){

                        if (lm!=null){

                            Location locationNetGps=lm.getLastKnownLocation( LocationManager.NETWORK_PROVIDER);

                            if (locationNetGps!=null){
                                ObtenerDatosTelefono();
                                double laa=locationNetGps.getLatitude();
                                double loo=locationNetGps.getLongitude();
                                GeoPoint geoPoint= new GeoPoint(loo,laa);
                                tb_geolocalizacion.setPosicion(geoPoint);
                                tb_geolocalizacion.setTip_conexion(ObtenerTipoConexion());
                                tb_geolocalizacion.setPorcentaje_bateria(ObtenerNielBateria());
                                tb_geolocalizacion.setTelf_operadora(telf_operadora);
                                tb_geolocalizacion.setTelf_nro(telf_nro);
                                tb_geolocalizacion.setTelf_imei(telf_imei);
                                tb_geolocalizacion.setTelf_marca(telf_marca);
                                tb_geolocalizacion.setTelf_version_so(telf_version_so);
                                //Toast.makeText(context,"obtenenido de gpsNet",Toast.LENGTH_LONG).show();
                                Crea_Edita_posiscion(tb_geolocalizacion);
                            }


                        }
                    }

                    */

                    if (GpsTelActivo){

                        if (lm!=null){
                            Location locationTelGps=lm.getLastKnownLocation( LocationManager.GPS_PROVIDER);


                            if (locationTelGps==null ){
                             //CQ:19-02-2020   Toast.makeText(context,"el telefono devuelve un valor nulo pese a estar activo el gps ",Toast.LENGTH_LONG).show();
                            return;
                            }


                            Toast.makeText(context,"Lat:"+locationTelGps.getLatitude()+"- Lng:"+locationTelGps.getLongitude(),Toast.LENGTH_LONG).show();



                            if (locationTelGps!=null){
                                ObtenerDatosTelefono();
                                double laa=locationTelGps.getLatitude();
                                double loo=locationTelGps.getLongitude();
                                GeoPoint geoPoint= new GeoPoint(loo,laa);
                                tb_geolocalizacion.setPosicion(geoPoint);
                                tb_geolocalizacion.setTip_conexion(ObtenerTipoConexion());
                                tb_geolocalizacion.setPorcentaje_bateria(ObtenerNielBateria());
                                tb_geolocalizacion.setTelf_operadora(telf_operadora);
                                tb_geolocalizacion.setTelf_nro(telf_nro);
                                tb_geolocalizacion.setTelf_imei(telf_imei);
                                tb_geolocalizacion.setTelf_marca(telf_marca);
                                tb_geolocalizacion.setTelf_version_so(telf_version_so);
                                //Toast.makeText(context,"obtenenido de gpsTel ",Toast.LENGTH_LONG).show();
                                Crea_Edita_posiscion(tb_geolocalizacion);
                            }
                        }
                    }




        }catch (Exception e){
            Toast.makeText(context,"No pudimos reportar tu ubicación, verifique su conexion de internet y GPS"+e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }




    public void Crea_Edita_posiscion(final tb_geolocalizacion tb_geolocalizacion){

        try{

            DocumentReference Lista= db.collection("tb_geolocalizacion").document(tb_geolocalizacion.getId());
            Lista.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) { //PV: valida que docuemnto exista en firebase

                            try {

                                Map<String, Object> data = new HashMap<>();
                                data.put("medio_intenet", tb_geolocalizacion.getTip_conexion());
                                data.put("fecha",FieldValue.serverTimestamp());
                                data.put("p_bateria", tb_geolocalizacion.getPorcentaje_bateria());
                                data.put("posicion", tb_geolocalizacion.getPosicion());
                                data.put("estado_online", tb_geolocalizacion.isEstado());
                                data.put("estado_alertado",true);

                                DocumentReference washingtonRef = db.collection("tb_geolocalizacion").document(tb_geolocalizacion.getId());
                                washingtonRef.update(data)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Toast.makeText(context,"Posición Actualizada",Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(context,"Error al actualizar pocision",Toast.LENGTH_LONG).show();
                                            }
                                        });

                            }catch (Exception ex){
                                Toast.makeText(context,"Error al actualizar pocision"+ex.getMessage(),Toast.LENGTH_LONG).show();
                            }

                        } else {

                            try {
                                Map<String, Object> data = new HashMap<>();
                                data.put("cod_vendedor", tb_geolocalizacion.getCod_vendedor());

                                //data.put("fecha", tb_geolocalizacion.getFecha());

                                data.put("fecha",FieldValue.serverTimestamp()) ;
                                data.put("medio_intenet",tb_geolocalizacion.getTip_conexion() );
                                data.put("nombres", tb_geolocalizacion.getNombres());
                                data.put("p_bateria", tb_geolocalizacion.getPorcentaje_bateria() );
                                data.put("posicion", tb_geolocalizacion.getPosicion() );
                                data.put("ruc_empresa", tb_geolocalizacion.getRuc_empresa() );
                                data.put("usuario", tb_geolocalizacion.getUsuario());

                                data.put("telf_operadora", tb_geolocalizacion.getTelf_operadora());
                                data.put("telf_nro",tb_geolocalizacion.getTelf_nro());
                                data.put("telf_imei", tb_geolocalizacion.getTelf_imei());
                                data.put("telf_marca",tb_geolocalizacion.getTelf_marca());
                                data.put("telf_version_so",tb_geolocalizacion.getTelf_version_so());
                                data.put("min_online",10);
                                data.put("estado_visible", tb_geolocalizacion.isEstado());
                                data.put("estado_activo", tb_geolocalizacion.isEstado());
                                data.put("estado_online", tb_geolocalizacion.isEstado());
                                data.put("estado_alerta", tb_geolocalizacion.isEstado());
                                data.put("estado_alertado",true);




                                String id=tb_geolocalizacion.getId();
                                CollectionReference tb_geolocalizacion = db.collection("tb_geolocalizacion");
                                tb_geolocalizacion.document(id).set(data).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        Toast.makeText(context,"Usuario suscrito geolocalización",Toast.LENGTH_LONG).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {

                                        Toast.makeText(context,"No se pudo suscribir al Usuario, por favor vuelva iniciar sessión.",Toast.LENGTH_LONG).show();
                                    }
                                });

                            }catch (Exception ex){
                                Toast.makeText(context,"No se pudo suscribir al Usuario, por favor vuelva iniciar sessión."+ex.getMessage(),Toast.LENGTH_LONG).show();
                            }


                        }
                    } else {
                        Toast.makeText(context,"Error consultar por id la existencia de obj.",Toast.LENGTH_LONG).show();
                    }

                }
            });

        }catch (Exception e){

            Toast.makeText(context,"No pudimos validar tus datos, por favor vuelva iniciar sessión." +e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }



    public class GeoGestor implements  LocationListener {
        @Override
        public void onLocationChanged(Location location) {

             Lon_prueba=location.getLongitude();
             Lat_prueba=location.getLatitude();
           //CQ:19-02-2020 Toast.makeText(context,"gps osilacion: "+Lon_prueba +"- "+Lat_prueba,Toast.LENGTH_LONG).show();

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

            //este metodo esta descartado
        }

        @Override
        public void onProviderEnabled(String provider) {
            Toast.makeText(context,"GPS Activado",Toast.LENGTH_LONG).show();
        }


        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(context,"GPS Activado",Toast.LENGTH_LONG).show();
        }
    }



}
