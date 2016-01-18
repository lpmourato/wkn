package com.mourato.whatsthekeynote.control;

import com.mourato.whatsthekeynote.R;

import android.content.Context;
import android.net.ConnectivityManager;
import android.widget.Toast;

public class ConnectionManager {

	public static ConnectionManager instance;
	private ConnectivityManager cm;
	private Context context;
	
	private ConnectionManager(Context context) {
		this.context = context;
		cm = (ConnectivityManager)this.context.getSystemService(Context.CONNECTIVITY_SERVICE);
	}
	
	public static ConnectionManager getInstance(Context context){
		if(instance == null){
			instance = new ConnectionManager(context);
		}
		return instance;
	}
	
	public boolean hasConnection(){
		int wifi = ConnectivityManager.TYPE_WIFI;  
	    int mobile = ConnectivityManager.TYPE_MOBILE;  
		if (cm.getNetworkInfo(mobile).isConnected() ||  
		        cm.getNetworkInfo(wifi).isConnected()){
			return true;
		}
		ToastManager.showToast(this.context, R.string.msg_no_connection, Toast.LENGTH_SHORT);
		return false;
	}
}
