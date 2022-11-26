package com.is6144.musicapp;


import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.journeyapps.barcodescanner.ScanContract;
import com.journeyapps.barcodescanner.ScanOptions;

     class MainActivity2 extends AppCompatActivity
{
    Button btn_scan; // I initialised the button from the view
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_main);
        btn_scan = findViewById(R.id.btn_scAn);// i am setting an instance from the button id
        btn_scan.setOnClickListener(v->
        {
            scanCode();  // so this will perform the on click action when the button is clicked
        });
    }

    private void scanCode()
    {
        ScanOptions options = new ScanOptions();
        options.setPrompt("Volume up to flash on");// im setting some messages for the app to alert
        options.setBeepEnabled(true);
        options.setOrientationLocked(true);
        options.setCaptureActivity(CaptureAct.class);//i am setting a camera permission
        barLauncher.launch(options);// this accepts the parameter
    }

    ActivityResultLauncher<ScanOptions> barLauncher = registerForActivityResult(new ScanContract(), result->// i can retrieve the data when it is captured
    {
        if(result.getContents() !=null)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity2.this); // this is for me to check if something is returned
            builder.setTitle("Result");
            builder.setMessage(result.getContents());
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialogInterface, int i)
                {
                    dialogInterface.dismiss();
                }
            }).show();// show method makes it visible
        }
    });

}