package com.mourato.whatsthekeynote.view;


import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Debug;
import android.util.TypedValue;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.control.ViewUtils;

public class MenuApp extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu_app);
//		Parse.initialize(this, "dIGdLY4YJF9uwSaT6Kd5EYQvsAwFyLLWZ0l0s00A", "Esgfcpad4Ib1UOxw95FOnV67cKuVy4mE8CIl7Pxd");
		//verifyIfLocationIsSaved();
		if(Debug.isDebuggerConnected()){
		    ((TextView)findViewById(R.id.txt_debugging_mode)).setVisibility(View.VISIBLE);
		}
		//TODO: remove this method
		changeButtonsFont();
	}

	/*
	private void verifyIfLocationIsSaved() {
		StorageController storage = new StorageController(this, Mode.Player);
		if(storage.getLatitude() == 0 && storage.getLongitude() == 0){
			MyLocationManager ml = new MyLocationManager(this);
			Location location = ml.getLocation();
			
			
		}
	}
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.menu_app, menu);
		return true;
	}

	public void clickHardMode(View paramView) {
		Bundle bundle = new Bundle();
		bundle.putString("show_msg", "show_msg");
		Intent intent = new Intent(this, HardMode.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void clickHearMode(View paramView) {
		Bundle bundle = new Bundle();
		bundle.putString("show_msg", "");
		Intent intent = new Intent(this, HearMode.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}

	public void clickMenu(View paramView) {
	}

	public void clickPlayer(View paramView) {
		 startActivity(new Intent(this, PlayerActivity.class));
	}

	public void clickRecords(View paramView) {
		 startActivity(new Intent(this, Score.class));
	}

	public void clickSingleMode(View paramView) {
		Bundle bundle = new Bundle();
		bundle.putString("show_msg", "show_msg");
		Intent intent = new Intent(this, SingleMode.class);
		intent.putExtras(bundle);
		startActivity(intent);
	}
	
	public void showAbout(View paramView) {
		startActivity(new Intent(this, About.class));
	}
	
	public void doNothing(View view){
	}
	
	//TODO: remove this method
	protected void changeButtonsFont(){
		TableLayout table = (TableLayout)findViewById(R.id.table);
		List<Button> list = ViewUtils.getButtonsList(table);
		for(int i = 0; i < list.size(); i++){
			Button button = list.get(i);
			button.setTextColor(Color.parseColor("#C5CAE9"));
			button.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
		}
	}
}
