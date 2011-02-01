package com.alu.alice_game.server;

import java.util.Collection;


import java.util.ArrayList;
import java.util.Hashtable;

import com.alu.alice_game.domain.Player;
import com.alu.alice_game.Config;

import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;


import android.content.Context;
import android.util.Log;

public class MultiPlayerServerSupportImpl {
	
	private HttpRequest hr = new HttpRequest();
	
    public Collection<Player> getOnlinePlayers() {
        Log.i("MultiPlayerServerSupportImpl", "getOnlinePlayers");
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

    public void sendMessage(String type, Player player, String message) {
        Log.i("MultiPlayerServerSupportImpl", "Message \"" + message + "\" is sent to " + player.getName() + " ip 192.168.1.104:8084");
        
        List<NameValuePair> nvp = new ArrayList<NameValuePair>(1);
        nvp.add(new BasicNameValuePair("message", message));
        this.hr.post("http://cf.conversationboard.com/alice_"+type, nvp);
         
    }


    public void checkForMessage(Context ctx, String type) {
        Log.i("MultiPlayerServerSupportImpl", "check for message");
        
        this.hr.get("http://cf.conversationboard.com/alice_"+type, ctx); 
    }

    
	public void onFinish() {
		Log.i("MultiPlayerServerSupportImpl", "finalize");
		hr.onFinish();
	}

}
