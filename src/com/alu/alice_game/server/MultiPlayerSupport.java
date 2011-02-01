package com.alu.alice_game.server;

import java.util.Collection;

import android.content.Context;

import com.alu.alice_game.domain.Player;

public interface MultiPlayerSupport {
	
	/**
	 * Checks cloud whether any users are online
	 * 
	 * @return collection of users
	 */
	Collection<Player> getOnlinePlayers();
	

	/**
	 * Sends a message to a player
	 * 
	 * @param player - player
	 * @param message - a message to send
	 * 
	 */
	public void sendMessage(Player player, String message);
	
	/**
	 * Checks whether there is a new message from the player
	 * 
	 * @param player - player
	 * @return message if available, o/w null
	 */
	public void checkForMessage(Context ctx);

}
