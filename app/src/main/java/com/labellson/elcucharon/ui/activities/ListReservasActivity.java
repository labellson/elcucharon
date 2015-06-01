package com.labellson.elcucharon.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.ui.adapter.ListReservaAdapter;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

import org.json.JSONException;

import java.io.IOException;
import java.util.List;

public class ListReservasActivity extends AppCompatActivity implements View.OnLongClickListener {

    private Toolbar mToolbar;
    private RecyclerView recView;
    private List<Reserva> mReservas;
    private ListReservaAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_reservas);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));

        recView = (RecyclerView) findViewById(R.id.rec_view_list_reserva);
        recView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recView.setItemAnimator(new DefaultItemAnimator());

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

    @Override
    public boolean onLongClick(View v) {
        DeleteReservaDialogFragment df = new DeleteReservaDialogFragment();
        df.setIdReserva(mReservas.get(recView.getChildAdapterPosition(v)).getId(), recView.getChildAdapterPosition(v));
        df.show(getFragmentManager(), "DeleteReserva");
        return true;
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
                mReservas = reservas;

                mAdapter = new ListReservaAdapter(reservas);
//            adapter.setOnClickListener(ListReservas.this);
                mAdapter.setOnLongClickListener(ListReservasActivity.this);
                recView.setAdapter(mAdapter);
            }
            pDialog.dismiss();
        }
    }

    private class DeleteReservaTask extends AsyncTask<Void, Void,Void>{

        private int idReserva;
        private int position;

        public DeleteReservaTask(int idReserva, int position) {
            this.idReserva = idReserva;
            this.position = position;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(ListReservasActivity.this, "Reserva Cancelada", Toast.LENGTH_SHORT).show();
            mReservas.remove(position);
            mAdapter.notifyItemRemoved(position);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                CucharonRestApi.deleteReserva(idReserva, User.load(ListReservasActivity.this).getAuth());
            } catch (IOException e) {
                Log.e("IOException", "DeleteReservaTask", e);
            }
            return null;
        }
    }

    public class DeleteReservaDialogFragment extends DialogFragment{

        private int idReserva;
        private int position;

        public void setIdReserva(int idReserva, int position){
            this.idReserva = idReserva;
            this.position = position;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(getString(R.string.cancelar_reserva)).setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    new DeleteReservaTask(idReserva, position).execute();
                }
            }).setNegativeButton("No", null);
            return builder.create();
        }
    }
}
