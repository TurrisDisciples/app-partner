package com.argentruck.argentruck_partner;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class ClientsActivity extends AppCompatActivity {

    private ListView mClientsList;
    private ClientsAdapter clientsAdapter;
    private Trip viaje;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clients);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        viaje = (Trip) getIntent().getSerializableExtra("viaje");

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
                Intent intent = new Intent(ClientsActivity.this, ItemDetailActivity.class);
                intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, 1);
                startActivity(intent);
            }
        });
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
//                Intent intent = new Intent(ClientsActivity.this, MapsActivity.class);
//                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
