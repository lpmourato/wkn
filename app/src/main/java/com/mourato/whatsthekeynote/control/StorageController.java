package com.mourato.whatsthekeynote.control;

import android.app.Activity;

import com.mourato.whatsthekeynote.model.Mode;
import com.mourato.whatsthekeynote.model.Player;
import com.mourato.whatsthekeynote.model.StorageInfo;



public class StorageController extends StorageInfo {

	public StorageController(Activity activity, Mode mode) {
		super(activity, mode);
	}

	public void savePlayerControl(Player player) {
		Player localPlayer = getPlayer();
		if (Mode.Player.equals(super.mode) || (player.getSingleScore() > localPlayer.getSingleScore()
				|| player.getHardScore() > localPlayer.getHardScore())) {
			super.savePlayer(player);
		}
	}

	public boolean isEquals(Player player){
		Player localPlayer = getPlayer();
		return Mode.Player.equals(super.mode) && player.getName().equalsIgnoreCase(localPlayer.getName());
	}

	public boolean isPlayer(){
		return "PLAYER".equalsIgnoreCase(getPlayer().getName());
	}
	@Override
	public Player getPlayer() {
		return super.getPlayer();
	}
	
	@Override
	public void savePublishScore(boolean paramBoolean) {
		super.savePublishScore(paramBoolean);
	}
	
	@Override
	public boolean getPublishScore() {
		return super.getPublishScore();
	}

	@Override
	public void saveLatitudeLongitude(double latitude, double longitude) {
		super.saveLatitudeLongitude(latitude, longitude);
	}
	
	@Override
	public double getLatitude() {
		return super.getLatitude();
	}
	
	@Override
	public double getLongitude() {
		return super.getLongitude();
	}
}