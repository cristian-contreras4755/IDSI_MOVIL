package com.example.usuario.idsiapp.Activities;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Adapters.AdaptadorListaCliente;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Models.Cliente;
import com.example.usuario.idsiapp.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class ListaClienteFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private OnFragmentInteractionListener mListener;
    private ListView lv_cliente;
    private TextView txt_buscar_cliente;
    private String ruc_empresa;
    private RequestQueue RQue;
    private AdaptadorListaCliente adaptadorListaCliente;
    private FragmentActivity ContextoActividadPrincipal;
    private IEnviarDatos iEnviarDatos;

    private String UrlBase;
    private NetWorkManager netWorkManager;



    private   RegistrarPedidoFragment registrarPedidoFragment;


    public  interface  IEnviarDatos {

        public  void ObtenerDatosCliente(String cod_cliente,String nombre_cliente);

    }








    public ListaClienteFragment() {
        // Required empty public constructor
    }


    public static ListaClienteFragment newInstance(String param1, String param2) {
        ListaClienteFragment fragment = new ListaClienteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //obtenermos la url base de de configuraciones

       // netWorkManager= new NetWorkManager(this);
       // UrlBase=netWorkManager.GetUrlBaseServices();



        if (getArguments() != null) {
            ruc_empresa = getArguments().getString("ruc_empresa");
         //  Toast.makeText(getContext(), "Ruc de la empresa: " +ruc_empresa, Toast.LENGTH_SHORT).show(); solo  para pruebas
            RQue=Volley.newRequestQueue(getContext());
            ConsultarServicio(ruc_empresa);
        }else {

            Toast.makeText(getContext(), "Error al obtener el ruc de la empresa", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View   Vista= inflater.inflate(R.layout.fragment_lista_cliente, container, false);
        lv_cliente=(ListView) Vista.findViewById(R.id.lv_lista_cliente);



        txt_buscar_cliente=Vista.findViewById(R.id.txt_buscar_cliente);


        txt_buscar_cliente.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

             //   adaptadorListaCliente.getItem().Fi


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });





        lv_cliente.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



               // Toast.makeText(getContext(),""+objcli.getNombre_cliente(),Toast.LENGTH_SHORT).show();


                try{

                    final Cliente objcli=(Cliente) lv_cliente.getItemAtPosition(position);
                    Bundle Caja_para_pedido= new Bundle();
                    Caja_para_pedido.putString("nombre_cliente",objcli.getNombre_cliente());
                    Caja_para_pedido.putString("cod_cliente",objcli.getCod_cliente());
                     iEnviarDatos.ObtenerDatosCliente(objcli.getCod_cliente(),objcli.getNombre_cliente());



                    // RegistrarPedidoFragment Rfg=RegistrarPedidoFragment();
                    //Rfg.setArguments(Caja_para_pedido);

                    FragmentTransaction FT = ContextoActividadPrincipal.getSupportFragmentManager().beginTransaction();
                    // FragmentTransaction transaction = fragmentManager.beginTransaction();
                    FT.hide(ListaClienteFragment.this);
                    FT.commit();


                }catch (Exception ex){
                    ex.getMessage();
                }




            }
        });



        return  Vista;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }



    @Override
    public void onAttach(Activity activity) {
        ContextoActividadPrincipal=(FragmentActivity)activity;
        super.onAttach(activity);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }












    public  void ConsultarServicio(String ruc_empresa) {

        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                "http://idsiserviciosgen-prod.us-east-1.elasticbeanstalk.com/api/Cliente/ObtenerListaClientes?ruc_empresa="+ruc_empresa,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{


                            ArrayList<Cliente> listaCliente= new ArrayList<>();

                            JSONArray clienteArray=response.getJSONArray("cliente");

                            for ( int i=0;i<clienteArray.length();i++ ){

                                Cliente cliente= new Cliente();
                                JSONObject  Objecto=clienteArray.getJSONObject(i);

                                cliente.setRuc_empresa(Objecto.getString("ruc_empresa"));
                                cliente.setCod_cliente(Objecto.getString("cod_cliente"));
                                cliente.setCod_tipo_doc(Objecto.getString("cod_tipo_doc"));
                                cliente.setNombre_corto_Tipo_doc(Objecto.getString("nombre_corto_Tipo_doc"));
                                cliente.setNro_documento(Objecto.getString("nro_documento"));
                                cliente.setNombre_cliente(Objecto.getString("nombre_cliente"));
                                cliente.setCod_pais(Objecto.getString("cod_pais"));
                                cliente.setCod_postal(Objecto.getString("cod_postal"));
                                cliente.setUbigeo(Objecto.getString("ubigeo"));
                                cliente.setDireccion(Objecto.getString("direccion"));
                                cliente.setTelefono1(Objecto.getString("telefono1"));
                                cliente.setTelefono2(Objecto.getString("telefono2"));
                                cliente.setFax(Objecto.getString("fax"));
                                cliente.setPagina_web(Objecto.getString("pagina_web"));
                                cliente.setObservacion(Objecto.getString("observacion"));
                                cliente.setRuc_empresa(Objecto.getString("estado"));
                                cliente.setCampo_adicional_01(Objecto.getString("campo_adicional_01"));
                                cliente.setCampo_adicional_02(Objecto.getString("campo_adicional_02"));
                                cliente.setCampo_adicional_03(Objecto.getString("campo_adicional_03"));
                                cliente.setCampo_adicional_04(Objecto.getString("campo_adicional_04"));
                                cliente.setCampo_adicional_05(Objecto.getString("campo_adicional_05"));
                                cliente.setCampo_adicional_06(Objecto.getString("campo_adicional_06"));
                                cliente.setCampo_adicional_07(Objecto.getString("campo_adicional_07"));
                                cliente.setCampo_adicional_08(Objecto.getString("campo_adicional_08"));
                                cliente.setCampo_adicional_09(Objecto.getString("campo_adicional_09"));
                                cliente.setCampo_adicional_10(Objecto.getString("campo_adicional_10"));

                                listaCliente.add(cliente);

                            }

                            ListarCliente(listaCliente);

                        }catch (Exception ex) {

                            ex.printStackTrace();
                        }

                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Toast.makeText(getContext(), "error" +error.getMessage(), Toast.LENGTH_SHORT).show();

            }


        });

        RQue.add(Requeste);


    }

    public  void ListarCliente(ArrayList<Cliente> listaCliente){

        adaptadorListaCliente=new AdaptadorListaCliente(getContext(),listaCliente);
        lv_cliente.setAdapter(adaptadorListaCliente);

    }






}
