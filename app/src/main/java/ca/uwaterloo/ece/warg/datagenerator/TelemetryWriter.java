package ca.uwaterloo.ece.warg.datagenerator;

/**
 * Created by cphajduk on 18/08/17.
 */

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.location.LocationManager;

import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import ca.uwaterloo.ece.warg.datagenerator.GPSDataListener;

public class TelemetryWriter{

    String[] CSV_HEADERS = {"lat","lon","sys_time","gps_time","pitch","roll","yaw","pitch_rate","roll_rate","yaw_rate","airspeed","altitude","ground_speed","heading","path_checksum","roll_rate_setpoint","pitch_rate_setpoint","yaw_rate_setpoint","roll_setpoint","pitch_setpoint","heading_setpoint","altitude_setpoint","throttle_setpoint","internal_battery_voltage","external_battery_voltage","program_state","autonomous_level","startup_errors","am_interchip_errors","pm_interchip_errors","gps_communication_errors","dl_transmission_errors","ul_receive_errors","peripheral_status","uhf_channel_status","ul_rssi","uhf_rssi","uhf_link_quality","waypoint_index","waypoint_count","following_path","roll_rate_kp","roll_rate_kd","roll_rate_ki","pitch_rate_kp","pitch_rate_kd","pitch_rate_ki","yaw_rate_kp","yaw_rate_kd","yaw_rate_ki","roll_angle_kp","roll_angle_kd","roll_angle_ki","pitch_angle_kp","pitch_angle_kd","pitch_angle_ki","heading_kp","heading_ki","altitude_kp","altitude_ki","ground_speed_kp","ground_speed_ki","path_kp","orbit_kp","ch1_in","ch2_in","ch3_in","ch4_in","ch5_in","ch6_in","ch7_in","ch8_in","ch1_out","ch2_out","ch3_out","ch4_out","ch5_out","ch6_out","ch7_out","ch8_out","channels_scaled","dl_rssi"};
    String[] mData = new String[CSV_HEADERS.length];

    String baseDir = android.os.Environment.getExternalStorageDirectory().getAbsolutePath();
    String fileName = "AnalysisData";
    String fileExt = ".csv";
    String filePath = baseDir + File.separator + fileName + fileExt;
    File f = new File(filePath);
    FileWriter mFileWriter;
    Context mContext;

    CSVWriter writer;

    GPSDataListener gpsListener;
    IMUListener imuListener;

    TelemetryWriter(Context context, String fileName){
        mContext = context;
        if (fileName != null) {
            File f = context.getExternalFilesDir(null);
            baseDir = f.getAbsolutePath();
            filePath = baseDir + File.separator + fileName + fileExt;

            try {
                // File exist
                if (f.exists() && !f.isDirectory()) {
                    mFileWriter = new FileWriter(filePath, true);
                    writer = new CSVWriter(mFileWriter);
                } else {
                    writer = new CSVWriter(new FileWriter(filePath));
                }
                writer.writeNext(CSV_HEADERS);
            } catch (java.io.IOException ex) {
                Log.e("Error", "IO Exception");
            }
        }

        //Request GPS Updates
        gpsListener = new GPSDataListener(mContext);
        if ( ContextCompat.checkSelfPermission( mContext, android.Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            LocationManager gpsManager = (LocationManager)mContext.getSystemService(Context.LOCATION_SERVICE);
            gpsManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, (LocationListener)gpsListener);
        }
        imuListener = new IMUListener(mContext);
    }

    String[] collectData(){
        //Data collection
        gpsListener.getGPS();

        float[] orientation = imuListener.getOrientation();
        float[] rotation = imuListener.getRotation();

        //Formatting
        mData[0] = String.valueOf(gpsListener.getLatitude());
        mData[1] = String.valueOf(gpsListener.getLongitude());
        mData[2] = String.valueOf(android.os.SystemClock.uptimeMillis());
        mData[3] = String.valueOf(gpsListener.getTime());
        if (orientation != null && rotation != null) {
            mData[4] = String.valueOf(orientation[1]); //Pitch
            mData[5] = String.valueOf(orientation[2]); //Roll
            mData[6] = String.valueOf(orientation[0]); //Yaw
            mData[7] = String.valueOf(rotation[1]); //Pitch Rate
            mData[8] = String.valueOf(rotation[2]); //Roll Rate
            mData[9] = String.valueOf(rotation[0]); //Yaw Rate
        }
        mData[10] = "0"; //Airspeed is zero
        mData[11] = String.valueOf(gpsListener.getAltitude());
        mData[12] = String.valueOf(gpsListener.getSpeed());
        mData[13] = String.valueOf(gpsListener.getHeading());
        mData[14] = "0"; //Path Checksum
        mData[15] = "0";
        mData[16] = "0";
        mData[17] = "0";
        mData[18] = "0";
        mData[19] = "0";
        mData[20] = "0";
        mData[21] = "0"; //Altitude Setpoint
        mData[22] = "0";
        mData[23] = "0";
        mData[24] = "0";
        mData[25] = "0";
        mData[26] = "0"; // Program State
        mData[27] = "0";
        mData[28] = "0";
        mData[29] = "0";
        mData[30] = "0";
        mData[31] = "0"; //GPS Communication Errors
        mData[32] = "0";
        mData[33] = "0";
        mData[34] = "0";
        mData[35] = "0";
        mData[36] = "0";
        mData[37] = "0";
        mData[38] = "0";
        mData[39] = "0";
        mData[40] = "0";
        mData[41] = "0";
        mData[42] = "0"; //Roll Rate KP
        mData[43] = "0";
        mData[44] = "0";
        mData[45] = "0";
        mData[46] = "0";
        mData[47] = "0";
        mData[48] = "0";
        mData[49] = "0";
        mData[50] = "0";
        mData[51] = "0";
        mData[52] = "0"; //Roll Angle KD
        mData[53] = "0";
        mData[54] = "0";
        mData[55] = "0";
        mData[56] = "0";
        mData[57] = "0";
        mData[58] = "0";
        mData[59] = "0";
        mData[60] = "0";
        mData[61] = "0";
        mData[62] = "0"; //Ground Speed KI
        mData[63] = "0";
        mData[64] = "0";
        mData[65] = "0";
        mData[66] = "0";
        mData[67] = "0";
        mData[68] = "0";
        mData[69] = "0";
        mData[70] = "0";
        mData[71] = "0";
        mData[72] = "0"; //Channel 8
        mData[73] = "0";
        mData[74] = "0";
        mData[75] = "0";
        mData[76] = "0";
        mData[77] = "0";
        mData[78] = "0";
        mData[79] = "0";
        mData[80] = "0";
        mData[81] = "0";

        return mData;
    }

    void writeData() {
            String[] data = collectData();
            if (data != null) {
                writer.writeNext(data);
            }
    }


}
