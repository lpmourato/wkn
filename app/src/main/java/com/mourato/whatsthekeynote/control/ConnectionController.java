package com.mourato.whatsthekeynote.control;

import android.os.Debug;

public class ConnectionController {

    public static final String ADDRESS = "";
    public static String LOCAL_ADDRESS = "http://172.28.148.46/wkn/%s";
    public static String REMOTE_ADDRESS = "http://wkn.orgfree.com/%s";
    public static String getAddress() {
        if(Debug.isDebuggerConnected()){
            return LOCAL_ADDRESS;
        }
        return REMOTE_ADDRESS;
    }
    
}
