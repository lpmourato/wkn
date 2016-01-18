package com.mourato.whatsthekeynote.json;


import android.app.ListFragment;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.adapters.PlayerAdapter;
import com.mourato.whatsthekeynote.control.ConnectionController;
import com.mourato.whatsthekeynote.control.ConnectionManager;
import com.mourato.whatsthekeynote.control.StorageController;
import com.mourato.whatsthekeynote.control.ToastManager;
import com.mourato.whatsthekeynote.model.Mode;
import com.mourato.whatsthekeynote.model.Player;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class PlayerListFragment extends ListFragment implements OnClickListener{
	
    private static List<Player> listSingleMode, listHardMode;
	private ProgressBar progress;
	protected PlayerTask taskSingle, taskHard;
	private String mode;
	private Context context;
	private TextView title, txtMsgError, txtName, txtScore;
	private Player player;
//	private ImageView avatar;

	public static PlayerListFragment initialize(Context context) {
		context = context;
		listSingleMode = new ArrayList<Player>();
		listHardMode = new ArrayList<Player>();
		return new PlayerListFragment();
	}
//	private PlayerListFragment(Context context) {
//        this.context = context;
//        listSingleMode = new ArrayList<Player>();
//        listHardMode = new ArrayList<Player>();
//    }
	
	public View onCreateView(LayoutInflater paramLayoutInflater,
			ViewGroup paramViewGroup, Bundle paramBundle) {
		View localView = paramLayoutInflater.inflate(R.layout.fragment_list, null);
		title = (TextView)localView.findViewById(R.id.title);
		txtMsgError = (TextView)localView.findViewById(R.id.txt_msg);
//		avatar = (ImageView)localView.findViewById(R.id.img_player);
		this.progress = ((ProgressBar) localView.findViewById(R.id.progressBar1));
		this.progress.setVisibility(View.VISIBLE);
		txtName = (TextView)localView.findViewById(R.id.txt_player_name);
		txtScore = (TextView)localView.findViewById(R.id.txt_score);
		mode = getArguments().getString("single");
		loadPlayerScore();
		((ToggleButton)localView.findViewById(R.id.btn_change_view)).setOnClickListener(this);
		return localView;
	}
	
	public void onActivityCreated(Bundle paramBundle) {
		super.onActivityCreated(paramBundle);
		setRetainInstance(true);
		if (ConnectionManager.getInstance(getActivity()).hasConnection()) {
			if (this.taskSingle == null)
				execute();
			if (this.taskSingle.getStatus() == AsyncTask.Status.RUNNING) 
				this.progress.setVisibility(View.VISIBLE);
			return;
		}
		this.progress.setVisibility(View.INVISIBLE);
	}

	private void execute() {
		this.taskSingle = new PlayerTask();
	    if(mode != null){
	        String address = ConnectionController.getAddress();
	    	this.taskSingle.execute(String.format(address, "?mode=single.json"));
	    }
	}


	private void loadPlayerScore() {
		player = new StorageController(getActivity(), Mode.Player).getPlayer();
		txtName.setText(player.getName());
        txtScore.setText(String.valueOf(player.getSingleScore()));
//        avatar.setBackground(player.getAvatar());
	}

	 @Override
    public void onClick(View v) {
	    PlayerAdapter playerAdapter = null;
	    if("single".equalsIgnoreCase(mode)){
	        playerAdapter = new PlayerAdapter(getActivity(), listHardMode);
	        this.title.setText(R.string.title_activity_hard_score);
	        txtScore.setText(String.valueOf(player.getHardScore()));
	        mode = "hard";
	    } else {
	        playerAdapter = new PlayerAdapter(getActivity(), listSingleMode);
	        this.title.setText(R.string.title_activity_single_score);
	        txtScore.setText(String.valueOf(player.getSingleScore()));
	        mode = "single";
	    }
        setListAdapter(playerAdapter);
    }

	class PlayerTask extends AsyncTask<String, Void, List<Player>> {
        PlayerTask() {}

		private List<Player> getListPlayers(InputStream inputStream) {
			
			try {
				JSONArray array = new JSONArray(streamToString(inputStream));
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonPlayer = array.getJSONObject(i);
					Player player = new Player(context, jsonPlayer.getString("name"), 0, 0);
					int avatar = 0;
					Object av = jsonPlayer.get("avatar");
					if(av != null && !"null".equalsIgnoreCase(av.toString())){
					    avatar = Integer.valueOf(av.toString());
					}
					player.setIdAvatar(avatar);
					
					player.setScore(jsonPlayer.getInt("score"));
					if("single".equalsIgnoreCase(mode)){
					    listSingleMode.add(player);
					} else {
					    listHardMode.add(player);
					}
				}
				txtMsgError.setVisibility(View.INVISIBLE);
			} catch (Exception e) {
				ToastManager.showToast(getActivity(), R.string.msg_error_getting_score_list, Toast.LENGTH_SHORT);
//				Log.e("Error getListPlayers: ", e.getMessage());
				cancelRequest();
			}
			return listSingleMode;
		}

		private void cancelRequest() {
			if(taskSingle != null)
				taskSingle.cancel(true);
			if(taskHard != null)
                taskHard.cancel(true);
			if(progress != null) {
			    getActivity().runOnUiThread(new Runnable() {
                    public void run() {
                        progress.clearAnimation();
                        progress.setVisibility(View.INVISIBLE);
                        txtMsgError.setVisibility(View.VISIBLE);
                    }
                });
			}
		}

		private String streamToString(InputStream is) throws IOException {
			byte[] bytes = new byte[1024];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int readed;
			while ((readed = is.read(bytes)) > 0) {
				baos.write(bytes, 0, readed);
			}
			return new String(baos.toByteArray());
		}

		@Override
		protected List<Player> doInBackground(String... url) {
			try {
				HttpURLConnection conn = (HttpURLConnection) new URL(url[0]).openConnection();
				conn.setRequestMethod("GET");
				conn.setDoInput(true);
				conn.setConnectTimeout(10000);
				conn.connect();
				if (conn.getResponseCode() == 200) {
					List<Player> list= getListPlayers(conn.getInputStream());
					return list;
				}
			} catch (Exception ex) {
				cancelRequest();
//				Log.e("Error doInBackground: ", ex.getMessage());
				return null;
			}
			return null;
		}

		@Override
		protected void onPostExecute(List<Player> list) {
			super.onPostExecute(list);
			try {
			    boolean isSingleModeList = "single".equalsIgnoreCase(mode);
				if (list != null && isSingleModeList) {
					PlayerAdapter playerAdapter = new PlayerAdapter(getActivity(), list);
					setListAdapter(playerAdapter);
				} 
	            if(isSingleModeList){
	                taskHard = new PlayerTask();
	                String address = ConnectionController.getAddress();
	                taskHard.execute(String.format(address, "?mode=hard.json"));
	                mode = "hard";
	            } else {
	                mode = "single";
	            }
	            getListView().setVisibility(View.VISIBLE);
	            progress.setVisibility(View.GONE);
			} catch (Exception ex) {
				Log.e(String.valueOf(R.string.msg_error_getting_score_list), ex.toString());
			}
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			getListView().setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
		}
	}
	
}