package com.mourato.whatsthekeynote.adapters;



import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.model.Player;

public class PlayerAdapter extends ArrayAdapter<Player> {
	public PlayerAdapter(Context paramContext, List<Player> list) {
		super(paramContext, 0, list);
	}

	public View getView(int position, View view, ViewGroup viewGroup) {
		Player player = (Player) getItem(position);
		if (view == null) {
			view = LayoutInflater.from(getContext()).inflate(R.layout.layout_line_score, null);
		}
//		ImageView avatar = (ImageView)view.findViewById(R.id.img_avatar);
//		avatar.setBackground(player.getAvatar());
		TextView txtName = (TextView) view.findViewById(R.id.txtName);
		txtName.setText(player.getName());
		TextView txtScore = (TextView) view.findViewById(R.id.txtScore);
		txtScore.setText(String.valueOf(player.getScore()));

		return view;
	}
}