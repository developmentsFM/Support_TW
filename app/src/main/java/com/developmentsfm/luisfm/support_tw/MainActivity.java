package com.developmentsfm.luisfm.support_tw;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;
    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;
    private ImageView refresh;

    private Button b1, b2, b3, b4;
    private View l1, l2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        refresh = (ImageView) findViewById(R.id.refresh);
        refresh.setOnClickListener(this);
        refresh.setVisibility(View.GONE);

        l1 = (View) findViewById(R.id.includeIN);
        l2 = (View)  findViewById(R.id.includeUP);


        l2.setVisibility(View.GONE);

        b1 = (Button) findViewById(R.id.button_11);
        b1.setOnClickListener(this);

        b2 = (Button) findViewById(R.id.button_12);
        b2.setOnClickListener(this);

        b3 = (Button) findViewById(R.id.button_21);
        b3.setOnClickListener(this);

        b4 = (Button) findViewById(R.id.button_22);
        b4.setOnClickListener(this);


        if (checkPlayServices(this)) {
            if(chekConecttion(this)){
                //Start IntentService to register this application with GCM.
                dialog = ProgressDialog.show(this, "","Please wait for few seconds...", true);
                boolean sentToken = sharedPreferences.getBoolean(VarPreferences.SENT_TOKEN_TO_SERVER, false);

                if(!sentToken){
                    Intent intent = new Intent(this, RegistrationIntentService.class);
                    startService(intent);
                    new checkGCMRegister().execute();
                }else
                    dialog.dismiss();
            }else{
                Toast.makeText(this,"This device does not have network access.",Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.refresh:

                Toast.makeText(this,"Updating",Toast.LENGTH_LONG).show();
                break;

            case R.id.button_11:

                l1.setVisibility(View.GONE);
                l2.setVisibility(View.VISIBLE);
                break;

            case R.id.button_12:

                Toast.makeText(this,"Starting",Toast.LENGTH_LONG).show();
                break;

            case R.id.button_21:

                l2.setVisibility(View.GONE);
                l1.setVisibility(View.VISIBLE);
                break;

            case R.id.button_22:

                Toast.makeText(this,"Recording",Toast.LENGTH_LONG).show();
                break;

        }



    }


    //Check GCMRegister
    private class checkGCMRegister extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            check();
            return null;
        }

        public void check(){
            while (true){
                boolean sentToken = sharedPreferences.getBoolean(VarPreferences.SENT_TOKEN_TO_SERVER, false);
                if(sentToken) break;
            }
        }

        protected void onPostExecute(String user){
            dialog.dismiss();
        }

    }








    //Check PlayServices
    private boolean checkPlayServices(Context context) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        int resultCode = apiAvailability.isGooglePlayServicesAvailable(context);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (apiAvailability.isUserResolvableError(resultCode)) {
                apiAvailability.getErrorDialog((Activity) context, resultCode, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            }else {
                Toast.makeText(this,"This device is not supported.",Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    //Check Connection
    public static boolean chekConecttion(Context context) {
        boolean bConectado = false;
        ConnectivityManager connec = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] redes = connec.getAllNetworkInfo();
        for (int i = 0; i < 2; i++) {
            if (redes[i].getState() == NetworkInfo.State.CONNECTED) {
                bConectado = true;
            }
        }
        return bConectado;
    }




}
