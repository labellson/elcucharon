package com.labellson.elcucharon.ui.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.Restaurante;
import com.labellson.elcucharon.tasks.FetchRestauranteTask;
import com.labellson.elcucharon.util.restapi.CucharonRestApi;

public class RestauranteActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private Restaurante mRestaurante;
    private ImageView mImgRestaurante;
    private TextView mTxtDescripcionRestaurante;
    private Button btnRservar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurante);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));

        mRestaurante = (Restaurante) this.getIntent().getExtras().getSerializable("RESTAURANTE");
        mImgRestaurante = (ImageView) findViewById(R.id.img_restaurante);
        mTxtDescripcionRestaurante = (TextView) findViewById(R.id.label_restaurante_descripcion);
        ProgressBar pBar = (ProgressBar) findViewById(R.id.loadingPanel);

        setTitle(mRestaurante.getNombre());
        mImgRestaurante.setImageBitmap(mRestaurante.getFoto());
        mTxtDescripcionRestaurante.setText(mRestaurante.getDescripcion());

        findViewById(R.id.btn_reservar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RestauranteActivity.this, ReservarActivity.class);
                startActivity(intent);
            }
        });

        new FetchRestauranteTask(this, mImgRestaurante, mTxtDescripcionRestaurante, mRestaurante, mImgRestaurante.getWidth(), pBar).execute();
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
