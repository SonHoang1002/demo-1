package com.example.demorecapcha;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApi;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.safetynet.SafetyNet;
import com.google.android.gms.safetynet.SafetyNetApi;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks {


    CheckBox checkBox;
    GoogleApiClient googleApiClient;
    String SiteKey = "6Lc3rSQfAAAAAILM5ShCIQzBnd6_SA_0D0lBbKTE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkBox = findViewById(R.id.checkBox);

        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(SafetyNet.API)
                .addConnectionCallbacks(MainActivity.this)
                .build();

        googleApiClient.connect();
        checkBox.setOnClickListener(view -> {
            if(checkBox.isChecked()){
                SafetyNet.SafetyNetApi.verifyWithRecaptcha(googleApiClient,SiteKey)
                        .setResultCallback(new ResultCallback<SafetyNetApi.RecaptchaTokenResult>() {
                            @Override
                            public void onResult(@NonNull SafetyNetApi.RecaptchaTokenResult recaptchaTokenResult) {

                                Status status = recaptchaTokenResult.getStatus();
                                if(status !=null && status.isSuccess()){

                                    Toast.makeText(getApplicationContext(),"Successfully Verified..",Toast.LENGTH_SHORT).show();
                                    checkBox.setTextColor(Color.GREEN);
                                }
                            }
                        });
            }else{
                checkBox.setTextColor(Color.RED);
            }
        });


    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}