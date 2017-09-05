package ca.uwaterloo.ece.warg.datagenerator;

/**
 * Created by cphajduk on 20/08/17.
 */
import android.util.Log;

import java.io.FileWriter;


public class CSVWriter {
    FileWriter mFW;
    CSVWriter(FileWriter fw) {
        mFW = fw;
    }

    void writeNext(String[] data) {
        try {
            for (int i = 0; i<data.length - 1; i++) {
                String str = data[i];
                if (str != null) {
                    mFW.write(str);
                }
                mFW.write(',');
            }
            if (data[data.length - 1] != null) {
                mFW.write(data[data.length - 1]);
            }
            mFW.write('\n');
            mFW.flush();
        }catch(java.io.IOException ex){
            Log.e("Error", "IO Error");
        }
    }

    void close(){
        try {
            mFW.close();
        } catch (java.io.IOException ex){
            Log.e("Error","IO Error");
        }
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        this.close();
    }

}
