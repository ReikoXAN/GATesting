package com.jonny.databaseexample;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class MainActivity extends AppCompatActivity {
    EditText edit_user;
    myDBHelper myDb;
    Button button_add;
    Button button_delete;
    Button button_azurepage;

    Button button_takephoto;
    Button button_savephoto;
    ImageView myImgViewPhotoHolder;

    static  final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        edit_user = (EditText)findViewById(R.id.editText);
        button_add = (Button)findViewById(R.id.btnAdd);
        button_delete = (Button)findViewById(R.id.btnDelete);
        myDb = new myDBHelper(this,null,null,1);
        //printDatabase();

        //Button myPhotobtn = (Button)findViewById(R.id.btnTakePhoto);
        myImgViewPhotoHolder = (ImageView)findViewById(R.id.imgViewPhoto);

        //disable camera if user has no button
        if(!hasCamera()){
            button_takephoto.setEnabled(false);
        }

        deleteBtn();
        OnClickTakePhoto();
        OnClickSavePhoto();
        toAzurePage();

    }

    private boolean hasCamera(){
        return getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    //launch camera



    public void addButtonClicked(View view){
        //adds the user input into the Seed constructor
        Seed seed = new Seed(edit_user.getText().toString());
        myDb.addSeed(seed);
        /*button_add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }
        );*/
    }

    public void OnClickTakePhoto(){
        button_takephoto = (Button)findViewById(R.id.btnTakePhoto);
        button_takephoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent myIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        //take a picture and pass results along to onActivityResults
                        startActivityForResult(myIntent, REQUEST_IMAGE_CAPTURE);

                    }
                }
        );
    }

    public void OnClickSavePhoto(){
        button_savephoto = (Button)findViewById(R.id.btnSaveImage);
        button_savephoto.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Bitmap myBit = ((BitmapDrawable)myImgViewPhotoHolder.getDrawable()).getBitmap();
                        ByteArrayOutputStream myBos = new ByteArrayOutputStream();
                        myBit.compress(Bitmap.CompressFormat.PNG, 100, myBos);
                        byte[] img = myBos.toByteArray();
                        //gets byteArray and converts to Bitmap
                        /*Bitmap bmp = BitmapFactory.decodeByteArray(img, 0, img.length);
                        ImageView image = (ImageView)findViewById(R.id.imageview1) ;
                        image.setImageBitmap(bmp);*/
                      boolean isInserted =  myDb.insertData(img);
                        if(isInserted==true){
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(MainActivity.this,"Data Inserted",Toast.LENGTH_LONG).show();
                        }

                    }
                }
        );
    }

    //if you wanna return image taken
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            //get the photo
            Bundle extras = data.getExtras();
            Bitmap photo = (Bitmap) extras.get("data");
            myImgViewPhotoHolder.setImageBitmap(photo);
        }
    }

    public void deleteBtn(){
        button_delete.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String inputText = edit_user.getText().toString();
                        myDb.DeleteSeed(inputText);
                    }
                }
        );

    }

    public void toAzurePage(){
        button_azurepage = (Button)findViewById(R.id.btnAzurePage);
        button_azurepage.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent azurePageIntent = new Intent("com.jonny.databaseexample.AzureTest");
                        startActivity(azurePageIntent);
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
