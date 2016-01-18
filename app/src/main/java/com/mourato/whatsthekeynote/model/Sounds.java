package com.mourato.whatsthekeynote.model;

import android.app.Activity;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;

public class Sounds {
	protected Activity activity;
	protected MediaPlayer mediaPlayer;
	private AudioManager am;

	public Sounds(Activity paramActivity) {
		this.activity = paramActivity;
		this.am = (AudioManager)this.activity.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
	}

	public void play(Instrument instrument, Mode mode, String... keyName) {
		try {
			
			float streamVolume = am.getStreamVolume(AudioManager.STREAM_MUSIC);
			streamVolume /= am.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
			AssetFileDescriptor afd = this.activity.getAssets().openFd(keyName[0]);
			this.mediaPlayer = new MediaPlayer();
			this.mediaPlayer.setVolume(streamVolume, streamVolume);
			this.mediaPlayer.setDataSource(afd.getFileDescriptor(),afd.getStartOffset(),afd.getLength());
			this.mediaPlayer.prepare();
			this.mediaPlayer.start();
			this.mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
				public void onCompletion(MediaPlayer mp) {
					mp.release();
				}
			});
		} catch (Exception e) {
			//this.mediaPlayer.release();
//			Log.e("Error playing sound", e.getMessage());
		}
	}

}