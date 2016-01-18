package com.mourato.whatsthekeynote.view;

import com.mourato.whatsthekeynote.R;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;

public class ButtonShape extends Button {

	public ButtonShape(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		setBackground(getResources().getDrawable(R.drawable.button_avatar_shape));
	}

	
	
}
