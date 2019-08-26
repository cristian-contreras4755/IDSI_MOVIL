package com.example.usuario.idsiapp.Activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.usuario.idsiapp.Common.NetWorkManager;
import com.example.usuario.idsiapp.Common.SessionManager;
import com.example.usuario.idsiapp.Models.ComonModels.PaginacionModel;
import com.example.usuario.idsiapp.Models.ProdStockAlm;
import com.example.usuario.idsiapp.Models.ProductoStockSimple;
import com.example.usuario.idsiapp.Models.StockProdxCod;
import com.example.usuario.idsiapp.R;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Base64;

public class ProductoStockTargetActivity extends AppCompatActivity {


    private SessionManager sessionManager;
    private NetWorkManager netWorkManager;
    private ProgressDialog progressDialog;
    private String UrlBase,UrlServices;
    private String  ruc_empresa,cod_prod;
    private RequestQueue RQue;
    private ArrayList<ProdStockAlm> listaProdStockAlm;
    private StockProdxCod stockProdxCod;
    private byte[] imgbyte=null;
    private  TableLayout   TbL_inv_listaProdstockAlm;
    //Edittext

    TextView lbl_NomprodTargetStock,lbl_CodprodTargetStock,lbl_cod_comercial_TargetStock,lbl_Marca_TargetStock,lbl_stockTotal,lbl_Marca_NomCorto,lbl_nom_clase,lbl_nom_sub_clase;
    TextView lbl_DescripcionTargetStock,lbl_NomCortoprodTargetStock,lbl_CodMarca_TargetStock,lbl_cod_clase,lbl_nomcortoclase,lbl_cod_sub_clase,lbl_nomcorto_sub_clase;

