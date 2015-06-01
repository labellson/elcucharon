package com.labellson.elcucharon.ui.activities;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.ui.adapter.ListReservaAdapter;
import com.labellson.elcucharon.ui.adapter.ViewPagerAdapter;
import com.labellson.elcucharon.ui.widget.SlidingTabLayout;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class ListReservasActivity extends AppCompatActivity{

    private Toolbar mToolbar;
    ViewPager pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout slidingTabLayout;
    CharSequence Titles[]={"Pasadas","Activas"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservas);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));

//        adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles);

        pager = (ViewPager) findViewById(R.id.pager);
//        pager.setAdapter(adapter);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.tabs);
        slidingTabLayout.setDistributeEvenly(true);

        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.tabsScrollColor);
            }
        });

//        slidingTabLayout.setViewPager(pager);

        new FetchReservasTask().execute();
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

    public class FetchReservasTask extends AsyncTask<Void, Void, List<Reserva>> {

        private ProgressDialog pDialog;

        public FetchReservasTask() {
            pDialog = new ProgressDialog(ListReservasActivity.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage("Cargando...");
            pDialog.setCancelable(true);
            pDialog.setMax(100);
        }

        @Override
        protected List<Reserva> doInBackground(Void... params) {
            try {
                return CucharonRestApi.fetchReservas(User.load(ListReservasActivity.this));
            } catch (IOException e) {
                Log.e("IOException", "FetchReservasTask", e);
                return null;
            } catch (JSONException e) {
                Log.e("JSONException", "FetchReservasTask", e);
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
            pDialog.setProgress(0);
            pDialog.show();
        }

        @Override
        protected void onPostExecute(List<Reserva> reservas) {
            if(reservas != null) {
                GregorianCalendar gc = new GregorianCalendar(new GregorianCalendar().get(Calendar.YEAR),
                        new GregorianCalendar().get(Calendar.MONTH),
                        new GregorianCalendar().get(Calendar.DAY_OF_MONTH));
                List<Reserva> r_pasadas = new ArrayList<Reserva>();
                List<Reserva> r_activa = new ArrayList<Reserva>();
                for(Reserva r : reservas){
                    if(r.getFecha().before(gc.getTime())){
                        r_pasadas.add(r);
                    }else{
                        r_activa.add(r);
                    }
                }
                adapter =  new ViewPagerAdapter(getSupportFragmentManager(),Titles, r_pasadas, r_activa);
                pager.setAdapter(adapter);
                slidingTabLayout.setViewPager(pager);
            }
            pDialog.dismiss();
        }
    }
}
