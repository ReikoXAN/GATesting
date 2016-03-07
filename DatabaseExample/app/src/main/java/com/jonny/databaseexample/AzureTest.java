package com.jonny.databaseexample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.microsoft.windowsazure.mobileservices.*;
import com.microsoft.windowsazure.mobileservices.http.ServiceFilterResponse;
import com.microsoft.windowsazure.mobileservices.table.TableOperationCallback;

public class AzureTest extends AppCompatActivity {


    private MobileServiceClient mClient;

    TodoItem item = new TodoItem();
    MyAzureTestTbl testitem = new MyAzureTestTbl();


    Button button_azure;
    EditText text_azure;
    EditText number_azure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_azure_test);
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

        toAzure();
        try {
            mClient = new MobileServiceClient("https://testerga.azurewebsites.net", this);
        } catch (Exception e) {

        }
        //item.Text ="Test item";
        text_azure = (EditText) findViewById(R.id.eTText);
        number_azure = (EditText) findViewById(R.id.eTNumber);




}

    public void toAzure() {
        button_azure = (Button) findViewById(R.id.btnToAzure);
        button_azure.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        testitem.Text = text_azure.getText().toString();
                        testitem.Number = Integer.parseInt(number_azure.getText().toString());
                        mClient.getTable(MyAzureTestTbl.class).insert(testitem, new TableOperationCallback<MyAzureTestTbl>() {
                            @Override
                            public void onCompleted(MyAzureTestTbl entity, Exception exception, ServiceFilterResponse response) {
                                if (exception == null) {
                                    // Insert succeeded
                                    //Toast.makeText(getApplicationContext(),"Inserted", Toast.LENGTH_LONG).show();
                                    Toast myToast = Toast.makeText(getApplicationContext(),"Inserted", Toast.LENGTH_LONG);
                                    myToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                    myToast.show();
                                    number_azure.setText("");
                                    text_azure.setText("");

                                } else {
                                    // Insert failed
                                    //Toast.makeText(getApplicationContext(),"Not Inserted", Toast.LENGTH_LONG).show();
                                    Toast myFailToast = Toast.makeText(getApplicationContext(),"Not Inserted", Toast.LENGTH_LONG);
                                    myFailToast.setGravity(Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
                                    myFailToast.show();
                                    number_azure.setText("");
                                    text_azure.setText("");
                                }
                            }
                        });
                    }
                }
        );

    }

}
