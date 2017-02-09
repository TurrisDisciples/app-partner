package com.argentruck.argentruck_partner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText mEmailView;
    private EditText mContrasenaView;
    private EditText mNombreView;
    private EditText mApellidoView;
    private EditText mDireccionView;
    private EditText mCBUView;
    private EditText mTelefonoView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mEmailView = (EditText) findViewById(R.id.email);
        mContrasenaView = (EditText) findViewById(R.id.password);
        mNombreView = (EditText) findViewById(R.id.name);
        mApellidoView = (EditText) findViewById(R.id.surname);
        mDireccionView = (EditText) findViewById(R.id.address);
        mCBUView = (EditText) findViewById(R.id.cbu);
        mTelefonoView = (EditText) findViewById(R.id.telephone);

        Button mRegisterButton = (Button) findViewById(R.id.email_register_button);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidData()) {
                    register();
                }
            }
        });
    }

    private boolean checkValidData() {
        //TODO: verificar que los valores ingresados para registrarse sean correctos
        return true;
    }

    private void register() {

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://localhost:3000/partners/";

        final Context context = getApplicationContext();

        JsonArrayRequest jsObjRequest = new JsonArrayRequest(Request.Method.POST, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show();
                    Intent intentMain = new Intent(RegisterActivity.this, MainActivity.class );
                    startActivity(intentMain);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> mRequestParams = new HashMap<>();
                mRequestParams.put("email", mEmailView.getText().toString());
                mRequestParams.put("password", mContrasenaView.getText().toString());
                mRequestParams.put("nombre", mNombreView.getText().toString());
                mRequestParams.put("apellido", mApellidoView.getText().toString());
                mRequestParams.put("direccion", mDireccionView.getText().toString());
                mRequestParams.put("cbu", mCBUView.getText().toString());
                mRequestParams.put("telefono", mTelefonoView.getText().toString());

                return mRequestParams;
            }
        };
        // Add the request to the RequestQueue.
        queue.add(jsObjRequest);
    }
}
