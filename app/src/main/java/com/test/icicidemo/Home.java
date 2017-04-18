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
        findViewById(R.id.atm_logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMap=new HashMap<>();
                hashMap.put("token",token);
                hashMap.put("type","logout");
                VolleyHelper.postRequestVolley(Home.this,"https://qrcodeatm.tk/api/get.php",hashMap,4,false);

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
            VolleyHelper.postRequestVolley(this,this,"https://qrcodeatm.tk/api/get.php",hashMap,1,false);
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
                VolleyHelper.postRequestVolley(this,this,"https://qrcodeatm.tk/api/get.php",hashMap,1,false);
            }
        } catch (JSONException e) {
            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();
        }
    }else if(requestCode==4){
        Log.d("response",response);
        try {
            JSONObject jsonObject=new JSONObject(response);

        } catch (JSONException e) {
            Toast.makeText(this,"Invalid",Toast.LENGTH_SHORT).show();
        }
    }


    }

    @Override
    public void requestEndedWithError(int requestCode, VolleyError error) {

    }
}
