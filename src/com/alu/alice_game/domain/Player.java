package com.alu.alice_game.domain;

import java.io.Serializable;
import android.widget.TextView;

/**
 * Class represents a single player object
 * 
 * @author david
 *
 */
public class Player implements Serializable {
	
	private static final long serialVersionUID = 3615386179984502167L;
	
	private String name;
	
	//stores their score so far
	private int score;
	
	//view to print score to
	public TextView scoreboard;
	
	//Queue URL of Player
	private String queueURL;

	//ALUM number Account
	private String alumNumber;
	
	public Player(){
		this("John Doe");
	}
	public Player(String name){
		this.name = name;
		score = 0;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getScore(){
		return score; 
	}
	
	public void setScore (int n){
		score =n;
                this.updateScoreboard();
	}
	//resets score of the player to 0
	public void resetScore(){
		score = 0;
		this.updateScoreboard();
	}

        public void setScoreBoard(TextView scoreboard) {
            this.scoreboard = scoreboard;
            this.resetScore();
        }

	// Updates the score on the screen
	public void updateScoreboard(){
		scoreboard.setText(name + ": " + score );
	}
	
	public String getURL(){
		return queueURL;
	}
	public void setURL(String url){
		queueURL = url;
	}
	
	public String getNumber(){
		return alumNumber;
	}
	
	public void setNumber(String number){
		alumNumber = number;
	}

}
