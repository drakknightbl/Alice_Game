package com.alu.alice_game.server;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.alu.alice_game.domain.Player;
import com.alu.alice_game.server.SimpleQueue;
import java.lang.String;


public class MultiPlayerAwsSupportImpl implements MultiPlayerSupport {
	// Used to store the List of all queues
	//String masterQueue = "aluGameMasterQueue";
	List<String> listOfAllQueues;
	//Queue of Owner's
	String ownerQueue;
	
	//Queue of Guest
	String guestQueue;
	
	
	MultiPlayerAwsSupportImpl(){
		this("Player 1", "Player2");
	}
	
	public MultiPlayerAwsSupportImpl(String player1, String player2){
		try{
			//checks if (masterQueue exists)
			//SimpleQueue.createQueue(masterQueue);
			//else create Master Queue
			
			ownerQueue = "aluGameQueue_" + player1;
			//create Owner Queue
			SimpleQueue.createQueue(ownerQueue);
			
			guestQueue = "aluGameQueue_" + player2;
			//search for Guest Queue
			// If none return error/cannot find player message screen.
			listOfAllQueues = SimpleQueue.getQueueUrls();
			if(listOfAllQueues.contains(guestQueue)){
				System.out.print("Player's Queue has been found");
			} else{
				System.out.print("Waiting for Guest Player to be online");
			}

		}
		catch(Throwable e){
			
		}
		
		
	}
	@Override
	public Collection<Player> getOnlinePlayers() {
		Player player1 = new Player();
		player1.setName("player_1");
		player1.setURL(ownerQueue);
		
		Player player2 = new Player();
		player2.setName("player_2");
		player2.setURL(guestQueue);
		
		Collection<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		
		return players;
		
	}

	@Override
	public void sendMessage(Player player, String message) {
		SimpleQueue.sendMessage(player.getURL(), message);

	}

	@Override
	public String checkForMessage(Player player) {
		
		return null;
	}

}
