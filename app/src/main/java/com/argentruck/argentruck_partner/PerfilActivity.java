package com.argentruck.argentruck_partner;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
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

public class PerfilActivity extends AppCompatActivity {

    private String email;
    private TextView mEmailView;
    private TextView mNombreView;
    private TextView mApellidoView;
    private TextView mDireccionView;
    private TextView mCBUView;
    private TextView mTelefonoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        //Se obtiene el email con el que se inicio sesion/registro
        Bundle bundle = getIntent().getExtras();
        email = bundle.getString("email");

        mEmailView = (TextView) findViewById(R.id.email);
        mNombreView = (TextView) findViewById(R.id.name);
        mApellidoView = (TextView) findViewById(R.id.surname);
        mDireccionView = (TextView) findViewById(R.id.address);
        mCBUView = (TextView) findViewById(R.id.cbu);
        mTelefonoView = (TextView) findViewById(R.id.telefono);

        Button mBackButton = (Button) findViewById(R.id.back_button);
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        getPartnerProfile();
    }

    private void getPartnerProfile() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = getResources().getString(R.string.http_ip) + "/partners/partner?email=" + email;

        final Context context = getApplicationContext();

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarResponse(response);
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

    private void procesarResponse(JSONObject response) {
        try{
            String mEmail = response.getString("email");
            String mNombre = response.getString("nombre");
            String mApellido = response.getString("apellido");
            String mDireccion = response.getString("direccion");
            String mCBU = response.getString("cbu");
            String mTelefono = response.getString("telefono");

            mEmailView.setText("Email: " + mEmail);
            mNombreView.setText("Nombre: " + mNombre);
            mApellidoView.setText("Apellido: " + mApellido);
            mDireccionView.setText("Direccion: " + mDireccion);
            mCBUView.setText("CBU: " + mCBU);
            mTelefonoView.setText("Telefono: " + mTelefono);

        } catch (JSONException e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
