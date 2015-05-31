package com.labellson.elcucharon.ui.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Reserva;
import com.labellson.elcucharon.model.Restaurante;
import com.labellson.elcucharon.tasks.FetchHoraReservaTask;
import com.labellson.elcucharon.tasks.FetchReservasRestauranteTask;
import com.labellson.elcucharon.tasks.FetchRestauranteTask;
import com.labellson.elcucharon.tasks.RegisterReservaTask;
import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ReservarActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private static Toolbar mToolbarReservar;
    private Date mDate;
    private CaldroidFragment mCaldroidFragment;
    private int mRestauranteId;
    private static String mHora;
    private static LinearLayout mBtn_hora;
    private SelectHourDialogFragment mDFragment;
    private Context context = this;
    private final SimpleDateFormat dfFecha = new SimpleDateFormat("yyyy-MM-dd");
    private final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        mToolbarReservar = (Toolbar) findViewById(R.id.toolbar_reservar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));
        mToolbarReservar.setBackgroundColor(getResources().getColor(R.color.grey));

        //Variables
        mRestauranteId = getIntent().getExtras().getInt("ID_RESTAURANTE");

        setUpCalendar();

        //View seleccionar hora
        mBtn_hora = (LinearLayout) findViewById(R.id.btn_elegir_hora);
        mDFragment = new SelectHourDialogFragment();
        mBtn_hora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDFragment.show(getFragmentManager(), "horas");
            }
        });

        //Btn Reservar
        mToolbarReservar.findViewById(R.id.btn_reservar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mDate == null || mHora  == null){
                    Toast.makeText(ReservarActivity.this, "Elige fecha y hora", Toast.LENGTH_SHORT).show();
                }else{
                    SharedPreferences sp = context.getSharedPreferences(context.getString(R.string.sp_name), Context.MODE_PRIVATE);
                    if(sp.getBoolean(getString(R.string.sp_learned_drawer), false)){
                        try {
                            new RegisterReservaTask(context, new Reserva(df.parse(dfFecha.format(mDate)+" "+mHora), sp.getInt(getString(R.string.sp_user_id), -1), new Restaurante(mRestauranteId))).execute();
                        } catch (ParseException e) {
                            Log.e("ParseException", "Error ReservarActivity", e);
                        }
                    }else{
                        Intent intent = new Intent(context, RegisterActivity.class);
                        //intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        context.startActivity(intent);
                        Toast.makeText(ReservarActivity.this, "Necesitas estar registrado", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        new FetchReservasRestauranteTask(this, mCaldroidFragment, mRestauranteId).execute();
    }

    private void setUpCalendar(){
//        mCalendar.setMinDate(new GregorianCalendar(2015, 0, 1).getTimeInMillis());
//        mCalendar.setMaxDate(new GregorianCalendar(2015, 12, 31).getTimeInMillis());

        GregorianCalendar gCalendar = new GregorianCalendar();
        mCaldroidFragment = new CaldroidFragment();

        //Agregamos los argumentos al calendario
        Bundle bundle = new Bundle();
        bundle.putString(CaldroidFragment.MIN_DATE, dfFecha.format(gCalendar.getTime()));
        bundle.putBoolean(CaldroidFragment.SHOW_NAVIGATION_ARROWS, false);
        mCaldroidFragment.setArguments(bundle);
        mCaldroidFragment.clearBackgroundResourceForDate(gCalendar.getTime());

        android.support.v4.app.FragmentTransaction t = getSupportFragmentManager().beginTransaction();
        t.replace(R.id.calendar, mCaldroidFragment);
        t.commit();

        final CaldroidListener listener = new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                if(mDate != null) mCaldroidFragment.clearBackgroundResourceForDate(mDate);
                mDate = date;
                mHora = null;
                mCaldroidFragment.setBackgroundResourceForDate(R.color.caldroid, date);
                mCaldroidFragment.refreshView();
                ((TextView)mBtn_hora.findViewById(R.id.value_elegir_hora)).setText("");
                mToolbarReservar.setBackgroundColor(getResources().getColor(R.color.grey));
                new FetchHoraReservaTask(context, mRestauranteId, date, mDFragment).execute();
            }
        };

        mCaldroidFragment.setCaldroidListener(listener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == android.R.id.home) {
//            this.finish();
//        }

        return super.onOptionsItemSelected(item);
    }

    public static class SelectHourDialogFragment extends DialogFragment{

        private String[] data;

        public void setData(String[] data){
            this.data = data;
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Elige una Hora").setItems(data, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mHora = data[which];
                    ((TextView) mBtn_hora.findViewById(R.id.value_elegir_hora)).setText(mHora);
                    mToolbarReservar.setBackgroundColor(getResources().getColor(R.color.myPrimaryColor));
                }
            });
            return builder.create();
        }
    }
}
