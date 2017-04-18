package com.test.icicidemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

/**
 * Created by Almond7 on 26-10-2016.
 */

public class SimpleScannerActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {
    private static final String TAG = SimpleScannerActivity.class.getSimpleName();
    private ZXingScannerView mScannerView;
    private LinearLayout ll_scanner_container;

    @Override
    public void onCreate(Bundle state) {
        super.onCreate(state);
        setContentView(R.layout.activity_scanner);
        ll_scanner_container = (LinearLayout) findViewById(R.id.ll_scanner_container);
//        mScannerView = new ZXingScannerView(this);   // Programmatically initialize the scanner view
        mScannerView = (ZXingScannerView) findViewById(R.id.zing_scanner);   // Programmatically initialize the scanner view
//        ll_scanner_container.addView(mScannerView);
        mScannerView.setFormats(ZXingScannerView.ALL_FORMATS);
        mScannerView.setShouldScaleToFill(true);
        mScannerView.setAutoFocus(true);
//        setContentView(mScannerView);                   // Set the scanner view as the content view
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this); // Register ourselves as a handler for scan results.
        mScannerView.startCamera();          // Start camera on resume

    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();           // Stop camera on pause
    }

    @Override
    public void onBackPressed() {
        setResult(0);
        finish();
    }

    @Override
    public void handleResult(Result rawResult) {
        // Do something with the result here
        Log.v(TAG, rawResult.getText()); // Prints scan results
        Log.v(TAG, rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
        Intent intent = new Intent();
        intent.putExtra("TOKEN", rawResult.getText());
        setResult(1, intent);
        finish();
        // If you would like to resume scanning, call this method below:
//        mScannerView.resumeCameraPreview(this);
 }

    public void clickScanCancel(View v){
        finish();
    }
}
