package com.mourato.whatsthekeynote.view;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.control.CustomAnimator;
import com.mourato.whatsthekeynote.model.Mode;


public class HearMode extends SingleMode {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hear_mode);
		this.mode = Mode.Hear;
		((ImageView)findViewById(R.id.imgGuitar)).setImageResource(R.drawable.button_menu_guitar_selected);
		super.changeButtonsFont();
	}
	
	@Override
	public void clickWhatKeyNote(View view) {
	    super.clickWhatKeyNote(view);
	    CustomAnimator.zoomInZoomOut(view, 700);
	}
	
}
