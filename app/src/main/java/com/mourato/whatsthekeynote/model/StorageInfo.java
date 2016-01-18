package com.mourato.whatsthekeynote.model;

import android.app.Activity;
import android.content.SharedPreferences;

public class StorageInfo {
	public static final String LOCAL_SCORE = "localScore";
	protected Activity activity;
	protected Mode mode;
	private static final String PLAYER_NAME = "playerName";
	private static final String SCORE_SINGLE_PLAYER = "scoreSinglePlayer";
	private static final String SCORE_HARD_PLAYER = "scoreHardPlayer";
	private static final String SEND_SCORE = "sendScore";
	private static final String LATITUDE = "latitude";
	private static final String LONGITUDE = "longitude";
	private static final String AVATAR = "avatar";
	private static final int DEFAULT_SCORE = 1;
	private SharedPreferences prefs;

	public StorageInfo(Activity activity) {
		this.activity = activity;
	}

	public StorageInfo(Activity activity, Mode mode) {
		this.activity = activity;
		this.mode = mode;
		this.prefs = this.activity.getSharedPreferences(LOCAL_SCORE, DEFAULT_SCORE);
	}

	protected boolean getPublishScore() {
		return this.activity.getSharedPreferences(SEND_SCORE, DEFAULT_SCORE).getBoolean(
				SEND_SCORE, false);
	}

	protected Player getPlayer() {
		Player player = new Player(this.activity, this.prefs.getString(PLAYER_NAME, "PLAYER"),
				this.prefs.getInt(SCORE_SINGLE_PLAYER, DEFAULT_SCORE), this.prefs.getInt(SCORE_HARD_PLAYER, DEFAULT_SCORE));
		int id = this.prefs.getInt(AVATAR, 0);
		player.setIdAvatar(id);
		player.setIdAvatarDrawable(RepositoryAvatars.getInstance().getAvatarHalf(id));
		return player;
	}

	protected void savePlayer(Player player) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.putString(PLAYER_NAME, player.getName());
		localEditor.putInt(AVATAR, player.getIdAvatar());
		if(Mode.Single.equals(mode))
			localEditor.putInt(SCORE_SINGLE_PLAYER, player.getSingleScore());
		if(Mode.Hard.equals(mode))
			localEditor.putInt(SCORE_HARD_PLAYER, player.getHardScore());
		localEditor.commit();
	}

	protected void savePublishScore(boolean send) {
		SharedPreferences.Editor localEditor = this.activity
				.getSharedPreferences(SEND_SCORE, DEFAULT_SCORE).edit();
		localEditor.putBoolean(SEND_SCORE, send);
		localEditor.commit();
	}
	
	protected void saveLatitudeLongitude(double latitude, double longitude) {
		SharedPreferences.Editor localEditor = this.prefs.edit();
		localEditor.putLong(LATITUDE, (long)latitude);
		localEditor.putLong(LONGITUDE, (long)longitude);
		localEditor.commit();
	}
	
	protected double getLatitude() {
		return this.prefs.getLong(LATITUDE, 0);
	}
	
	protected double getLongitude() {
		return this.prefs.getLong(LONGITUDE, 0);
	}
}