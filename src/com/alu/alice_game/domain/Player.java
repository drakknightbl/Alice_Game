package com.alu.alice_game.domain;

import java.io.Serializable;

/**
 * Class represents a single player object
 * 
 * @author david
 *
 */
public class Player implements Serializable {
	
	private static final long serialVersionUID = 3615386179984502167L;
	
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
