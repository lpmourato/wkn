package com.mourato.whatsthekeynote.model;

import com.mourato.whatsthekeynote.R;

public class RepositoryAvatars {

	private int[] avatars, avatars_half ;
	private static RepositoryAvatars instance;
	private RepositoryAvatars() {
		avatars = new int[23];
		avatars[0] = R.drawable.avatar;
		avatars[1] = R.drawable.bald_head;
		avatars[2] = R.drawable.bald_head_half;
		avatars[3] = R.drawable.beard_man;
		avatars[4] = R.drawable.beard_man_half;
		avatars[5] = R.drawable.black_power;
		avatars[6] = R.drawable.black_power_half;
		avatars[7] = R.drawable.man_long_shirt;
		avatars[8] = R.drawable.man_long_shirt_half;
		avatars[9] = R.drawable.punk_man;
		avatars[10] = R.drawable.punk_man_half;
		
		avatars[11] = R.drawable.blond_girl;
		avatars[12] = R.drawable.blond_girl_half;
		avatars[13] = R.drawable.blue_girl;
		avatars[14] = R.drawable.blue_girl_half;
		avatars[15] = R.drawable.nurse_girl;
		avatars[16] = R.drawable.nurse_girl_half;
		avatars[17] = R.drawable.pelican_girl;
		avatars[18] = R.drawable.pelican_girl_half;
		avatars[19] = R.drawable.power_girl;
		avatars[20] = R.drawable.power_girl_half;
		avatars[21] = R.drawable.short_hair;
		avatars[22] = R.drawable.short_hair_half;
		
		avatars_half = new int[11];
        avatars_half [0] = R.drawable.bald_head_half;
        avatars_half [1] = R.drawable.beard_man_half;
        avatars_half [2] = R.drawable.black_power_half;
        avatars_half [3] = R.drawable.man_long_shirt_half;
        avatars_half [4] = R.drawable.punk_man_half;
        
        avatars_half [5] = R.drawable.blond_girl_half;
        avatars_half [6] = R.drawable.blue_girl_half;
        avatars_half [7] = R.drawable.nurse_girl_half;
        avatars_half [8] = R.drawable.pelican_girl_half;
        avatars_half [9] = R.drawable.power_girl_half;
        avatars_half [10] = R.drawable.short_hair_half;
	}
	
	public static RepositoryAvatars getInstance() {
		if(instance == null){
			instance = new RepositoryAvatars();
		}
		return instance;
	}
	
//	public int[] getAvatars() {
//		return avatars;
//	}
	
	public int[] getAvatarsHalf() {
        return avatars_half;
    }
	
	public int getAvatar(int id) {
	    if(id < avatars.length){
	        return avatars[id];
	    }
		return avatars[0];
	}
	
	public int getAvatarHalf(int id) {
        return avatars_half[id];
    }
}
