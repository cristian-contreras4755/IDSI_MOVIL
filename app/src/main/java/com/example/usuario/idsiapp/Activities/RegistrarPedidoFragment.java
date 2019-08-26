package com.example.usuario.idsiapp.Activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.usuario.idsiapp.Adapters.AdaptadorDetallePedido;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.Models.PedidoDetModel;
import com.example.usuario.idsiapp.Models.PedidoModel;
import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link RegistrarPedidoFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link RegistrarPedidoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistrarPedidoFragment extends Fragment {

    private OnFragmentInteractionListener mListener;



    private RequestQueue RQue;
    private String cod_producto;
    private String nombre_producto1;
    private Double precio;

    Date FechaActual;

    private ImageButton btn_BuscarCliente;
    private ImageButton btn_BuscarProducto;
    private Button btn_agregarItemDetalle;
    private Button btn_registrarPedido;
    ListView lv_detallePedido;
    private AdaptadorDetallePedido adaptadorDetallePedido;
    TextView TV_nombreCliente;
    ArrayList<PedidoDetModel> ListaDetallePedido;

    private TextView txt_nombreProd;
    private TextView txt_cantidadprod;


    private  String  PC_nombre_cliente;
    private  String  PC_cod_cliente;



    public RegistrarPedidoFragment() {
        // Required empty public constructor
    }


    public static RegistrarPedidoFragment newInstance(String param1, String param2) {
        RegistrarPedidoFragment fragment = new RegistrarPedidoFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

            PC_nombre_cliente = getArguments().getString("nombre_cliente");
            PC_cod_cliente = getArguments().getString("cod_cliente");

        }else {

            Toast.makeText(getContext(),"Error al obtener paramentros",Toast.LENGTH_SHORT).show();
        }





    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View Vista=inflater.inflate(R.layout.fragment_registrar_pedido, container, false);
        TV_nombreCliente=Vista.findViewById(R.id.txt_nombreCliente);

        if (PC_nombre_cliente!=null){
            TV_nombreCliente.setText(PC_nombre_cliente);
        }



        //inicializamos las fechas
        FechaActual=new Date();
        FechaActual.getDate();


        btn_BuscarCliente=(ImageButton)Vista.findViewById(R.id.IB_buscarCliene);
        btn_BuscarProducto=(ImageButton)Vista.findViewById(R.id.IB_buscarProd);
        btn_agregarItemDetalle=(Button)Vista.findViewById(R.id.btn_anadir_item );
        btn_registrarPedido=(Button)Vista.findViewById(R.id.btn_registrarPedido);


        lv_detallePedido=(ListView) Vista.findViewById(R.id.lv_detalle);

        ListaDetallePedido= new ArrayList<>();

        /*Cajas de texto*/
         txt_nombreProd=(TextView)Vista.findViewById(R.id.txt_nombreProd);
         txt_cantidadprod=(TextView)Vista.findViewById(R.id.txt_CantProd);





        btn_BuscarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Bundle  bundle_ListarClieFrament = new Bundle();
                bundle_ListarClieFrament.putString("ruc_empresa","20169999991");

                ListaClienteFragment listaClienteFragment= new ListaClienteFragment();
                listaClienteFragment.setArguments(bundle_ListarClieFrament);

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.hide(RegistrarPedidoFragment.this);
                transaction.add(R.id.content_principal, listaClienteFragment);
                transaction.commit();
            }
        });






        btn_BuscarProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle  bundle_ListarProdFrament = new Bundle();
                bundle_ListarProdFrament.putString("ruc_empresa","4584654654");

