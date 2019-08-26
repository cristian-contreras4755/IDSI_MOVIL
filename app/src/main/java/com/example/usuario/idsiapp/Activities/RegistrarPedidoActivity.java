package com.example.usuario.idsiapp.Activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpResponse;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Adapters.AdaptadorDetallePedido;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.PedidoDetModel;
import com.example.usuario.idsiapp.Models.PedidoModel;
import com.example.usuario.idsiapp.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

//import  com.google.firebase.datad

public class RegistrarPedidoActivity extends AppCompatActivity /*implements View.OnClickListener */{


    private ImageButton btn_BuscarCliente;
    private ImageButton btn_BuscarProducto;
    private ImageButton btn_BuscarFechaEntrega;
    private EditText txt_cantidadpiker;

    private NetWorkManager netWorkManager;



    private String  ruc_empresa;
    private String  usuario_session;
    private String  cod_vendedor_session;
    private TextView txt_nombreProd;
    private TextView txt_cantidadprod;

    private TextView  txt_nombreCliente;
    private TextView  txt_CodigoCliente;

    private TextView   txt_observaciones;
    private TextView txt_precioProd_hide;
    private TextView txt_codigoprod_hide;
    private TextView txt_codigoClient_hide;

    private Button btn_mas;
    private Button btn_menos;

    public Integer Cantidad;



    //variables de cabecera
    private TextView  txt_fechaEntrega;
    private TextView  txt_direc_entrega;



    Calendar calendar;
    DatePickerDialog dtpd;



    static final int Codigo_result_Prod = 1;
    static final int Codigo_result_Cli= 2;

    private RequestQueue RQue;
    private Button btn_agregarItemDetalle;
    private Button btn_registrarPedido;
    ListView lv_detallePedido;


    Date FechaActual;




    ArrayList<PedidoDetModel> ListaDetallePedido;

    private AdaptadorDetallePedido adaptadorDetallePedido;

    //controles totales
    TextView lbl_subtotal,lbl_igv,lbl_total;




    private SessionManager sessionManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_pedido);
        sessionManager= new SessionManager(getApplicationContext());
        netWorkManager= new NetWorkManager(getApplicationContext());



        lbl_subtotal=(TextView) findViewById(R.id.lbl_subtotal);
        lbl_igv=(TextView) findViewById(R.id.lbl_igvtotal);
        lbl_total=(TextView) findViewById(R.id.lbl_total);



        lv_detallePedido=(ListView)findViewById(R.id.lv_detalle);

        RQue=Volley.newRequestQueue(getApplicationContext());

        //ruc_empresa="20160000001";  pruebas
        //obteniendo
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");
        usuario_session=sessionManager.ObtenerUsuario_Session("usuario_global");
        cod_vendedor_session=sessionManager.ObtenerCodVendedor_Session("codvendedor_global");

        btn_BuscarCliente=(ImageButton)findViewById(R.id.IB_buscarCliene);
        btn_BuscarProducto=(ImageButton)findViewById(R.id.IB_buscarProd);
        txt_nombreProd=(TextView)findViewById(R.id.txt_nombreProd);
        txt_cantidadprod=(TextView)findViewById(R.id.txt_CantProd);
        txt_nombreCliente=(TextView)findViewById(R.id.txt_nombreCliente);
        txt_observaciones=(TextView)findViewById(R.id.txt_observaciones);
        txt_cantidadpiker=(EditText) findViewById(R.id.txt_cantidadproducto);

        btn_mas=(Button)findViewById(R.id.btn_mas);
        btn_menos=(Button)findViewById(R.id.btn_menos);



        //variables de cabecera de pedido

        txt_fechaEntrega=(TextView)findViewById(R.id.txt_fechaEntrega);
        txt_direc_entrega=(TextView)findViewById(R.id.txt_direc_entrega);


        txt_precioProd_hide=(TextView)findViewById(R.id.txt_precioProd_hide);
        txt_codigoprod_hide=(TextView)findViewById(R.id.txt_codigoprod_hide);
        txt_codigoClient_hide=(TextView)findViewById(R.id.txt_codigoClient_hide);




        ListaDetallePedido= new ArrayList<PedidoDetModel>();
        btn_agregarItemDetalle=(Button)findViewById(R.id.btn_anadir_item );
        btn_registrarPedido=(Button)findViewById(R.id.btn_registrarPedido);
        btn_BuscarFechaEntrega=(ImageButton)findViewById(R.id.IB_seleccionafecha);


        //inicializamos las fechas
        FechaActual= new Date();


