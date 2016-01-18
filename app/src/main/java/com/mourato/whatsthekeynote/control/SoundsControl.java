package com.mourato.whatsthekeynote.control;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.model.Instrument;
import com.mourato.whatsthekeynote.model.Mode;
import com.mourato.whatsthekeynote.model.Sounds;


public class SoundsControl extends Sounds {

	private static String keyNote;
	private static String actualNote = "";
	private Handler handler;
	private Timer timer;
	private TimerTask timerTask;
	private Instrument beforeInstrument;
	
	public SoundsControl(Activity activity) {
		super(activity);
		this.timer = new Timer();
		this.handler = new Handler();
	}

	public void play(Instrument instrument, Mode mode, String... keyName) {
		String path = "";
		if(Mode.Hear.equals(mode) && keyName != null) {
			path = String.format("%s/%s2.%s", instrument, keyName[0], "mp3");
		} else if(Mode.Hard.equals(mode)){
			path = ControlRepositoryNotes.getInstance().getNotePath(instrument, mode);
			keyNote = rename(path);
		} else if((beforeInstrument == null || !instrument.equals(beforeInstrument)) || "".equals(actualNote)){
			beforeInstrument = instrument;
			actualNote = path = ControlRepositoryNotes.getInstance().getNotePath(instrument, mode);  
			keyNote = rename(path);
		} else if(Mode.Single.equals(mode)){
			path = actualNote;
		}
		if (!Mode.Hear.equals(mode)) {
			TextView localTextView = (TextView) this.activity.findViewById(R.id.txtNote);
			localTextView.setText(keyNote);
		}
		
		if(path != null)
			super.play(instrument, mode, path.toLowerCase());
	}
	
	public void autoPlay(final Instrument instrument, final Mode mode) {
		try {
			if (this.timerTask == null) {
				this.timerTask = new TimerTask() {
					public void run() {
						handler.post(new Runnable() {
							public void run() {
								play(instrument, mode);
							}
						});
					}
				};
				this.timer.schedule(this.timerTask, 1000L, 2000L);
			}
			return;
		} catch (IllegalStateException localIllegalStateException) {
			ToastManager.showToast(super.activity, R.string.error_play_sound, Toast.LENGTH_SHORT);
		}
	}
	
	private String rename(String paramString) {
		return paramString.replaceAll("([a-z]*/)([a-z]*)([1-3]?)(.mp3)", "$2");
	}
	
	public boolean match(String paramString) {
        boolean bool = paramString.equalsIgnoreCase(keyNote);
        keyNote = "";
        actualNote = "";
        return bool;
	}
	
	public void stop() {
		if ((this.timerTask != null) && (super.mediaPlayer != null)) {
			this.timerTask.cancel();
			this.timerTask = null;
			super.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
					mp.stop();
				}
			});
		}
	}
	
}
