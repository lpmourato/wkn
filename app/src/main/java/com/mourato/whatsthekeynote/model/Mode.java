package com.mourato.whatsthekeynote.model;

public enum Mode {

	Single("single"),
	Hard("hard"),
	Hear("hear"), 
	Player("player");
	
	private String mode;
	private Mode(String mode) {
		this.mode = mode;
	}
	
	public Mode getMode(String mode) {
		for (Mode m : Mode.values()) {
			if(mode.contains(m.toString().toLowerCase())){
				return m;
			}
		}
		return null;
	}
}
