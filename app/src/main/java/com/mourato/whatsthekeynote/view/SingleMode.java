package com.mourato.whatsthekeynote.view;

import java.util.List;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.control.CustomAnimator;
import com.mourato.whatsthekeynote.control.SoundsControl;
import com.mourato.whatsthekeynote.control.StorageController;
import com.mourato.whatsthekeynote.control.ToastManager;
import com.mourato.whatsthekeynote.control.ViewUtils;
import com.mourato.whatsthekeynote.model.Instrument;
import com.mourato.whatsthekeynote.model.Mode;
import com.mourato.whatsthekeynote.model.Player;


public class SingleMode extends Activity {

	protected static int score = 0;
	protected Mode mode;
	private static boolean isFirstInstance = true;
	private static Player player;
	private Instrument instrument;
	private SoundsControl soundsControl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.activity_single_mode);
			this.mode = Mode.Single;
			this.instrument = Instrument.guitar;
			((ImageView) findViewById(R.id.imgGuitar))
					.setImageResource(R.drawable.button_menu_guitar_selected);
			this.soundsControl = new SoundsControl(this);
			loadPlayer();
			isFirstInstance = false;
			TextView localTextView = (TextView) findViewById(R.id.txtScore);
			score = 0;
			localTextView.setText(String.valueOf(0));
			String single = getIntent().getExtras().getString("show_msg");
			if("show_msg".equals(single)) {
				ToastManager.showToast(this, R.string.show_msg_touch_screen, Toast.LENGTH_SHORT);
			}
		} catch(Exception ex) {
			ToastManager.showToast(this, R.string.error_open, Toast.LENGTH_LONG);
			finish();
		}
		changeButtonsFont();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.single_mode, menu);
		return true;
	}

	@Override
	protected void onDestroy() {
		if(this.soundsControl != null) {
			savePlayerScore();
			this.soundsControl.stop();
		}
		super.onDestroy();
		System.gc();
	}

	@Override
	protected void onPause() {
		savePlayerScore();
		this.soundsControl.stop();
		if(!Mode.Hear.equals(mode)){
			//set toggle button to false when the app come back 
			((ToggleButton)findViewById(R.id.toggleButton2)).setChecked(false);
			//enable the instrument in center screen
			((LinearLayout)findViewById(R.id.instrument)).setEnabled(true);
		}
		super.onPause();
		System.gc();
	}

	protected void changeButtonsFont(){
		TableLayout table = (TableLayout)findViewById(R.id.table);
		List<Button> list = ViewUtils.getButtonsList(table);
		for(int i = 0; i < list.size(); i++){
			Button button = list.get(i);
			button.setTextColor(Color.parseColor("#C5CAE9"));
			button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
			button.setText(button.getText().toString().toUpperCase());
		}
	}
	
	public void clickWhatKeyNote(View view) {
		
		Button buttonKeyPressed = (Button) findViewById(view.getId());
		if(Mode.Hear.equals(mode)){
			this.soundsControl.play(instrument, mode, buttonKeyPressed.getContentDescription().toString().replace("#", "S"));
		}else {
			String key = ((TextView)findViewById(R.id.txtNote)).getText().toString().toUpperCase().replace("S", "#");
			//verify if the default text is already setted in hidded TextView
			if("NOTE".equalsIgnoreCase(key)){
			    ToastManager.showToast(this, R.string.show_msg_touch_screen, Toast.LENGTH_SHORT);			    
				return;
			}
			
			 Button button = (Button)view;
			 if (this.soundsControl.match(buttonKeyPressed.getContentDescription().toString().replace("#", "S"))) {
				score++;
				((TextView) findViewById(R.id.txtScore)).setText(String.valueOf(score));
				button.setBackgroundResource(R.drawable.button_key_note_right);
				changeButtonAnswer(buttonKeyPressed, button);
			} else {
			    button.setBackgroundResource(R.drawable.button_key_note_wrong);
				TableLayout table = (TableLayout)findViewById(R.id.table);
				int id = ViewUtils.getButton(table, key);
				Button correctButton = (Button)findViewById(id);
				changeButtonAnswer(correctButton, button);
			}
		}
	}

	private void changeButtonAnswer(final Button button, final Button wrongButton) {
		CustomAnimator.zoomInZoomOut(button, 750).addListener(new AnimatorListener() {
			@Override
			public void onAnimationStart(Animator animation) {
			}
			@Override
			public void onAnimationRepeat(Animator animation) {
			}
			@Override
			public void onAnimationEnd(Animator animation) {
				button.setBackgroundResource(R.drawable.selector_button_keys);
				wrongButton.setBackgroundResource(R.drawable.selector_button_keys);
			}
			@Override
			public void onAnimationCancel(Animator animation) {
			}
		});
	}
	
	public void play(View view) {
		ToggleButton toggleButton = (ToggleButton)findViewById(R.id.toggleButton2);
		boolean autoPlay = toggleButton.isChecked();
		LinearLayout imgButton = (LinearLayout) findViewById(R.id.instrument);
		
		if(autoPlay && imgButton.getTag().equals("play")){
			imgButton.setTag("stop");
			soundsControl.autoPlay(instrument, mode);
		} else if(autoPlay && imgButton.getTag().equals("stop")){
			stop(imgButton);
		} else {
			soundsControl.play(instrument, mode);
		}

	}

	public void choseInstrument(View view) {
		ImageView localImageView = (ImageView) view;
		if(!Mode.Hear.equals(mode))
			((RelativeLayout)findViewById(R.id.background)).setEnabled(true);
		unSelectButtons();
		if(!Mode.Hear.equals(mode))
			stop(view);
		switch (localImageView.getId()) {
			case R.id.imgAll:
				this.instrument = Instrument.all;
				localImageView.setImageResource(R.drawable.button_menu_all_selected);
				choseBackground(R.drawable.selector_bg_all);
				break;
			case R.id.imgBass:
				this.instrument = Instrument.bass;
				localImageView.setImageResource(R.drawable.button_menu_bass_selected);
				choseBackground(R.drawable.selector_bg_bass);
				break;
			case R.id.imgPiano:
				this.instrument = Instrument.piano;
				localImageView.setImageResource(R.drawable.button_menu_piano_selected);
				choseBackground(R.drawable.selector_bg_piano);
				break;
			case R.id.imgGuitar:
			default:
				this.instrument = Instrument.guitar;
				localImageView.setImageResource(R.drawable.button_menu_guitar_selected);
				choseBackground(R.drawable.selector_bg_guitar);
				break;

		}
	}

	private void choseBackground(int background) {
		LinearLayout relativeLayout = (LinearLayout) findViewById(R.id.instrument);
		try {
			relativeLayout.setBackgroundResource(background);
			return;
		} catch (OutOfMemoryError localOutOfMemoryError) {
//			Log.e("choseBackground: ", String.valueOf(R.string.insuficient_memory));
		}
	}

	private void stop(View imageButton) {
		imageButton.setTag("play");
		((ToggleButton) findViewById(R.id.toggleButton2)).setChecked(false);
		this.soundsControl.stop();
	}

	public void toggleStopSound(View view){
		ToggleButton toggole = (ToggleButton)findViewById(R.id.toggleButton2);
		LinearLayout background = ((LinearLayout)findViewById(R.id.instrument));
		if(toggole.isChecked()){
			background.setTag("stop");
			background.setEnabled(false);
			this.soundsControl.autoPlay(instrument, mode);
			((TextView)findViewById(R.id.txt_msg)).setVisibility(View.INVISIBLE);
		} else {
			background.setTag("play");
			background.setEnabled(true);
			this.soundsControl.stop();
			((TextView)findViewById(R.id.txt_msg)).setVisibility(View.VISIBLE);
		}
	} 
	
	private void unSelectButtons() {
		if (!Mode.Hear.equals(this.mode))
			((ImageView) findViewById(R.id.imgAll))
					.setImageResource(R.drawable.button_menu_all);
		((ImageView) findViewById(R.id.imgBass))
				.setImageResource(R.drawable.button_menu_bass);
		((ImageView) findViewById(R.id.imgGuitar))
				.setImageResource(R.drawable.button_menu_guitar);
		((ImageView) findViewById(R.id.imgPiano))
				.setImageResource(R.drawable.button_menu_piano);
	}

	/**
	 * Player info
	 **/
	private void loadPlayer() {
		if ((isFirstInstance) && (!Mode.Hear.equals(this.mode)))
			player = new StorageController(this, this.mode).getPlayer();
	}

	private void savePlayerScore() {
		if (!Mode.Hear.equals(this.mode) && player != null) {
			StorageController localStorageController = new StorageController(this, this.mode);
			if(Mode.Single.equals(mode))
				player.setSingleScore(score);
			if(Mode.Hard.equals(mode))
				player.setHardScore(score);
			localStorageController.savePlayerControl(player);
		}
	}
	
	public void changeBg(View view){
		play(view);
	}
	
	public void doNothing(View view){
	}
}
