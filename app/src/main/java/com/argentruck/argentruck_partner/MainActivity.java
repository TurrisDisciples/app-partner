package com.argentruck.argentruck_partner;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private ListView mTripsList;
    private TripsAdapter tripsAdapter;
    private List<Integer> listaDeImagenes;
    private List<Trip> viajes;
    private String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Se obtiene el email con el que se inicio sesion/registro
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTripActivity.class);
                intent.putExtra("email", email);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        setearImagenes();
        getTravelsInfo();

        // Instancia del ListView.
        mTripsList = (ListView) findViewById(R.id.trips_list);

//        // Inicializar el adaptador con la fuente de datos.
//        tripsAdapter = new TripsAdapter(this,
//                TripsRepository.getInstance().getClients());
//
//        //Relacionando la lista con el adaptador
//        mTripsList.setAdapter(tripsAdapter);

        // Eventos
        mTripsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, ClientsActivity.class);
                intent.putExtra("viaje", viajes.get(position));
                startActivity(intent);
            }
        });
    }

    public void getTravelsInfo() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //TODO: Acomodar esta IP
        String url = "http://192.168.1.103:3000/partners/myTravels?email=" + email;

        final Context context = getApplicationContext();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                // Inicializar el adaptador con la fuente de datos.
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                tripsAdapter = new TripsAdapter(context, procesarResponse(response));

                //Relacionando la lista con el adaptador
                mTripsList.setAdapter(tripsAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }

    public List<Trip> procesarResponse(JSONArray response) {
        List<Trip> viajes = new ArrayList<>();
        List<Client> client_list = new ArrayList<>();
        JSONObject trip = new JSONObject();
        JSONArray clients_registered;
        JSONObject client_data;
        JSONObject register_data;
        String capacidad;
        Client client;
        for(int i  = 0; i < response.length(); i++) {
            try {
                trip = response.getJSONObject(i);
                clients_registered = trip.getJSONArray("registers");
                for(int j = 0; j < clients_registered.length(); j++) {
                    register_data = clients_registered.getJSONObject(j);
                    capacidad = register_data.getString("capacity");
                    client_data = register_data.getJSONObject("userId");
                    client_list.add(new Client(client_data.getString("_id"),
                                               client_data.getString("nombre") + " " + client_data.getString("apellido"),
                                               client_data.getString("telefono"),
                                               client_data.getString("direccion"),
                                               client_data.getString("email"),
                                               capacidad, listaDeImagenes.get(j)));

                }
                viajes.add(new Trip(trip.getString("_id"),
                                    trip.getString("origin"),
                                    trip.getString("destiny"),
                                    trip.getString("capMax"),
                                    trip.getString("date"),
                                    client_list));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        this.viajes = viajes;
        return viajes;

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.perfil) {
            Intent intent = new Intent(this, PerfilActivity.class);
            intent.putExtra("email", email);
            startActivity(intent);
        } else if (id == R.id.sign_out) {
            Toast.makeText(getApplicationContext(), "Cerrando Sesion", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, InitialActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_share) {
            Toast.makeText(getApplicationContext(), "Compartir", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_send) {
            Toast.makeText(getApplicationContext(), "Enviar", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setearImagenes() {
        listaDeImagenes = new ArrayList<>();
        listaDeImagenes.add(R.drawable.lead_photo_1);
        listaDeImagenes.add(R.drawable.lead_photo_2);
        listaDeImagenes.add(R.drawable.lead_photo_3);
        listaDeImagenes.add(R.drawable.lead_photo_4);
        listaDeImagenes.add(R.drawable.lead_photo_5);
        listaDeImagenes.add(R.drawable.lead_photo_6);
        listaDeImagenes.add(R.drawable.lead_photo_7);
        listaDeImagenes.add(R.drawable.lead_photo_8);
        listaDeImagenes.add(R.drawable.lead_photo_9);
    }
}
