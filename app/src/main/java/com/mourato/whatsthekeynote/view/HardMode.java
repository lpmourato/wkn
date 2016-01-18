package com.mourato.whatsthekeynote.view;

import android.os.Bundle;
import android.widget.ImageView;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.model.Mode;

public class HardMode extends SingleMode {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hard_mode);
		this.mode = Mode.Hard;
		((ImageView)findViewById(R.id.imgGuitar)).setImageResource(R.drawable.button_menu_guitar_selected);
		super.changeButtonsFont();
	}

}
