package com.qiyi;

/**
 * Created by TWINKLE on 2017/11/21.
 */

import java.io.UnsupportedEncodingException;

public class Protect
{
    public static final int GPad = 2;
    public static final int GPhone = 0;
    public static final int GPhone_game_live_app = 4;
    public static final int GPhone_sdk = 1;
    public static final int GPhone_trd_lenovo_app = 3;

    static
    {
        System.loadLibrary("protect");
    }

    public static String getContent(Object paramObject, int paramInt, String paramString1, String paramString2)
    {
        return getContentJNI(paramObject, paramInt, paramString1, paramString2);
    }

    public static String getContent2(Object paramObject, String paramString1, String paramString2, String paramString3, String paramString4)
    {
        return getContentJNI2(paramObject, paramString1, paramString2, paramString3, paramString4);
    }

    private static native String getContentJNI(Object paramObject, int paramInt, String paramString1, String paramString2);

    private static native String getContentJNI2(Object paramObject, String paramString1, String paramString2, String paramString3, String paramString4);

    private static native int getEmuInfo(Object paramObject);

    public static int getEmud(Object paramObject)
    {
        return getEmuInfo(paramObject);
    }

    public static byte[] getQddc(Object paramObject, byte[] paramArrayOfByte)
    {
        return getQddcJNI(paramObject, paramArrayOfByte);
    }

    private static native byte[] getQddcJNI(Object paramObject, byte[] paramArrayOfByte);

    public static String getQdsc(Object paramObject, String paramString)
    {
        try
        {
            paramObject = getQdscJNI(paramObject, paramString.getBytes("UTF-8"));
            return (String)paramObject;
        }
        catch (UnsupportedEncodingException e) {}
        return "";
    }

    private static native String getQdscJNI(Object paramObject, byte[] paramArrayOfByte);

    public static String getQdtm(Object paramObject, String paramString1, String paramString2, String paramString3)
    {
        return getQdtmJNI(paramObject, paramString1, paramString2, paramString3);
    }

    private static native String getQdtmJNI(Object paramObject, String paramString1, String paramString2, String paramString3);

    public static String getQdvf(Object paramObject, String paramString1, String paramString2)
    {
        return getQdvfJNI(paramObject, paramString1, paramString2);
    }

    private static native String getQdvfJNI(Object paramObject, String paramString1, String paramString2);
}
