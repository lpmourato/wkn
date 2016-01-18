package com.mourato.whatsthekeynote.model;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import android.annotation.SuppressLint;

@SuppressLint("UseSparseArrays")
public class RepositoryNotes {

	private static RepositoryNotes instance;
	private static Map<Integer,String> mapBass;
	private static Map<Integer,String> mapGuitar;
	private static Map<Integer,String> mapPiano;
	
	@SuppressWarnings("serial")
	private static Map<Integer, Instrument> mapInstrument = new HashMap<Integer, Instrument>(){
		{
			put(0, Instrument.all);
			put(1, Instrument.bass);
			put(2, Instrument.guitar);
			put(3, Instrument.piano);
		}
	};
	private RepositoryNotes(){
		mapBass = new HashMap<Integer, String>();
		mapGuitar = new HashMap<Integer, String>();
		mapPiano = new HashMap<Integer, String>();
	}
	
	public static RepositoryNotes getInstance(){
		if(instance == null){
			instance = new RepositoryNotes();
			fillMapBass();
			fillMapGuitar();
			fillMapPiano();
		}
		return instance;
	}

	private static void fillMapBass() {
		mapBass.put(0,"bass/a");
		mapBass.put(1,"bass/as");
		mapBass.put(2,"bass/b");
		mapBass.put(3,"bass/b");
		mapBass.put(4,"bass/cs");
		mapBass.put(5,"bass/cs");
		mapBass.put(6,"bass/ds");
		mapBass.put(7,"bass/ds");
		mapBass.put(8,"bass/f");
		mapBass.put(9,"bass/fs");
		mapBass.put(10,"bass/g");
		mapBass.put(11,"bass/gs");
		
		mapBass.put(12,"bass/a1");
		mapBass.put(13,"bass/as1");
		mapBass.put(14,"bass/b1");
		mapBass.put(15,"bass/c1");
		mapBass.put(16,"bass/cs1");
		mapBass.put(17,"bass/d1");
		mapBass.put(18,"bass/ds1");
		mapBass.put(19,"bass/e1");
		mapBass.put(20,"bass/f1");
		mapBass.put(21,"bass/fs1");
		mapBass.put(22,"bass/g1");
		mapBass.put(23,"bass/gs1");
		
		mapBass.put(24,"bass/a2");
		mapBass.put(25,"bass/as2");
		mapBass.put(26,"bass/b2");
		mapBass.put(27,"bass/c2");
		mapBass.put(28,"bass/cs2");
		mapBass.put(29,"bass/d2");
		mapBass.put(30,"bass/ds2");
		mapBass.put(31,"bass/e2");
		mapBass.put(32,"bass/f2");
		mapBass.put(33,"bass/fs2");
		mapBass.put(34,"bass/g2");
		mapBass.put(35,"bass/gs2");
		mapBass.put(36,"bass/c28");
	}
	
	private static void fillMapGuitar(){
		mapGuitar.put(0,"guitar/a1");
		mapGuitar.put(1,"guitar/as1");
		mapGuitar.put(2,"guitar/b1");
		mapGuitar.put(3,"guitar/b1");
		mapGuitar.put(4,"guitar/cs");
		mapGuitar.put(5,"guitar/cs");
		mapGuitar.put(6,"guitar/ds");
		mapGuitar.put(7,"guitar/e");
		mapGuitar.put(8,"guitar/f");
		mapGuitar.put(9,"guitar/fs");
		mapGuitar.put(10,"guitar/g");
		mapGuitar.put(11,"guitar/gs");
		
		mapGuitar.put(12,"guitar/a1");
		mapGuitar.put(13,"guitar/as1");
		mapGuitar.put(14,"guitar/b1");
		mapGuitar.put(15,"guitar/c1");
		mapGuitar.put(16,"guitar/cs1");
		mapGuitar.put(17,"guitar/d1");
		mapGuitar.put(18,"guitar/ds1");
		mapGuitar.put(19,"guitar/e1");
		mapGuitar.put(20,"guitar/f1");
		mapGuitar.put(21,"guitar/fs1");
		mapGuitar.put(22,"guitar/g1");
		mapGuitar.put(23,"guitar/gs1");
		
		mapGuitar.put(24,"guitar/a2");
		mapGuitar.put(25,"guitar/as2");
		mapGuitar.put(26,"guitar/b2");
		mapGuitar.put(27,"guitar/c2");
		mapGuitar.put(28,"guitar/cs2");
		mapGuitar.put(29,"guitar/d2");
		mapGuitar.put(30,"guitar/ds2");
		mapGuitar.put(31,"guitar/e2");
		mapGuitar.put(32,"guitar/f2");
		mapGuitar.put(33,"guitar/fs2");
		mapGuitar.put(34,"guitar/g2");
		mapGuitar.put(35,"guitar/gs2");
		
		mapGuitar.put(36,"guitar/a3");
		mapGuitar.put(37,"guitar/c3");
		mapGuitar.put(38,"guitar/cs3");
		mapGuitar.put(39,"guitar/d3");
		mapGuitar.put(40,"guitar/ds3");
		mapGuitar.put(41,"guitar/e3");
		mapGuitar.put(42,"guitar/f3");
		mapGuitar.put(43,"guitar/fs3");
		mapGuitar.put(44,"guitar/g3");
		mapGuitar.put(45,"guitar/gs3");
	}
	
	private static void fillMapPiano(){
		mapPiano.put(0,"piano/a");
		mapPiano.put(1,"piano/as");
		mapPiano.put(2,"piano/b");
		mapPiano.put(3,"piano/c");
		mapPiano.put(4,"piano/cs");
		mapPiano.put(5,"piano/d");
		mapPiano.put(6,"piano/ds");
		mapPiano.put(7,"piano/e");
		mapPiano.put(8,"piano/f");
		mapPiano.put(9,"piano/fs");
		mapPiano.put(10,"piano/g");
		mapPiano.put(11,"piano/gs");
		
		mapPiano.put(12,"piano/a1");
		mapPiano.put(13,"piano/as1");
		mapPiano.put(14,"piano/b1");
		mapPiano.put(15,"piano/c1");
		mapPiano.put(16,"piano/cs1");
		mapPiano.put(17,"piano/d1");
		mapPiano.put(18,"piano/ds1");
		mapPiano.put(19,"piano/e1");
		mapPiano.put(20,"piano/f1");
		mapPiano.put(21,"piano/fs1");
		mapPiano.put(22,"piano/g1");
		mapPiano.put(23,"piano/gs1");
		
		mapPiano.put(24,"piano/a2");
		mapPiano.put(25,"piano/as2");
		mapPiano.put(26,"piano/b2");
		mapPiano.put(27,"piano/c2");
		mapPiano.put(28,"piano/cs2");
		mapPiano.put(29,"piano/d2");
		mapPiano.put(30,"piano/ds2");
		mapPiano.put(31,"piano/e2");
		mapPiano.put(32,"piano/f2");
		mapPiano.put(33,"piano/fs2");
		mapPiano.put(34,"piano/g2");
		mapPiano.put(35,"piano/gs2");
	}
	
	public Map<Integer, String> getNotePath(Instrument instrument){
		switch (instrument) {
			case all:
				return getAnyInstrument();
			case bass:
				return mapBass;
			case guitar:
				return mapGuitar;
			case piano:
			default:
				return mapPiano;
		}
	}

	private Map<Integer, String>  getAnyInstrument() {
		int rand = new Random().nextInt(mapInstrument.size());
		Instrument instrument = mapInstrument.get(rand);
		return getNotePath(instrument);
	}
}
