package com.argentruck.argentruck_partner;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * An activity representing a single Item detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ItemListActivity}.
 */
public class ItemDetailActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {

    private Trip viaje;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ListView mClientsList;
    private ClientsAdapter clientsAdapter;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        appBarLayout.addOnOffsetChangedListener(this);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        if (getIntent().getBooleanExtra("mostrar_fab", false)) {
            email = getIntent().getStringExtra("email");
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.INVISIBLE);
        }

        final Context context = getApplicationContext();
        final Activity activity = this;

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alert = new AlertDialog.Builder(activity);
                alert.setTitle("Crear Viaje");
                alert.setMessage("¿Está seguro que quiere crear el viaje?");
                alert.setPositiveButton("SI", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        pedirCapacidad();
//                                Toast.makeText(context, "El viaje se ha creado", Toast.LENGTH_LONG).show();
                    }
                });
                alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "El viaje sigue pendiente", Toast.LENGTH_LONG).show();
                    }
                });
//                        .show();

                AlertDialog alert11 = alert.create();
                alert11.show();
            }
        });

        viaje = (Trip) getIntent().getSerializableExtra("viaje");

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final TextView origenField = (TextView) findViewById(R.id.origenField);
        final TextView destinoField = (TextView) findViewById(R.id.destinoField);
        final TextView capacidadField = (TextView) findViewById(R.id.capacidadField);
        final TextView capacidadRellenadaField = (TextView) findViewById(R.id.capacidadRellenadaField);

        origenField.setText(viaje.getOrigen());
        destinoField.setText(viaje.getDestino());
        String capacidad = viaje.getCapacidad();
        if (!capacidad.equals("-")) capacidad += " tn";
        capacidadField.setText(capacidad);
        capacidadRellenadaField.setText(viaje.getCapacidadRellena() + " tn");


        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
//        if (savedInstanceState == null) {
//            // Create the detail fragment and add it to the activity
//            // using a fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(ItemDetailFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(ItemDetailFragment.ARG_ITEM_ID));
//            ItemDetailFragment fragment = new ItemDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.item_detail_container, fragment)
//                    .commit();
//        }


        // Instancia del ListView.
        mClientsList = (ListView) findViewById(R.id.clients_list);

        // Inicializar el adaptador con la fuente de datos.
        clientsAdapter = new ClientsAdapter(this, viaje.getClientList());

        //Relacionando la lista con el adaptador
        mClientsList.setAdapter(clientsAdapter);

        // Eventos
        mClientsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(ItemDetailActivity.this, ClientsActivity.class);
                intent.putExtra("viaje", viaje);
                startActivity(intent);
            }
        });

    }

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        if (id == android.R.id.home) {
//            // This ID represents the Home or Up button. In the case of this
//            // activity, the Up button is shown. For
//            // more details, see the Navigation pattern on Android Design:
//            //
//            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
//            //
//            navigateUpTo(new Intent(this, ItemListActivity.class));
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
//        if (offset == 0)
//        {
//            fab.setVisibility(View.VISIBLE);
//        }
//        else
//        {
//            fab.setVisibility(View.INVISIBLE);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {

        getMenuInflater().inflate(R.menu.clients_menu_icons, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.map:
                String direction = viaje.getDestino().replace(" ", "+");
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<0>,<0>?q=" + direction));
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void pedirCapacidad() {
        final Context context = getApplicationContext();
        final Activity activity = this;
        final EditText edittext = new EditText(activity);
        final LinearLayout horizontal = new LinearLayout(activity);
        horizontal.setOrientation(LinearLayout.HORIZONTAL);
        horizontal.setMinimumWidth(1000);
        horizontal.setGravity(1);
        horizontal.addView(edittext);
//        horizontal.set

        AlertDialog.Builder alert = new AlertDialog.Builder(activity);
        alert.setTitle("Carga");
        alert.setMessage("Ingrese la carga total a llevar");
        alert.setView(horizontal);
        alert.setPositiveButton("CREAR", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                crearViaje(edittext.getText().toString());
//                Toast.makeText(context, "El viaje se ha creado", Toast.LENGTH_LONG).show();
            }
        });
        alert.setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "El viaje sigue pendiente", Toast.LENGTH_LONG).show();
            }
        });
//                        .setIcon(android.R.drawable.ic_dialog_alert)
        AlertDialog alert11 = alert.create();
        alert11.show();
    }

    private void crearViaje(String capacidad) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = getResources().getString(R.string.http_ip) + "/partners/travel";

        final Context context = getApplicationContext();

        JSONObject jsParam = new JSONObject();
        JSONObject mRequestParams = new JSONObject();

        try {
            mRequestParams.put("email", email);
            mRequestParams.put("origin", viaje.getOrigen());
            mRequestParams.put("destiny", viaje.getDestino());
            mRequestParams.put("capMax", capacidad);
            mRequestParams.put("date", viaje.getFecha());
            mRequestParams.put("_id", viaje.getId());

            jsParam.put("data", mRequestParams);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.PUT, url,
                jsParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
//                if ((response.toString()).contains("created")) {
                Toast.makeText(context, "Listo, viaje hecho", Toast.LENGTH_SHORT).show();
                Intent intentMain = new Intent(context, MainActivity.class);
                intentMain.putExtra("email", email);
                startActivity(intentMain);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }
}