    ImageView imv_img_productostock;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_stock_target);
        sessionManager= new SessionManager(this);
        netWorkManager= new NetWorkManager(this);
        TbL_inv_listaProdstockAlm=(TableLayout) findViewById(R.id.TbL_inv_listaProdstockAlm);
        ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");
        // ruc_empresa="20160000001";
        //obtener codigo de producto
        cod_prod=getIntent().getStringExtra("cod_prod");

        //ruc_empresa=sessionManager.ObtenerRuc_Session("ruc_global");
        imv_img_productostock=(ImageView) findViewById(R.id.imv_img_productostock);
        lbl_NomprodTargetStock=(TextView) findViewById(R.id.lbl_NomprodTargetStock);
        lbl_CodprodTargetStock=(TextView) findViewById(R.id.lbl_CodprodTargetStock);
        lbl_cod_comercial_TargetStock=(TextView) findViewById(R.id.lbl_cod_comercial_TargetStock);
        lbl_Marca_TargetStock=(TextView) findViewById(R.id.lbl_Marca_TargetStock);
        lbl_stockTotal=(TextView) findViewById(R.id.lbl_stockTotal);


        lbl_Marca_NomCorto=(TextView) findViewById(R.id.lbl_Marca_NomCorto);
        lbl_nom_clase=(TextView) findViewById(R.id.lbl_nom_clase);
        lbl_nom_sub_clase=(TextView) findViewById(R.id.lbl_nom_sub_clase);


        lbl_DescripcionTargetStock=(TextView) findViewById(R.id.lbl_DescripcionTargetStock);
        lbl_NomCortoprodTargetStock=(TextView) findViewById(R.id.lbl_NomCortoprodTargetStock);
        lbl_CodMarca_TargetStock=(TextView) findViewById(R.id.lbl_CodMarca_TargetStock);
        lbl_cod_clase=(TextView) findViewById(R.id.lbl_cod_clase);
        lbl_nomcortoclase=(TextView) findViewById(R.id.lbl_nomcortoclase);
        lbl_cod_sub_clase=(TextView) findViewById(R.id.lbl_cod_sub_clase);
        lbl_nomcorto_sub_clase=(TextView) findViewById(R.id.lbl_nomcorto_sub_clase);



        UrlBase=netWorkManager.GetUrlBaseServices();
        RQue= Volley.newRequestQueue(getApplicationContext());
        ConsultaStock();
    }


    public void ConsultaStock() {

        if (ruc_empresa.isEmpty() ||  ruc_empresa==null){
            Toast.makeText(getApplicationContext(),"No puedimos encontrar el ruc de la empresa",Toast.LENGTH_LONG).show();
            return;
        }
/*
        if (!netWorkManager.Is_Online()){
            AlertaError("Validación de conexion de internet","Asegurese de  de  tener cuna conexion a internet.");
            return;
        }
*/

        UrlServices= UrlBase+"/Inventario/ObtenerStockProdxcod?ruc="+ruc_empresa+"&cd_prod="+cod_prod;
        AlertEspera("Espere","Cargando..");
        JsonObjectRequest Requeste = new JsonObjectRequest(Request.Method.GET,
                UrlServices,
                null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        try{

                            listaProdStockAlm= new ArrayList<ProdStockAlm>();
                            JSONObject Jobj= new JSONObject(response.toString());
                            String ImgTextByte=Jobj.get("img").toString();

                            if (ImgTextByte!="null"){
                                byte[] decodedString = Base64.getDecoder().decode(ImgTextByte.getBytes());
                                imgbyte= decodedString;
                            }


                            JSONArray datos=response.getJSONArray("lista_prod_stock");
                            for ( int i=0;i<datos.length();i++ ){

                                ProdStockAlm productoStock= new ProdStockAlm();

                                JSONObject  Objecto=datos.getJSONObject(i);
                                productoStock.setCod_producto(Objecto.getString("cod_producto"));

                                if (Objecto.getString("cod_com").length()==0){
                                    productoStock.setCod_com("--");
                                }else {

                                    productoStock.setCod_com(Objecto.getString("cod_com"));
                                }

                                //productoStock.setCod_com(Objecto.getString("cod_com")==null?"-":Objecto.getString("cod_com"));
                                productoStock.setNombre_prod(Objecto.getString("nombre_prod"));
                                productoStock.setNom_corto_prod(Objecto.getString("nom_corto_prod"));
                                productoStock.setDesc_prod(Objecto.getString("desc_prod"));


                                productoStock.setNom_corto_marca(Objecto.getString("nom_corto_marca")==null?"-":Objecto.getString("nom_corto_marca"));
                                productoStock.setCod_marca(Objecto.getString("cod_marca")==null?"-":Objecto.getString("cod_marca"));
                                productoStock.setNom_marca(Objecto.getString("nom_marca")==null?"-":Objecto.getString("nom_marca"));


                                productoStock.setCod_clase(Objecto.getString("cod_clase")==null?"-":Objecto.getString("cod_clase"));
                                productoStock.setNom_clase(Objecto.getString("nom_clase")==null?"-":Objecto.getString("nom_clase"));
                                productoStock.setNom_corto_clase(Objecto.getString("nom_corto_clase")==null?"-":Objecto.getString("nom_corto_clase"));


                                productoStock.setCod_subclase(Objecto.getString("cod_subclase")==null?"-":Objecto.getString("cod_subclase"));
                                productoStock.setNom_subclase(Objecto.getString("nom_subclase")==null?"-":Objecto.getString("nom_subclase"));
                                productoStock.setNom_corto_subclase(Objecto.getString("nom_corto_subclase")==null?"-":Objecto.getString("nom_corto_subclase"));


                                productoStock.setAlmacen(Objecto.getString("almacen"));
                                productoStock.setStock(Objecto.getString("stock"));
                                listaProdStockAlm.add(productoStock);
                            }


                            GenerarTabla(listaProdStockAlm);

                        }catch (Exception ex) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(),"1error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
                            Log.d("error:",ex.getMessage().toString());

                            Log.d("error:",ex.getMessage().toString());
                        }


                    }


                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                progressDialog.dismiss();
                AlertaError("Listar stock productos.","por favor,  intentelo  dentro de unos minutos");
            }

        });
        RQue.add(Requeste);
    }




    public void GenerarTabla(ArrayList<ProdStockAlm> listaStockProd){

        try {

            TableRow tbrowTitulos = new TableRow(this);
            TextView tv0 = new TextView(this);
            tv0.setText("Almacen");
            tv0.setTextColor(Color.WHITE);
            tv0.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv0);


            TextView tv1 = new TextView(this);
            tv1.setText("Stock");
            tv1.setTextColor(Color.WHITE);
            tv1.setBackground(getDrawable(R.drawable.text_view_cabeceratabla));
            tbrowTitulos.addView(tv1);

            tbrowTitulos.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.WRAP_CONTENT,
                    TableLayout.LayoutParams.FILL_PARENT));

            TbL_inv_listaProdstockAlm.addView(tbrowTitulos);

            for (int i=0;i<listaStockProd.size();i++){

                View tableRow = LayoutInflater.from(this).inflate(R.layout.elemento_item_listaprodstockalmacen,null,false);
                TextView txt_Almacen=(TextView)tableRow.findViewById(R.id.txt_item_almacen);
                TextView txt_itemstock=(TextView)tableRow.findViewById(R.id.txt_item_stock);
                txt_Almacen.setText(listaStockProd.get(i).getAlmacen());
                txt_itemstock.setText(listaStockProd.get(i).getStock());
                TbL_inv_listaProdstockAlm.addView(tableRow);
            }



            double Stocktotal=0;
            for ( ProdStockAlm prodStockAlm :listaStockProd ){
                Stocktotal+=Double.parseDouble(prodStockAlm.getStock()) ;
            }

            lbl_NomprodTargetStock.setText(listaStockProd.get(0).getNombre_prod());
            lbl_CodprodTargetStock.setText(listaStockProd.get(0).getCod_producto());
            lbl_cod_comercial_TargetStock.setText(listaStockProd.get(0).getCod_com());
            lbl_Marca_TargetStock.setText(listaStockProd.get(0).getNom_marca());



            lbl_Marca_NomCorto.setText(listaStockProd.get(0).getNom_corto_marca());
            lbl_nom_clase.setText(listaStockProd.get(0).getNom_clase());
            lbl_nom_sub_clase.setText(listaStockProd.get(0).getNom_subclase());
            lbl_stockTotal.setText(""+ Stocktotal);


            lbl_DescripcionTargetStock.setText(listaStockProd.get(0).getDesc_prod());;
            lbl_NomCortoprodTargetStock.setText(listaStockProd.get(0).getNom_corto_prod());
            lbl_CodMarca_TargetStock.setText(listaStockProd.get(0).getCod_marca());
            lbl_cod_clase.setText(listaStockProd.get(0).getCod_clase());
            lbl_nomcortoclase.setText(listaStockProd.get(0).getNom_corto_clase());
            lbl_cod_sub_clase.setText(listaStockProd.get(0).getCod_subclase());
            lbl_nomcorto_sub_clase.setText(listaStockProd.get(0).getNom_corto_subclase());

            pintar();
            progressDialog.dismiss();
        }catch (Exception ex){
            progressDialog.dismiss();
            Toast.makeText(getApplicationContext(),"error al cosultar"+ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    public  void pintar(){

        if (imgbyte==null) {
            return;
        }

        if (imgbyte.length==0) {
            imv_img_productostock.setImageResource(R.drawable.img_nofound);
            return;
        }


        Toast.makeText(getApplicationContext(),"entro a pintar"+imgbyte,Toast.LENGTH_LONG).show();

        Bitmap bitmap=BitmapFactory.decodeByteArray(imgbyte,0,imgbyte.length);
        imv_img_productostock.setImageBitmap(bitmap);

    }





    public void AlertEspera(String Titulo, String Mensaje){
        progressDialog = new ProgressDialog(this);
        progressDialog.setIcon(R.mipmap.ic_launcher_logo);
        progressDialog.setTitle(Titulo);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(Mensaje);
        progressDialog.show();
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

}
