package com.example.usuario.idsiapp.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Common.GeoManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.tb_geolocalizacion;
import com.example.usuario.idsiapp.R;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import static java.net.Proxy.Type.HTTP;


public class PrincipalActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        RegistrarPedidoFragment.OnFragmentInteractionListener,
        ListaClienteFragment.OnFragmentInteractionListener, ListaClienteFragment.IEnviarDatos {


    private String ruc_global,usuario_global,Empresa_global,cod_vendedor_global,nomvendedor_global;
    private SessionManager sessionManager;
    private TextView  TVlbl_empresa_global,TVlbl_usuario_global ;
    private GeoManager geoManager;
    private RequestQueue RQue;
    private String id;




    private TimerTask Tarea_ActulizarPosicicion;
    Timer timer;

    final private Handler handler= new Handler();
    //f
    private  Boolean Estado_Geolocalizacion_Vendedor;
    private  Integer Intervalo_actualizacion_posicion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        sessionManager= new SessionManager(getApplicationContext());
        RQue=Volley.newRequestQueue(getApplicationContext());
        geoManager= new GeoManager(getApplicationContext());

        SolicitarPermiso();

       Estado_Geolocalizacion_Vendedor= getIntent().getBooleanExtra("ib_geolocalizacion",false);
       Intervalo_actualizacion_posicion=getIntent().getIntExtra("intevalo_consulta_seg",0);

        ruc_global=sessionManager.ObtenerRuc_Session("ruc_global");
        usuario_global=sessionManager.ObtenerUsuario_Session("usuario_global");
        Empresa_global=sessionManager.ObtenerEmpresa_Session("empresa_global");
        cod_vendedor_global=sessionManager.ObtenerCodVendedor_Session("codvendedor_global");
        nomvendedor_global=sessionManager.ObtenerNomVendedor_Session("nomvendedor_global");
        id= ruc_global+cod_vendedor_global;


        //consultar
        //Estado_Geolocalizacion_Vendedor= sessionManager.Obtenerestado_localizacion_Session("estado_localizacion_global");
        // Intervalo_actualizacion_posicion= sessionManager.Obtenerminuto_envio_localizacion_Session("tiempo_envio_localizacion_global");







        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);







        //envio de texto a constroles
        TVlbl_empresa_global= (TextView)headerView.findViewById(R.id.lbll_empresa_global);
        TVlbl_usuario_global= (TextView)headerView.findViewById(R.id.lbll_usuario_global);
        TVlbl_empresa_global.setText(sessionManager.ObtenerEmpresa_Session("empresa_global"));
        TVlbl_usuario_global.setText(sessionManager.ObtenerUsuario_Session("usuario_global"));


        //handler = new Handler();
        if(Estado_Geolocalizacion_Vendedor==true && Intervalo_actualizacion_posicion>0){

            SolicitarPermiso();
            Long  milisegundos= Long.parseLong(String.valueOf(Intervalo_actualizacion_posicion));

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    runOnUiThread(new Runnable() {
                        public void run() {

                            try{
                                if (Estado_Geolocalizacion_Vendedor){
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
                                    //geoManager.ObtenerPosiscion(tb_geolocalizacion);
                                    geoManager.ObtenerPosiscion2(tb_geolocalizacion); //METODO PARA VERSIONES ANDROID DE 6.0 A 8.0
                                }


                            }catch (Exception ex){

                                Toast.makeText(getApplicationContext(),"no se pudo enviar posicion",Toast.LENGTH_LONG);
                            }

                        }
                    });

                }
            }, 0,milisegundos);

        }








    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        Fragment FragmentoOpcion= null;
        boolean fragmentoSelectActual= false;

        if (id == R.id.nav_pedido) {
/*
            FragmentoOpcion= new RegistrarPedidoFragment();
            fragmentoSelectActual=true;
            */

            Intent I = new Intent(PrincipalActivity.this,RegistrarPedidoActivity.class);
            startActivity(I);



        }else if (id == R.id.nav_cons_stock_Grilla_reportes) {


            Intent I = new Intent(PrincipalActivity.this,ListaInvStockActivity.class);
            startActivity(I);


        }else if (id == R.id.nav_ubic_vendedores) {

            //enviar a google crome la ruta de

            Uri webpage = Uri.parse("http://idsierp.dyndns.org:5050/Maps/Index?ruc_empresa="+ruc_global);
            Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
            startActivity(webIntent);


        } else if (id == R.id.nav_producto) {

            Intent I = new Intent(PrincipalActivity.this,ListaPedidoActivity.class);
            startActivity(I);
            //EnviarPosicion();
          //  Toast.makeText(getApplicationContext(),Estado_Geolocalizacion_Vendedor+"-"+Intervalo_actualizacion_posicion,Toast.LENGTH_LONG).show();
          //  Intent I = new Intent(PrincipalActivity.this,PruebasActivity.class);
           // startActivity(I);
           // Toast.makeText(getApplicationContext(),"estado geo"+Estado_Geolocalizacion_Vendedor,Toast.LENGTH_LONG).show();
           // Toast.makeText(getApplicationContext(),"milisegundos"+Intervalo_actualizacion_posicion,Toast.LENGTH_LONG).show();
          //  Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
           // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
           // Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();


        } else if (id == R.id.nav_cliente) {

            Intent I = new Intent(PrincipalActivity.this,ListaClienteTargetActivity.class);
            startActivity(I);

        } else if (id == R.id.nav_cons_reportes) {

            Intent I = new Intent(PrincipalActivity.this,ListaInvProdStockActivity.class);
            startActivity(I);

            /*
            Intent I = new Intent(PrincipalActivity.this,ListaInvStockActivity.class);
            startActivity(I);
            */

        } else if (id == R.id.nav_share) {
            AlertaAdvertencia_Logout("Cierre de Session","¿Esta seguro de cerrar session?");
           // Tarea_ActulizarPosicicion.cancel();
           // handler.removeMessages(0);

        }

        if (fragmentoSelectActual==true){

           getSupportFragmentManager().beginTransaction().replace(R.id.content_principal, FragmentoOpcion).commit();
        }




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    @Override
    public void onFragmentInteraction(Uri uri) {

    }



    @Override
    public void ObtenerDatosCliente(String cod_cliente, String nombre_cliente) {


        try{

            FragmentManager ReFragment=getSupportFragmentManager();

                RegistrarPedidoFragment registrarPedidoFragment=(RegistrarPedidoFragment)ReFragment.findFragmentById(R.id.RegistrarPedidoFragmentID) ;
                registrarPedidoFragment.RecibirParametro( cod_cliente,nombre_cliente);

             // ReFragment.beginTransaction().show(registrarPedidoFragment).commit();


        }catch (Exception ex){ ex.getMessage();}


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



    public void AlertaAdvertencia_Logout(String Titulo,String Mensaje){

        AlertDialog.Builder Alert= new AlertDialog.Builder(this);
        Alert.setIcon(R.mipmap.ic_launcher_logo);
        Alert.setTitle(Titulo);
        Alert.setMessage(Mensaje);
        Alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                finish();
                sessionManager.LogOut();


            }
        });
        Alert.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


            }
        });

        AlertDialog alertDialog=Alert.create();
        alertDialog.show();
    }








    public void  SolicitarPermiso(){
        int permisoGPS =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_COARSE_LOCATION);
        int permisoGPS2 =ActivityCompat.checkSelfPermission(getApplicationContext(),Manifest.permission.ACCESS_FINE_LOCATION);

        if (permisoGPS!= PackageManager.PERMISSION_GRANTED || permisoGPS2!= PackageManager.PERMISSION_GRANTED ){


            AlertDialog.Builder Alert= new AlertDialog.Builder(this);
            Alert.setIcon(R.mipmap.ic_launcher_logo);
            Alert.setTitle("Permisos de GPS");
            Alert.setMessage("Estimado usuario,por favor active el GPS del telefono.");
            Alert.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION},1);

                }
            });
            AlertDialog alertDialog=Alert.create();
            alertDialog.show();
        }
    }











    public  void  EnviarPosicion(){

/*
        TimerTask timerTask= new TimerTask() {
            @Override
            public void run() {

                AsyncTask asyncTask= new AsyncTask() {
                    @Override
                    protected Object doInBackground(Object[] objects) {


                        new Handler (Looper.getMainLooper()).post(new Runnable() {
                            @Override
                            public void run() {

                                try{


                                    Toast.makeText(getApplicationContext(),"igreso a metodo",Toast.LENGTH_LONG);
                                    if (Estado_Geolocalizacion_Vendedor){
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
                                        //geoManager.ObtenerPosiscion(tb_geolocalizacion);
                                        geoManager.ObtenerPosiscion2(tb_geolocalizacion); //METODO PARA VERSIONES ANDROID DE 6.0 A 8.0

                                    }


                                }catch (Exception ex){

                                    AlertaAdvertencia("Error","Error, al enviar posicion");
                                }


                            }
                        });

                    return  null;

                    }
                };
                asyncTask.execute();

            }

        };
        tiempo.schedule(timerTask,0,milisegundos);


*/







/*
         timer = new Timer();
        TimerTask Tarea_ActulizarPosicicion = new TimerTask() {
            @Override
            public void run() {
                try{
                    if (Estado_Geolocalizacion_Vendedor){
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
                        //geoManager.ObtenerPosiscion(tb_geolocalizacion);
                        geoManager.ObtenerPosiscion2(tb_geolocalizacion); //METODO PARA VERSIONES ANDROID DE 6.0 A 8.0
                    }

                }catch (Exception ex){

                    Toast.makeText(getApplicationContext(),"no se pudo enviar posicion",Toast.LENGTH_LONG);
                }

            }
        };

        timer.schedule(Tarea_ActulizarPosicicion, 0, 1000*10); //execute in every 50000 ms
        */


    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
       timer.cancel();

      sessionManager.LogOut_force();

    }


}

