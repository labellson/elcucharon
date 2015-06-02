package com.labellson.elcucharon.ui.activities;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.labellson.elcucharon.R;
import com.labellson.elcucharon.model.User;
import com.labellson.elcucharon.tasks.RegisterTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar mToolbar;
    private EditText txtUserName;
    private EditText txtUserDni;
    private EditText txtUserMovil;
    private EditText txtUserEmail;
    private EditText txtUserPass;
    private ImageView imgAvatar;
    private Button btnRegistro;
    private Bitmap mBitmap;
    private final int IMAGE = 1;
    private final int PIC_CROP = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));

        //Componentes del activity
        txtUserName = (EditText) findViewById(R.id.txt_user_name);
        txtUserDni = (EditText) findViewById(R.id.txt_user_dni);
        txtUserMovil = (EditText) findViewById(R.id.txt_user_movil);
        txtUserEmail = (EditText) findViewById(R.id.txt_user_email);
        txtUserPass = (EditText) findViewById(R.id.txt_user_pass);
        imgAvatar = (ImageView) findViewById(R.id.imgAvatar);
        imgAvatar.setOnClickListener(this);

        btnRegistro = (Button) findViewById(R.id.btn_registro);

        btnRegistro.setOnClickListener(this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == IMAGE){
                Uri picUri = getPickImageResultUri(data);
                doCrop(picUri);
            }else if (requestCode == PIC_CROP){
                mBitmap = (Bitmap) data.getExtras().getParcelable("data");
                imgAvatar.setImageDrawable(new NavigationDrawerFragment.RoundImage(mBitmap));
            }
        }
    }

    private void doCrop(Uri picUri) {
        try {
            //call the standard crop action intent (the user device may not support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            //indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            //set crop properties
            cropIntent.putExtra("crop", "true");
            //indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            //indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            //retrieve data on return
            cropIntent.putExtra("return-data", true);
            //start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch(ActivityNotFoundException anfe){
            String errorMessage = "Oops! - No se puede recortar la imagen";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imgAvatar: getImage(); break;
            case R.id.btn_registro: registrar(); break;
        }
    }

    public void getImage(){
        try {
            startActivityForResult(getPickImageChooserIntent(), IMAGE);
        }catch(ActivityNotFoundException anfe){
            String errorMessage = "Oops! - el dispositivo no soporta captura de imagenes";
            Toast toast = Toast.makeText(RegisterActivity.this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void registrar(){
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
                    mBitmap, txtUserPass.getText().toString());
            RegisterTask registerTask = new RegisterTask(u, txtUserPass.getText().toString(), RegisterActivity.this);
            registerTask.execute();
        }
    }

    public Intent getPickImageChooserIntent() {

// Determine Uri of camera image to  save.
        Uri outputFileUri =  getCaptureImageOutputUri();

        List<Intent> allIntents = new ArrayList<>();
        PackageManager packageManager =  getPackageManager();

// collect all camera intents
        Intent captureIntent = new  Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> listCam =  packageManager.queryIntentActivities(captureIntent, 0);
        for (ResolveInfo res : listCam) {
            Intent intent = new  Intent(captureIntent);
            intent.setComponent(new  ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            if (outputFileUri != null) {
                intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);
            }
            allIntents.add(intent);
        }

// collect all gallery intents
        Intent galleryIntent = new  Intent(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        List<ResolveInfo> listGallery =  packageManager.queryIntentActivities(galleryIntent, 0);
        for (ResolveInfo res : listGallery) {
            Intent intent = new  Intent(galleryIntent);
            intent.setComponent(new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
            intent.setPackage(res.activityInfo.packageName);
            allIntents.add(intent);
        }

// the main intent is the last in the  list (fucking android) so pickup the useless one
        Intent mainIntent =  allIntents.get(allIntents.size() - 1);
        for (Intent intent : allIntents) {
            if  (intent.getComponent().getClassName().equals("com.android.documentsui.DocumentsActivity"))  {
                mainIntent = intent;
                break;
            }
        }
        allIntents.remove(mainIntent);

// Create a chooser from the main  intent
        Intent chooserIntent =  Intent.createChooser(mainIntent, "Elige una fuente");

// Add all other intents
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS,  allIntents.toArray(new Parcelable[allIntents.size()]));

        return chooserIntent;
    }

    /**
     * Get URI to image received from capture  by camera.
     */
    private Uri getCaptureImageOutputUri() {
        Uri outputFileUri = null;
        File getImage = getExternalCacheDir();
        if (getImage != null) {
            outputFileUri = Uri.fromFile(new File(getImage.getPath(), "pickImageResult.jpeg"));
        }
        return outputFileUri;
    }

    /**
     * Get the URI of the selected image from  {@link #getPickImageChooserIntent()}.<br/>
     * Will return the correct URI for camera  and gallery image.
     *
     * @param data the returned data of the  activity result
     */
    public Uri getPickImageResultUri(Intent  data) {
        boolean isCamera = true;
        if (data != null) {
            String action = data.getAction();
            isCamera = action != null  && action.equals(MediaStore.ACTION_IMAGE_CAPTURE);
        }
        return isCamera ?  getCaptureImageOutputUri() : data.getData();
    }
}
