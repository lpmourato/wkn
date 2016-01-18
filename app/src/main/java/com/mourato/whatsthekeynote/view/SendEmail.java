package com.mourato.whatsthekeynote.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.control.ToastManager;

public class SendEmail extends Activity {
	private Spinner spinnerSubject;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_send_email);
		this.spinnerSubject = (Spinner)findViewById(R.id.spinnerSubject);
		List<String> list = new ArrayList<String>();
		list.add(getString(R.string.send_suggestion));
		list.add(getString(R.string.title_activity_send_email));
		list.add(getString(R.string.report_bug));
		ArrayAdapter<String> array = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
		this.spinnerSubject.setAdapter(array);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.send_email, menu);
		return true;
	}

	public void clickSendEmail(View view) {
		String msg = ((EditText)findViewById(R.id.editMessage)).getText().toString();
		if(msg.length() > 10) {
			Intent email = new Intent(Intent.ACTION_SEND);
			email.setData(Uri.parse("mailto:"));
			email.setType("text/plain");
			email.putExtra(Intent.EXTRA_EMAIL, new String[]{"whatsthekeynote@gmail.com"});
			String subject = this.spinnerSubject.getSelectedItem().toString();
			email.putExtra(Intent.EXTRA_SUBJECT, subject);
			email.putExtra(Intent.EXTRA_TEXT, msg);
			try {
				startActivity(Intent.createChooser(email, getString(R.string.send_email)));
				finish();
			}catch (Exception e){
				ToastManager.showToast(this, R.string.msg_error_send_message, Toast.LENGTH_SHORT);
			}
		} else {
			ToastManager.showToast(this, R.string.msg_fill_message, Toast.LENGTH_SHORT);
		}
	}
}
