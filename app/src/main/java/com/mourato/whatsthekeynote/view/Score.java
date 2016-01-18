package com.mourato.whatsthekeynote.view;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.Menu;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.json.PlayerListFragment;


public class Score extends Activity {

	private PlayerListFragment listScores;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score);
		this.listScores = ((PlayerListFragment) getFragmentManager().findFragmentByTag("singleFrag"));
		Bundle params = new Bundle();
		params.putString("single","single");
		listScores = PlayerListFragment.initialize(this);
		listScores.setArguments(params);
		
		getFragmentManager().beginTransaction()
		.add(R.id.contentTabs, listScores, "singleFrag")
		.commit();
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    return super.onCreateOptionsMenu(menu);
	}


	@Override
	public void onAttachFragment(Fragment fragment) {
		super.onAttachFragment(fragment);
	}

}

