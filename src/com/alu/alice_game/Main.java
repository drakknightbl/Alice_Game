package com.alu.alice_game;

import java.util.ArrayList;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.media.MediaPlayer;

public class Main extends Activity {
	
	//private static int IMAGE_POSITION_TAG = 0;
	
	private ArrayList<Integer> image_array = new ArrayList<Integer>();
	
	private ArrayList<Integer> random_image_array = new ArrayList<Integer>();
	private ArrayList<Integer> print_image_array = new ArrayList<Integer>();
	
	private boolean isSender; // true is sender false is receiver, ie. sender sets the order
	
	private int score;
	
	private TextView myScore;
	private Button sound_optn;
	private ImageButton button0;
	private ImageButton button1;
	private ImageButton button2;
	private ImageButton playAgainBtn;
	private ImageButton tryAgainBtn;
	private ImageView image_0;
	private ImageView image_1;
	private ImageView image_2;
	private int button0_image = R.drawable.cartoon0;
	private int button1_image = R.drawable.cartoon1;
	private int button2_image = R.drawable.cartoon2;
	private int inactive_image = R.drawable.cartoon3;
	
	private MediaPlayer background_music;
	
    /** Called when the activity is first created. */
	private int numOfRounds = 3;
	private ArrayList<Integer> genOrder(){
		ArrayList<Integer> random_image_array = new ArrayList<Integer>();
		Random r = new Random();
		Log.i("genOrder()", "before while");
		while(image_array.size() > 0){
			int rand_num = r.nextInt(image_array.size());
			random_image_array.add( image_array.get(rand_num));
			image_array.remove(rand_num);
			Log.i("Main","Random number is " + rand_num);
		}
		return random_image_array;
	}//genOrder
	
	// starts the animation of three characters and destroys print_image_array
	private void startAnimation(){
		//ArrayList<Integer> image_array_copy = random_image_array;
		ImageView image = (ImageView) findViewById(print_image_array.get(0));
		print_image_array.remove(0);
		Animation move = AnimationUtils.loadAnimation(Main.this, R.anim.z_move_1);
		move.setAnimationListener(new AnimListener());
		image.startAnimation(move);
		
		Log.i("Main","onAnimtionEnd");
	}// startAnimation
	
	
	//Checks for End of animation and starts the next animation.
	private class AnimListener implements Animation.AnimationListener{

		@Override
		public void onAnimationEnd(Animation animation) {
			// TODO Auto-generated method stub
			if( print_image_array.size() > 0 ){
			Main.this.startAnimation();
			}
			else{

				button0.setVisibility(View.VISIBLE);
				button1.setVisibility(View.VISIBLE);
				button2.setVisibility(View.VISIBLE);
				 // Instructions for Game
		        Toast greeting_instructions = Toast.makeText(getApplicationContext(), "Click In the Same Order", Toast.LENGTH_SHORT);
		        greeting_instructions.setGravity(Gravity.CENTER, 0, -50);
		        greeting_instructions.show();
		       
			}
			Log.i("Main", "onAnimationEnd()");
		}

		@Override
		public void onAnimationRepeat(Animation animation) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onAnimationStart(Animation animation) {
			// TODO Auto-generated method stub
			
		}
		
	}// AnimListener
	
	//resets the buttons state to active
	private void reset_button(){
		button0.setClickable(true);
		button1.setClickable(true);
		button2.setClickable(true);
		button0.setImageResource(button0_image);
		button1.setImageResource(button1_image);
		button2.setImageResource(button2_image);
		button0.setVisibility(View.INVISIBLE);
		button1.setVisibility(View.INVISIBLE);
		button2.setVisibility(View.INVISIBLE);
	}// reset_buttons
	
