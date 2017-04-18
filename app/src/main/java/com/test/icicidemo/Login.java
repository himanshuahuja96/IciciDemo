package com.test.icicidemo;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import java.util.HashMap;

public class Login extends AppCompatActivity implements VolleyInterface {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    @Override
    public void requestStarted(int requestCode) {

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
        if(requestCode==7)
        {
            Log.d("response",response);
            if(response.equals("Success"))
            {
                startActivityForResult(new Intent(this,SimpleScannerActivity.class),1);
            }
            else if(response.equals("Invalid Details"))
            {
                Toast.makeText(this,"Invalid Details",Toast.LENGTH_SHORT).show();
            }
            else  if(response.equals("Account created"))
            {
                Toast.makeText(this,"Account created",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {

    }
}
