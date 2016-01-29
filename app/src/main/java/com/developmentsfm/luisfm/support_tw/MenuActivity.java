package com.developmentsfm.luisfm.support_tw;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    private Button alarm;

    boolean a = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        alarm = (Button) findViewById(R.id.button);
        alarm.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.button){

           // if(a) startService(new Intent(this, Alert_Service.class));
           //     else stopService(new Intent(this, Alert_Service.class));

           // a = !a;

           stopService(new Intent(this, Alert_Service.class));

        }

    }
}
