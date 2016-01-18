package com.mourato.whatsthekeynote.control;

import java.util.Map;
import java.util.Random;

import com.mourato.whatsthekeynote.model.Instrument;
import com.mourato.whatsthekeynote.model.Mode;
import com.mourato.whatsthekeynote.model.RepositoryNotes;


public class ControlRepositoryNotes {

	private static ControlRepositoryNotes instance;

	  public static ControlRepositoryNotes getInstance()
	  {
	    if (instance == null)
	      instance = new ControlRepositoryNotes();
	    return instance;
	  }

	  public String getNotePath(Instrument instrument, Mode mode, String... keyName)
	  {
	    try
	    {
	      Map<Integer,String> localMap = RepositoryNotes.getInstance().getNotePath(instrument);
	      String path = (String)localMap.get(Integer.valueOf(new Random().nextInt(localMap.size()-1))) + ".mp3";
	      if(Mode.Single.equals(mode) && matchNaturalKeyNote(path)){
	    	  return getNotePath(instrument, mode);
	      }
	      return path;
	    }
	    catch (Exception ex)
	    {
	      //Log.e(String.valueOf(R.string.msg_error_get_path_note), ex.getMessage());
	    }
	    return null;
	  }

	  private boolean matchNaturalKeyNote(String paramString) {
			return paramString.matches("[a-z/]+[a-g]+([s]+[0-4]?).mp3");
		}
}
