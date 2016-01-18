package com.mourato.whatsthekeynote.control;



import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.View;

public class CustomAnimator {

    
    private static final float MAX_ZOOM = 1.2F;
    private static final float MIN_ZOOM = 1F;

	public static AnimatorSet zoomInZoomOut(View view, int duration) {
        AnimatorSet aninSet = new AnimatorSet();
        Animator zoonInX = ObjectAnimator.ofFloat(view, "scaleX", MIN_ZOOM, MAX_ZOOM);
        Animator zoonInY = ObjectAnimator.ofFloat(view, "scaleY", MIN_ZOOM, MAX_ZOOM);
        
        Animator zoonOutX = ObjectAnimator.ofFloat(view, "scaleX", MAX_ZOOM, MIN_ZOOM);
        Animator zoonOutY = ObjectAnimator.ofFloat(view, "scaleY", MAX_ZOOM, MIN_ZOOM);

        List<Animator> zoomInList = new ArrayList<Animator>();
        zoomInList.add(zoonInX);
        zoomInList.add(zoonInY);
        
        List<Animator> zoomOutList = new ArrayList<Animator>();
        zoomOutList.add(zoonOutX);
        zoomOutList.add(zoonOutY);
        
        aninSet.playTogether(zoomInList);
        aninSet.playTogether(zoomOutList);
        aninSet.setDuration(duration);
        aninSet.start();
        return aninSet;
    }
}
