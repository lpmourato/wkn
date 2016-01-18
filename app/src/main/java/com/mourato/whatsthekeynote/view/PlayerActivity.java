package com.mourato.whatsthekeynote.view;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.control.ConnectionController;
import com.mourato.whatsthekeynote.control.ConnectionManager;
import com.mourato.whatsthekeynote.control.StorageController;
import com.mourato.whatsthekeynote.control.ToastManager;
import com.mourato.whatsthekeynote.model.Mode;
import com.mourato.whatsthekeynote.model.Player;
import com.mourato.whatsthekeynote.model.RepositoryAvatars;


public class PlayerActivity extends Activity implements OnClickListener, TextWatcher {
	protected static final int SELECTED_AVATAR = 100;
    private StorageController control;
	private Player player;
	private SendPlayerTask task;
	private static boolean hasSent = false;
	private Drawable backgroundButton;
	private EditText edtName;
	private Button btnSaveSubmit;
	private ImageView avatar;
	private boolean isAvatarChanged;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_player);
		this.control = new StorageController(this, Mode.Player);
		findViewById(R.id.btn_save_submit).setOnClickListener(this);
//		avatar = (ImageView)findViewById(R.id.img_player);
//		final Intent intent = new Intent(this, GalleryAvatarsActivity.class);
//		avatar.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Bundle bundle = new Bundle();
//                bundle.putInt("SELECTED_AVATAR", player.getIdAvatar());
//                intent.putExtra("SELECTED_AVATAR", bundle);
//                startActivityForResult(intent, SELECTED_AVATAR);
//            }
//        });
		edtName = (EditText) findViewById(R.id.edtPlayerName);
		edtName.addTextChangedListener(this);
		btnSaveSubmit = (Button) findViewById(R.id.btn_save_submit);
		loadPlayer();
		backgroundButton = getResources().getDrawable(R.drawable.button_send_score);
		btnSaveSubmit.setBackground(backgroundButton);
	}
	
	protected void onActivityResult(int requestCod, int resultCode, Intent data) {
	    int idAvatar = 0;
	    if(data!= null){
	        idAvatar = data.getBundleExtra("SELECTED_AVATAR").getInt("SELECTED_AVATAR");
	    }
        if(resultCode == SELECTED_AVATAR && player.getIdAvatar() != idAvatar){
            int res = RepositoryAvatars.getInstance().getAvatarHalf(idAvatar);
            player.setIdAvatar(idAvatar);
            avatar.setBackground(getApplicationContext().getResources().getDrawable(res));
            btnSaveSubmit.setBackground(getResources().getDrawable(R.drawable.button_save_score));
            isAvatarChanged = true;
        }
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_save) {
			savePlayer();
		}
		else if (item.getItemId() == android.R.id.home) {
			finish();
		}
		return super.onOptionsItemSelected(item);
	}

	private void loadPlayer() {
	    isAvatarChanged = false;
		player = control.getPlayer();
		edtName.setText(player.getName());
		((TextView) findViewById(R.id.btn_single)).setText(String
				.valueOf(player.getSingleScore()));
		((TextView) findViewById(R.id.btn_hard)).setText(String
				.valueOf(player.getHardScore()));
		((ImageView)findViewById(R.id.img_player)).setBackground(player.getAvatar());
	}

	private void savePlayer() {
		String name = ((EditText) findViewById(R.id.edtPlayerName)).getText().toString();
		if (match(name)) {
			showMessage(name);
		} else {
			ToastManager.showToast(this, R.string.msg_invalid_name, Toast.LENGTH_SHORT);
		}
	}

	private boolean match(String name){
		return name.matches("^[A-Z]{3,}+[0-9_A-Z]*");
	}
	
	private void showMessage(final String name) {
		DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface paramAnonymousDialogInterface,
					int option) {
				switch (option) {
				case AlertDialog.BUTTON_POSITIVE:
			        if(changedScreenName()){
			            control.savePublishScore(false);
			            player.setName(name);
			        }
					control.savePlayerControl(player);
					break;
				default:
				case AlertDialog.BUTTON_NEGATIVE:
					ToastManager.showToast(getApplicationContext(), R.string.operation_canceled, Toast.LENGTH_SHORT);
					break;
				}
				loadPlayer();
			}
		};
		AlertDialog alert = new AlertDialog.Builder(this)
				.setTitle(R.string.replace_name).setMessage(R.string.msg_replace_name)
				.setPositiveButton("Yes", listener)
				.setNegativeButton("No", listener).create();
		alert.show();
	}

	public void clickSend(View view) {
		String name = player.getName();
		if((changedScreenName() || isAvatarChanged) && (match(name) && !control.isEquals(player))){
			ToastManager.showToast(getApplicationContext(), R.string.msg_save_player_name, Toast.LENGTH_SHORT);
		} else if (ConnectionManager.getInstance(this).hasConnection()){
			hasPlayer();
		}
	}

	//FALTA ATUALIZAR O PLAYER PELO ENDEREÇO DA URL
	
	@SuppressLint("DefaultLocale")
	private void sendScore(String create) {
		if(ConnectionManager.getInstance(this).hasConnection()) {
			String name = player.getName();
			int singleScore = player.getSingleScore();
			int hardScore = player.getHardScore();
			int avatar = player.getIdAvatar();
			task = new SendPlayerTask();
			String address = "";
			String add = String.format(ConnectionController.getAddress(), "?name=%s&single=%d&hard=%d&avatar=%d&create=yes");
			if(create != null) {
				address = String.format(add, name, singleScore, hardScore, avatar);
				control.savePublishScore(true);
			} else {
			    add = String.format(ConnectionController.getAddress(), "?name=%s&single=%d&hard=%d&avatar=%d");
				address = String.format(add, name, singleScore, hardScore, avatar);
			}
			task.execute(address);
		}
	}

	private boolean changedScreenName(){
		String name = ((EditText)findViewById(R.id.edtPlayerName)).getText().toString();
		if(!player.getName().equals(name)){
			return true;
		}
		return false;
	}
	
	private void hasPlayer(){
		if(ConnectionManager.getInstance(this).hasConnection()) {
			hasSent = control.getPublishScore();
			String name = player.getName();
			task = new SendPlayerTask();
			String add = String.format(ConnectionController.getAddress(), "?hasplayer=%s");
			String address = String.format(add, name);
			task.execute(address);
		}
	}
	
	class SendPlayerTask extends AsyncTask<String, Void, String> {
		
		SendPlayerTask() {
		}

		private String streamToString(InputStream paramInputStream)
				throws IOException {
			byte[] arrayOfByte = new byte[1024];
			ByteArrayOutputStream localByteArrayOutputStream = new ByteArrayOutputStream();
			while (true) {
				int i = paramInputStream.read(arrayOfByte);
				if (i <= 0)
					return new String(localByteArrayOutputStream.toByteArray());
				localByteArrayOutputStream.write(arrayOfByte, 0, i);
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			btnSaveSubmit.setEnabled(false);
		}

		@Override
		protected String doInBackground(String... url) {
			try {
				HttpURLConnection http = (HttpURLConnection) new URL(url[0])
						.openConnection();
				http.setRequestMethod("GET");
				http.setDoInput(true);
				http.setConnectTimeout(15000);
				http.connect();
				if (http.getResponseCode() == 200) {
					String result = streamToString(http.getInputStream());
					return result;
				}
			} catch (MalformedURLException ex) {
				ex.printStackTrace();
				return null;
			} catch (IOException ex1) {
				ex1.printStackTrace();
			}
			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			result = result.replace("\n", "");
			if ("success".equalsIgnoreCase(result)) {
				ToastManager.showToast(getApplicationContext(), R.string.success_update, Toast.LENGTH_SHORT);
			} else if ("1".equals(result) && hasSent){
				sendScore(null);
			} else if ("1".equals(result) && !hasSent && !changedScreenName()){
				ToastManager.showToast(getApplicationContext(), R.string.already_exists, Toast.LENGTH_SHORT);
			} else if ("1".equals(result) && !hasSent && changedScreenName()){
				ToastManager.showToast(getApplicationContext(), R.string.msg_save_player_name, Toast.LENGTH_SHORT);
			}  else if ("0".equals(result) && !hasSent){
				sendScore("create");
			} else {
				ToastManager.showToast(getApplicationContext(), R.string.error_update, Toast.LENGTH_SHORT);
			}
			btnSaveSubmit.setEnabled(true);
		}
	}

	@Override
	public void onClick(View v) {
		Drawable bg = v.getBackground();
		if(bg.equals(backgroundButton)){
			btnSaveSubmit.setBackground(getResources().getDrawable(R.drawable.button_send_score_selected));
			clickSend(v);
		} else if(changedScreenName() || isAvatarChanged){
			savePlayer();
			btnSaveSubmit.setBackground(backgroundButton);
		}
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		if(changedScreenName()){
			btnSaveSubmit.setBackground(getResources().getDrawable(R.drawable.button_save_score));
		}
	}

	@Override
	public void afterTextChanged(Editable s) {
//		btnSaveSubmit.setBackground(backgroundButton);
	}
	
}
