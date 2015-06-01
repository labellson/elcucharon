package com.labellson.elcucharon.ui.activities;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Restaurante;
import com.labellson.elcucharon.ui.NavigationDrawerCallbacks;
import com.labellson.elcucharon.ui.adapter.CardRestauranteAdapter;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class MainActivity extends ActionBarActivity
        implements NavigationDrawerCallbacks, View.OnClickListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private Toolbar mToolbar;
    private RecyclerView recView;
    private List<Restaurante> restaurantes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.fragment_drawer);

        // Configuramos el RecyclerView y le damos tamaño fijo
        recView = (RecyclerView) findViewById(R.id.rec_view_main);
        recView.setHasFixedSize(true);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recView.setItemAnimator(new DefaultItemAnimator());

        // Set up the drawer.
        mNavigationDrawerFragment.setup(R.id.fragment_drawer, (DrawerLayout) findViewById(R.id.drawer), mToolbar);
        // populate the navigation drawer
        //mNavigationDrawerFragment.setUserData("Daniel", "labellson@gmail.com", BitmapFactory.decodeResource(getResources(), R.drawable.avatar));
        ListRestaurantes restaurantes_async_task = new ListRestaurantes(this);
        restaurantes_async_task.execute();
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
//        Toast.makeText(this, "Menu item selected -> " + position, Toast.LENGTH_SHORT).show();
        switch (position){
            case 0:
                Intent intent = new Intent(MainActivity.this, ListReservasActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    public void onBackPressed() {
        if (mNavigationDrawerFragment.isDrawerOpen())
            mNavigationDrawerFragment.closeDrawer();
        else
            super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MainActivity.this, RestauranteActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("RESTAURANTE", restaurantes.get(recView.getChildAdapterPosition(v)));
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private class ListRestaurantes extends AsyncTask<Void, Void, List<Restaurante>>{

        public ListRestaurantes(Context context) {
            this.context = context;
        }

        private Context context;

        @Override
        protected List<Restaurante> doInBackground(Void... params) {
            try {
                return CucharonRestApi.listRestaurantes(200);
            } catch (IOException e) {
                Log.e("Rest listRestaurante", "IOException", e);
                return null;
            } catch (JSONException e) {
                Log.e("Rest listRestaurante", "JSONException", e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(final List<Restaurante> result) {
            if(result == null){
                Toast.makeText(MainActivity.this, "Sin Resultados", Toast.LENGTH_SHORT).show();
                Log.i("fallo","No funciono");
            }else{
                restaurantes = result;
                //Creamos el adapter
                CardRestauranteAdapter adapter = new CardRestauranteAdapter(result);
                adapter.setOnClickListener((View.OnClickListener) context);
                recView.setAdapter(adapter);

                Log.i("Success", "Ferpecto");
            }
        }
    }


}
