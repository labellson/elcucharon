package com.labellson.elcucharon.ui.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CalendarView;

import com.labellson.elcucharon.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReservarActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private CalendarView mCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservar);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));

        setUpCalendar();
    }

    private void setUpCalendar(){
        mCalendar = (CalendarView) findViewById(R.id.reserva_calendar);
        GregorianCalendar gCalendar = new GregorianCalendar();

        mCalendar.setMinDate(new GregorianCalendar(2015, 0, 1).getTimeInMillis());
        mCalendar.setMaxDate(new GregorianCalendar(2015, 12, 31).getTimeInMillis());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
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
