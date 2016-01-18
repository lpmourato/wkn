package com.mourato.whatsthekeynote.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mourato.whatsthekeynote.R;
import com.mourato.whatsthekeynote.model.RepositoryAvatars;

public class GalleryAvatarsActivity extends FragmentActivity implements OnClickListener {

    private ViewPager viewPager;
    private int currentItem;
    private int amount = 0;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_gallery);
		((Button)findViewById(R.id.btn_first)).setOnClickListener(this);
		((Button)findViewById(R.id.btn_back)).setOnClickListener(this);
		((Button)findViewById(R.id.btn_select)).setOnClickListener(this);
		((Button)findViewById(R.id.btn_next)).setOnClickListener(this);
		((Button)findViewById(R.id.btn_end)).setOnClickListener(this);
		currentItem = this.getIntent().getBundleExtra("SELECTED_AVATAR").getInt("SELECTED_AVATAR");
		viewPager = (ViewPager)findViewById(R.id.pager);
        FragmentManager fm = getSupportFragmentManager();
        AvatarFragmentPagerAdapter pagerAdapter = new AvatarFragmentPagerAdapter(fm);
        
		viewPager.setAdapter(pagerAdapter);
		viewPager.setCurrentItem(currentItem);
		amount = viewPager.getAdapter().getCount()-1;
	}
	
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_first:
                currentItem = 0;
                break;
            case R.id.btn_back:
                currentItem = currentItem > 0 ? --currentItem : currentItem;
                break;
            case R.id.btn_select:
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("SELECTED_AVATAR", currentItem);
                intent.putExtra("SELECTED_AVATAR", bundle);
                setResult(PlayerActivity.SELECTED_AVATAR, intent);
                finish();
                break;
            case R.id.btn_next:
                currentItem = currentItem < amount ? ++currentItem : currentItem;
                break;
            case R.id.btn_end:
                currentItem = amount;
                break;
            default:
                break;
        }
        viewPager.setCurrentItem(currentItem);
        
    }
    
    
    /**
     * Fragments
     * @author lpm
     * @version 1.0
     * @created Mar 2, 2015
     */
    public class AvatarFragment extends Fragment{
        int idAvatar;
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle data = getArguments();
            idAvatar = data.getInt("current_image", 0);
        }
     
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.layout_avatar, container,false);
            ImageView avatar = (ImageView)v.findViewById(R.id.id_avatar);
            avatar.setBackground(getApplicationContext().getResources().getDrawable(idAvatar));
            return v;
        }
    }
    
    public class AvatarFragmentPagerAdapter extends FragmentPagerAdapter{
        int list[];
        final int PAGE_COUNT = RepositoryAvatars.getInstance().getAvatarsHalf().length;
     
        public AvatarFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
            list = RepositoryAvatars.getInstance().getAvatarsHalf();
        }
     
        @Override
        public Fragment getItem(int position) {
     
            AvatarFragment myFragment = new AvatarFragment();
            Bundle data = new Bundle();
            currentItem = viewPager.getCurrentItem();
            data.putInt("current_image", list[position]);
            myFragment.setArguments(data);
            return myFragment;
        }
     
        @Override
        public int getCount() {
            return PAGE_COUNT;
        }
    }
}
