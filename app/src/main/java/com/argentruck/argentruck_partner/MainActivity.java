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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewTripActivity.class);
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
                startActivity(intent);
            }
        });
    }

    public void getTravelsInfo() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://192.168.0.5:3000/partners/myTravels?email=seba@live.com";

        final Context context = getApplicationContext();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                // Inicializar el adaptador con la fuente de datos.
                tripsAdapter = new TripsAdapter(context, procesarResponse(response));
//
//                //Relacionando la lista con el adaptador
                mTripsList.setAdapter(tripsAdapter);
                Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
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
        JSONObject trip = new JSONObject();
        for(int i  = 0; i < response.length(); i++) {
            try {
                trip = (JSONObject) response.get(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            try {
                viajes.add(new Trip(trip.getString("_id") ,
                                    trip.getString("origin"),
                                    trip.getString("destiny"),
                                    trip.getString("capMax"),
                                    trip.getString("date")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
            // Handle the camera action
        } else if (id == R.id.sign_out) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

//    public void getIP() {
//        final TextView mTextView = (TextView) findViewById(R.id.textView2);
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
//        String url ="http://ip.jsontest.com/";
//
//        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,
//                null, new Response.Listener<JSONObject>() {
//            @Override
//            public void onResponse(JSONObject response) {
//                try {
//                    mTextView.setText("Your IP is: "+ response.get("ip"));
//                } catch (JSONException e) {
//
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                mTextView.setText("That didn't work!");
//            }
//        });
//        // Add the request to the RequestQueue.
//        queue.add(jsObjRequest);
//    }
}
