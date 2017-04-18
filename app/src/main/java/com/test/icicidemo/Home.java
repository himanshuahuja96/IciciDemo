package com.test.icicidemo;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class Home extends AppCompatActivity implements VolleyInterface {
    String token;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.atm_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(Home.this,SimpleScannerActivity.class),1);
            }
        });
        findViewById(R.id.login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = ((TextInputLayout)findViewById(R.id.username)).getEditText().getText().toString();
                String access = ((TextInputLayout)findViewById(R.id.access_code)).getEditText().getText().toString();
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("username",user);
                hashMap.put("accesscode",access);
                VolleyHelper.postRequestVolley(Home.this,Home.this,"https://www.qrcodeatm.tk/api/api.php",hashMap,7,false);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1 && resultCode==1){
            token=data.getStringExtra("TOKEN");
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("token",token);
            hashMap.put("type","check");
            VolleyHelper.postRequestVolley(this,this,"http://icicidemo.16mb.com/get.php",hashMap,1,false);
        }
    }

    @Override
    public void requestStarted(int requestCode) {

    }

    @Override
    public void requestCompleted(int requestCode, String response) {
    if(requestCode==1){
        Log.d("response",response);
        try {
            JSONObject jsonObject=new JSONObject(response);
            if(jsonObject.getString("iciciuserid").equals("-1")){
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("token",token);
                hashMap.put("type","update");
                hashMap.put("iciciuserid","arpit");
                hashMap.put("acc_rej","0");
                VolleyHelper.postRequestVolley(this,this,"http://icicidemo.16mb.com/get.php",hashMap,1,false);
            }
        } catch (JSONException e) {
            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();
        }
    }

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
