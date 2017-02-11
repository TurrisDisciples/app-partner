package com.argentruck.argentruck_partner;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InitialActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initial);

        Button mSignInButton = (Button) findViewById(R.id.iniciar_sesion);
        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentLogIn = new Intent(InitialActivity.this, LoginActivity.class );
                startActivity(intentLogIn);
            }
        });

        Button mRegisterButton = (Button) findViewById(R.id.crear_cuenta);
        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentRegister = new Intent(InitialActivity.this, RegisterActivity.class );
                startActivity(intentRegister);
            }
        });
    }
    @Override
    public void onBackPressed() {}
}
