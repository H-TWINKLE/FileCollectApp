package com.twinkle.hfilm.java;

import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by TWINKLE on 2017/11/16.
 */

public class Util {

    //信息写入本地
    public static void writeFileSdcard(String fileName, byte[] message) {

        try {
            File fp = new File(Environment.getExternalStorageDirectory() + "/hf", fileName);
            FileOutputStream fos = new FileOutputStream(fp, true);
            byte[] buffer = message;
            fos.write(buffer);

            fos.close();


        } catch (Exception e) {

            e.printStackTrace();

        }

    }


    public static void copy(String filename, InputStream is) {
        try {

            String databaseFilename = Environment.getExternalStorageDirectory().getAbsolutePath() + "/hf/db/" + filename;
            if (!(new File(databaseFilename)).exists()) {
                FileOutputStream fos = new FileOutputStream(databaseFilename);
                byte[] buffer = new byte[8192];
                int count = 0;

                while ((count = is.read(buffer)) > 0) {
                    fos.write(buffer, 0, count);
                }
                fos.close();
                is.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static final String vf(String paramString) {
        Object localObject;
        try {

            byte[] arrayOfByte = MessageDigest.getInstance("MD5").digest(paramString.getBytes());
            int j = arrayOfByte.length;
            int i = 0;
            paramString = "";
            // Object localObject;
            for (; ; ) {
                localObject = paramString;
                if (i >= j) {
                    break;
                }
                String str = Integer.toHexString(arrayOfByte[i] & 0xFF);
                localObject = str;
                if (str.length() == 1) {
                    localObject = "0" + str;
                }
                paramString = paramString + (String) localObject;
                i += 1;
            }
            return (String) localObject;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            localObject = "";
        }

        return (String) localObject;
    }


}
