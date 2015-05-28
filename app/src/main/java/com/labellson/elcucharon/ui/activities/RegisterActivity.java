package com.labellson.elcucharon.ui.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.tasks.RegisterTask;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar mToolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));

        //Componentes del activity
        final EditText txtUserName = (EditText) findViewById(R.id.txt_user_name);
        final EditText txtUserDni = (EditText) findViewById(R.id.txt_user_dni);
        final EditText txtUserMovil = (EditText) findViewById(R.id.txt_user_movil);
        final EditText txtUserEmail = (EditText) findViewById(R.id.txt_user_email);
        final EditText txtUserPass = (EditText) findViewById(R.id.txt_user_pass);
        Button btnRegistro = (Button) findViewById(R.id.btn_registro);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUserDni.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Introduce un dni", Toast.LENGTH_SHORT).show();
                else if(txtUserMovil.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Introduce un telefono", Toast.LENGTH_SHORT).show();
                else if(txtUserEmail.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Introduce el email", Toast.LENGTH_SHORT).show();
                else if(txtUserPass.getText().toString().isEmpty())
                    Toast.makeText(RegisterActivity.this, "Introduce una contraseña", Toast.LENGTH_SHORT).show();
                else {
                    User u = new User(txtUserEmail.getText().toString(),
                            txtUserMovil.getText().toString(),
                            txtUserDni.getText().toString(),
                            txtUserName.getText().toString(),
                            null, txtUserPass.getText().toString());
                    RegisterTask registerTask = new RegisterTask(u, txtUserPass.getText().toString(), RegisterActivity.this);
                    registerTask.execute();
                }
            }
        });
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