	//resets the game
	private void reset(){
		score = 0;
		myScore.setText("Score:" + score);
		numOfRounds =3;
		random_image_array.clear();
		this.reset_button();
		this.gameSetUpPerRound();
	}//reset
	
	
	// Use to start each round of the game and creates a random order 
	private void gameSetUpPerRound(){
		// Win Game is rounds == 0
		if(numOfRounds == 0){
			//announce win screen
			Toast toast = Toast.makeText(getApplicationContext(), "You Win!", Toast.LENGTH_SHORT);
			toast.setGravity(Gravity.CENTER, 0, -50);
			toast.show();
			// launch play again window
			Animation fade_in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
	        fade_in.setAnimationListener(new Animation.AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					playAgainBtn.setClickable(false);
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					playAgainBtn.setVisibility(View.VISIBLE);
					playAgainBtn.setClickable(true);
					// TODO Auto-generated method stub
					
				}
			});
	        playAgainBtn.startAnimation(fade_in);
	        background_music.stop();
	        
			Log.i("gameSetUpPerRound", "Win");
		}// if
		else{
			image_array.add( new Integer(R.id.image0));
	    	image_array.add( new Integer(R.id.image1));
	        image_array.add( new Integer(R.id.image2));
	        random_image_array = this.genOrder();
	        for(int i = 0; i < random_image_array.size(); i++){
	        	print_image_array.add(random_image_array.get(i));
	        }// for
	        this.startAnimation();
	        if(numOfRounds == 3){
	        Toast round = Toast.makeText(getApplicationContext(), "Starting Round 1", Toast.LENGTH_SHORT);
	        round.setGravity(Gravity.CENTER, 0, -50);
	        round.show();
	        }// if
	        numOfRounds--;
		}// else
		Log.i("Main", "gameSetUpPerRound");
	}// gameSetUpPerRound
	
	
	// Used to determine whether the clicks are right or wrong
	private class myOnClick implements View.OnClickListener {

		Main m;
		ImageButton button;
		
		public myOnClick(Main m, ImageButton button){
			this.m = m;
			this.button = button;
		}

		@Override
		public void onClick(View v) {
			Integer image_position_pressed = (Integer) v.getTag(R.string.image_position_tag);
			// Below is Code for player to follow sequence
			Integer image_position_current = random_image_array.get(0);
			if(image_position_pressed.equals(image_position_current)) {
				//disable button
				button.setClickable(false);
				button.setImageResource(inactive_image);
				//increase score
				score ++;
				myScore.setText("Score:" + score);
				random_image_array.remove(0);
				Log.i("Main", "right");
				if(random_image_array.size() == 0){
					//end current round
					String continue_msg ="\nStarting Round " + (4 - numOfRounds);
					if(numOfRounds == 0){
						continue_msg = "";
					}
					Toast nextround = Toast.makeText(getApplicationContext(), "Round Complete" + continue_msg, Toast.LENGTH_SHORT);
					nextround.setGravity(Gravity.CENTER, 0, -50);
					nextround.show();
					//update score
					myScore.setText("Score:" + score);
					for(int i = 0; i < 1000000 ; i++);
					// start next round
					m.gameSetUpPerRound();
					m.reset_button();
				}//if
			} else {
				// game over screen
				Toast toast = Toast.makeText(getApplicationContext(), "Game Over\nScore:" + score, Toast.LENGTH_LONG);
				toast.setGravity(Gravity.CENTER, 0, -50);
				toast.show();
				// launch try again
				m.reset_button();
				Animation fade_in = AnimationUtils.loadAnimation(m, R.anim.fade_in);
				fade_in.setAnimationListener(new Animation.AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						tryAgainBtn.setClickable(false);
						
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						tryAgainBtn.setVisibility(View.VISIBLE);
						tryAgainBtn.setClickable(true);
					}
				});
				tryAgainBtn.startAnimation(fade_in);
				Log.i("Main", "wrong");
			}
			// TODO Auto-generated method stub
			
		}
		
	}// myOnClick
	
	//To Reset the game when lost or win.
	private class myPlayAgainClickListener implements View.OnClickListener{
		Main m;
		myPlayAgainClickListener(Main m){
			this.m = m;
		}
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			playAgainBtn.setVisibility(View.INVISIBLE);
			tryAgainBtn.setVisibility(View.INVISIBLE);
			playAgainBtn.setClickable(false);
			tryAgainBtn.setClickable(false);
			m.reset();
		}
		
	}// myPlayAgainClickListener
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        score = 0; // set the basic score to 0
        // create base set of image id
        
        setContentView(R.layout.main);
        //Initialization
        this.myScore = (TextView) findViewById(R.id.score_text);
        this.image_0 = (ImageView) findViewById(R.id.image0);
        this.image_1 = (ImageView) findViewById(R.id.image1);
        this.image_2 = (ImageView) findViewById(R.id.image2);
        sound_optn = (Button) findViewById (R.id.sound_optn);
        button0 = (ImageButton) findViewById(R.id.button0);
        button1 = (ImageButton) findViewById(R.id.button1);
        button2 = (ImageButton) findViewById(R.id.button2);
        playAgainBtn = (ImageButton) findViewById(R.id.play_again_img);
        playAgainBtn.setClickable(false);
        tryAgainBtn = (ImageButton) findViewById(R.id.try_again_img);
        tryAgainBtn.setClickable(false);
        // hide images
        image_0.setVisibility(View.INVISIBLE);
        image_1.setVisibility(View.INVISIBLE);
        image_2.setVisibility(View.INVISIBLE);
        // hide buttons
		button0.setVisibility(View.INVISIBLE);
		button1.setVisibility(View.INVISIBLE);
		button2.setVisibility(View.INVISIBLE);
        
		background_music = MediaPlayer.create(getApplicationContext(), R.raw.freeze_ray);
		background_music.setLooping(true);
		background_music.start();
        this.gameSetUpPerRound();
        
        playAgainBtn.setOnClickListener(new myPlayAgainClickListener(this));
        tryAgainBtn.setOnClickListener(new myPlayAgainClickListener(this));
	   
        //Sound Options
        
        sound_optn.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// Check Sound Options
				try{
					if(background_music.isPlaying()){
						background_music.pause();
						//unmute image
					}//if
					else{
						background_music.start();
						//mute image
					}//else
				} catch(IllegalStateException e){
					
				}//catch
			}
		});
        
        // 3 Image Buttons
		button0.setTag(R.string.image_position_tag, new Integer(R.id.image0));
		button0.setOnClickListener(new myOnClick(this, button0));
		    	      	   
		    	   /*
		    	   button0.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						Integer image = R.id.image0;
						if( image.equals(random_image_array.get(0))){
							// disable button
							button0.setClickable(false);
							
							// increase score
							score++;
							myScore.setText("Score: " + score);
							//remove element
							random_image_array.remove(0);
							Log.i("Button0", "Right");
						}//if
						else{
							score--;
							myScore.setText("Score:" + score);
							//Lose Animation
							//display Try Again Text
							//random_image_array.clear();
							Log.i("Button0", "Wrong");
						}//else
						// TODO Auto-generated method stub
						
					}//onClick
		    	   });// setOnClickListener
		    	   */
		    	   
		button1.setTag(R.string.image_position_tag, new Integer(R.id.image1));
		button1.setOnClickListener(new myOnClick(this, button1));
		    	  
		button2.setTag(R.string.image_position_tag, new Integer(R.id.image2));
		button2.setOnClickListener(new myOnClick(this, button2));
		    	
		    	
        Log.i("Main", "onCreate");
    }// onCreate
    
    
    public void onStop(){
    	//Stops background music if it is playing.
    	try{
    	if(background_music.isPlaying()){
    	background_music.stop();
    	}//if
    	}catch (IllegalStateException e){
    	}
    	// must call otherwise will cause errors
    	super.onStop();
    }//onStop
}//Main