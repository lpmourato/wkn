package com.mourato.whatsthekeynote.model;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class Player
{
	private String name;
	private int singleScore;
	private int hardScore;
	private int score;
	private Drawable avatar;
	private int idAvatar;
	private Context context;
	public Player(Context context, String paramString, int singleScore, int hardScore)
	{
		this.context = context;
		this.name = paramString;
		this.singleScore= singleScore;
		this.hardScore= hardScore;
	}

	public String getName()
	{
		return this.name;
	}

	public int getSingleScore()
	{
		return this.singleScore;
	}

	public int getHardScore()
	{
		return this.hardScore;
	}

	public void setName(String paramString)
	{
		this.name = paramString;
	}

	public void setSingleScore(int score)
	{
		this.singleScore= score;
	}

	public void setHardScore(int score)
	{
		this.hardScore= score;
	}
	
	public void setScore(int score)
	{
		this.score= score;
	}
	
	public int getScore()
	{
		return this.score;
	}	

	public Drawable getAvatar() {
	    if(idAvatar == 0){
	        return avatar = context.getResources().getDrawable(RepositoryAvatars.getInstance().getAvatarHalf(0)); 
	    }
	    avatar = context.getResources().getDrawable(RepositoryAvatars.getInstance().getAvatarHalf(idAvatar));
		return avatar;
	}

	public int getIdAvatar() {
		return idAvatar;
	}

	public void setIdAvatar(int idAvatar) {
	    if(idAvatar != 0){
	        int res = RepositoryAvatars.getInstance().getAvatar(idAvatar);
	        setIdAvatarDrawable(res);
	    }
        this.idAvatar = idAvatar;
    }
	
	public void setIdAvatarDrawable(int idDrawble) {
		avatar = context.getResources().getDrawable(idDrawble);
	}

	public String toString()
	{
		return this.name;
	}
}