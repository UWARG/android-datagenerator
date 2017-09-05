package ca.uwaterloo.ece.warg.datagenerator;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * Created by cphajduk on 29/08/17.
 */

public class IMUListener implements SensorEventListener {

    float[] mGravity;
    float[] mGeomagnetic;
    float[] mRotation;

    float[] mOrientation;

    private final SensorManager mSensorManager;
    private final Sensor mAccelerometer;
    private final Sensor mMagnetometer;
    private final Sensor mGyroscope;

    IMUListener(Context context){
        mSensorManager = (SensorManager)context.getSystemService(Context.SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mMagnetometer = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
        mGyroscope = mSensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);

        mSensorManager.registerListener((SensorEventListener)this,mAccelerometer,1000);
        mSensorManager.registerListener((SensorEventListener)this,mMagnetometer,1000);
        mSensorManager.registerListener((SensorEventListener)this,mGyroscope,1000);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
            mGravity = sensorEvent.values;
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
            mGeomagnetic = sensorEvent.values;
        }
        else if(sensorEvent.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
            mRotation = sensorEvent.values;
        }

        if (mGravity != null && mGeomagnetic != null){
            float R[] = new float[9];
            float I[] = new float[9];
            boolean success = SensorManager.getRotationMatrix(R, I, mGravity, mGeomagnetic);
            if (success){
                mOrientation = new float[3];
                SensorManager.getOrientation(R, mOrientation);

            }
        }
    }

    public float[] getOrientation(){
        return mOrientation;
    }

    public float[] getRotation(){
        return mRotation;
    }
}
