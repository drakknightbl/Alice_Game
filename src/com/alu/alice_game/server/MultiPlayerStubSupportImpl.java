package com.alu.alice_game.server;

import java.util.ArrayList;
import java.util.Collection;

import android.content.Context;
import android.util.Log;

import com.alu.alice_game.domain.Player;

public class MultiPlayerStubSupportImpl implements MultiPlayerSupport {

	@Override
	public Collection<Player> getOnlinePlayers() {
		
		Player player1 = new Player();
		player1.setName("player_1");
		
		Player player2 = new Player();
		player2.setName("player_2");
		
		Collection<Player> players = new ArrayList<Player>();
		players.add(player1);
		players.add(player2);
		
		return players;
	}

	@Override
	public void sendMessage(Player player, String message) {
		System.out.println("Message \"" + message + "\" is sent to " + player.getName());
	}

	@Override
	public void checkForMessage(Context ctx) {
		Log.i("MultiPlayerStubbSupp", "message");
	}

}