/*
        txt_cantidadpiker.setMinValue(1);
        txt_cantidadpiker.setMaxValue(1000);
        txt_cantidadpiker.setBackgroundColor(1);

        txt_cantidadpiker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                txt_cantidadpiker.setValue(picker.getValue());
            }
        });
*/

        Cantidad=1;

        txt_cantidadpiker.setText( Cantidad.toString());
        btn_mas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               // Toast.makeText(getApplicationContext(), "El producto: "+Cantidad, Toast.LENGTH_SHORT).show();

                try{
                    if (txt_cantidadpiker.getText().toString().length()==0||txt_cantidadpiker.getText().toString().equals("0")){
                        txt_cantidadpiker.setText("1");
                    }

                    Cantidad=Integer.parseInt(txt_cantidadpiker.getText().toString());
                    if (Cantidad>=0){
                        Cantidad=Cantidad+1;
                        txt_cantidadpiker.setText( Cantidad.toString());
                    }


                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "mensaje "+ex.getMessage(), Toast.LENGTH_SHORT).show();

                }



            }
        });

        btn_menos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{

                    if (txt_cantidadpiker.getText().toString().length()==0||txt_cantidadpiker.getText().toString().equals("0")){
                        txt_cantidadpiker.setText("1");
                    }
                    Cantidad=Integer.parseInt(txt_cantidadpiker.getText().toString());

                    if (Cantidad<=1){
                        Cantidad=1;
                        txt_cantidadpiker.setText( Cantidad.toString());
                    }else {
                        Cantidad=Cantidad-1;
                        txt_cantidadpiker.setText( Cantidad.toString());
                    }



                }catch (Exception ex){
                    Toast.makeText(getApplicationContext(), "mensaje "+ex.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        });


        txt_cantidadpiker.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if( txt_cantidadpiker.getText().toString().equals("0")){
                    txt_cantidadpiker.setText("1");
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });







        btn_BuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle  bundle_ListarClieActivity = new Bundle();
                bundle_ListarClieActivity.putString("ruc_empresa",ruc_empresa);

                Intent I= new Intent(RegistrarPedidoActivity.this,ListaClienteActivity.class);
                I.putExtras(bundle_ListarClieActivity);
                startActivityForResult(I,Codigo_result_Cli);

            }
        });







        btn_BuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle  bundle_ListarProdActivity = new Bundle();
                bundle_ListarProdActivity.putString("ruc_empresa",ruc_empresa);

                Intent I= new Intent(RegistrarPedidoActivity.this,ListaProductoActivity.class);
                I.putExtras(bundle_ListarProdActivity);
                startActivityForResult(I,Codigo_result_Prod);
            }
        });




        lv_detallePedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final PedidoDetModel objpedidoDet=(PedidoDetModel)lv_detallePedido.getItemAtPosition(position);

                EliminarItem(position);

                //yo

                //Toast.makeText(getApplicationContext(), "N:"+objpedidoDet.getDescripcion(), Toast.LENGTH_SHORT).show();

            }
        });









        btn_BuscarFechaEntrega.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                calendar = Calendar.getInstance();
                calendar.setTime(new Date());
                int Dia=calendar.get(Calendar.DAY_OF_MONTH);
                int Mes=calendar.get(Calendar.MONTH);
                int Anio=calendar.get(Calendar.YEAR);

                dtpd= new DatePickerDialog(RegistrarPedidoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {

                      int mes=  datePicker.getMonth();
                       // Toast.makeText(getApplicationContext(), "El producto:"+mes, Toast.LENGTH_LONG).show();
                        txt_fechaEntrega.setText(dayOfMonth+"/"+(month+1)+"/"+year);
                    }
                },Anio,Mes,Dia);



                dtpd.show();

            }
        });






        btn_agregarItemDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {


                    if ( ValidarControlesDetallePedido()==true){


                        String Nombreproducto=txt_nombreProd.getText().toString();
                        Integer Cantidadproducto=Integer.parseInt(txt_cantidadpiker.getText().toString());
                        String CodigoProducto=txt_codigoprod_hide.getText().toString();
                        Double Precioproducto=(Double.parseDouble(  txt_precioProd_hide.getText().toString()));


                        if (ValidarProductosRepetidos(CodigoProducto)==true){

                            Toast.makeText(getApplicationContext(), "El producto: "+Nombreproducto+" ya existe en lista!", Toast.LENGTH_LONG).show();
                        }else if(txt_cantidadpiker.getText().toString().length()==0||txt_cantidadpiker.getText().toString()=="0"){

                            Toast.makeText(getApplicationContext(), "Ingrese  la cantidad  de producto a pedir", Toast.LENGTH_LONG).show();
                        }else  {
                            //calculo de y  02 decimales impuestos

                            double subtotalItem=FormateoDecimales((Cantidadproducto*Precioproducto),2);
                            double IGVItem=FormateoDecimales(((Cantidadproducto*Precioproducto)*0.18),2);
                            double TotalItem= FormateoDecimales(subtotalItem+IGVItem,2);


                            //calculo de  impuestos

                            PedidoDetModel pedidoDetModel= new PedidoDetModel();

                            pedidoDetModel.setItem(1);
                            pedidoDetModel.setCod_producto(CodigoProducto);
                            pedidoDetModel.setCod_servicio(null);
                            pedidoDetModel.setDescripcion(Nombreproducto);
                            pedidoDetModel.setCod_unidad_medida(1);
                            pedidoDetModel.setPrecio_unitario(Precioproducto);
                            pedidoDetModel.setCantidad(Cantidadproducto);
                            pedidoDetModel.setValor(subtotalItem);
                            pedidoDetModel.setDsctoP(1);
                            pedidoDetModel.setDsctoI(1);
                            pedidoDetModel.setBim(1);
                            pedidoDetModel.setIgv(IGVItem);
                            pedidoDetModel.setTotal(TotalItem);
                            pedidoDetModel.setObservacion("");
                            pedidoDetModel.setValor_venta_unitario(TotalItem);
                            pedidoDetModel.setTotalVtaSD(1);
                            pedidoDetModel.setPrecioUnitSD(1);
                            pedidoDetModel.setPendEnt(1);
                            //pedidoDetModel.setCod_almacen("");
                            pedidoDetModel.setFecha_modificacion(FechaActual);
                            pedidoDetModel.setUsuario_modifica(usuario_session);
                            pedidoDetModel.setCod_centro_costo("01010101");
                            pedidoDetModel.setCod_centro_costo_sub("01010101");
                            pedidoDetModel.setCod_centro_costo_sub_sub("01010101");

                            AgregarItem(pedidoDetModel);
                            CalcularTotalesPedido();
                            RefrescarDetallePedido();
                            LimpiarControlesDetallePedido();

                        }
                    }

                }catch (Exception Ex){

                    Ex.getMessage();
                }

            }





        });




        btn_registrarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{




                    if (ValidarControlesCabeceraPedido()){
                        //validacion ValidarControlesCabeceraPedido();

                        if (netWorkManager.Is_Online()){

                        PedidoModel pedidoModel= new PedidoModel();
                        pedidoModel.setRuc_empresa(ruc_empresa);
                        pedidoModel.setNro_orden_pedido(null);//lo  pone la la empresa

                        pedidoModel.setFecha_emision(FechaActual);
                        pedidoModel.setCod_forma_pago("01");//forma de pago al escoger
                        pedidoModel.setCod_vendedor(cod_vendedor_session);//tiene que ser el usuario de sistema
                        pedidoModel.setCod_area("010101");
                        //pedidoModel.setCod_cte(""); null
                        pedidoModel.setDir_entrega(txt_direc_entrega.getText().toString());
                        pedidoModel.setCod_cliente(txt_codigoClient_hide.getText().toString());
                        pedidoModel.setFecha_entrega(FechaActual);//deberia seleccionar fecha de entrega en el form
                        pedidoModel.setObservacion(txt_observaciones.getText().toString());


                        pedidoModel.setValor_total(Double.parseDouble(lbl_subtotal.getText().toString()));
                        pedidoModel.setTotal_Dscto_p(0);
                        pedidoModel.setTotal_Dscto_i(0);
                        pedidoModel.setValor_Neto(Double.parseDouble(lbl_subtotal.getText().toString()));
                        // pedidoModel.setInf("");
                        pedidoModel.setDscto_Fnz_inafecto_p(0.00);
                        pedidoModel.setDscto_Fnz_inafecto_i(0.00);
                        pedidoModel.setBim(Double.parseDouble(lbl_subtotal.getText().toString()));
                        pedidoModel.setBin_neto(Double.parseDouble(lbl_subtotal.getText().toString()));
                        pedidoModel.setIgv(Double.parseDouble(lbl_igv.getText().toString()));
                        pedidoModel.setTotal(Double.parseDouble(lbl_total.getText().toString()));


                        pedidoModel.setCod_moneda("01");//01= soles se podria realizar un menu
                        pedidoModel.setCambio_moneda(0);//este cambio esta por verse como obtener el tipo de cambio
                        pedidoModel.setUsuario_crea(usuario_session);
                        //pedidoModel.setCod_cotizacion(""); este campo es en caso vaya relacionada a una cotizacion
                        pedidoModel.setTipo_autorizacion(0);
                        pedidoModel.setFecha_vencimiento(FechaActual);
                        pedidoModel.setCod_centro_costo("01010101");
                        pedidoModel.setCod_centro_costo_sub("01010101");
                        pedidoModel.setCod_centro_costo_sub_sub("01010101");

                        pedidoModel.setDetalle(ListaDetallePedido);

                        // ConvertirPedidoJson(pedidoModel);
                        EnviarPedido( ruc_empresa, pedidoModel);
                        InicializarControles();

                        }else {
                            AlertaAdvertencia_Atras("REGISTRO DE PEDIDOS","No se pudo completar la operacíon, verifique su conexión de internet");

                        }

                    }


                }catch (Exception ex){ex.getMessage();
                    Toast.makeText(getApplicationContext(),"Error en la validacion de controles ex"+ex.getMessage(),Toast.LENGTH_LONG).show();
                }



            }
        });


    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // Check which request we're responding to
        if (requestCode == Codigo_result_Prod) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {


                txt_nombreProd.setText(data.getStringExtra("nombre_producto"));
                txt_codigoprod_hide.setText(data.getStringExtra("cod_producto"));
                txt_precioProd_hide.setText(String.valueOf(data.getDoubleExtra("valorventa",0.0)));

            }
        }


        if (requestCode == Codigo_result_Cli){

            if (resultCode == 2) {

                txt_nombreCliente.setText(data.getStringExtra("nombre_cliente"));
                txt_codigoClient_hide.setText(data.getStringExtra("cod_cliente"));
            }
        }



    }



    public  void  AgregarItem(PedidoDetModel pedidoDetModel){

        try{

            ListaDetallePedido.add(pedidoDetModel);
        }catch (Exception ex){ex.getMessage();}

    }





    public  void  EliminarItem(int index){

        try{
            ListaDetallePedido.remove(index);
            RefrescarDetallePedido();
           // Toast.makeText(getApplicationContext(), "Eliminacion correcta", Toast.LENGTH_SHORT).show();

        }catch (Exception ex){ex.getMessage();}

    }





    public  void  RefrescarDetallePedido() {

        adaptadorDetallePedido = new AdaptadorDetallePedido(getApplicationContext(),ListaDetallePedido);
        lv_detallePedido.setAdapter(adaptadorDetallePedido);
        CalcularTotalesPedido();
    }








    public  void  LimpiarControlesDetallePedido(){

        try{

            txt_nombreProd.setText("");
            txt_codigoprod_hide.setText("");
            txt_precioProd_hide.setText("");
            txt_cantidadpiker.setText("1");
        }catch (Exception ex){ex.getMessage();

        }

    }


    public void LimpiarControlesCabeceraPedido(){

        txt_nombreCliente.setText("");
        txt_nombreCliente.setFocusable(true);
        txt_codigoClient_hide.setText("");
        txt_fechaEntrega.setText("");
        txt_direc_entrega.setText("");
        txt_observaciones.setText("");


        LimpiarControlesDetallePedido();

    }



    public  boolean  ValidarControlesCabeceraPedido(){

        Boolean  Estado=false;

        try{


            if (txt_nombreCliente.getText().toString().isEmpty() || txt_codigoClient_hide.getText().toString().isEmpty()){

                Toast.makeText(getApplicationContext(),"Seleccione un cliente",Toast.LENGTH_LONG).show();
                txt_nombreCliente.setFocusable(true);
                Estado=false;
            }else if(txt_fechaEntrega.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Seleccione la fecha de entrega del pedido",Toast.LENGTH_LONG).show();
                txt_fechaEntrega.setFocusable(true);
                Estado=false;

            }else if(txt_direc_entrega.getText().toString().isEmpty()){
                Toast.makeText(getApplicationContext(),"Ingrese la direcciona de entrega del pedido.",Toast.LENGTH_LONG).show();
                txt_direc_entrega.setFocusable(true);
                Estado=false;

            }else if (ListaDetallePedido.size()<1){
                Toast.makeText(getApplicationContext(), "El pedido no pudo ser registrado,ingrese productos", Toast.LENGTH_LONG).show();
                Estado=false;
            }else {
                Estado=true;
            }


        }catch (Exception ex){

            Estado=false;
            ex.getMessage();

        }

        return  Estado;
    }


    public  boolean  ValidarControlesDetallePedido(){

      Boolean  Estado=false;

        try{


            if (txt_nombreProd.getText().toString().isEmpty() || txt_codigoprod_hide.getText().toString().isEmpty()){

                Toast.makeText(getApplicationContext(),"Seleccione un producto",Toast.LENGTH_LONG).show();
                Estado=false;
            }else {

                Estado=true;
            }

        }catch (Exception ex){

            Estado=false;
            ex.getMessage();

        }

        return  Estado;
    }


    public  void CalcularTotalesPedido(){

        Boolean  Estado=false;

        try{

           double Subtotal=0;//ListaDetallePedido.stream().mapToDouble(o->o.getTotal()).sum();
            double igv=0;
            double total=0;

           for ( PedidoDetModel pedidoDetModel:ListaDetallePedido){

               Subtotal+=pedidoDetModel.getValor();
               igv+=pedidoDetModel.getIgv();
               total+=pedidoDetModel.getTotal();
           }

            lbl_subtotal.setText(String.valueOf(FormateoDecimales(Subtotal,2)));
            lbl_igv.setText(String.valueOf(FormateoDecimales(igv,2) ));
            lbl_total.setText(String.valueOf(FormateoDecimales(total,2)));

        }catch (Exception ex){

            ex.getMessage();
        }

    }

    public  void EnviarPedido(String ruc_empresa,PedidoModel pedidoModel) {

        JSONObject jsonPedido= new JSONObject();
        jsonPedido=ConvertirPedidoJson(pedidoModel);

    //  txt_nombreProd.setText(jsonPedido.toString() );

        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.POST,
                "http://idsierp.dyndns.org:5000/api/Pedido",
                jsonPedido,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                           String Respuesta= response.get("ruc_empresa")+","+response.get("cod_pedido")+","+response.get("mensaje");
                           // Toast.makeText(getApplicationContext(),Respuesta,Toast.LENGTH_SHORT).show();

                            AlertaAdvertencia("REGISTRO DE PEDIDOS","El pedido fue registrado correctamente,"+response.get("cod_pedido"));

                        }catch (Exception ex) {

                            AlertaError("REGISTRO DE PEDIDOS","verifique y vuelva a registrar  el pedido");
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {



                NetworkResponse networkResponse = error.networkResponse;

                if (networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_BAD_REQUEST) {

                    Toast.makeText(getApplicationContext(), "error: mala respuesta", Toast.LENGTH_SHORT).show();


                }else if(networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_INTERNAL_ERROR) {

                    AlertaError("REGISTRO DE PEDIDOS","verifique y vuelva a registrar  el pedido");

                }else if(networkResponse != null && networkResponse.statusCode ==HttpsURLConnection.HTTP_UNAUTHORIZED){

                    AlertaError("REGISTRO DE PEDIDOS","verifique y vuelva a registrar  el pedido");
                }



            }


        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                return super.getParams();


            }

            @Override
            public byte[] getPostBody() {



                return super.getPostBody();



            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }


        };


        RQue.add(Requeste);


    }

    public  JSONObject  ConvertirPedidoJson(PedidoModel pedidoModel){

        JSONObject jsonPedido=new JSONObject ();

        try{

            jsonPedido.put("ruc_empresa",pedidoModel.getRuc_empresa().isEmpty()?null:pedidoModel.getRuc_empresa());
            jsonPedido.put("nro_orden_pedido", "");
            jsonPedido.put("fecha_emision", ObtenerFechaString(pedidoModel.getFecha_emision()));
            jsonPedido.put("cod_forma_pago", pedidoModel.getCod_forma_pago());
            jsonPedido.put("cod_vendedor", pedidoModel.getCod_vendedor());
            jsonPedido.put( "cod_area", pedidoModel.getCod_area());
            jsonPedido.put("cod_cte", pedidoModel.getCod_cte());
            jsonPedido.put( "dir_entrega", pedidoModel.getDir_entrega());
            jsonPedido.put("cod_cliente", pedidoModel.getCod_cliente());
            jsonPedido.put( "fecha_entrega", ObtenerFechaString(pedidoModel.getFecha_entrega()));
            jsonPedido.put("observacion", pedidoModel.getObservacion());
            jsonPedido.put("Valor_total", pedidoModel.getValor_total());
            jsonPedido.put("total_Dscto_p", pedidoModel.getTotal_Dscto_p());
            jsonPedido.put("total_Dscto_i", pedidoModel.getTotal_Dscto_i());
            jsonPedido.put("Valor_Neto",pedidoModel.getValor_Neto());
            jsonPedido.put("inf", pedidoModel.getInf());
            jsonPedido.put("dscto_fnz_inf_p", pedidoModel.getDscto_fnz_inf_p());
            jsonPedido.put("dscto_fnz_inf_i", pedidoModel.getDscto_fnz_inf_i());
            jsonPedido.put("inf_neto", pedidoModel.getInf_neto());
            jsonPedido.put("dscto_Fnz_inafecto_p", pedidoModel.getDscto_Fnz_inafecto_i());
            jsonPedido.put("dscto_Fnz_inafecto_i", pedidoModel.getDscto_Fnz_inafecto_p());
            jsonPedido.put("bim", pedidoModel.getBim());
            jsonPedido.put("bin_neto", pedidoModel.getBin_neto());
            jsonPedido.put("igv", pedidoModel.getIgv());
            jsonPedido.put("total", pedidoModel.getTotal());
            jsonPedido.put("cod_moneda", pedidoModel.getCod_moneda());
            jsonPedido.put("cambio_moneda", pedidoModel.getCambio_moneda());
            jsonPedido.put("usuario_crea", pedidoModel.getUsuario_crea());
            jsonPedido.put("cod_cotizacion", pedidoModel.getCod_cotizacion());
            jsonPedido.put("tipo_autorizacion", pedidoModel.getTipo_autorizacion());
            jsonPedido.put("cod_centro_costo", pedidoModel.getCod_centro_costo());
            jsonPedido.put("cod_centro_costo_sub", pedidoModel.getCod_centro_costo_sub());
            jsonPedido.put("cod_centro_costo_sub_sub", pedidoModel.getCod_centro_costo_sub_sub());
            jsonPedido.put("fecha_vencimiento",ObtenerFechaString(pedidoModel.getFecha_vencimiento()));



            JSONArray jsonArrayDetPedido=new JSONArray ();

            int GeneradorItem=1;
            for ( PedidoDetModel pedidoDetModel   :pedidoModel.getDetalle())
            {


                JSONObject jsonDetPedido=new JSONObject ();

                jsonDetPedido.put("item",GeneradorItem);
                jsonDetPedido.put("cod_producto", pedidoDetModel.getCod_producto());
                jsonDetPedido.put("cod_servicio", pedidoDetModel.getCod_servicio());
                jsonDetPedido.put("descripcion", pedidoDetModel.getDescripcion());
                jsonDetPedido.put("cod_unidad_medida", pedidoDetModel.getCod_unidad_medida());
                jsonDetPedido.put("precio_unitario",pedidoDetModel.getPrecio_unitario());
                jsonDetPedido.put("cantidad", pedidoDetModel.getCantidad());
                jsonDetPedido.put("valor", pedidoDetModel.getValor());
                jsonDetPedido.put("DsctoP", pedidoDetModel.getDsctoP());
                jsonDetPedido.put("DsctoI", pedidoDetModel.getDsctoI());
                jsonDetPedido.put("bim", pedidoDetModel.getBim());
                jsonDetPedido.put("igv", pedidoDetModel.getIgv());
                jsonDetPedido.put("total", pedidoDetModel.getTotal());
                jsonDetPedido.put("observacion", pedidoDetModel.getObservacion());
                jsonDetPedido.put("valor_venta_unitario", pedidoDetModel.getValor_venta_unitario());
                jsonDetPedido.put("TotalVtaSD", pedidoDetModel.getTotalVtaSD());
                jsonDetPedido.put("PrecioUnitSD", pedidoDetModel.getPrecioUnitSD());
                jsonDetPedido.put("PendEnt", pedidoDetModel.getPendEnt());
                jsonDetPedido.put("cod_almacen", pedidoDetModel.getCod_almacen());
                jsonDetPedido.put("fecha_modificacion",ObtenerFechaString(pedidoDetModel.getFecha_modificacion()));
                jsonDetPedido.put("usuario_modifica", pedidoDetModel.getUsuario_modifica());
                jsonDetPedido.put("cod_centro_costo", pedidoDetModel.getCod_centro_costo());
                jsonDetPedido.put("cod_centro_costo_sub", pedidoDetModel.getCod_centro_costo_sub());
                jsonDetPedido.put("cod_centro_costo_sub_sub", pedidoDetModel.getCod_centro_costo_sub_sub());
                jsonArrayDetPedido.put(jsonDetPedido);

                GeneradorItem=GeneradorItem+1;

            }

            jsonPedido.put("detalle",jsonArrayDetPedido);


          //  txt_observaciones.setText(jsonPedido.toString());



        }catch(Exception ex){

            ex.getMessage();
        }

      return   jsonPedido;

    }

    public String ObtenerFechaString( Date Fecha){

             String fechaComoCadena;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 fechaComoCadena = sdf.format(Fecha);
        return fechaComoCadena;
    }

    public  Double FormateoDecimales(double Nro, int NroDecimales ){
        return Math.round(Nro*Math.pow(10,NroDecimales))/Math.pow(10,NroDecimales);
    }


    public boolean  ValidarProductosRepetidos(String CodProducto){

        Boolean Estado=false;

        for ( PedidoDetModel item  :ListaDetallePedido) {

            if (item.getCod_producto().equals(CodProducto)){

                Estado=true;
                break;
            }
        }

    return  Estado;
    }



    public void InicializarControles(){


        LimpiarControlesCabeceraPedido();
        ListaDetallePedido.clear();
        RefrescarDetallePedido();
        LimpiarControlesDetallePedido();
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


            }
        });
        AlertDialog alertDialog=Alert.create();
        alertDialog.show();
    }



}
