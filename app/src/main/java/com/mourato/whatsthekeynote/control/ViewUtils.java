package com.mourato.whatsthekeynote.control;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import android.view.View;
import android.widget.Button;

public class ViewUtils {
	
	public static List<Button> getButtonsList(View container) {
		List<Button> list = new ArrayList<Button>();
		List<View> views = container.getTouchables();
		for(View view : views){
			if(view instanceof Button){
				list.add((Button)view);
			}
		}
		return list;
	}
	
	public static int getButton(View container, String tag) {
		Button b = null;
		List<Button> list = getButtonsList(container);
		Iterator<Button> it = list.iterator();
		while(it.hasNext()){
			b = it.next();
			if(b.getText().toString().equalsIgnoreCase(tag))
				return b.getId();
		}
		return -1;
	}
}
