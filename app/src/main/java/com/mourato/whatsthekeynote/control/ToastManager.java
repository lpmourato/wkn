package com.mourato.whatsthekeynote.control;

import android.content.Context;
import android.widget.Toast;

/**
 * 
 * @author nglauber(http://nglauber.blogspot.com.br/2011/10/android-dicas-5.html)
 *
 */
public class ToastManager {

	private static Toast toast;
	public static void showToast(Context ctx, int res, int time) {
		if (toast != null) {
			toast.cancel();
			toast.setText(res);
		} else {
			toast = Toast.makeText(ctx, res, time);
		}
		toast.show();
		toast = null;
	}
}
