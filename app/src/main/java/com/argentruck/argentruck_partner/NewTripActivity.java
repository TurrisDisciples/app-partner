package com.argentruck.argentruck_partner;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class NewTripActivity extends AppCompatActivity {

    private String email;
    private EditText mOrigen;
    private EditText mDestino;
    private EditText mCapacidad;
    private EditText mFecha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_trip);

        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");

        mOrigen = (EditText) findViewById(R.id.origen);
        mDestino = (EditText) findViewById(R.id.destino);
        mCapacidad = (EditText) findViewById(R.id.capacidad);
        mFecha = (EditText) findViewById(R.id.fecha);

        Button mRegisterButton = (Button) findViewById(R.id.crear_viaje_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidData()) {
                    registrarNuevoViaje();
                }
            }
        });
    }

    private boolean checkValidData() {
        String origen = mOrigen.getText().toString().trim();
        String destino = mDestino.getText().toString().trim();
        String capacidad = mCapacidad.getText().toString().trim();
        String fecha = mFecha.getText().toString().trim();

        if (TextUtils.isEmpty(origen)) {
            Toast.makeText(getApplicationContext(), "Este campo es obligatorio", Toast.LENGTH_LONG).show();
            mOrigen.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(destino)) {
            Toast.makeText(getApplicationContext(), "Este campo es obligatorio", Toast.LENGTH_LONG).show();
            mDestino.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(capacidad)) {
            Toast.makeText(getApplicationContext(), "Este campo es obligatorio y mayor a 0", Toast.LENGTH_LONG).show();
            mCapacidad.requestFocus();
            return false;
        }
        if (TextUtils.isEmpty(fecha)) {
            Toast.makeText(getApplicationContext(), "Este campo es obligatorio y con formato AAAA-MM-DD", Toast.LENGTH_LONG).show();
            mFecha.requestFocus();
            return false;
        }
        return true;
    }

    private void registrarNuevoViaje() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //TODO: Acomodar esta ip!!!!!
        final String url = "http://192.168.1.103:3000/partners/travel";

        final Context context = getApplicationContext();

        JSONObject jsParam = new JSONObject();
        JSONObject mRequestParams = new JSONObject();

        try {
            mRequestParams.put("email", email);
            mRequestParams.put("origin", mOrigen.getText().toString().trim());
            mRequestParams.put("destiny", mDestino.getText().toString().trim());
            mRequestParams.put("capMax", mCapacidad.getText().toString().trim());
            mRequestParams.put("date", mFecha.getText().toString().trim());

            jsParam.put("data", mRequestParams);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, url,
                jsParam, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if ((response.toString()).contains("created")) {
                    Toast.makeText(context, "Viaje creado correctamente", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                }
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
}

