package com.alu.alice_game.server;

import java.util.Collection;
import java.util.ArrayList;

import com.alu.alice_game.domain.Player;
import com.alu.alice_game.Config;

public class MultiPlayerServerSupportImpl implements MultiPlayerSupport {

    @Override
    public Collection<Player> getOnlinePlayers() {
        Player player1 = new Player();
        if (Config.MY_PLAYER_TYPE == Config.CREATOR_PLAYER_TYPE) {
            player1.setName("Jake");
        } else { 
            player1.setName("Baby Sitter");
        }

        Player player2 = new Player();
        if (Config.MY_PLAYER_TYPE == Config.CREATOR_PLAYER_TYPE) {
            player2.setName("Baby Sitter");


        } else {
            player2.setName("Jake");
        }


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
    public String checkForMessage(Player player) {
            return "a new message	";
    }

}
