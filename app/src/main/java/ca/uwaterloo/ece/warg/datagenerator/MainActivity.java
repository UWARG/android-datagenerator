package ca.uwaterloo.ece.warg.datagenerator;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    final static String TAG = "MainActivity";
    Handler mDataHandler = new Handler();
    int mDelay = 1000; //Milliseconds
    boolean handlerStop = false;
    String textViewOutput = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},2);
        }
        if (ActivityCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},3);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null){
            getFragmentManager().beginTransaction().replace(R.id.container,Camera2VideoFragment.newInstance(this)).commit();
        }

        final TelemetryWriter tw = new TelemetryWriter(this, null);
        mDataHandler.postDelayed(new Runnable(){
            public void run(){
                String temp[] = tw.collectData();
                textViewOutput = "";
                for (int i = 0; i<temp.length; i++) {
                    String str = temp[i];
                    if (str != null) {
                        textViewOutput += str + ",";
                    }
                }
                final TextView mTextOutput = (TextView)(findViewById(R.id.textView));
                mTextOutput.setText(textViewOutput);
                if (!handlerStop) {
                    mDataHandler.postDelayed(this, mDelay);
                }
            }
        },mDelay);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG,"Granted");
                    // permission was granted, yay!
                } else {
                    Log.d(TAG,"Denied");
                    // permission denied, boo!
                }
                return;
            }
            case 2: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG,"Granted");
                    // permission was granted, yay!
                } else {
                    Log.d(TAG,"Denied");
                    // permission denied, boo!
                }
                return;
            }
            case 3: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG,"Granted");
                    // permission was granted, yay!
                } else {
                    Log.d(TAG,"Denied");
                    // permission denied, boo!
                }
                return;
            }
        }
    }

}