/*
                ListaProductoFragment listaProductoFragment= new ListaProductoFragment();
                listaProductoFragment.setArguments(bundle_ListarProdFrament);


                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.content_principal, listaProductoFragment);
                transaction.addToBackStack(null);
                transaction.commit();
*/


            }
        });






        btn_agregarItemDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {

                    String Nombreproducto=txt_nombreProd.getText().toString();
                    Integer Cantidadproducto=Integer.parseInt(txt_cantidadprod.getText().toString());


                    //calculo de  impuestos


                    //calculo de  impuestos


                    PedidoDetModel pedidoDetModel= new PedidoDetModel();

                    pedidoDetModel.setItem(1);
                    pedidoDetModel.setCod_producto("");
                    // pedidoDetModel.setCod_servicio("");
                    pedidoDetModel.setDescripcion(Nombreproducto);
                    pedidoDetModel.setCod_unidad_medida(1);
                    pedidoDetModel.setPrecio_unitario(1);
                    pedidoDetModel.setCantidad(Cantidadproducto);
                    pedidoDetModel.setDsctoP(1);
                    pedidoDetModel.setDsctoI(1);
                    pedidoDetModel.setBim(1);
                    pedidoDetModel.setIgv(1);
                    pedidoDetModel.setTotal(118);
                    pedidoDetModel.setObservacion("");
                    pedidoDetModel.setValor_venta_unitario(1);
                    pedidoDetModel.setTotalVtaSD(1);
                    pedidoDetModel.setPrecioUnitSD(1);
                    pedidoDetModel.setPendEnt(1);
                    pedidoDetModel.setCod_almacen("");
                    pedidoDetModel.setFecha_modificacion(FechaActual);
                    pedidoDetModel.setUsuario_modifica("");
                    pedidoDetModel.setCod_centro_costo("01010101");
                    pedidoDetModel.setCod_centro_costo_sub("01010101");
                    pedidoDetModel.setCod_centro_costo_sub_sub("01010101");

                    AgregarItem(pedidoDetModel);
                    RefrescarDetallePedido();

                }catch (Exception Ex){

                    Ex.getMessage();
                }
            }
        });






        btn_registrarPedido.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try{
                    PedidoModel pedidoModel= new PedidoModel();

                    pedidoModel.setRuc_empresa("20169999991");
                    pedidoModel.setNro_orden_pedido("200");//lo  pone la la empresa
                    pedidoModel.setFecha_emision(FechaActual);
                    pedidoModel.setCod_forma_pago("01");//forma de pago al escoger
                    pedidoModel.setCod_vendedor("VND0001");//tiene que ser el usuario de sistema
                    pedidoModel.setCod_area("010101");
                    //pedidoModel.setCod_cte(""); null
                    pedidoModel.setDir_entrega("av.las amricas cuadra 10.");
                    pedidoModel.setCod_cliente("CLT0000018");
                    pedidoModel.setFecha_entrega(FechaActual);//deberia seleccionar fecha de entrega en el form
                    pedidoModel.setObservacion("entrgar productos buenos");

                    /*
                    pedidoModel.setValor_total("");
                    pedidoModel.setTotal_Dscto_p(0);
                    pedidoModel.setTotal_Dscto_i(0);
                    pedidoModel.setValor_Neto("");
                    pedidoModel.setInf("");
                    pedidoModel.setDscto_Fnz_inafecto_p("");
                    pedidoModel.setDscto_Fnz_inafecto_i("");
                    pedidoModel.setBim("");
                    pedidoModel.setBin_neto("");
                    pedidoModel.setIgv("");
                    pedidoModel.setTotal("");
                    */

                    pedidoModel.setCod_moneda("01");//01= soles se podria realizar un menu
                    pedidoModel.setCambio_moneda(0);//este cambio esta por verse como obtener el tipo de cambio
                    pedidoModel.setUsuario_crea("usu02");
                    //pedidoModel.setCod_cotizacion(""); este campo es en caso vaya relacionada a una cotizacion
                    pedidoModel.setTipo_autorizacion(0);
                    pedidoModel.setFecha_vencimiento(FechaActual);
                    pedidoModel.setCod_centro_costo("01010101");
                    pedidoModel.setCod_centro_costo_sub("01010101");
                    pedidoModel.setCod_centro_costo_sub_sub("01010101");

                    pedidoModel.setDetalle(ListaDetallePedido);



                }catch (Exception ex){ex.getMessage();}

            }
        });








/*
        lv_detallePedido.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


              //    final PedidoDetModel objpedidoDet=(PedidoDetModel)lv_detallePedido.getItemAtPosition(position);

                //  String nombre=objpedidoDet.getDescripcion();


                Toast.makeText(getContext(), "N:"+position , Toast.LENGTH_SHORT).show();



                //  ListaDetallePedido.remove(position);
                // RefrescarDetallePedido();

            }
        });


*/





        return Vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;


        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }





    }




    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }





















    public  void  AgregarItem(PedidoDetModel pedidoDetModel){

        try{

            ListaDetallePedido.add(pedidoDetModel);
        }catch (Exception ex){ex.getMessage();}

    }



    public  void  EliminarItem(int position){

        try{

            // ListaDestallePedido.add(pedidoDetModel);
        }catch (Exception ex){ex.getMessage();}

    }


    public  void  RefrescarDetallePedido() {

        adaptadorDetallePedido = new AdaptadorDetallePedido(getContext(),ListaDetallePedido);
        lv_detallePedido.setAdapter(adaptadorDetallePedido);
    }





    public void RecibirParametro(String nombre_cliente, String cod_cliente){

        TV_nombreCliente.setText(nombre_cliente+"-"+cod_cliente);
    }













}
