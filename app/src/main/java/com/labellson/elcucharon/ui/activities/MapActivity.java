package com.labellson.elcucharon.ui.activities;

import android.support.v4.app.FragmentActivity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Restaurante;

public class MapActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private GoogleMap mMap;
    private Restaurante mRestaurante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));

        mMap = ((SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map)).getMap();

        mRestaurante = (Restaurante) getIntent().getExtras().getSerializable("RESTAURANTE");

        //Añadimos un marcador
        LatLng restaurante_position = new LatLng(mRestaurante.getLat(), mRestaurante.getLng());
        mMap.addMarker(new MarkerOptions().position(restaurante_position).title(mRestaurante.getNombre()));

        //Situamos el mapa en el marcador
        CameraUpdate camUpd = CameraUpdateFactory.newLatLngZoom(restaurante_position, 16);
        mMap.animateCamera(camUpd);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
